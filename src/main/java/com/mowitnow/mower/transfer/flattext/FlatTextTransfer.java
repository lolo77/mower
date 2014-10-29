package com.mowitnow.mower.transfer.flattext;

/**
 * Created by ffradet on 23/10/2014.
 */
public class FlatTextTransfer {
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * @return the input charset name according to the defined input stream<br>
     * default is 'UTF-8'
     */
    public String getCharsetName() {
        return DEFAULT_CHARSET;
    }
}
