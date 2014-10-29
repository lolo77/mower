package com.mowitnow.mower.core.ground;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.geoloc.Position;
import com.mowitnow.mower.core.ground.shape.IGroundShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ffradet on 18/10/2014.
 */
public abstract class GroundAbstract implements IGround {
    private List<IActor> lstActors;
    private IGroundShape groundShape;

    /**
     * @param groundShape
     */
    public GroundAbstract(IGroundShape groundShape) {
        lstActors = new ArrayList<IActor>();
        this.groundShape = groundShape;
    }


    /**
     * @param actor
     */
    @Override
    public void attachActor(IActor actor) {
        if (!lstActors.contains(actor)) {
            lstActors.add(actor);
        }
    }


    /**
     * @param actor
     */
    @Override
    public void detachActor(IActor actor) {
        lstActors.remove(actor);
    }

    @Override
    public List<IActor> getLstActors() {
        return lstActors;
    }

    @Override
    public IGroundShape getGroundShape() {
        return groundShape;
    }


    /**
     * By default, update the actor GeoLoc to the nearest valid position (ie. getNearestValidGeoLoc)
     *
     * @param actor
     * @param newGeoLoc
     * @return
     */
    @Override
    public void onUpdateActor(IActor actor, GeoLoc newGeoLoc) {
        actor.getGeoLoc().copyFrom(getNearestValidGeoLoc(actor, newGeoLoc));
    }

    /**
     * Make the actor staying inside the ground, allowing it to slide against the limit.
     *
     * @param actor
     * @param newGeoLoc
     * @return
     */
    protected GeoLoc getNearestValidGeoLoc(IActor actor, GeoLoc newGeoLoc) {
        Position newPosValid = getGroundShape().getNearestValidPosition(newGeoLoc.getPosition());
        return new GeoLoc(newPosValid, newGeoLoc.getOrientation());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GroundAbstract{");
        sb.append("lstActors=").append(lstActors);
        sb.append(", groundShape=").append(groundShape);
        sb.append('}');
        return sb.toString();
    }
}