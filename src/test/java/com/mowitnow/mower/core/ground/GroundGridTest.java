package com.mowitnow.mower.core.ground;

import com.mowitnow.mower.core.ground.actor.ActorMower;
import com.mowitnow.mower.core.ground.exception.GroundAttachException;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.core.ground.shape.GroundShapeAxisOrientedBox;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ffradet on 28/10/2014.
 */
public class GroundGridTest {

    @Test
    public void testGridAttachOneActor() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        Assert.assertTrue(grid.getLstActors().size() == 1);
    }

    @Test
    public void testGridAttachTwoActors() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        ActorMower act2 = new ActorMower();
        act2.initGeoLoc(new Position(2, 2), Orientation.DEG_0);
        grid.attachActor(act2);

        Assert.assertTrue(grid.getLstActors().size() == 2);
    }

    @Test(expected = GroundAttachException.class)
    public void testGridAttachThreeActorsCollision() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        ActorMower act2 = new ActorMower();
        act2.initGeoLoc(new Position(2, 2), Orientation.DEG_0);
        grid.attachActor(act2);

        ActorMower act3 = new ActorMower();
        act3.initGeoLoc(new Position(2, 2), Orientation.DEG_0);
        grid.attachActor(act3);
    }

    @Test
    public void testGridNearestValidGeoLoc() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        ActorMower act2 = new ActorMower();
        act2.initGeoLoc(new Position(2, 2), Orientation.DEG_0);
        grid.attachActor(act2);

        GeoLoc newGeoLoc = new GeoLoc(act2.getGeoLoc());
        newGeoLoc.setPosition(new Position(1, 1));
        newGeoLoc = grid.getNearestValidGeoLoc(act2, newGeoLoc); // Try to place act2 over act1

        Assert.assertTrue(newGeoLoc.getPosition().isEqualsTo(new Position(2, 2))); // act2 should remain at the same position (2, 2)

        newGeoLoc.setPosition(new Position(-1, 1));
        newGeoLoc = grid.getNearestValidGeoLoc(act2, newGeoLoc); // Try to place act2 out of the groundShape
        Assert.assertTrue(newGeoLoc.getPosition().isEqualsTo(new Position(0, 1))); // act2 should move to the border of the groundShape : position (0, 1)

        newGeoLoc.setPosition(new Position(2, 1));
        newGeoLoc = grid.getNearestValidGeoLoc(act2, newGeoLoc); // Try to place act2 on an empty cell
        Assert.assertTrue(newGeoLoc.getPosition().isEqualsTo(new Position(2, 1))); // act2 should move to the new position (2, 1)
    }

    @Test
    public void testGridUpdateActorPosition() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        grid.onUpdateActor(act1, new GeoLoc(new Position(2, 2), Orientation.DEG_0));

        Assert.assertTrue(act1.getGeoLoc().getPosition().equals(new Position(2, 2)));
        Assert.assertTrue(act1.getGeoLoc().getOrientation().getNearest().equals(Orientation.DEG_0));
    }

    @Test
    public void testGridUpdateActorOrientation() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        grid.onUpdateActor(act1, new GeoLoc(new Position(1, 1), Orientation.DEG_180));

        Assert.assertTrue(act1.getGeoLoc().getPosition().equals(new Position(1, 1)));
        Assert.assertTrue(act1.getGeoLoc().getOrientation().getNearest().equals(Orientation.DEG_180));
    }

    @Test
    public void testGridUpdateActorPositionOrientation() {
        GroundShapeAxisOrientedBox shape = new GroundShapeAxisOrientedBox(new Position(5, 5));
        GroundGrid grid = new GroundGrid(shape);

        ActorMower act1 = new ActorMower();
        act1.initGeoLoc(new Position(1, 1), Orientation.DEG_0);
        grid.attachActor(act1);

        grid.onUpdateActor(act1, new GeoLoc(new Position(2, 2), Orientation.DEG_180));

        Assert.assertTrue(act1.getGeoLoc().getPosition().equals(new Position(2, 2)));
        Assert.assertTrue(act1.getGeoLoc().getOrientation().getNearest().equals(Orientation.DEG_180));
    }

}
