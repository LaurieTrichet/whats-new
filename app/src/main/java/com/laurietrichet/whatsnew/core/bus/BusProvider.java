package com.laurietrichet.whatsnew.core.bus;

import android.os.Handler;
import android.os.Looper;

import com.laurietrichet.whatsnew.utils.PostEventRunnable;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by laurie on 23/07/15.
 */
public enum BusProvider {

    INSTANCE;

    private Bus bus = new Bus(ThreadEnforcer.ANY);

    public Bus getBus() {
        return bus;
    }

    public static void postOnUiThread (Object event){
        Runnable runnable = new PostEventRunnable(event);
        new Handler(Looper.getMainLooper()).post(runnable);
    }

}
