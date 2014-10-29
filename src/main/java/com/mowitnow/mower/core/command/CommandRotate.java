package com.mowitnow.mower.core.command;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;

/**
 * Created by ffradet on 17/10/2014.
 */
public class CommandRotate implements ICommand {

    private Orientation orientationRelative;


    /**
     * @param orientationRelative
     */
    public CommandRotate(Orientation orientationRelative) {
        this.orientationRelative = orientationRelative;
    }


    /**
     * @return
     */
    public Orientation getOrientationRelative() {
        return orientationRelative;
    }

    @Override
    public GeoLoc execute(IActor actor) {
        return new GeoLoc(actor.getGeoLoc().getPosition(), actor.getGeoLoc().getOrientation().rotate(orientationRelative));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CommandRotate{");
        sb.append("orientationRelative=").append(orientationRelative);
        sb.append('}');
        return sb.toString();
    }
}
