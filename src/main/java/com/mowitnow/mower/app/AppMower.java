package com.mowitnow.mower.app;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.transfer.IEngineReader;
import com.mowitnow.mower.transfer.IEngineWriter;
import com.mowitnow.mower.transfer.exception.TransferException;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextWriter;

import java.io.*;


/**
 * Created by ffradet on 17/10/2014.
 */
public class AppMower {

    /**
     * Finished ok<br>
     * "by convention, a nonzero status code indicates abnormal termination" (System.exit)
     */
    public static final int STATUS_OK = 0;

    /**
     * No file provided on the command line
     */
    public static final int STATUS_ERR_NO_FILE = -1;

    /**
     * More than one file provided on the command line
     */
    public static final int STATUS_ERR_TOO_MUCH_FILES = -2;

    /**
     * An error occurred during reading or writing the data
     */
    public static final int STATUS_ERR_IO = -3;

    /**
     * An error occurred during the execution of the engine
     */
    public static final int STATUS_ERR_EXECUTION = -4;

    /**
     * @param args Exit with the appropriate code<br>
     *             ie. App.STATUS_*
     */
    public static void main(String... args) {
        AppMower app = new AppMower();
        int iStatus = app.execute(args);
        System.exit(iStatus);
    }

    /**
     * @param args
     * @return the OS exit code
     */
    public int execute(String... args) {
        if ((args == null) || (args.length < 1)) {
            return STATUS_ERR_NO_FILE;
        }

        if (args.length > 1) {
            return STATUS_ERR_TOO_MUCH_FILES;
        }

        return loadAndRun(args[0]);
    }

    /**
     * @param fileName
     * @return the OS exit code
     */
    private int loadAndRun(String fileName) {
        int iStatus = STATUS_OK;
        try {
            final File f = new File(fileName);
            /**
             * An EngineLoaderFactory could be done to enable transparent multiple source loading (or input format detection).
             */
            IEngineReader reader = new FlatTextReader() {
                @Override
                public InputStream getInputStream() throws IOException {
                    return new FileInputStream(f);
                }
            };

            Engine engine = reader.readEngine();
            engine.run();
            IEngineWriter writer = new FlatTextWriter() {
                @Override
                public OutputStream getOutputStream() throws IOException {
                    return System.out;
                }
            };
            writer.writeEngine(engine);
        } catch (TransferException e) {
            // Reading or writing error
            iStatus = STATUS_ERR_IO;
        } catch (Exception e) {
            // Another error happened
            iStatus = STATUS_ERR_EXECUTION;
        }

        return iStatus;
    }
}
