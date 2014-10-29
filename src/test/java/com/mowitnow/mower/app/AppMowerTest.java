package com.mowitnow.mower.app;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


/**
 * Created by ffradet on 28/10/2014.
 */
public class AppMowerTest {

    @Before
    public void deflectStandardOut() {
        // To prevent junit from not launching the success test
        final ByteArrayOutputStream buf = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buf));
    }

    @Test
    public void testErrorNoFile() {
        AppMower app = new AppMower();
        int iStatus = app.execute();
        Assert.assertTrue(iStatus == AppMower.STATUS_ERR_NO_FILE);

        iStatus = app.execute(new String[0]);
        Assert.assertTrue(iStatus == AppMower.STATUS_ERR_NO_FILE);
    }

    @Test
    public void testErrorTooMuchFiles() {
        AppMower app = new AppMower();
        int iStatus = app.execute(new String[2]);
        Assert.assertTrue(iStatus == AppMower.STATUS_ERR_TOO_MUCH_FILES);
    }

    @Test
    public void testErrorIO() {
        AppMower app = new AppMower();
        String[] args = new String[1];
        args[0] = "not existing file";
        int iStatus = app.execute(args);
        Assert.assertTrue(iStatus == AppMower.STATUS_ERR_IO);
    }

    @Test
    public void testErrorExecution() {
        AppMower app = new AppMower();
        String[] args = new String[1];
        args[0] = "testCollisionInit.txt";
        int iStatus = app.execute(args);
        Assert.assertTrue(iStatus == AppMower.STATUS_ERR_EXECUTION);
    }

    @Test
    public void testAppSuccess() {
        AppMower app = new AppMower();
        String[] args = new String[1];
        args[0] = "testSuccess.txt";
        int iStatus = app.execute(args);
        Assert.assertTrue(iStatus == AppMower.STATUS_OK);
    }
}
