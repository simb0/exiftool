package nl.xillio.exiftool.process;


import me.biesaart.utils.Log;
import nl.xillio.exiftool.ProcessPool;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

/**
 * This is the Windows implementation of the {@link ExifToolProcess}.
 *
 * @author Thomas Biesaart
 */
public class WindowsExifToolProcess extends AbstractExifToolProcess {
    private static final Logger LOGGER = Log.get();
    private static final URL EMBEDDED_BINARY = ProcessPool.class.getResource("/exiftool.exe");
    private final Path nativeBinary;

    public WindowsExifToolProcess(Path nativeBinary) {
        Objects.requireNonNull(nativeBinary);
        this.nativeBinary = nativeBinary.toAbsolutePath();
    }

    @Override
    public boolean needInit() {
        return !Files.exists(nativeBinary);
    }


    @Override
    public void init() throws IOException {
        if (!needInit()) {
            return;
        }

        Files.createDirectory(nativeBinary.getParent());
        Files.createFile(nativeBinary);

        LOGGER.info("Deploying exiftool binary to " + nativeBinary);
        try (InputStream stream = EMBEDDED_BINARY.openStream()) {
            Files.copy(stream, nativeBinary, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    @Override
    protected Process buildProcess(ProcessBuilder processBuilder) throws IOException {
        processBuilder.command(nativeBinary.toString(), "-stay_open", "True", "-@", "-");
        return processBuilder.start();
    }
}
