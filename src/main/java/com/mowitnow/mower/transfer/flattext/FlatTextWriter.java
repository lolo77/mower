package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.core.engine.EngineActor;
import com.mowitnow.mower.transfer.IEngineWriter;
import com.mowitnow.mower.transfer.exception.TransferException;
import com.mowitnow.mower.transfer.flattext.entity.FlatTextEngineActor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created by ffradet on 23/10/2014.
 */
public abstract class FlatTextWriter extends FlatTextTransfer implements IEngineWriter {

    public static final String EOL = System.getProperty("line.separator");
    public static final String DEFAULT_SEPARATOR = " ";

    private BufferedWriter writer = null;
    private FlatTextLineParams currentLine = null;

    /**
     * @throws java.io.IOException
     */
    private void ensureStreamIsOpen() throws IOException {
        if (writer == null) {
            writer = new BufferedWriter(new OutputStreamWriter(getOutputStream(), getCharsetName()));
        }
    }

    /**
     * @return
     */
    public String getSeparator() {
        return DEFAULT_SEPARATOR;
    }

    /**
     *
     */
    public void newLine() {
        if (currentLine != null) {
            flushLine();
            currentLine = new FlatTextLineParams();
        }
    }

    /**
     *
     */

    private void flushLine() throws TransferException {
        if (currentLine != null) {
            try {
                ensureStreamIsOpen();
                String s = currentLine.join(getSeparator());
                writer.write(s);
                writer.write(EOL);
            } catch (IOException e) {
                throw new TransferException(e);
            }
        }
    }

    /**
     * @param s
     */
    public void write(String s) {
        if (currentLine == null) {
            currentLine = new FlatTextLineParams();
        }
        currentLine.addParams(s);
    }

    /**
     *
     */
    private void close() throws TransferException {
        if (writer != null) {
            try {
                writer.close();
                writer = null;
            } catch (Exception e) {
                throw new TransferException(e);
            }
        }
    }


    @Override
    public void writeEngine(Engine engine) throws TransferException {
        List<EngineActor> engineActorList = engine.getLstEngineActors();
        for (EngineActor engineActor : engineActorList) {
            FlatTextEngineActor.write(engineActor, this);
        }

        close();
    }
}
