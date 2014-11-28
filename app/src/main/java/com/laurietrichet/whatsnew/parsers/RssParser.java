package com.laurietrichet.whatsnew.parsers;

import com.laurietrichet.whatsnew.model.Feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

/**
 * Rss Feed parser using {@link org.xmlpull.v1.XmlPullParser}
 */
class RssParser implements IParser<Feed>{

    private XmlPullParser xpp ;

    enum FeedKey {
        channel
    }

    /**
     * Constructor
     * @throws Exception relay the execption launch by the XMLPullParser object.
     */
    RssParser() throws Exception{
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        xpp = factory.newPullParser();
    }

    @Override
    public Feed parse(String input) throws Exception {
        Feed feed = null;
        StringReader stringReader = new StringReader(input);
        xpp.setInput(stringReader);
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            if (eventType == XmlPullParser.START_TAG){
                if (xpp.getName().equals(FeedKey.channel.name())){
                    feed = FeedParser.parse(xpp, null);
                }
            }
            eventType = xpp.next();
        }
        return feed;
    }
}
