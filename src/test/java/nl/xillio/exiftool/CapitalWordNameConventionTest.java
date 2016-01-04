package nl.xillio.exiftool;

import nl.xillio.exiftool.query.TagNameConvention;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;


public class CapitalWordNameConventionTest {

    @Test
    public void testToConvention() throws Exception {
        TagNameConvention convention = new CapitalWordNameConvention();

        assertEquals(convention.toConvention("Test Case"), "Test Case");
    }
}