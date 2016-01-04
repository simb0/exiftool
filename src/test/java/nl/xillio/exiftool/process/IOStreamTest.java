package nl.xillio.exiftool.process;

import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static org.mockito.Mockito.*;

/**
 * Unit test for closing IO streams.
 *
 * @author Pieter Dirk Soels
 */
public class IOStreamTest {

    @Test
    public void testCloseNormalConditions() throws IOException {
        // Mock dependencies
        BufferedReader input = mock(BufferedReader.class);
        OutputStreamWriter output = mock(OutputStreamWriter.class);
        IOStream stream = new IOStream(input, output);

        // Run
        stream.close();

        // Verify
        verify(input, times(1)).close();
        verify(output, times(1)).close();
    }

    @Test
    public void testCloseInputException() throws IOException {
        // Mock dependencies
        BufferedReader input = mock(BufferedReader.class);
        OutputStreamWriter output = mock(OutputStreamWriter.class);
        IOStream stream = new IOStream(input, output);

        doThrow(IOException.class).when(input).close();

        // Run
        stream.close();

        // Verify
        verify(input, times(1)).close();
        verify(output, times(1)).close();
    }

    @Test
    public void testCloseOutputException() throws IOException {
        // Mock dependencies
        BufferedReader input = mock(BufferedReader.class);
        OutputStreamWriter output = mock(OutputStreamWriter.class);
        IOStream stream = new IOStream(input, output);

        doThrow(IOException.class).when(output).close();

        // Run
        stream.close();

        // Verify
        verify(input, times(1)).close();
        verify(output, times(1)).close();
    }
}
