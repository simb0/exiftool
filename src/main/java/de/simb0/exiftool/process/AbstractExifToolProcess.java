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


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class represents the base implementation of the ExifToolProcess.
 *
 * @author Thomas Biesaart
 */
abstract class AbstractExifToolProcess implements ExifToolProcess {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractExifToolProcess.class);
    private Status status = Status.NEW;
    private Process process;
    private IOStream streams;

    @Override
    public void close() {
        status = Status.CLOSING;
        try {
            streams.getWriter().write("-stay_open\nFalse\n");
            streams.getWriter().flush();
            streams.close();
            process.destroyForcibly();
        } catch (IOException e) {
            LOGGER.error("Failed to send close command to process", e);
        }
        status = Status.CLOSED;
    }

    @Override
    public void start() throws IOException {
        if (status != Status.NEW) {
            throw new IllegalStateException("This process is not new. It is either running or has been closed");
        }
        LOGGER.debug("Starting exiftool process");

        status = Status.STARTING;

        try {
            init();
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

            this.process = buildProcess(processBuilder);
            this.streams = new IOStream(process);
        } finally {
            // Set status to new in case of errors
            status = Status.NEW;
        }

        // We are done!
        status = Status.IDLE;
    }

    @Override
    public ExecutionResult run(String... arguments) throws IOException {
        requireRunning();

        LOGGER.debug("Running exiftool {}", Arrays.toString(arguments));

        status = Status.RUNNING;

        LOGGER.debug("Sending arguments");
        // Send arguments
        for (String argument : arguments) {
            streams.getWriter().write(argument + "\n");
        }
        streams.getWriter().write("-execute\n");
        streams.getWriter().flush();

        return new ExecutionResult(streams.getReader(), () -> status = Status.IDLE, "{ready}");
    }

    private void requireRunning() throws IOException {
        if (status == Status.NEW) {
            start();
        }

        if (!isAvailable()) {
            throw new IllegalStateException("This process is not available!");
        }
    }


    protected abstract Process buildProcess(ProcessBuilder processBuilder) throws IOException;

    @Override
    public boolean isRunning() {
        return status == Status.RUNNING;
    }

    @Override
    public boolean isClosed() {
        return status == Status.CLOSED;
    }

    @Override
    public boolean isAvailable() {
        return status == Status.IDLE;
    }

    private enum Status {
        NEW,
        STARTING,
        RUNNING,
        IDLE,
        CLOSING,
        CLOSED
    }
}
