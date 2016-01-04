package nl.xillio.exiftool;

import nl.xillio.exiftool.query.FileQueryOptions;

import java.util.Collections;
import java.util.List;

/**
 * This class is the main implementation of the FileQueryOptions.
 *
 * @author Thomas Biesaart
 */
public class FileQueryOptionsImpl extends AbstractQueryOptions implements FileQueryOptions {

    @Override
    public List<String> buildArguments() {
        return Collections.emptyList();
    }
}
