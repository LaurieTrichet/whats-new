package com.laurietrichet.whatsnew.core.data.model;

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
