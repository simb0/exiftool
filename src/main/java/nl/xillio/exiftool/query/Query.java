package nl.xillio.exiftool.query;

import nl.xillio.exiftool.process.ExifToolProcess;

import java.io.IOException;
import java.util.List;

/**
 * This interface is implemented by every available query.
 *
 * @author Thomas Biesaart
 */
public interface Query<T> {
    /**
     * Build arguments from this query.
     *
     * @return a list of arguments
     */
    List<String> buildExifArguments();

    /**
     * Run this query.
     *
     * @return the result
     */
    T run(ExifToolProcess process) throws IOException;
}
