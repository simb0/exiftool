package nl.xillio.exiftool;

import nl.xillio.exiftool.process.ExecutionResult;
import nl.xillio.exiftool.process.ExifToolProcess;
import nl.xillio.exiftool.query.Projection;
import nl.xillio.exiftool.query.Query;
import nl.xillio.exiftool.query.QueryOptions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the abstract implementation of query. It provides some convenience methods that most queries need.
 *
 * @param <T> The type of options used by this query
 * @param <U> The return type of the run method
 */
abstract class AbstractQuery<T extends QueryOptions, U> implements Query<U> {
    private final Path path;
    private final T options;
    private final Projection projection;

    protected AbstractQuery(Path path, Projection projection, T options) throws NoSuchFileException {
        this.path = path;
        this.options = options;
        this.projection = projection;


        if (!Files.exists(path)) {
            throw new NoSuchFileException("Could not find file " + path);
        }
    }

    @Override
    public List<String> buildExifArguments() {
        List<String> result = new ArrayList<>();
        result.add(path.toAbsolutePath().toString());
        result.addAll(options.buildArguments());
        result.addAll(projection.buildArguments());
        return result;
    }

    protected abstract U buildResult(ExecutionResult executionResult);

    @Override
    public U run(ExifToolProcess process) throws IOException {
        List<String> arguments = buildExifArguments();
        ExecutionResult executionResult = process.run(arguments.toArray(new String[arguments.size()]));
        return buildResult(executionResult);
    }

    public Path getPath() {
        return path;
    }

    public T getOptions() {
        return options;
    }

    public Projection getProjection() {
        return projection;
    }
}
