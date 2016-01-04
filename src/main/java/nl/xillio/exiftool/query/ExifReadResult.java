package nl.xillio.exiftool.query;

import java.util.Iterator;

/**
 * This interface represents an object that is returned from a read query.
 *
 * @author Thomas Biesaart
 */
public interface ExifReadResult extends Iterator<ExifTags>, AutoCloseable {
    @Override
    void close();
}
