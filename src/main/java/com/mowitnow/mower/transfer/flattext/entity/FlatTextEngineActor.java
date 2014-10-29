package com.mowitnow.mower.transfer.flattext.entity;

import com.mowitnow.mower.core.command.ICommand;
import com.mowitnow.mower.core.engine.EngineActor;
import com.mowitnow.mower.core.ground.actor.IActor;
import com.mowitnow.mower.transfer.flattext.FlatTextLineParams;
import com.mowitnow.mower.transfer.flattext.FlatTextReader;
import com.mowitnow.mower.transfer.flattext.FlatTextWriter;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextEngineActorFormatException;

import java.util.ArrayList;
import java.util.List;

/**
 * EngineActor entity
 * <p/>
 * The file format specifies a couple of lines per actor :
 * <p/>
 * initialPosX initialPosY initialOrientation
 * commandSequence
 * <p/>
 * Created by ffradet on 21/10/2014.
 */
public class FlatTextEngineActor {

    /**
     * @param reader
     * @return
     */
    public static EngineActor read(FlatTextReader reader) {

        // Line 1 : [position] [orientation]
        IActor actor = actor = FlatTextActor.read(reader);

        if (!reader.isLineParamsConsumed()) {
            // Extra parameter detected
            throw new FlatTextEngineActorFormatException("Malformed actor declaration line");
        }
        // Line 2 : [commands sequence]
        FlatTextLineParams lineParams = reader.getNextLineParams();

        if (lineParams == null) {
            // No more params may be present
            throw new FlatTextEngineActorFormatException("Malformed actor commands declaration line");
        }

        String sSequence = lineParams.getNextParam();
        if (!reader.isLineParamsConsumed()) {
            // Extra parameter may be detected
            throw new FlatTextEngineActorFormatException("Malformed actor commands declaration line : extra parameter found after command sequence");
        }

        if (sSequence == null) {
            // sSequence should never be null (due to FlatTextLineParams implementation)
            throw new FlatTextEngineActorFormatException("Malformed actor commands declaration line : command sequence parameter is null");
        }

        List<ICommand> lstCommands = new ArrayList<ICommand>();
        for (char c : sSequence.toCharArray()) {
            FlatTextCommand cc = FlatTextCommand.fromCode(c);

            if (cc == null) {
                throw new FlatTextEngineActorFormatException("FlatTextEngineActor : unknown FlatTextCommand : code=" + c);
            }

            lstCommands.add(cc.getCommand());
        }

        EngineActor engineActor = new EngineActor(actor);
        engineActor.addCommands(lstCommands);

        return engineActor;
    }

    /**
     * @param engineActor
     * @param writer
     */
    public static void write(EngineActor engineActor, FlatTextWriter writer) {
        IActor actor = engineActor.getActor();
        FlatTextActor.write(actor, writer);
        writer.newLine();
    }

}
