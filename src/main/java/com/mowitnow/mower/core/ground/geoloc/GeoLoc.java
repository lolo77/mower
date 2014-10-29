package com.mowitnow.mower.core.ground.geoloc;

/**
 * This class is a container for Position ans Orientation<br>
 * <p/>
 * Created by ffradet on 19/10/2014.
 */
public class GeoLoc {

    private Position position;
    private Orientation orientation;

    /**
     *
     */
    public GeoLoc() {
        this(null, null);
    }

    /**
     * @param position
     * @param orientation
     */
    public GeoLoc(Position position, Orientation orientation) {
        this.position = position;
        this.orientation = orientation;
    }

    /**
     * @param other
     */
    public GeoLoc(GeoLoc other) {
        copyFrom(other);
    }

    /**
     * @param other
     */
    public void copyFrom(GeoLoc other) {
        position = new Position(other.getPosition());
        orientation = new Orientation(other.getOrientation());
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GeoLoc{");
        sb.append("position=").append(position);
        sb.append(", orientation=").append(orientation);
        sb.append('}');
        return sb.toString();
    }
}
