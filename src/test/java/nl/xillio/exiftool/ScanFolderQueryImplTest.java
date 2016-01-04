package nl.xillio.exiftool;

import nl.xillio.exiftool.query.Projection;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ScanFolderQueryImplTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorConstraintFile() throws IOException {
        Path file = Files.createTempFile("unittest", "");
        new ScanFolderQueryImpl(file, new Projection(), new FolderQueryOptionsImpl());
        Files.delete(file);
    }

    @Test(expectedExceptions = NoSuchFileException.class)
    public void testConstructorConstraintNotExist() throws NoSuchFileException {
        new ScanFolderQueryImpl(Paths.get("I surely do not exist"), null, null);
    }

}
