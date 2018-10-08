/**
 * Copyright 2015 Xillio BV
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.simb0.exiftool.process;

import org.apache.commons.io.IOUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.AssertJUnit.assertTrue;


public class AbstractExifToolProcessTest {

    @Test
    public void testClose() throws IOException {
        MockProcess process = new MockProcess(IOUtils.toInputStream(""));
        process.start();
        process.close();

        // Test if the process is killed.
        verify(process.buildProcess(null)).destroyForcibly();
        assertTrue(process.isClosed());
        assertFalse(process.isAvailable());
        assertFalse(process.isRunning());
    }

    @Test(expectedExceptions = IllegalStateException.class)
    public void testCantStartTwice() throws IOException {
        MockProcess process = new MockProcess(IOUtils.toInputStream(""));
        process.start();
        process.start();
    }

    @Test
    public void testRun() throws IOException {
        MockProcess process = new MockProcess(IOUtils.toInputStream("Line A\nLine B"));

        ExecutionResult result = process.run("Some", "Input");

        assertEquals(result.next(), "Line A");
        assertEquals(result.next(), "Line B");
        Assert.assertFalse(result.hasNext());
    }

    private static class MockProcess extends AbstractExifToolProcess {
        private final Process process;

        private MockProcess(InputStream inputStream) {
            this.process = mock(Process.class, RETURNS_DEEP_STUBS);
            when(process.getInputStream()).thenReturn(inputStream);
        }

        @Override
        protected Process buildProcess(ProcessBuilder processBuilder) throws IOException {
            return process;
        }

        @Override
        public boolean needInit() {
            return false;
        }

        @Override
        public void init() throws IOException {

        }
    }
}
