package nl.xillio.exiftool;

import com.google.common.base.CaseFormat;
import nl.xillio.exiftool.query.TagNameConvention;

/**
 * This TagNameConvention will convert all the tags to UpperCamelCasing.
 *
 * @author Thomas Biesaart
 */
public class UpperCamelCaseNameConvention implements TagNameConvention {
    @Override
    public String toConvention(String originalTagName) {
        String underscore = originalTagName.replaceAll("\\s", "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscore);
    }
}
