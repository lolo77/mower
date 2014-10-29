package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.ground.actor.ActorMower;
import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.transfer.exception.TransferException;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextWriter;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextActorFormatException;

/**
 * Created by ffradet on 23/10/2014.
 */
public class FlatTextActor {

    /**
     * Read [position] [orientation] on the same line
     *
     * @param reader
     * @return an ActorMower instance
     */
    public static IActor read(FlatTextReader reader) {
        try {
            Position initialPosition = FlatTextPosition.read(reader);
            Orientation initialOrientation = FlatTextOrientation.read(reader);

            IActor actor = new ActorMower();
            actor.initGeoLoc(initialPosition, initialOrientation);

            return actor;
        } catch (TransferException e) {
            // Encapsulate the source exception
            throw new FlatTextActorFormatException(e);
        }
    }

    /**
     * Write [position] [orientation] on the same line
     *
     * @param actor
     * @param writer
     * @return
     */
    public static void write(IActor actor, FlatTextWriter writer) {
        GeoLoc geoLoc = actor.getGeoLoc();
        FlatTextPosition.write(geoLoc.getPosition(), writer);
        FlatTextOrientation.write(geoLoc.getOrientation(), writer);
    }
}
