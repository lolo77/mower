package com.mowitnow.mower.transfer.flattext.exception;

import com.mowitnow.mower.transfer.exception.TransferException;

/**
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextPositionFormatException extends TransferException {

    public FlatTextPositionFormatException(String message) {
        super(message);
    }

    public FlatTextPositionFormatException(Throwable cause) {
        super(cause);
    }

    public FlatTextPositionFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
