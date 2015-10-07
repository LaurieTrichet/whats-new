package com.laurietrichet.whatsnew.core.net.client;

import android.test.InstrumentationTestCase;

import com.laurietrichet.whatsnew.FileUtility;
import com.laurietrichet.whatsnew.core.bus.BusProvider;
import com.laurietrichet.whatsnew.core.bus.ParsingError;
import com.laurietrichet.whatsnew.core.data.model.Rss;
import com.laurietrichet.whatsnew.net.client.IWebServiceClient;
import com.laurietrichet.whatsnew.net.client.WebServiceClients;
import com.laurietrichet.whatsnew.core.bus.RssFeedAvailable;
import com.squareup.otto.Subscribe;

import junit.framework.Assert;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by laurie on 23/07/15.
 * Class to test the request though network. Check cache strategy.
 */
public class FeedWebserviceTests extends InstrumentationTestCase{


    private String feedStream;
    private CountDownLatch signal;

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
        BusProvider.INSTANCE.getBus().register(this);
        signal = new CountDownLatch(1);
        IWebServiceClient client = WebServiceClients.createClient();
        client.get("http://feeds.news.com.au/public/rss/2.0/aus_climate_809.xml");

        waitForSignal(signal);
    }

    private void waitForSignal (CountDownLatch countDownLatch){
        try {
            countDownLatch.await(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Subscribe public void onRssReceived (RssFeedAvailable event){
        signal.countDown();
        Assert.assertNotNull(event);
        Assert.assertEquals(event.getRss().getChannel().getTitle(), "The Australian | Climate");

        BusProvider.INSTANCE.getBus().unregister(this);
    }

    @Subscribe public void onParsingErrorReceived (ParsingError event){
        signal.countDown();
        Assert.fail();

        BusProvider.INSTANCE.getBus().unregister(this);
    }
}
