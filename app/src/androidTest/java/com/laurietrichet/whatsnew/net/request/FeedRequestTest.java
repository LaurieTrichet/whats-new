package com.laurietrichet.whatsnew.net.request;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laurietrichet.whatsnew.FileUtility;
import com.laurietrichet.whatsnew.model.Feed;

import junit.framework.Assert;

import java.io.InputStream;

public class FeedRequestTest extends InstrumentationTestCase {

    private String goodXmlString;
    private String badXmlString;

    private NetworkResponse goodNetworkResponse;
    private NetworkResponse badNetworkResponse;

    private FeedRequest request;

    private String url;

    private static int nbCall = 0;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        AssetManager assetMgr = this.getInstrumentation().getContext().getAssets();
        InputStream stream = assetMgr.open("rss2sample.xml");
        goodXmlString = FileUtility.file2String(stream);

        stream = assetMgr.open("rss2sample_bad.xml");
        badXmlString = FileUtility.file2String(stream);

        goodNetworkResponse = new NetworkResponse(goodXmlString.getBytes());
        badNetworkResponse = new NetworkResponse(badXmlString.getBytes());

        url = "feed://www.rssboard.org/files/sample-rss-2.xml";
    }

    public void testParseNetworkResponse() throws Exception {
        Response.Listener<Feed> feedListener = new FeedListener();
        Response.ErrorListener errorListener = new MyErrorListener();

        request = new FeedRequest(url, feedListener, errorListener);
        Response response = request.parseNetworkResponse(goodNetworkResponse);
        Assert.assertTrue(response.isSuccess());

        nbCall++;
        response = request.parseNetworkResponse(badNetworkResponse);
        Assert.assertFalse(response.isSuccess());
    }

    public void testDeliverResponse() throws Exception {

    }

    private static class FeedListener implements Response.Listener<Feed> {
        @Override
        public void onResponse(Feed response) {
            Assert.assertEquals(0, nbCall);
            Assert.assertNotNull(response);
        }
    }

    private static class MyErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Assert.assertEquals(1, nbCall);
            Assert.assertNotNull(error);
        }
    }
}