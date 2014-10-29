package com.mowitnow.mower.core.ground.shape;

import com.mowitnow.mower.core.ground.geoloc.Position;

/**
 * Define the ground shape.
 * <p/>
 * Created by ffradet on 18/10/2014.
 */
public interface IGroundShape {

    /**
     * @param pos
     * @return the nearest position projected to the limit
     */
    public Position getNearestValidPosition(Position pos);
}
