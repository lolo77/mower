package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.ground.GroundGrid;
import com.mowitnow.mower.core.ground.IGround;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.core.ground.shape.GroundShapeAxisOrientedBox;
import com.mowitnow.mower.core.ground.shape.IGroundShape;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextGroundFormatException;

/**
 * Ground entity
 * <p/>
 * The specified file format restricts the shape to
 * an axis-oriented rectangle (i.e. a GroundShapeAxisOrientedBox instance)
 * managed as a grid (i.e. a GroundGrid instance)
 * <p/>
 * Created by ffradet on 21/10/2014.
 */
public class FlatTextGround {

    /**
     * @param reader
     * @return
     */
    public static IGround read(FlatTextReader reader) {
        // [position]
        Position extentMax = FlatTextPosition.read(reader);
        if (!reader.isLineParamsConsumed()) {
            throw new FlatTextGroundFormatException("Malformed ground declaration line : extra parameter found");
        }

        IGroundShape groundShape = new GroundShapeAxisOrientedBox(extentMax);
        IGround ground = new GroundGrid(groundShape);

        return ground;

    }

}
