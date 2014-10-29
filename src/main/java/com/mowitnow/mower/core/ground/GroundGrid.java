package com.mowitnow.mower.core.ground;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.exception.GroundAttachException;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.core.ground.shape.IGroundShape;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represent the space as a multi-dimensional grid (n-dimensions according to Position)
 * The space represented by this class can be any combination of cells (ie. not only 2d rectangles).
 * This possibility is delegated to the IGroundShape interface.
 * <p/>
 * Created by ffradet on 18/10/2014.
 */
public class GroundGrid extends GroundAbstract {
    private static final Logger LOG = LoggerFactory.getLogger(GroundGrid.class);

    private Map<Position, IActor> mapGridOccupation = new TreeMap<Position, IActor>(new Position.PositionComparator());

    /**
     * @param groundShape
     */
    public GroundGrid(IGroundShape groundShape) {
        super(groundShape);
    }

    /**
     * Due to the GroundGrid integer coordinates, the actor position must be rounded and replaced inside it.
     *
     * @param position
     * @return
     */
    private static Position toGridPosition(Position position) {
        return new Position(Math.round(position.getX()), Math.round(position.getY()));
    }

    /**
     * @param pos
     * @return
     */
    private IActor getGridOccupation(Position pos) {
        return mapGridOccupation.get(pos);
    }

    /**
     * @param actor
     */
    private void clearGridOccupation(IActor actor) {
        IActor curActor = getGridOccupation(actor.getGeoLoc().getPosition());
        if (curActor != actor) {
            throw new IllegalArgumentException("Removing another IActor from the same position !");
        }
        mapGridOccupation.remove(actor.getGeoLoc().getPosition());
    }

    /**
     * @param actor
     */
    private void setGridOccupation(IActor actor, Position newPos) {
        mapGridOccupation.put(newPos, actor);
    }

    @Override
    public void attachActor(IActor actor) {
        Position posActor = actor.getGeoLoc().getPosition();

        if (getGridOccupation(posActor) != null) {
            // Tried to attach an actor on a non-empty cell
            throw new GroundAttachException();
        }
        super.attachActor(actor);
        setGridOccupation(actor, actor.getGeoLoc().getPosition());
    }

    @Override
    public void detachActor(IActor actor) {
        super.detachActor(actor);
        clearGridOccupation(actor);
    }

    /**
     * Update the actor GeoLoc according to the groundShape and the other actors on the grid cells.
     *
     * @param actor
     * @param newGeoLoc
     * @return
     */
    @Override
    public void onUpdateActor(IActor actor, GeoLoc newGeoLoc) {
        LOG.debug("onUpdateActor -> newGeoLoc = {}", newGeoLoc);

        // Check ground-related positioning rules (i.e. the grid cell must be empty and inside the ground shape)
        GeoLoc newGeoLocValid = getNearestValidGeoLoc(actor, newGeoLoc);
        Position newPosValid = newGeoLocValid.getPosition();

        LOG.debug("onUpdateActor -> newGeoLocValid (grid / actor) = {}", newGeoLocValid);

        if (!newPosValid.equals(actor.getGeoLoc().getPosition())) {
            // Any movement ? update the grid Map
            clearGridOccupation(actor);
            setGridOccupation(actor, newPosValid);
        }

        // Update the actor's GeoLoc (Position & Orientation)
        actor.getGeoLoc().copyFrom(newGeoLocValid);
    }

    /**
     * Perform 4 operations :
     * <ul>
     * <li>Transform the position into integer coordinates to fit in the grid system</li>
     * <li>Check the new position against the ground shape</li>
     * <li>Check the new position against the grid occupation</li>
     * <li>Then, update the actor GeoLoc</li>
     * </ul>
     *
     * @param actor
     * @param newGeoLoc
     * @return
     */
    @Override
    protected GeoLoc getNearestValidGeoLoc(IActor actor, GeoLoc newGeoLoc) {
        // Set the position as integer coordinates
        GeoLoc newGeoLocGrid = new GeoLoc(newGeoLoc);
        newGeoLocGrid.setPosition(toGridPosition(newGeoLoc.getPosition()));

        // Check against IGroundShape to remain inside the ground shape
        newGeoLocGrid = super.getNearestValidGeoLoc(actor, newGeoLocGrid);

        // Check IActor collisions
        IActor newPosActor = getGridOccupation(newGeoLocGrid.getPosition());
        if ((newPosActor == null) || (newPosActor.equals(actor))) {
            // The cell is empty or is occupied by the actor itself, newGeoLocGrid is the next valid position.
            return newGeoLocGrid;
        }
        // Another actor is present on the new position : keep the current actor's position (do not move)
        return actor.getGeoLoc();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroundGrid{");
        sb.append("mapGridOccupation=").append(mapGridOccupation);
        sb.append('}');
        sb.append(" super=").append(super.toString());
        return sb.toString();
    }
}
