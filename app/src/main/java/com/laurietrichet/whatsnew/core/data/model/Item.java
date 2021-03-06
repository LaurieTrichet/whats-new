package com.laurietrichet.whatsnew.core.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by laurie on 22/11/14.
 * Represent a RSS item object. To construct a Item object use the Builder class.
 * About RSS specification:
 * http://www.rssboard.org/rss-specification
 */
@Root(strict = false)
public class Item implements Parcelable{

    @Element
    private String title;
    @Element
    private String link;
    @Element
    private String description;

    public Item() {
    }

    private Item (Builder builder){
        this.title = builder.title;
        this.link = builder.link;
        this.description = builder.description;
    }

    protected Item(Parcel in) {
        title = in.readString();
        link = in.readString();
        description = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
    }

    public static class Builder {
        private String title;
        private String link;
        private String description;

        /**
         * At least one of title or description must be present.
         * @param itemTitle
         * @param itemDescription
         */
        public Builder (String itemTitle, String itemDescription){
            if (itemTitle == null && itemDescription == null)
                throw new IllegalArgumentException("At least one of title or description must be present.");
            this.title = itemTitle;
            this.description = itemDescription;
        }

        public Item build (){
            return new Item(this);
        }

        public void link (String itemLink){
            this.link = itemLink;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }
}
