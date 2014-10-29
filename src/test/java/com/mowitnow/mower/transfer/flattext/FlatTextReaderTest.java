package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextActorFormatException;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextEngineActorFormatException;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextGroundFormatException;
import com.mowitnow.mower.transfer.flattext.exception.FlatTextPositionFormatException;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Test the FlatTextReader class
 * The input format is specified by the FlatTextReader instance.
 * <p/>
 * Created by ffradet on 21/10/2014.
 */
public class FlatTextReaderTest {

    /**
     * @param input the input stream as a string
     * @return the IEngineReader as a FlatTextReader instance
     */
    public static FlatTextReader buildReaderFromString(final String input) {
        return new FlatTextReader() {
            @Override
            public InputStream getInputStream() throws IOException {
                return new ByteArrayInputStream(input.getBytes(getCharsetName()));
            }
        };
    }

    @Test
    public void testEmptyInput() {
        FlatTextReader reader = buildReaderFromString("\t \t\n   \n\t \t \n \t \t\n\t\t \n  \t\t");
        Assert.assertTrue(reader.getNextLineParams() == null);

        reader = buildReaderFromString("");
        Assert.assertTrue(reader.getNextLineParams() == null);
    }

    @Test(expected = FlatTextPositionFormatException.class)
    public void testBadFormatGroundExtent() {
        FlatTextReader reader = buildReaderFromString("x 5\n1 2 N\n GAGAGAGAA");
        Engine engine = reader.readEngine();
    }

    @Test
    public void testBadFormatNoActor() {
        FlatTextReader reader = buildReaderFromString("5 5\n");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextGroundFormatException.class)
    public void testBadFormatGroundExtraParam() {
        FlatTextReader reader = buildReaderFromString("5 5 z\n1 2 N\n GAGAGAGAA");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextActorFormatException.class)
    public void testBadFormatActorPosition() {
        FlatTextReader reader = buildReaderFromString("5 5\nx 2 N\n GAGAGAGAA");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextActorFormatException.class)
    public void testBadFormatActorOrientation() {
        FlatTextReader reader = buildReaderFromString("5 5\n 1 2 o\n GAGAGAGAA");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextEngineActorFormatException.class)
    public void testBadFormatActorExtraParam() {
        FlatTextReader reader = buildReaderFromString("5 5\n 1 2 N extra\n GAGAGAGAA");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextEngineActorFormatException.class)
    public void testBadFormatActorNoCommand() {
        FlatTextReader reader = buildReaderFromString("5 5\n 1 2 N ");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextEngineActorFormatException.class)
    public void testBadFormatActorUnknownCommand() {
        FlatTextReader reader = buildReaderFromString("5 5\n 1 2 N\nGAGAK ");
        Engine engine = reader.readEngine();
    }

    @Test(expected = FlatTextEngineActorFormatException.class)
    public void testBadFormatActorExtraCommand() {
        FlatTextReader reader = buildReaderFromString("5 5\n 1 2 N\nGAGA G ");
        Engine engine = reader.readEngine();
    }


}
