package nl.xillio.exiftool.process;


import java.io.IOException;

/**
 * This is the osx implementation of the {@link ExifToolProcess}.
 *
 * @author Thomas Biesaart
 */
public class OSXExifToolProcess extends AbstractExifToolProcess {

    @Override
    protected Process buildProcess(ProcessBuilder processBuilder) throws IOException {
        String exifBin = System.getenv("exiftool_bin");
        if (exifBin == null) {
            exifBin = "/usr/local/bin/exiftool";
        }

        processBuilder.command(exifBin, "-stay_open", "True", "-@", "-");
        return processBuilder.start();
    }

    @Override
    public boolean needInit() {
        return false;
    }

    @Override
    public void init() throws IOException {
        // No need to deploy. We just need the exiftool installed
    }
}
