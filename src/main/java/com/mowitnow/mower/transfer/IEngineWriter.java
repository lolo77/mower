package com.mowitnow.mower.transfer;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.transfer.exception.TransferException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ffradet on 23/10/2014.
 */
public interface IEngineWriter {
    /**
     * @return
     * @throws IOException
     */
    public OutputStream getOutputStream() throws IOException;

    /**
     * @param engine
     * @throws TransferException
     */
    public void writeEngine(Engine engine) throws TransferException;
}
