package com.mowitnow.mower.transfer;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.transfer.exception.TransferException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ffradet on 20/10/2014.
 */
public interface IEngineReader {
    /**
     * @return
     * @throws IOException
     */
    public InputStream getInputStream() throws IOException;

    /**
     * @return
     * @throws TransferException
     */
    public Engine readEngine() throws TransferException;
}
