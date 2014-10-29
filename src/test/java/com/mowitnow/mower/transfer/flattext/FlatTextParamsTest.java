package com.mowitnow.mower.transfer.flattext;

import com.mowitnow.mower.transfer.exception.TransferException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by ffradet on 20/10/2014.
 */
public class FlatTextParamsTest {

    @Test
    public void testSplitSpaces() {
        // 1<SP>3<TAB>5<SP><SP>7
        FlatTextLineParams p = new FlatTextLineParams("\t1 3  5  7 ");

        Assert.assertTrue(p.getNextParam().equals("1"));
        Assert.assertTrue(p.getNextParam().equals("3"));
        Assert.assertTrue(p.getNextParam().equals("5"));
        Assert.assertTrue(p.getNextParam().equals("7"));
        try {
            p.getNextParam(); // throw FlatTextLoaderException: Tried to fetch more params than existing !
            Assert.assertTrue(false);
        } catch (TransferException e) {
            // ok
        }
    }


}
