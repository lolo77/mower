package com.mowitnow.mower.transfer.flattext.exception;

import com.mowitnow.mower.transfer.exception.TransferException;

/**
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextActorFormatException extends TransferException {

    public FlatTextActorFormatException(String message) {
        super(message);
    }

    public FlatTextActorFormatException(Throwable cause) {
        super(cause);
    }

    public FlatTextActorFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
