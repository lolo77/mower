package com.mowitnow.mower.core.ground.geoloc;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ffradet on 18/10/2014.
 */
public class OrientationTest {

    @Test
    public void testNearest() {
        Orientation angle;
        angle = new Orientation(Math.toRadians(314));
        Assert.assertFalse(angle.getNearest().equals(Orientation.DEG_0));

        angle = new Orientation(Math.toRadians(316));
        Assert.assertTrue(angle.getNearest().equals(Orientation.DEG_0));

        angle = new Orientation(Math.toRadians(-46));
        Assert.assertFalse(angle.getNearest().equals(Orientation.DEG_0));

        angle = new Orientation(Math.toRadians(-44));
        Assert.assertTrue(angle.getNearest().equals(Orientation.DEG_0));

        angle = new Orientation(Double.NaN);
        Assert.assertTrue(angle.getNearest() == null);

        angle = new Orientation(Double.POSITIVE_INFINITY);
        Assert.assertTrue(angle.getNearest() == null);

        angle = new Orientation(Double.NEGATIVE_INFINITY);
        Assert.assertTrue(angle.getNearest() == null);
    }
}
