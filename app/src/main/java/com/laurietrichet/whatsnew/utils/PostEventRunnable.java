package com.laurietrichet.whatsnew.utils;

import android.support.annotation.NonNull;

import com.laurietrichet.whatsnew.core.bus.BusProvider;


/**
 * Created by laurie on 30/09/15.
 */
public class PostEventRunnable implements Runnable {

    private Object event;

    public PostEventRunnable(@NonNull Object event) {
        this.event = event;
    }

    @Override
    public void run() {
        BusProvider.INSTANCE.getBus().post(event);
    }
}
