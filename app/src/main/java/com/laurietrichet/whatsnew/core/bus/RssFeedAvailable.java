package com.laurietrichet.whatsnew.core.bus;

import com.laurietrichet.whatsnew.core.data.model.Rss;

/**
 * Created by laurie on 06/10/15.
 */
public class RssFeedAvailable {
    private final Rss rss;

    public RssFeedAvailable(Rss rss) {
        this.rss = rss;
    }

    public Rss getRss() {
        return rss;
    }
}
