package com.mowitnow.mower.transfer.flattext.exception;

import com.mowitnow.mower.transfer.exception.TransferException;

/**
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextEngineActorFormatException extends TransferException {
    public FlatTextEngineActorFormatException(String message) {
        super(message);
    }

    public FlatTextEngineActorFormatException(Throwable cause) {
        super(cause);
    }

    public FlatTextEngineActorFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
