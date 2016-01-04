package nl.xillio.exiftool;

import nl.xillio.exiftool.process.ExecutionResult;
import nl.xillio.exiftool.query.ExifTags;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.testng.Assert.*;


public class ExifReadResultImplTest {
    @Test
    public void testReadResult() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("======== D:\\File\nTest Value: 5\nOther Value: 234tgr\n{ready}"));
        ExecutionResult executionResult = new ExecutionResult(bufferedReader, () -> {
        }, "{ready}");
        ExifReadResultImpl readResult = new ExifReadResultImpl(executionResult, 10, a -> a);

        assertTrue(readResult.hasNext());

        ExifTags tags = readResult.next();
        ExifTags expected = new ExifTagsImpl();
        expected.put("File Path", "D:\\File");
        expected.put("Test Value", "5");
        expected.put("Other Value", "234tgr");

        assertFalse(readResult.hasNext());
        assertEquals(tags, expected);
    }
}
