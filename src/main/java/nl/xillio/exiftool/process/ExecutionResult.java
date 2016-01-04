package nl.xillio.exiftool.process;

import me.biesaart.utils.Log;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class represents the result of a call to the process.
 * It will cache a single line from a {@link BufferedReader}.
 *
 * @author Thomas Biesaart
 */
public class ExecutionResult implements Iterator<String> {
    private static final Logger LOGGER = Log.get();
    private final BufferedReader reader;
    private final StatusCallback statusCallback;
    private final String killString;
    private String cachedLine;
    private boolean shouldClose;

    /**
     * Create a new ExecutionResult.
     *
     * @param reader         the reader the read the output from
     * @param statusCallback the callback that should be called when reading the output is done
     * @param killString     the string that marks the end of this output
     */
    public ExecutionResult(BufferedReader reader, StatusCallback statusCallback, String killString) {
        this.reader = reader;
        this.statusCallback = statusCallback;
        this.killString = killString;
        cacheNext();
    }

    private void cacheNext() {
        try {
            cachedLine = reader.readLine();

            if (cachedLine == null || cachedLine.equals(killString)) {
                shouldClose = true;
                statusCallback.releaseProcess();
            }
        } catch (IOException e) {
            LOGGER.error("Failed to read next line", e);
            cachedLine = null;
            shouldClose = true;
        }
    }

    @Override
    public boolean hasNext() {
        return !shouldClose && cachedLine != null;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements in this result");
        }

        String current = cachedLine;
        cacheNext();
        return current;
    }

    public void close() {
        // Read all data
        while (!shouldClose) {
            cacheNext();
        }
    }
}
