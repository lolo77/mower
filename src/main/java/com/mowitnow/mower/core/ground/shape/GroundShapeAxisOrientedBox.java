package com.mowitnow.mower.core.ground.shape;

import com.mowitnow.mower.core.ground.geoloc.Position;

/**
 * Axis-Oriented Box<br>
 * extentMax represents the maximum valid Position.
 * <p/>
 * Created by ffradet on 18/10/2014.
 */
public class GroundShapeAxisOrientedBox implements IGroundShape {
    private Position extentMax;

    /**
     *
     */
    public GroundShapeAxisOrientedBox(Position extentMax) {
        this.extentMax = extentMax;
    }

    /**
     * @return
     */
    public Position getExtentMax() {
        return extentMax;
    }

    @Override
    public Position getNearestValidPosition(Position pos) {
        Position newPos = pos.max(Position.ORIGIN);
        newPos = newPos.min(extentMax);
        return newPos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroundShapeAOBox{");
        sb.append("extentMax=").append(extentMax);
        sb.append('}');
        return sb.toString();
    }
}
