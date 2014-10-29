package com.mowitnow.mower.core.ground.actor;

import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.ground.IGround;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Orientation;
import com.mowitnow.mower.core.ground.geoloc.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Base class for every actor
 * <p/>
 * Created by ffradet on 17/10/2014.
 */
public abstract class ActorAbstract implements IActor {

    private static final Logger LOG = LoggerFactory.getLogger(ActorAbstract.class);

    private static final Position DEFAULT_POSITION = new Position(0, 0);
    private static final Orientation DEFAULT_ORIENTATION = Orientation.DEG_90;

    private final GeoLoc geoLoc = new GeoLoc();
    private IGround ground = null;

    /**
     *
     */
    public ActorAbstract() {
        reset();
    }

    /**
     * @return
     */
    protected Orientation defaultOrientation() {
        return DEFAULT_ORIENTATION;
    }

    /**
     * @return
     */
    protected Position defaultPosition() {
        return DEFAULT_POSITION;
    }

    /**
     * @return
     */
    protected GeoLoc defaultGeoLoc() {
        return new GeoLoc(defaultPosition(), defaultOrientation());
    }

    /**
     *
     */
    public void reset() {
        GeoLoc defaultGeoLoc = defaultGeoLoc();
        geoLoc.copyFrom(defaultGeoLoc);
    }

    @Override
    public void initGeoLoc(Position position, Orientation orientation) {
        geoLoc.setPosition(position);
        geoLoc.setOrientation(orientation);
    }


    /**
     * @param ground
     */
    @Override
    public void setGround(IGround ground) {
        this.ground = ground;
    }

    /**
     * @param command
     */
    @Override
    public void execute(ICommand command) {
        LOG.debug("execute command {}", command);
        GeoLoc newGeoLoc = command.execute(this);
        ground.onUpdateActor(this, newGeoLoc);
    }

    @Override
    public GeoLoc getGeoLoc() {
        return geoLoc;
    }

    /**
     * Do not output <code>ground</code> to avoid cycling.
     *
     * @return
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActorAbstract{");
        sb.append("geoLoc=").append(geoLoc);
        sb.append('}');
        return sb.toString();
    }
}
