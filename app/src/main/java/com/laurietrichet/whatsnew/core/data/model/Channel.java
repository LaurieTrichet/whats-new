package com.laurietrichet.whatsnew.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by laurie on 06/10/15.
 */
@Root(strict=false)
public class Channel implements Parcelable{

    @Element
    private String title;

    @ElementList(entry="link",inline=true,required=false)
    public List<Link> links;

    @Element
    private String description;

    /*optional*/
    private String imageUrl;
    private int ttl;

    @ElementList(inline=true)
    private List<Item> items;

    public Channel() {
    }

    protected Channel(Parcel in) {
        title = in.readString();
        links = in.createTypedArrayList(Link.CREATOR);
        description = in.readString();
        imageUrl = in.readString();
        ttl = in.readInt();
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Parcelable.Creator<Rss> CREATOR = new Parcelable.Creator<Rss>() {
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
        dest.writeString(title);
        dest.writeTypedList(links);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeInt(ttl);
        dest.writeTypedList(items);
    }

    /*Getters*/

    public String getTitle() {
        return title;
    }

    public List<Link> getLinks() {
        return links;
    }

    public static Creator<Rss> getCREATOR() {
        return CREATOR;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getTtl() {
        return ttl;
    }

    public List<Item> getItems() {
        return items;
    }
}
