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
package de.simb0.exiftool;

import de.simb0.exiftool.process.ExecutionResult;
import de.simb0.exiftool.query.ExifTags;
import de.simb0.exiftool.query.StandardTag;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.StringReader;

import static org.testng.Assert.*;


public class ExifReadResultImplTest {
    @Test
    public void testReadResult() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("======== D:\\File\nFileSize: 5\nXPAuthor: 234tgr\n{ready}"));
        ExecutionResult executionResult = new ExecutionResult(bufferedReader, () -> {
        }, "{ready}");
        ExifReadResultImpl readResult = new ExifReadResultImpl(executionResult, 10, a -> a);

        assertTrue(readResult.hasNext());

        ExifTags tags = readResult.next();
        ExifTags expected = new ExifTagsImpl();
        expected.put(StandardTag.SOURCE_FILE, "D:\\File");
        expected.put(StandardTag.FILE_SIZE, "5");
        expected.put(StandardTag.AUTHOR, "234tgr");

        assertFalse(readResult.hasNext());
        assertEquals(tags, expected);
    }
}
