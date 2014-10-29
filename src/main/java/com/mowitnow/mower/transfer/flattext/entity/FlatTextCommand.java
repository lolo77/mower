package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.command.CommandMove;
import com.mowitnow.mower.core.command.CommandRotate;
import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.ground.geoloc.Orientation;

/**
 * Mapping code -> ICommand
 * <p/>
 * Created by ffradet on 17/10/2014.
 */
public enum FlatTextCommand {
    LEFT('G', new CommandRotate(Orientation.DEG_90)),
    RIGHT('D', new CommandRotate(Orientation.DEG_270)),
    FORWARD('A', new CommandMove(1));

    private ICommand command;
    private char code;

    /**
     * @param code
     * @param command
     */
    private FlatTextCommand(char code, ICommand command) {
        this.command = command;
        this.code = code;
    }

    /**
     * @param code
     * @return
     */
    public static FlatTextCommand fromCode(char code) {
        char upCode = Character.toUpperCase(code);
        for (FlatTextCommand c : values()) {
            if (c.getCode() == upCode) {
                return c;
            }
        }
        return null;
    }

    /**
     * @return
     */
    public char getCode() {
        return code;
    }

    /**
     * @return
     */
    public ICommand getCommand() {
        return command;
    }
}
