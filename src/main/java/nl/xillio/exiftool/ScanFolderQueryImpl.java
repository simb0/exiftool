package nl.xillio.exiftool;

import nl.xillio.exiftool.process.ExecutionResult;
import nl.xillio.exiftool.query.ExifReadResult;
import nl.xillio.exiftool.query.FolderQueryOptions;
import nl.xillio.exiftool.query.Projection;
import nl.xillio.exiftool.query.ScanFolderQuery;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * This class represents the default implementation of the ScanFolderQuery.
 *
 * @author Thomas Biesaart
 */
class ScanFolderQueryImpl extends AbstractQuery<FolderQueryOptions, ExifReadResult> implements ScanFolderQuery {


    public ScanFolderQueryImpl(Path folder, Projection projection, FolderQueryOptions folderQueryOptions) throws NoSuchFileException {
        super(folder, projection, folderQueryOptions);

        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException(folder + " is not a folder");
        }
    }

    @Override
    protected ExifReadResult buildResult(ExecutionResult executionResult) {
        return new ExifReadResultImpl(executionResult, 100, getOptions().getTagNameConvention());
    }
}
