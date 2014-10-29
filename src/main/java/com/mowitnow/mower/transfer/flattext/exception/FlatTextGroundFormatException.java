package com.mowitnow.mower.transfer.flattext.exception;

import com.mowitnow.mower.transfer.exception.TransferException;

/**
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextGroundFormatException extends TransferException {

    public FlatTextGroundFormatException(String message) {
        super(message);
    }

    public FlatTextGroundFormatException(Throwable cause) {
        super(cause);
    }

    public FlatTextGroundFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
