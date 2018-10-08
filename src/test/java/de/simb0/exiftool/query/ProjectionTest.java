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
package de.simb0.exiftool.query;

import org.testng.annotations.Test;

import java.util.Arrays;

import static org.testng.Assert.assertEquals;


public class ProjectionTest {

    @Test
    public void testBuildArguments() throws Exception {
        Projection projection = new Projection();
        projection.put(StandardTag.TITLE, false);
        projection.put(StandardTag.X_RESOLUTION, true);

        assertEquals(projection.buildArguments(), Arrays.asList("--XPTitle", "-XResolution"));
    }
}
