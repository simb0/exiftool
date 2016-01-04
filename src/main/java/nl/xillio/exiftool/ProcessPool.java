package nl.xillio.exiftool;

import me.biesaart.utils.Log;
import nl.xillio.exiftool.process.ExifToolProcess;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * This class represents a pool that can run low level actions on available processes.
 *
 * @author Thomas Biesaart
 */
public class ProcessPool implements AutoCloseable {
    private static final Logger LOGGER = Log.get();

    /**
     * These are all the processes that have been built.
     */
    private List<ExifToolProcess> processes = new ArrayList<>();

    /**
     * These processes have been given away but not yet returned.
     */
    private List<ExifToolProcess> leasedProcesses = new ArrayList<>();
    private final Supplier<ExifToolProcess> processBuilder;

    public ProcessPool(Supplier<ExifToolProcess> processBuilder) {
        this.processBuilder = processBuilder;
    }

    public synchronized ExifTool getAvailable() {
        ExifToolProcess process = processes.stream()
                .filter(proc -> !leasedProcesses.contains(proc))
                .findAny()
                .orElse(null);

        if (process == null) {
            process = createNew();
        }

        leasedProcesses.add(process);

        LOGGER.info("Giving out {}", process);

        return new ExifTool(process, this::release);
    }

    private ExifToolProcess createNew() {
        LOGGER.info("Creating new exiftool process");
        ExifToolProcess process = processBuilder.get();
        processes.add(process);
        return process;
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Sonar doesn't do lambdas
    private void release(ExifToolProcess process) {
        LOGGER.info("Releasing {}", process);
        if (!process.isAvailable()) {
            // This process is still busy. Kill it
            processes.remove(process);
            process.close();
        }
        leasedProcesses.remove(process);
    }

    /**
     * Close all idle processes.
     */
    public void clean() {
        List<ExifToolProcess> processesToKill = processes.stream()
                .filter(proc -> !leasedProcesses.contains(proc))
                .filter(ExifToolProcess::isAvailable)
                .collect(Collectors.toList());
        processesToKill.forEach(this::close);
    }

    @SuppressWarnings("squid:UnusedPrivateMethod") // Sonar doesn't do lambdas
    private void close(ExifToolProcess process) {
        processes.remove(process);
        process.close();
    }

    @Override
    public void close() {
        processes.forEach(ExifToolProcess::close);
    }

    public int size() {
        return processes.size();
    }
}
