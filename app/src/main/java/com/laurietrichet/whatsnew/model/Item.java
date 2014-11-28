package com.laurietrichet.whatsnew.model;

/**
 * Created by laurie on 22/11/14.
 * Represent a RSS item object. To construct a Item object use the Builder class.
 * About RSS specification:
 * http://www.rssboard.org/rss-specification
 */
public class Item {

    private String title;
    private String link;
    private String description;

    private Item (Builder builder){
        this.title = builder.title;
        this.link = builder.link;
        this.description = builder.description;
    }

    public static class Builder {
        private String title;
        private String link;
        private String description;

        public Builder (String itemTitle, String itemLink, String itemDescription){
            this.title = itemTitle;
            this.link = itemLink;
            this.description = itemDescription;
        }

        public Item build (){
            return new Item(this);
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
