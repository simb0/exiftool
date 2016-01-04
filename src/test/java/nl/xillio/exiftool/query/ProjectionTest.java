package nl.xillio.exiftool.query;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;


public class ProjectionTest {

    @Test
    public void testBuildArguments() throws Exception {
        Projection projection = new Projection();
        projection.put("tag", false);
        projection.put("tagg", true);

        assertEquals(projection.buildArguments(), Arrays.asList("--tag", "-tagg"));
    }
}
