package com.mowitnow.mower.core.ground.actor;

/**
 * A Mower is nothing more than the base class.<br>
 * This class exists in order to clarify about what this actor is and to allow some future enhancement.<br>
 * <p/>
 * Created by ffradet on 17/10/2014.
 */
public class ActorMower extends ActorAbstract {

    /**
     *
     */
    public ActorMower() {
        super();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ActorMower");
        sb.append(" super=");
        sb.append(super.toString());
        return sb.toString();
    }

}
