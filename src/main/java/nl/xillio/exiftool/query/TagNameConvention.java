package nl.xillio.exiftool.query;

/**
 * This interface represents an object that can convert a tag name to a naming convention.
 *
 * @author Thomas Biesaart
 */
public interface TagNameConvention {
    /**
     * Convert a name to this convention.
     *
     * @param originalTagName the original tag name
     * @return the convention name
     */
    String toConvention(String originalTagName);
}
