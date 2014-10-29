package com.mowitnow.mower.core.command;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Position;

/**
 * Created by ffradet on 17/10/2014.
 */
public class CommandMove implements ICommand {

    private long speed;

    /**
     * @param speed
     */
    public CommandMove(long speed) {
        this.speed = speed;
    }


    /**
     * @return
     */
    public long getSpeed() {
        return speed;
    }


    @Override
    public GeoLoc execute(IActor actor) {
        Position relativeMovement = Position.fromPolar(actor.getGeoLoc().getOrientation().getAngleRad(), getSpeed());
        return new GeoLoc(actor.getGeoLoc().getPosition().add(relativeMovement), actor.getGeoLoc().getOrientation());
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommandMove{");
        sb.append("speed=").append(speed);
        sb.append('}');
        return sb.toString();
    }
}
