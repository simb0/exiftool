package nl.xillio.exiftool.process;

import java.io.IOException;

/**
 * This interface represents an instance of an exiftool process.
 *
 * @author Thomas Biesaart
 */
public interface ExifToolProcess extends AutoCloseable {
    /**
     * Check if this process needs initialization.
     *
     * @return true if this process required initialization
     */
    boolean needInit();

    /**
     * Initialize the program.
     *
     * @throws IOException if initialization fails
     */
    void init() throws IOException;

    /**
     * Check if this process is currently running a query.
     *
     * @return true if this process is running a query
     */
    boolean isRunning();

    /**
     * Check if this process has been closed.
     *
     * @return true if and only if this process has closed
     */
    boolean isClosed();

    /**
     * Check if this process is ready to execute a query.
     *
     * @return true if and only if this process is available
     */
    boolean isAvailable();

    /**
     * Start this process.
     *
     * @throws IOException if execution failed
     */
    void start() throws IOException;

    /**
     * Close this process.
     */
    @Override
    void close();

    /**
     * Run arguments on this process.
     *
     * @param arguments the arguments
     * @return the line iterator containing the string output of the query
     * @throws IOException           if execution fails
     * @throws IllegalStateException if this process is already running
     */
    ExecutionResult run(String... arguments) throws IOException;
}
