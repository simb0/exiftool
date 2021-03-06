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

import de.simb0.exiftool.query.FileQueryOptions;

import java.util.Collections;
import java.util.List;

/**
 * This class is the main implementation of the FileQueryOptions.
 *
 * @author Thomas Biesaart
 */
public class FileQueryOptionsImpl extends AbstractQueryOptions implements FileQueryOptions {

    @Override
    public List<String> buildArguments() {
        return Collections.emptyList();
    }
}
