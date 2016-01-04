package nl.xillio.exiftool;

import nl.xillio.exiftool.query.FolderQueryOptions;
import nl.xillio.exiftool.query.Projection;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the options needed for a query on a folder.
 *
 * @author Thomas Biesaart
 */
public class FolderQueryOptionsImpl extends AbstractQueryOptions implements FolderQueryOptions {

    private boolean recursive = true;
    private Projection extensionFilter = new Projection();

    @Override
    public boolean isRecursive() {
        return recursive;
    }

    @Override
    public void setRecursive(boolean recursive) {
        this.recursive = recursive;
    }

    @Override
    public Projection getExtensionFilter() {
        return extensionFilter;
    }

    @Override
    public void setExtensionFilter(Projection filter) {
        this.extensionFilter = filter;
    }

    @Override
    public List<String> buildArguments() {
        List<String> result = new ArrayList<>();
        if (isRecursive()) {
            result.add("-r");
        }

        if (extensionFilter.isEmpty()) {
            result.add("-ext");
            result.add("*");
        } else {
            extensionFilter.forEach(
                    (extension, include) -> {
                        if (include) {
                            result.add("-ext");
                        } else {
                            result.add("--ext");
                        }
                        result.add(extension);
                    }
            );
        }
        return result;
    }
}
