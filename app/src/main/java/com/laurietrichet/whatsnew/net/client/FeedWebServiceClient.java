package com.laurietrichet.whatsnew.net.client;

import android.content.Context;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.laurietrichet.whatsnew.model.Feed;
import com.laurietrichet.whatsnew.net.VolleyHelper;
import com.laurietrichet.whatsnew.net.request.FeedRequest;

/**
 * Web service client to retrieve rss feed
 */
public class FeedWebServiceClient extends AbstractVolleyWebServiceClient<Feed> {


    private final Context applicationContext;

    public FeedWebServiceClient(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Request createRequest(String entryPoint, Bundle params,
                                     final WebServiceClientListener <Feed> listener) {
        Response.Listener <Feed> feedListener = new FeedListener(listener);

        Response.ErrorListener errorListener = new MyErrorListener(listener);

        return new FeedRequest(entryPoint, feedListener, errorListener);
    }

    @Override
    protected int send(Request request) {
        return (request == VolleyHelper.INSTANCE.addToRequestQueue(applicationContext, request))? 0 : -1;
    }

    private static class FeedListener implements Response.Listener<Feed> {
        private final WebServiceClientListener<Feed> listener;

        public FeedListener(WebServiceClientListener<Feed> listener) {
            this.listener = listener;
        }

        @Override
        public void onResponse(Feed response) {
            listener.onSuccess(response);
        }
    }

    private static class MyErrorListener implements Response.ErrorListener {
        private final WebServiceClientListener<Feed> listener;

        public MyErrorListener(WebServiceClientListener<Feed> listener) {
            this.listener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            listener.onError(new Error(error.getLocalizedMessage()));
        }
    }
}
