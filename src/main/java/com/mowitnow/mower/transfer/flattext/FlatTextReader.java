package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.core.engine.EngineActor;
import com.mowitnow.mower.core.ground.IGround;
import com.mowitnow.mower.transfer.IEngineReader;
import com.mowitnow.mower.transfer.exception.TransferException;
import com.mowitnow.mower.transfer.flattext.entity.FlatTextEngineActor;
import com.mowitnow.mower.transfer.flattext.entity.FlatTextGround;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This reader implements the following file format :
 * Line 1 : Ground max extents : X Y
 * Line 2 : Actor initial state : X Y Orientation
 * Line 3 : Actor command sequence [AGD] : commands
 * Optional couples like lines 2 & 3 can follow.
 * <p/>
 * Empty lines are ignored.
 * A line is first trimmed and is then split with "\s+" regex (supports Space and Tab as separators).
 * <p/>
 * Created by ffradet on 18/10/2014.
 */
public abstract class FlatTextReader extends FlatTextTransfer implements IEngineReader {

    private BufferedReader reader = null;
    private FlatTextLineParams currentLineParams = null;

    /**
     *
     */
    public FlatTextReader() {
    }


    /**
     * @throws IOException
     */
    private void ensureStreamIsOpen() throws IOException {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(getInputStream(), getCharsetName()));
        }
    }

    /**
     * Read the next line from the input stream.<br>
     * The line is trimmed.<br>
     * Empty lines are bypassed.<br>
     *
     * @return
     * @throws IOException
     */
    private String readNextLine() throws IOException {
        ensureStreamIsOpen();

        String s = "";
        while (s.length() == 0) {
            s = reader.readLine();

            if (s == null) {
                return null;
            }

            s = s.trim();
        }

        return s;
    }

    /**
     * Read the next line from the input stream
     *
     * @return
     */
    private FlatTextLineParams readNextParams() throws TransferException {
        currentLineParams = null;
        try {
            String nextLine = readNextLine();

            if (nextLine == null) {
                return null;
            }
            currentLineParams = new FlatTextLineParams(nextLine);
        } catch (IOException e) {
            throw new TransferException(e);
        }
        return currentLineParams;
    }

    /**
     * @return the next line params if the current is totally consumed<br>
     * the current if at least one param remains.
     */
    public FlatTextLineParams getNextLineParams() throws TransferException {

        if (isLineParamsConsumed()) {
            // Line entirely parsed : proceed with the next line from the input stream
            currentLineParams = readNextParams();
        }
        return currentLineParams;
    }

    /**
     * @return
     */
    public boolean isLineParamsConsumed() {
        return (currentLineParams == null) || (!currentLineParams.hasParam());
    }

    /**
     * @return true if some parameters remains unconsumed
     */
    private boolean hasParameters() {
        try {
            ensureStreamIsOpen();
            getNextLineParams();
        } catch (Exception e) {
            return false;
        }
        return (reader != null) && (currentLineParams != null);
    }

    /**
     * Load the engine from the input stream
     *
     * @return an Engine instance
     */
    @Override
    public Engine readEngine() throws TransferException {
        try {
            IGround ground = FlatTextGround.read(this);
            Engine engine = new Engine(ground);

            while (hasParameters()) {
                EngineActor engineActor = FlatTextEngineActor.read(this);
                engineActor.getActor().setGround(ground);
                engine.addEngineActor(engineActor);
            }

            return engine;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                    reader = null;
                }
            } catch (IOException e) {
                throw new TransferException(e);
            }
        }
    }
}
