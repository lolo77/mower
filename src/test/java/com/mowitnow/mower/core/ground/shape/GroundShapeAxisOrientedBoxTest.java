package com.mowitnow.mower.core.ground.shape;

import com.mowitnow.mower.core.ground.geoloc.Position;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ffradet on 28/10/2014.
 */
public class GroundShapeAxisOrientedBoxTest {

    @Test
    public void testNearest() {
        Position maxExtents = new Position(4, 4);
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(maxExtents);

        Position posNearest = shape.getNearestValidPosition(new Position(5, 2));
        Assert.assertTrue(posNearest.equals(new Position(4, 2)));

        posNearest = shape.getNearestValidPosition(new Position(5, 5));
        Assert.assertTrue(posNearest.equals(new Position(4, 4)));

        posNearest = shape.getNearestValidPosition(new Position(2, 5));
        Assert.assertTrue(posNearest.equals(new Position(2, 4)));

        posNearest = shape.getNearestValidPosition(new Position(-1, 5));
        Assert.assertTrue(posNearest.equals(new Position(0, 4)));

        posNearest = shape.getNearestValidPosition(new Position(-1, 2));
        Assert.assertTrue(posNearest.equals(new Position(0, 2)));

        posNearest = shape.getNearestValidPosition(new Position(-1, -1));
        Assert.assertTrue(posNearest.equals(new Position(0, 0)));

        posNearest = shape.getNearestValidPosition(new Position(2, -1));
        Assert.assertTrue(posNearest.equals(new Position(2, 0)));

        posNearest = shape.getNearestValidPosition(new Position(5, -1));
        Assert.assertTrue(posNearest.equals(new Position(4, 0)));

        posNearest = shape.getNearestValidPosition(new Position(2, 2));
        Assert.assertTrue(posNearest.equals(new Position(2, 2)));
    }
}
