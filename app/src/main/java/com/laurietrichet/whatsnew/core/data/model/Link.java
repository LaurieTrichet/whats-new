package com.laurietrichet.whatsnew.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Text;

/**
 * Created by laurie on 07/10/15.
 */
public class Link implements Parcelable{
    @Attribute(required=false)
    public String href;

    @Attribute(required=false)
    public String rel;

    @Attribute(name="type",required=false)
    public String contentType;

    @Text(required=false)
    public String link;

    public Link() {
    }

    protected Link(Parcel in) {
        href = in.readString();
        rel = in.readString();
        contentType = in.readString();
        link = in.readString();
    }

    public static final Creator<Link> CREATOR = new Creator<Link>() {
        @Override
        public Link createFromParcel(Parcel in) {
            return new Link(in);
        }

        @Override
        public Link[] newArray(int size) {
            return new Link[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(href);
        dest.writeString(rel);
        dest.writeString(contentType);
        dest.writeString(link);
    }
}
