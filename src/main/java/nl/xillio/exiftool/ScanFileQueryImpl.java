package nl.xillio.exiftool;

import me.biesaart.utils.Log;
import nl.xillio.exiftool.process.ExecutionResult;
import nl.xillio.exiftool.query.*;
import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

/**
 * This class provides the main implementation of the ScanFileQuery.
 *
 * @author Thomas Biesaart
 */
class ScanFileQueryImpl extends AbstractQuery<FileQueryOptions, ExifTags> implements ScanFileQuery {

    private static final Logger LOGGER = Log.get();

    public ScanFileQueryImpl(Path path, Projection projection, FileQueryOptions options) throws NoSuchFileException {
        super(path, projection, options);

        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException(path + " is not a file");
        }
    }

    @Override
    protected ExifTags buildResult(ExecutionResult executionResult) {
        TagNameConvention nameConvention = getOptions().getTagNameConvention();
        ExifTags result = new ExifTagsImpl();
        result.put(nameConvention.toConvention("File Path"), getPath().toAbsolutePath().toString());

        executionResult.forEachRemaining(
                line -> processLine(line, nameConvention, result)
        );

        return result;
    }

    private void processLine(String line, TagNameConvention convention, ExifTags result) {
        int separator = line.indexOf(":");

        if (separator == -1) {
            LOGGER.error("Failed to parse [{}] as a field", line);
            return;
        }

        String key = line.substring(0, separator).trim();
        String value = line.substring(separator + 1).trim();

        result.put(convention.toConvention(key), value);
    }
}
