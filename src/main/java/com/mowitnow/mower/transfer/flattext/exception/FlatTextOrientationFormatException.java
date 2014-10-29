package com.mowitnow.mower.transfer.flattext.exception;

import com.mowitnow.mower.transfer.exception.TransferException;

/**
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextOrientationFormatException extends TransferException {

    public FlatTextOrientationFormatException(String message) {
        super(message);
    }

    public FlatTextOrientationFormatException(Throwable cause) {
        super(cause);
    }

    public FlatTextOrientationFormatException(String message, Throwable cause) {
        super(message, cause);
    }

}
