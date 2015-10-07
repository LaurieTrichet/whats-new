package com.laurietrichet.whatsnew.net.client.okhttp;

import com.laurietrichet.whatsnew.core.bus.BusProvider;
import com.laurietrichet.whatsnew.core.bus.RssFeedAvailable;
import com.laurietrichet.whatsnew.core.data.model.Rss;
import com.laurietrichet.whatsnew.net.client.IWebServiceClient;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;

/**
 * Created by laurie on 05/10/15.
 * Implementation using OkHttp
 */
public class OkHttpClientImpl implements IWebServiceClient {

    private OkHttpClient okHttpClient;

    public OkHttpClientImpl() {
        okHttpClient = new OkHttpClient();
    }

    @Override
    public void get(String resourcePath) {
        Request request = new Request.Builder()
                .url(resourcePath)
                .build();
        Response response = null;
        Serializer serializer = new Persister();
        Rss rss = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            ResponseHandler.postNetworkError(e.getLocalizedMessage());
        }

        if (response != null){
            try {
                rss = serializer.read(Rss.class, response.body().byteStream(), false);
            }catch (Exception e) {
                e.printStackTrace();
                ResponseHandler.postParsingError(e.getLocalizedMessage());
            }

            if (rss != null){
                BusProvider.postOnUiThread(new RssFeedAvailable(rss));
            }
        }
    }
}
