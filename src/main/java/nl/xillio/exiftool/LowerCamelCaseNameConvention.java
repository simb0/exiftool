package nl.xillio.exiftool;

import com.google.common.base.CaseFormat;
import nl.xillio.exiftool.query.TagNameConvention;

/**
 * This TagNameConvention will convert all the tags to lower camel casing.
 *
 * @author Thomas Biesaart
 */
public class LowerCamelCaseNameConvention implements TagNameConvention {
    @Override
    public String toConvention(String originalTagName) {
        String underscore = originalTagName.replaceAll("\\s", "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, underscore);
    }
}
