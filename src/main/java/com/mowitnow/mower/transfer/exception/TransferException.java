package com.mowitnow.mower.transfer.exception;

/**
 * General purpose read & write exception
 * <p/>
 * Created by ffradet on 20/10/2014.
 */
public class TransferException extends RuntimeException {

    public TransferException(String message) {
        super(message);
    }

    public TransferException(Throwable cause) {
        super(cause);
    }

    public TransferException(String message, Throwable cause) {
        super(message, cause);
    }
}
