package nl.xillio.exiftool.process;


import java.io.IOException;

/**
 * This is the linux implementation of the {@link ExifToolProcess}.
 *
 * @author Thomas Biesaart
 */
public class UnixExifToolProcess extends AbstractExifToolProcess {

    @Override
    protected Process buildProcess(ProcessBuilder processBuilder) throws IOException {
        String exifBin = System.getenv("exiftool_bin");
        if (exifBin == null) {
            throw new IOException("Please set your exiftool_bin environmental variable to the path to your exiftool installation.");
        }

        String perlBin = System.getenv("perl_bin");

        if (perlBin == null) {
            perlBin = "/usr/bin/perl";
        }

        processBuilder.command(perlBin, exifBin, "-stay_open", "True", "-@", "-");
        return processBuilder.start();
    }

    @Override
    public boolean needInit() {
        return false;
    }

    @Override
    public void init() throws IOException {
        // The user has to manually install Exiftool
    }
}
