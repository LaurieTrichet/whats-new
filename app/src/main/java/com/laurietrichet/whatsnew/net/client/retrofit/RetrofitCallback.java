package com.laurietrichet.whatsnew.net.client.retrofit;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by laurie on 18/07/15.
 */
public class RetrofitCallback <T> implements Callback<T> {
    @Override
    public void success(T t, Response response) {

    }

    @Override
    public void failure(RetrofitError retrofitError) {

    }
}
