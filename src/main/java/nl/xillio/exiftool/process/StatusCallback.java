package nl.xillio.exiftool.process;

/**
 * This interface represents a process that can be released.
 *
 * @author Thomas Biesaart
 */
public interface StatusCallback {
    /**
     * Release the process.
     */
    void releaseProcess();
}
