/**
 * Copyright 2015 Xillio BV
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.simb0.exiftool;

import de.simb0.exiftool.pool.ProcessPool;
import de.simb0.exiftool.process.ExifToolProcess;
import de.simb0.exiftool.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.function.Consumer;

/**
 * This class represents an easy-to-use facade around the ExifTool wrapper library.
 *
 * @author Thomas Biesaart
 */
public class ExifTool implements AutoCloseable {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExifTool.class);

    private final ExifToolProcess process;
    private final Consumer<ExifToolProcess> releaseMethod;

    public ExifTool(ExifToolProcess process, Consumer<ExifToolProcess> releaseMethod) {
        this.process = process;
        this.releaseMethod = releaseMethod;
    }

    @Override
    public void close() {
        LOGGER.debug("Releasing exiftool process");
        releaseMethod.accept(process);
    }

    public ExifReadResult readFieldsForFolder(Path path, Projection projection, FolderQueryOptions folderQueryOptions) throws IOException {
        LOGGER.info("Reading tags for files in " + path);

        ScanFolderQuery scanFolderQuery = new ScanFolderQueryImpl(path, projection, folderQueryOptions);

        return scanFolderQuery.run(process);
    }

    public ExifTags readFieldsForFile(Path file, Projection projection, FileQueryOptions options) throws IOException {
        LOGGER.info("Reading tags for " + file);
        ScanFileQuery scanFileQuery = new ScanFileQueryImpl(file, projection, options);
        return scanFileQuery.run(process);
    }


    public static void main(String args[]) {
        System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "DEBUG");

        ProcessPool exifToolPool = ProcessPool.buildPool(Paths.get("C:\\Users\\adrebert\\Picardo\\rest\\src\\test\\resources\\exiftool.exe"));

        File f = new File("C:\\Users\\adrebert\\Picardo\\rest\\src\\test\\resources\\video\\test.mp4");
        Projection p = new Projection();
        p.put(StandardTag.CREATE_DATE, true);
        p.put(StandardTag.FILE_CREATE_DATE, true);
        p.put(StandardTag.DATE_TIME_ORIGINAL, true);
        for (int z = 0; z < 100; z++) {
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try (ExifTool tool = exifToolPool.get()) {
                        ExifTags result = null;
                        try {
                            result = tool.readFieldsForFile(f.toPath(), p, new FileQueryOptionsImpl());
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.exit(1);
                        }
                        System.out.println(result);
                    }
                }
                System.out.println("#######******************************************************#######");
                System.out.println("#######******************************************************#######");
                System.out.println("#######****"+Thread.currentThread().getName()+" is done! #######****");
                System.out.println("#######******************************************************#######");
            }).start();
        }
    }

}
