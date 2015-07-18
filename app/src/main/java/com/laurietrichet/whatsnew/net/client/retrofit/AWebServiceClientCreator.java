package com.laurietrichet.whatsnew.net.client.retrofit;

import android.support.annotation.NonNull;

import com.laurietrichet.whatsnew.net.client.IWebServiceClient;

import retrofit.RestAdapter;
import retrofit.converter.SimpleXMLConverter;

/**
 * Created by laurie on 18/07/15.
 * Helper to generate webservice clients using Retrofit.
 */
public abstract class AWebServiceClientCreator {

    /**
     * Return a web service client using retrofit.
     * @param url String url for the webservice root.
     * @param clazz interface for the retrofit client.
     * @param <T> Type of the interface, must extend from IWebServiceClient.
     * @return an instance of clazz.
     */
    public static <T extends IWebServiceClient> T createClient (@NonNull String url, @NonNull Class<T> clazz){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(url)
                .setConverter(new SimpleXMLConverter())
                .build();
        return restAdapter.create(clazz);
    }
}
