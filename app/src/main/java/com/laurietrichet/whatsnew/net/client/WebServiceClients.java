package com.laurietrichet.whatsnew.net.client;

import android.support.annotation.NonNull;

import com.laurietrichet.whatsnew.net.client.okhttp.OkHttpClientImpl;


/**
 * Created by laurie on 18/07/15.
 * Factory class to retrieve a web service client instance.
 */
public abstract class WebServiceClients {

    /**
     * Return a web service client.
     * @return an instance of the chosen IWebServiceClient Impl.
     */
    public static IWebServiceClient createClient(){
        return new OkHttpClientImpl ();
    }
}
