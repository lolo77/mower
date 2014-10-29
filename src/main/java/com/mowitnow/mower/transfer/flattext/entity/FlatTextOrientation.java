package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.transfer.flattext.FlatTextLineParams;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextWriter;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextOrientationFormatException;

/**
 * Mapping code -> Orientation
 * <p/>
 * Created by ffradet on 19/10/2014.
 */
public enum FlatTextOrientation {

    EAST("E", Orientation.DEG_0),
    NORTH("N", Orientation.DEG_90),
    WEST("W", Orientation.DEG_180),
    SOUTH("S", Orientation.DEG_270);

    private String code;
    private Orientation orientation;

    /**
     * @param _code
     * @param _orientation
     */
    private FlatTextOrientation(String _code, Orientation _orientation) {
        code = _code;
        orientation = _orientation;
    }

    /**
     * @param reader
     * @return
     */
    public static Orientation read(FlatTextReader reader) {
        FlatTextLineParams params = reader.getNextLineParams();

        String code = params.getNextParam();

        // Look for predefined instance
        FlatTextOrientation instance = FlatTextOrientation.fromCode(code);
        if (instance == null) {
            throw new FlatTextOrientationFormatException("FlatTextOrientation unknown : code=" + code);
        }

        return instance.getOrientation();
    }


    /**
     * @param orientation
     * @param writer
     */
    public static void write(Orientation orientation, FlatTextWriter writer) {
        FlatTextOrientation instance = FlatTextOrientation.fromOrientation(orientation.getNearest());
        if (instance == null) {
            // Should never happen until Orientation.ALL_VALUES is empty !
            throw new FlatTextOrientationFormatException("Nearest Orientation instance not found : angleRad=" + orientation.getAngleRad());
        }
        writer.write(String.valueOf(instance.getCode()));
    }

    /**
     * @param code
     * @return
     */
    public static FlatTextOrientation fromCode(String code) {
        // Look for predefined instance
        FlatTextOrientation instance = null;
        for (FlatTextOrientation co : values()) {
            if (co.getCode().equalsIgnoreCase(code)) {
                instance = co;
                break;
            }
        }
        return instance;
    }

    /**
     * @param orientation
     * @return
     */
    public static FlatTextOrientation fromOrientation(Orientation orientation) {
        // Look for predefined instance
        FlatTextOrientation instance = null;
        for (FlatTextOrientation co : values()) {
            if (co.getOrientation().equals(orientation)) {
                instance = co;
                break;
            }
        }
        return instance;
    }

    /**
     * @return
     */
    public String getCode() {
        return code;
    }

    /**
     * @return
     */
    public Orientation getOrientation() {
        return orientation;
    }
}
