package com.mowitnow.mower.core.ground;

import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.core.ground.geoloc.GeoLoc;
import com.mowitnow.mower.core.ground.shape.IGroundShape;

import java.util.List;

/**
 * Created by ffradet on 18/10/2014.
 */
public interface IGround {

    /**
     * @return the ground shape
     */
    public IGroundShape getGroundShape();

    /**
     * @return the actor list
     */
    public List<IActor> getLstActors();

    /**
     * Attach the actor to the ground
     *
     * @param actor
     */
    public void attachActor(IActor actor);

    /**
     * Detach the actor from the ground
     *
     * @param actor
     */
    public void detachActor(IActor actor);

    /**
     * Update the actor GeoLoc (position & orientation) according to the ground-related positioning rules.
     *
     * @param actor
     * @param newGeoLoc
     */
    public void onUpdateActor(IActor actor, GeoLoc newGeoLoc);
}
