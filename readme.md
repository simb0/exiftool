# What's new
Fixed size blockingqueue mechanism implemented

# Java Exiftool
Exiftool info in java.

## What?
This project provides a wrapper for those who need to get information from exiftool in their java software.
It provides a simple yet powerful querying interface.

## Who?
This wrapper should be usable by any java developer with a couple of years of experience.

## Why?
We were in need of a java wrapper for exiftool ourselves but found that 
[the most popular wrapper] (https://github.com/thebuzzmedia/exiftool) had been discontinued.

## Project dependencies
- [Google Guava](https://github.com/google/guava)
- [SLF4J](https://github.com/qos-ch/slf4j)
- [Apache Commons Lang](https://github.com/apache/commons-lang)

## Usage
    Windows:
    ProcessPool exifToolPool = ProcessPool.buildPool(Paths.get("WINDOWS PATH TO exiftool"));
    Unix MacOs:
    ProcessPool exifToolPool = ProcessPool.buildPool(null);
Pool is now ready wo work. The system processes itself are startet when needed.
 
    try (ExifTool tool = exifToolPool.get()) {
        /** File to inspect **/
        File f = new File("test.mp4");
        
        /** Attributes neded from exiftool **/
        Projection p = new Projection();
        p.put(StandardTag.CREATE_DATE, true);
        p.put(StandardTag.FILE_CREATE_DATE, true);
        p.put(StandardTag.DATE_TIME_ORIGINAL, true);
        
        ExifTags result = tool.readFieldsForFile(f.toPath(), p, new FileQueryOptionsImpl());
    }
{filePath=test.mp4, fileCreationDate/time=2018:09:06 22:08:25+02:00, createDate=0000:00:00 00:00:00}

    
## License

    Copyright 2015 Xillio BV
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
        http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
