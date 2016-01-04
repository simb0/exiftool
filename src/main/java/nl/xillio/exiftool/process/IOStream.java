package nl.xillio.exiftool.process;

import me.biesaart.utils.Log;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * This class represents a convenience wrapper around input and output streams.
 *
 * @author Thomas Biesaart
 */
class IOStream implements AutoCloseable {
    private static final Logger LOGGER = Log.get();
    private final BufferedReader reader;
    private final OutputStreamWriter writer;

    public IOStream(BufferedReader reader, OutputStreamWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public IOStream(Process process) {
        this(new BufferedReader(new InputStreamReader(process.getInputStream())), new OutputStreamWriter(process.getOutputStream()));
    }

    public BufferedReader getReader() {
        return reader;
    }

    public OutputStreamWriter getWriter() {
        return writer;
    }

    @Override
    public void close() {
        LOGGER.debug("Closing IOStream");
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.error("Exception while closing stream", e);
        }

        try {
            writer.close();
        } catch (IOException e) {
            LOGGER.error("Exception while closing stream", e);
        }
        LOGGER.debug("IOStream closed");
    }
}
