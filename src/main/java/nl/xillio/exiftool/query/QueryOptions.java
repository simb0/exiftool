package nl.xillio.exiftool.query;

import java.util.List;

/**
 * This interface represents the base class for options for queries.
 *
 * @author Thomas Biesaart
 */
public interface QueryOptions {

    TagNameConvention getTagNameConvention();

    void setTagNameConvention(TagNameConvention tagNameConvention);

    List<String> buildArguments();
}
