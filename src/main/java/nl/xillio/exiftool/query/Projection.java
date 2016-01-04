package nl.xillio.exiftool.query;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This interface represents a filter of tags that should be fetched.
 *
 * @author Thomas Biesaart
 */
public class Projection extends HashMap<String, Boolean> {

    /**
     * Get the tag arguments for this projection.
     *
     * @return the arguments
     */
    public List<String> buildArguments() {
        List<String> result = new ArrayList<>();

        forEach((key, value) -> {
            String prefix = value ? "-" : "--";
            result.add(prefix + key);
        });

        return result;
    }
}
