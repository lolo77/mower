package com.mowitnow.mower.core.engine;

import com.mowitnow.mower.core.ground.IGround;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Created by ffradet on 18/10/2014.
 */
public class Engine implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(Engine.class);

    private final IGround ground;
    private final List<EngineActor> lstEngineActors = new ArrayList<EngineActor>();


    /**
     *
     */
    public Engine(IGround ground) {
        this.ground = ground;
    }

    /**
     * @param engineActor
     */
    public void addEngineActor(EngineActor engineActor) {
        lstEngineActors.add(engineActor);
        ground.attachActor(engineActor.getActor());
    }

    /**
     * @return
     */
    public IGround getGround() {
        return ground;
    }

    /**
     * @return
     */
    public List<EngineActor> getLstEngineActors() {
        return Collections.unmodifiableList(lstEngineActors);
    }

    /**
     *
     */
    public void rewind() {
        for (EngineActor ea : lstEngineActors) {
            ea.rewind();
        }
    }

    /**
     * Runs entirely one actor at a time
     *
     * @return true if a command was executed
     */
    protected boolean executeSequential() {
        boolean bExecutedCommand = false;

        for (EngineActor ea : lstEngineActors) {
            bExecutedCommand = ea.execute();

            if (bExecutedCommand) {
                // Stop right after having executed the first available command
                break;
            }
        }

        return bExecutedCommand;
    }

    /**
     * @return true if a command was executed
     */
    protected boolean execute() {
        return executeSequential();
    }

    /**
     * Thread-ready Runnable interface
     */
    @Override
    public void run() {
        while (execute()) {
        }
    }
}
