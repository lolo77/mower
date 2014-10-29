package com.mowitnow.mower.core.ground.actor;

import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.ground.IGround;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.core.ground.geoloc.Position;

/**
 * Created by ffradet on 17/10/2014.
 */
public interface IActor {

    /**
     * @return the current actor GeoLoc (position & orientation)
     */
    public GeoLoc getGeoLoc();

    /**
     * @param ground
     */
    public void setGround(IGround ground);

    /**
     * @param position
     * @param orientation
     */
    public void initGeoLoc(Position position, Orientation orientation);

    /**
     * @param command
     */
    public void execute(ICommand command);

}
