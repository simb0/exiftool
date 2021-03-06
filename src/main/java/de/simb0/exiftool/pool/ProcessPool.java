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
package de.simb0.exiftool.pool;

import de.simb0.exiftool.ExifTool;
import de.simb0.exiftool.process.ExifToolProcess;
import de.simb0.exiftool.process.OSXExifToolProcess;
import de.simb0.exiftool.process.UnixExifToolProcess;
import de.simb0.exiftool.process.WindowsExifToolProcess;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * This class represents a pool that can run low level actions on available processes.
 *
 * @author Thomas Biesaart
 */
public class ProcessPool implements AutoCloseable, Pool<ExifToolProcess> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessPool.class);

    /**
     * In these queue are all available processes
     */
    private BlockingQueue<ExifToolProcess> processQueue;

    /**
     * These processes have been given away but not yet returned.
     */
    private List<ExifToolProcess> leasedProcesses = new ArrayList<>();
    private final Supplier<ExifToolProcess> processBuilder;
    private boolean shutdown;
    private final int maxPoolSize;
    private final int poolTimeout;

    private ProcessPool(int maxPoolSize, int poolTimeout) {
        this.maxPoolSize = maxPoolSize;
        this.poolTimeout = poolTimeout;
        processBuilder = null;
    }

    private ProcessPool(Supplier<ExifToolProcess> processBuilder, int maxPoolSize, int poolTimeout) {
        this.maxPoolSize = maxPoolSize;
        this.poolTimeout = poolTimeout;
        this.processBuilder = processBuilder;
        shutdown = false;
        processQueue = new LinkedBlockingQueue();
        for (int i = 0; i < maxPoolSize; i++) {
            processQueue.add(createNew());
        }
    }

    /**
     * @throws NoExifProcessesAvailable if no process is after the timeout available
     * @return available process bound with the ExifTool
     */
    @Override
    public ExifTool get() {
        if (!shutdown) {
            try {
                ExifToolProcess process = processQueue.poll(poolTimeout, TimeUnit.SECONDS);

                if(process == null) {
                   throw new NoExifProcessesAvailable(String.format("The maxPoolSize was %s leased processes %s, waitet %s seconds.", maxPoolSize, leasedProcesses.size(), poolTimeout));
                }

                leasedProcesses.add(process);

                LOGGER.info("Giving out {}", process);

                return new ExifTool(process, this::release);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        throw new IllegalStateException("Object pool is already shutdown.");
    }

    private ExifToolProcess createNew() {
        LOGGER.info("Creating new exiftool process");
        ExifToolProcess process = processBuilder.get();
        return process;
    }

    public void release(ExifToolProcess process) {
        LOGGER.info("Releasing {}", process);
        if (!process.isAvailable()) {
            // This process is still busy. Kill it
            process.close();
            //TODO : Create new one?
        } else {
            processQueue.offer(process);
        }
        leasedProcesses.remove(process);
    }

    @Override
    public void close() {
        leasedProcesses.forEach(ExifToolProcess::close);
    }

    public int size() {
        return processQueue.size();
    }

    /**
     *
     * @param windowsBinaryLocation path to exiftool.exe
     * @param maxPoolSize the max size of the pool
     * @param poolTimeout wait time for a poolprocess in sec before an exception is thrown
     * @return
     */
    public static ProcessPool buildPool(Path windowsBinaryLocation, int maxPoolSize, int poolTimeout) {
        if (SystemUtils.IS_OS_WINDOWS) {
            return new ProcessPool(() -> new WindowsExifToolProcess(windowsBinaryLocation), maxPoolSize, poolTimeout);
        }

        if (SystemUtils.IS_OS_MAC) {
            return new ProcessPool(OSXExifToolProcess::new, maxPoolSize, poolTimeout);
        }

        if (SystemUtils.IS_OS_UNIX) {
            return new ProcessPool(UnixExifToolProcess::new, maxPoolSize, poolTimeout);
        }

        throw new NotImplementedException("No implementation for " + SystemUtils.OS_NAME);
    }

    @Override
    public void shutdown() {
        leasedProcesses.forEach(ExifToolProcess::close);
        processQueue.forEach(ExifToolProcess::close);
    }

    public class NoExifProcessesAvailable extends RuntimeException {
        public NoExifProcessesAvailable(String message) {
        }
    }
}
