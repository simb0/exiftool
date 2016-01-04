package nl.xillio.exiftool;

import nl.xillio.exiftool.query.TagNameConvention;

/**
 * This TagNameConvention will not convert the tags.
 *
 * @author Thomas Biesaart
 */
public class CapitalWordNameConvention implements TagNameConvention {
    @Override
    public String toConvention(String originalTagName) {
        return originalTagName;
    }
}
