package nl.xillio.exiftool;

import nl.xillio.exiftool.process.ExifToolProcess;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author Thomas Biesaart
 */
public class ProcessPoolTest {

    @Test
    public void testGetProcessRoutine() {
        Factory factory = mock(Factory.class, RETURNS_DEEP_STUBS);
        when(factory.build().isAvailable()).thenReturn(true);

        ProcessPool processPool = new ProcessPool(factory::build);

        verify(factory, times(1)).build();

        try (ExifTool tool = processPool.getAvailable()) {
            verify(factory, times(2)).build();
        }

        try (ExifTool tool = processPool.getAvailable()) {
            verify(factory, times(2)).build();

            try (ExifTool tool2 = processPool.getAvailable()) {
                verify(factory, times(3)).build();
            }
        }

        try (ExifTool tool = processPool.getAvailable()) {
            verify(factory, times(3)).build();
        }

        assertEquals(processPool.size(), 2);
        processPool.clean();
        assertEquals(processPool.size(), 0);
        processPool.close();


    }

    private interface Factory {
        ExifToolProcess build();
    }
}
