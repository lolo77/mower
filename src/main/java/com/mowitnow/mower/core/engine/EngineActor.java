package com.mowitnow.mower.core.engine;

import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.ground.actor.IActor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by ffradet on 18/10/2014.
 */
public class EngineActor {
    private final IActor actor;
    private final List<ICommand> lstCommands = new ArrayList<ICommand>();
    private int curIndex = 0;

    /**
     * @param actor
     */
    public EngineActor(IActor actor) {
        this.actor = actor;
    }

    /**
     * @param lstCommands
     */
    public void addCommands(List<ICommand> lstCommands) {
        this.lstCommands.addAll(lstCommands);
    }

    /**
     * @return
     */
    public List<ICommand> getLstCommands() {
        return Collections.unmodifiableList(lstCommands);
    }

    /**
     * @return the actor
     */
    public IActor getActor() {
        return actor;
    }

    /**
     *
     */
    public void rewind() {
        curIndex = 0;
    }

    /**
     * @return the next command
     */
    private ICommand getNextCommand() {

        if (curIndex < lstCommands.size()) {
            return lstCommands.get(curIndex++);
        }

        return null;
    }


    /**
     * Execute the next command
     *
     * @return true if a command was executed
     */
    public boolean execute() {
        ICommand cmd = getNextCommand();
        boolean bCmdValid = (cmd != null);
        if (bCmdValid) {
            actor.execute(cmd);
        }
        return bCmdValid;
    }
}
