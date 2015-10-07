package com.laurietrichet.whatsnew.net.client.okhttp;

import com.laurietrichet.whatsnew.net.client.IWebServiceClient;

import retrofit.http.GET;

/**
 * Web service client to retrieve rss feed
 */
public interface FeedWebServiceClient extends IWebServiceClient{

    void get ();
}
