package com.mowitnow.mower.core.engine;

import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.ground.GroundGrid;
import com.mowitnow.mower.core.ground.IGround;
import com.mowitnow.mower.core.ground.exception.GroundAttachException;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.core.ground.shape.GroundShapeAxisOrientedBox;
import com.mowitnow.mower.core.ground.shape.IGroundShape;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextReaderTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by ffradet on 28/10/2014.
 */
public class EngineTest {
    @Test
    public void testEngineOneActor() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n \n1 2 n\nGAGAGAgaa");
        Engine engine = reader.readEngine();

        IGround ground = engine.getGround();
        Assert.assertTrue(ground instanceof GroundGrid);

        IGroundShape groundShape = ground.getGroundShape();
        Assert.assertTrue(groundShape instanceof GroundShapeAxisOrientedBox);
        Assert.assertTrue(((GroundShapeAxisOrientedBox) groundShape).getExtentMax().equals(new Position(5, 5)));

        List<EngineActor> lstActors = engine.getLstEngineActors();
        Assert.assertTrue(lstActors.size() == 1);

        EngineActor engineActor = lstActors.get(0);

        GeoLoc actorGeoLoc = engineActor.getActor().getGeoLoc();
        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(1, 2)));
        Assert.assertTrue(actorGeoLoc.getOrientation().equals(Orientation.DEG_90));

        List<ICommand> lstCommands = engineActor.getLstCommands();
        Assert.assertTrue(lstCommands.size() == 9);

        engine.run();

        actorGeoLoc = engineActor.getActor().getGeoLoc();
        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(1, 3)));
        Assert.assertTrue(actorGeoLoc.getOrientation().getNearest().equals(Orientation.DEG_90));
    }

    @Test
    public void testEngineTwoActorsSequential() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n"
                + "\r"
                + "1 2 N\n"
                + "GAGaGAGAA\n" +
                "3 3 e\n\r" +
                "\r\n" +
                "AADAADAdDA\n");

        Engine engine = reader.readEngine();

        IGround ground = engine.getGround();
        Assert.assertTrue(ground instanceof GroundGrid);

        IGroundShape groundShape = ground.getGroundShape();
        Assert.assertTrue(groundShape instanceof GroundShapeAxisOrientedBox);
        Assert.assertTrue(((GroundShapeAxisOrientedBox) groundShape).getExtentMax().equals(new Position(5, 5)));

        List<EngineActor> lstActors = engine.getLstEngineActors();
        Assert.assertTrue(lstActors.size() == 2);

        EngineActor engineActor = lstActors.get(0);

        GeoLoc actorGeoLoc = engineActor.getActor().getGeoLoc();
        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(1, 2)));
        Assert.assertTrue(actorGeoLoc.getOrientation().equals(Orientation.DEG_90));

        engineActor = lstActors.get(1);
        actorGeoLoc = engineActor.getActor().getGeoLoc();
        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(3, 3)));
        Assert.assertTrue(actorGeoLoc.getOrientation().equals(Orientation.DEG_0));

        List<ICommand> lstCommands = engineActor.getLstCommands();
        Assert.assertTrue(lstCommands.size() == 10);

        engine.run();

        actorGeoLoc = engineActor.getActor().getGeoLoc();
        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(5, 1)));
        Assert.assertTrue(actorGeoLoc.getOrientation().getNearest().equals(Orientation.DEG_0));
    }


    @Test
    public void testEngineOneActorOutOfGrid() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n \n1 2 N\nAAAAGAAGAAAAAA");
        Engine engine = reader.readEngine();

        engine.run();

        List<EngineActor> lstActors = engine.getLstEngineActors();
        Assert.assertTrue(lstActors.size() == 1);
        EngineActor engineActor = lstActors.get(0);
        GeoLoc actorGeoLoc = engineActor.getActor().getGeoLoc();

        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(0, 0)));
        Assert.assertTrue(actorGeoLoc.getOrientation().getNearest().equals(Orientation.DEG_270));
    }

    @Test
    public void testEngineTwoActorsCollisionSequential() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n \n1 2 N\nAAAAGAAGAAAAAA\n0 1 E\nAAAAAA");
        Engine engine = reader.readEngine();

        engine.run();

        List<EngineActor> lstActors = engine.getLstEngineActors();
        Assert.assertTrue(lstActors.size() == 2);

        EngineActor engineActor = lstActors.get(0);
        GeoLoc actorGeoLoc = engineActor.getActor().getGeoLoc();

        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(0, 2)));
        Assert.assertTrue(actorGeoLoc.getOrientation().getNearest().equals(Orientation.DEG_270));

        engineActor = lstActors.get(1);
        actorGeoLoc = engineActor.getActor().getGeoLoc();

        Assert.assertTrue(actorGeoLoc.getPosition().equals(new Position(5, 1)));
        Assert.assertTrue(actorGeoLoc.getOrientation().getNearest().equals(Orientation.DEG_0));
    }

    @Test(expected = GroundAttachException.class)
    public void testEngineTwoActorsCollisionInit() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n \n1 2 N\nADADA\n1 2 E\nADA");
        Engine engine = reader.readEngine();
    }
}
