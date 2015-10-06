package com.laurietrichet.whatsnew.core.net.client;

import android.test.InstrumentationTestCase;

import com.laurietrichet.whatsnew.FileUtility;
import com.laurietrichet.whatsnew.core.data.model.Rss;

import junit.framework.Assert;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;

/**
 * Created by laurie on 23/07/15.
 * Class to test the request though network. Check cache strategy.
 */
public class FeedWebserviceTests extends InstrumentationTestCase{


    private String feedStream;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        try {
            this.feedStream = FileUtility.file2String(getInstrumentation().getContext().getAssets().open("rss2sample.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testParse (){
        Serializer serializer = new Persister();
        Rss rss = null;
        try {
            rss = serializer.read(Rss.class, this.feedStream, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertNotNull(rss);
        Assert.assertEquals(rss.getChannel().getTitle(), "Liftoff News");
        Assert.assertEquals(rss.getChannel().getItems().size(), 4);
        Assert.assertEquals(rss.getChannel().getItems().get(0).getTitle(), "Star City");
    }

    public void testGetFeed (){
        //TODO test cache strategy

    }
}
