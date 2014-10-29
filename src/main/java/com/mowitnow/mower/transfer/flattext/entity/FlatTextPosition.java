package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.transfer.flattext.FlatTextLineParams;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextWriter;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextPositionFormatException;

/**
 * Decorator to read a Position
 * <p/>
 * Created by ffradet on 20/10/2014.
 */
public class FlatTextPosition {

    /**
     * @param reader
     * @return
     */
    public static Position read(FlatTextReader reader) {
        FlatTextLineParams params = reader.getNextLineParams();

        Position position = null;
        int paramIndex = 0;
        try {
            double x = Double.parseDouble(params.getNextParam());
            double y = Double.parseDouble(params.getNextParam());
            position = new Position(x, y);
        } catch (NumberFormatException e) {
            throw new FlatTextPositionFormatException(e);
        }

        return position;
    }

    /**
     * @param position
     * @param writer
     */
    public static void write(Position position, FlatTextWriter writer) {
        // [position as rounded integer values]
        writer.write(String.valueOf((int) Math.round(position.getX())));
        writer.write(String.valueOf((int) Math.round(position.getY())));
    }

}
