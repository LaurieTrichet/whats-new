package com.laurietrichet.whatsnew.parsers;

import com.laurietrichet.whatsnew.model.Item;

import org.xmlpull.v1.XmlPullParser;

/**
 * Class to parse rss item object
 */
class ItemParser {

    enum ItemField {
        title,
        link,
        description
    }

    static Item parse(XmlPullParser xpp, String namespace) throws Exception {
        XmlPullParser xmlPullParser = xpp;
        xmlPullParser.require(XmlPullParser.START_TAG, namespace, FeedParser.ChannelField.item.name());

        String itemTitle = null;
        String itemLink = null;
        String itemDescription = null;

        String name;

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                /*to skip end tags*/
                continue;
            }
            name = xmlPullParser.getName();
            if (name.equals(ItemField.title.name())){
                itemTitle = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ItemField.title.name());
            } else if (name.equals(ItemField.link.name())){
                itemLink = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ItemField.link.name());
            } else if (name.equals(ItemField.description.name())){
                itemDescription = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ItemField.description.name());
            } else {
                xmlPullParser.nextText();
            }
        }

        Item.Builder itemBuilder = new Item.Builder(itemTitle,itemLink,itemDescription);
        return itemBuilder.build();
    }
}
