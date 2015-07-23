package com.laurietrichet.whatsnew.core.bus;

import com.squareup.otto.Bus;

/**
 * Created by laurie on 23/07/15.
 */
public class BusEvent {
    private static Bus bus = new Bus();

    public static Bus getBus() {
        return bus;
    }
}
