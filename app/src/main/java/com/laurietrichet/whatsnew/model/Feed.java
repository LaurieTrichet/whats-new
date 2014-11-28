package com.laurietrichet.whatsnew.model;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by laurie on 22/11/14.
 * Represent a RSS feed object. To construct a Feed object use the Builder class.
 * About RSS specification:
 * http://www.rssboard.org/rss-specification
 */
public class Feed {

    private String title;
    private String link;
    private String description;

    /*optional*/
    private String imageUrl;
    private int ttl;

    private List<Item> itemList;

    private Feed () throws UnsupportedOperationException{
        throw new UnsupportedOperationException("forbidden constructor");
    }

    private Feed (Builder builder){
        this.title = builder.title;
        this.link = builder.link;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.ttl =  builder.ttl;
        this.itemList = builder.itemList;
    }

    /**
     * Builder class to construct a Feed object
     */
    public static class Builder{

        private String title;
        private String link;
        private String description;

        /*optional*/
        private String imageUrl = null;
        private int ttl = 0;
        private List<Item> itemList;

        /**
         * Use the builder to construct a Feed object
         * @param feedTitle
         * @param feedLink
         * @param feedDescription
         */
        public Builder (@NonNull String feedTitle, @NonNull String feedLink, @NonNull String feedDescription){

            this.title = feedTitle;
            this.link = feedLink;
            this.description = feedDescription;
        }

        /**
         * Set the image Url of the feed
         * @param feedImageUrl
         * @return builder object with the imageUrl field set
         */
        public Builder imageUrl (String feedImageUrl){
            this.imageUrl = feedImageUrl;
            return this;
        }

        /**
         * Set the ttl (time to live) of the feed, t's a number of minutes that indicates how long
         * a channel can be cached before refreshing from the source
         * @param feedTtl
         * @return builder object with the ttl field set
         */
        public Builder ttl (int feedTtl){
            this.ttl = feedTtl;
            return this;
        }


        public Builder itemList (List<Item> feedItemList){
            this.itemList = feedItemList;
            return this;
        }


        /**
         * Build method to construct a feed object from the current builder
         * @return Feed object with the info given to the builder.
         */
        public Feed build (){
            return new Feed(this);
        }
    }

    /*Getters*/

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
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

    public List<Item> getItemList() {
        return itemList;
    }
}
