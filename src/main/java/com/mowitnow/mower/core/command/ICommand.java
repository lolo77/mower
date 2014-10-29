package com.mowitnow.mower.core.command;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;

/**
 * Created by ffradet on 17/10/2014.
 */
public interface ICommand {

    public GeoLoc execute(IActor actor);

}
