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

import de.simb0.exiftool.pool.ProcessPool;
import org.testng.annotations.Test;

import java.nio.file.Paths;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * @author Alexander Drebert
 */
public class ProcessPoolTest {

    private ProcessPool testee = ProcessPool.buildPool(Paths.get("any"));

    @Test
    public void testCreatePool() {
        assertThat(testee.size()).isEqualTo(6);
    }

    @Test
    public void testGetProcessFromPool() {
        assertThat(testee.get()).isNotNull();
    }

    @Test(expectedExceptions = ProcessPool.NoExifProcessesAvailable.class)
    public void testOverload() {
        for (int i = 0; i < testee.maxPoolSize+1; i++) {
            assertThat(testee.get()).isNotNull();
        }
        assertThat(testee.size()).isEqualTo(0);

    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testRelease() {
        testee = ProcessPool.buildPool(Paths.get("any"));
        for (int i = 0; i < testee.maxPoolSize-1; i++) {
            assertThat(testee.get()).isNotNull();
        }
        assertThat(testee.size()).isEqualTo(1);
        // 1 available get() -> 0 close -> 1
        testee.get().close();
        assertThat(testee.size()).isEqualTo(1);

        assertThat(testee.get()).isNotNull();
        assertThat(testee.size()).isEqualTo(0);
    }

}
