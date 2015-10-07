package com.laurietrichet.whatsnew.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by laurie on 22/11/14.
 * Represent a RSS feed object. To construct a Rss object use the Builder class.
 * About RSS specification:
 * http://www.rssboard.org/rss-specification
 */
@Root(strict = false)
public class Rss implements Parcelable {

    @Element
    private Channel channel;

    @Attribute
    private String version;

    public Rss() {
    }

    public Channel getChannel() {
        return channel;
    }

    protected Rss(Parcel in) {
        channel = in.readParcelable(Channel.class.getClassLoader());
    }

    public static final Creator<Rss> CREATOR = new Creator<Rss>() {
        @Override
        public Rss createFromParcel(Parcel in) {
            return new Rss(in);
        }

        @Override
        public Rss[] newArray(int size) {
            return new Rss[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(channel, flags);
    }
}
