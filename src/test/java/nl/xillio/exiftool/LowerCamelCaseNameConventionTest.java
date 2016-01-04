package nl.xillio.exiftool;

import nl.xillio.exiftool.query.TagNameConvention;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class LowerCamelCaseNameConventionTest {

    @Test
    public void testToConvention() throws Exception {
        String expectedOutput = "testCase";
        TagNameConvention convention = new LowerCamelCaseNameConvention();

        assertEquals(convention.toConvention("Test Case"), expectedOutput);
        assertEquals(convention.toConvention("Test_Case"), expectedOutput);
    }
}