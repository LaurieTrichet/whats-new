package com.laurietrichet.whatsnew.parsers;

import android.content.res.AssetManager;
import android.test.InstrumentationTestCase;

import com.laurietrichet.whatsnew.FileUtility;
import com.laurietrichet.whatsnew.model.Feed;
import com.laurietrichet.whatsnew.model.Item;

import junit.framework.Assert;

import java.io.InputStream;

public class FeedParserTest extends InstrumentationTestCase {

    private RssParser rssParser;
    private String xml;

    public void setUp() throws Exception {
        super.setUp();

        rssParser = new RssParser();

        AssetManager assetMgr = this.getInstrumentation().getContext().getAssets();
        InputStream stream = assetMgr.open("rss2sample.xml");
        xml = FileUtility.file2String(stream);
    }

    public void testParse() throws Exception {
        Feed feed = rssParser.parse(xml);
        Assert.assertNotNull(feed);
        Assert.assertEquals("Liftoff News",feed.getTitle());
        Assert.assertEquals("http://liftoff.msfc.nasa.gov/",feed.getLink());
        Assert.assertEquals("Liftoff to Space Exploration.",feed.getDescription());
        Assert.assertEquals(4, feed.getItemList().size());

        Item item = feed.getItemList().get(0);
        Assert.assertEquals("Star City",item.getTitle());
        Assert.assertEquals("http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp",item.getLink());
        Assert.assertEquals("How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."
                ,item.getDescription());

    }
}