package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.core.engine.Engine;
import com.mowitnow.mower.transfer.IEngineWriter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * Test the FlatTextWriter class :
 * One line per actor : X Y Orientation
 * <p/>
 * Created by ffradet on 28/10/2014.
 */
public class FlatTextWriterTest {

    public static String writeEngine(Engine engine) throws UnsupportedEncodingException {
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();

        IEngineWriter writer = new FlatTextWriter() {
            @Override
            public OutputStream getOutputStream() throws IOException {
                return buf;
            }
        };

        writer.writeEngine(engine);

        return buf.toString(FlatTextWriter.DEFAULT_CHARSET);
    }

    @Test
    public void testWriteZeroActor() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5");
        Engine engine = reader.readEngine();

        try {
            String result = writeEngine(engine);
            Assert.assertTrue(result.equals(""));
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
        }

    }


    @Test
    public void testWriteOneActor() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n1 2 N\nGAA");
        Engine engine = reader.readEngine();

        try {
            String result = writeEngine(engine);
            Assert.assertTrue(result.equals("1 2 N" + FlatTextWriter.EOL));
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testWriteTwoActors() {
        FlatTextReader reader = FlatTextReaderTest.buildReaderFromString("5 5\n1 2 N\nGAGA\n3 4 W\nA");
        Engine engine = reader.readEngine();

        try {
            String result = writeEngine(engine);
            Assert.assertTrue(result.equals("1 2 N" + FlatTextWriter.EOL + "3 4 W" + FlatTextWriter.EOL));
        } catch (UnsupportedEncodingException e) {
            Assert.assertTrue(false);
        }
    }
}
