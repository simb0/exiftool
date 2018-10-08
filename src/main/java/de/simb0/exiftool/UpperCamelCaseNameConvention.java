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

import com.google.common.base.CaseFormat;
import de.simb0.exiftool.query.TagNameConvention;

/**
 * This TagNameConvention will convert all the tags to UpperCamelCasing.
 *
 * @author Thomas Biesaart
 */
public class UpperCamelCaseNameConvention implements TagNameConvention {
    @Override
    public String toConvention(String originalTagName) {
        String underscore = originalTagName.replaceAll("\\s", "_");
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, underscore);
    }
}
