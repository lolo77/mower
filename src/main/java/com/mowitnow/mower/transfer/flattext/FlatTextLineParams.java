package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.transfer.exception.TransferException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hold a sequence of strings as an array of parameters
 * <p/>
 * Created by ffradet on 21/10/2014.
 */
public class FlatTextLineParams {

    /*
     * Use whitespaces as default separator
     */
    private static final String DEFAULT_REGEXP_SEPARATOR = "\\s+";

    private List<String> params;


    /**
     * @param line
     * @param regExpSeparator
     */
    public FlatTextLineParams(String line, String regExpSeparator) {
        String sTrimmedLine = (line != null) ? line.trim() : null;

        if ((regExpSeparator == null) || (sTrimmedLine == null) || (sTrimmedLine.length() == 0)) {
            params = new ArrayList<String>();
        } else {
            params = new ArrayList<String>(Arrays.asList(sTrimmedLine.split(regExpSeparator)));
        }
    }

    /**
     * @param line
     */
    public FlatTextLineParams(String line) {
        this(line, DEFAULT_REGEXP_SEPARATOR);
    }

    /**
     *
     */
    public FlatTextLineParams() {
        this(null, null);
    }

    /**
     * @return
     */
    public boolean hasParam() {
        return params.size() > 0;
    }

    /**
     * @return
     */
    public String getNextParam() {
        String s = null;

        if (params.size() > 0) {
            s = params.get(0);
            params.remove(0);
        } else {
            throw new TransferException("Tried to fetch more params than existing !");
        }
        return s;
    }

    /**
     * @param params
     * @return this
     */
    public FlatTextLineParams addParams(String... params) {
        this.params.addAll(Arrays.asList(params));
        return this;
    }

    /**
     * @param separator
     * @return
     */
    public String join(String separator) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String s : params) {
            if (!first) {
                sb.append(separator);
            } else {
                first = false;
            }
            sb.append(s);
        }

        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FlatTextLineParams{");
        sb.append("params=").append(params);
        sb.append('}');
        return sb.toString();
    }
}
