package com.laurietrichet.whatsnew.net.client.retrofit;

import com.laurietrichet.whatsnew.core.bus.BusEvent;
import com.laurietrichet.whatsnew.core.bus.HTTPError;
import com.laurietrichet.whatsnew.core.bus.NetworkError;
import com.laurietrichet.whatsnew.core.bus.ParsingError;
import com.laurietrichet.whatsnew.core.bus.UnexpectedError;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by laurie on 18/07/15.
 */
public class RetrofitCallback <T> implements Callback<T> {
    @Override
    public void success(T t, Response response) {
        BusEvent.getBus().post(t);
    }

    @Override
    public void failure(RetrofitError retrofitError) {

        switch (retrofitError.getKind()){
            case NETWORK:
                BusEvent.getBus().post(new NetworkError());
                break;
            case CONVERSION:
                BusEvent.getBus().post(new ParsingError(retrofitError.getLocalizedMessage()));
                break;
            case HTTP:
                HTTPError.Builder builder = new HTTPError.Builder();
                builder.setRequestUrl(retrofitError.getUrl())
                        .setResponseUrl(retrofitError.getResponse().getUrl())
                        .setStatus(retrofitError.getResponse().getStatus());
                BusEvent.getBus().post(builder.build());
                break;
            case UNEXPECTED:
                BusEvent.getBus().post(new UnexpectedError());
                break;
        }
    }
}
