package com.laurietrichet.whatsnew.net.client;

import android.test.InstrumentationTestCase;

import com.laurietrichet.whatsnew.model.Feed;
import com.laurietrichet.whatsnew.net.request.FeedRequest;

import junit.framework.Assert;

public class FeedWebServiceClientTest extends InstrumentationTestCase {

    public void testCreateRequest () throws Exception{
        FeedWebServiceClient feedWebServiceClient =
                new FeedWebServiceClient(this.getInstrumentation().getContext());

        String entryPoint = "feed://www.rssboard.org/files/sample-rss-2.xml";

        FeedRequest feedRequest = (FeedRequest) feedWebServiceClient.createRequest(entryPoint,null,listener);
        Assert.assertNotNull(feedRequest);
        Assert.assertEquals(entryPoint, feedRequest.getUrl());
    }

    /**public void test (){

    }*/

    AbstractVolleyWebServiceClient.WebServiceClientListener<Feed> listener = new AbstractVolleyWebServiceClient.WebServiceClientListener<Feed>() {
        @Override
        public void onSuccess(Feed obj) {

        }

        @Override
        public void onError(Error error) {

        }
    };
}