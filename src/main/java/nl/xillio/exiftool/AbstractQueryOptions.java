package nl.xillio.exiftool;

import nl.xillio.exiftool.query.QueryOptions;
import nl.xillio.exiftool.query.TagNameConvention;

/**
 * @author Thomas Biesaart
 */
abstract class AbstractQueryOptions implements QueryOptions {
    private TagNameConvention tagNameConvention = new LowerCamelCaseNameConvention();

    @Override
    public TagNameConvention getTagNameConvention() {
        return tagNameConvention;
    }

    @Override
    public void setTagNameConvention(TagNameConvention tagNameConvention) {
        this.tagNameConvention = tagNameConvention;
    }


}
