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

import de.simb0.exiftool.process.ExifToolProcess;

import java.io.IOException;
import java.util.List;

/**
 * This interface is implemented by every available query.
 *
 * @author Thomas Biesaart
 */
public interface Query<T> {
    /**
     * Build arguments from this query.
     *
     * @return a list of arguments
     */
    List<String> buildExifArguments();

    /**
     * Run this query.
     *
     * @return the result
     */
    T run(ExifToolProcess process) throws IOException;
}
