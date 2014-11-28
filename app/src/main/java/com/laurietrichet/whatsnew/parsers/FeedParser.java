package com.laurietrichet.whatsnew.parsers;

import com.laurietrichet.whatsnew.model.Feed;
import com.laurietrichet.whatsnew.model.Item;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse the channel object of a feed
 */
class FeedParser {

    enum ChannelField {
        title,
        link,
        description,
        item
    }

    static Feed parse (XmlPullParser xpp, String namespace)
            throws Exception {

        XmlPullParser xmlPullParser = xpp;
        xmlPullParser.require(XmlPullParser.START_TAG, namespace, RssParser.FeedKey.channel.name());

        Feed.Builder feedBuilder;
        String feedTitle = null;
        String feedLink = null;
        String feedDescription = null;
        List<Item> itemList = new ArrayList<Item>();

        String name;

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                /*to skip end tags*/
                continue;
            }
            name = xmlPullParser.getName();
            if (name.equals(ChannelField.title.name())){
                feedTitle = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ChannelField.title.name());
            } else if (name.equals(ChannelField.link.name())){
                feedLink = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ChannelField.link.name());
            } else if (name.equals(ChannelField.description.name())){
                feedDescription = XmlPullParserUtils.readTag(
                        xmlPullParser, namespace, ChannelField.description.name());
            } else if (name.equals(ChannelField.item.name())){
                itemList.add(ItemParser.parse (xmlPullParser, namespace));
            } else {
                xmlPullParser.nextText();
            }
        }

        feedBuilder = new Feed.Builder(feedTitle, feedLink, feedDescription);
        feedTitle = null;
        feedLink = null;
        feedDescription = null;

        feedBuilder.itemList(itemList);
        itemList = null;

        return feedBuilder.build();
    }
}
