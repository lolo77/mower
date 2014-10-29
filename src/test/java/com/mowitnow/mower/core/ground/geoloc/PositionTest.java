package com.mowitnow.mower.core.ground.geoloc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ffradet on 28/10/2014.
 */
public class PositionTest {

    @Test
    public void testPolar() {
        Position pos = Position.fromPolar(Math.toRadians(180), 1000);
        Assert.assertTrue(Math.round(pos.getX()) == -1000);
        Assert.assertTrue(Math.round(pos.getY()) == 0);

        pos = Position.fromPolar(Math.toRadians(45), 1000);
        Assert.assertTrue(Math.round(pos.getX()) == 707);
        Assert.assertTrue(Math.round(pos.getY()) == 707);
    }

    @Test
    public void testEquals() {
        Position pos = new Position(0.1, 1.0);
        Assert.assertTrue(pos.equals(new Position(1.0 / 10.0, 10.0 / 10.0)));
    }

    @Test
    public void testGreaterThan() {
        Position pos = new Position(0.0, 0.0);
        Assert.assertTrue(pos.isGreaterThan(new Position(0.0, -1.0)));
        Assert.assertTrue(pos.isGreaterThan(new Position(-1.0, 0.0)));
    }

    @Test
    public void testLowerThan() {
        Position pos = new Position(0.0, 0.0);
        Assert.assertTrue(pos.isLowerThan(new Position(0.0, 1.0)));
        Assert.assertTrue(pos.isLowerThan(new Position(1.0, 0.0)));
    }

    @Test
    public void testMin() {
        Position pos1 = new Position(0.0, 0.0);
        Position pos2 = new Position(1.0, 1.0);
        Assert.assertTrue(pos1.min(pos2).equals(pos1));
        Assert.assertFalse(pos1.min(pos1).equals(pos2));
        Assert.assertTrue(pos1.min(pos1).equals(pos1));
    }


    @Test
    public void testMax() {
        Position pos1 = new Position(0.0, 0.0);
        Position pos2 = new Position(1.0, 1.0);
        Assert.assertFalse(pos1.max(pos2).equals(pos1));
        Assert.assertTrue(pos2.max(pos1).equals(pos2));
        Assert.assertTrue(pos1.max(pos1).equals(pos1));
    }

    @Test
    public void testAdd() {
        Position pos1 = new Position(1.0, 2.0);
        Position pos2 = new Position(3.0, 4.0);
        Position pos3 = new Position(4.0, 6.0);
        Assert.assertTrue(pos1.add(pos2).equals(pos3));
        Assert.assertFalse(pos1.add(pos3).equals(pos2));
    }
}
