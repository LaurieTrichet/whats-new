package com.laurietrichet.whatsnew.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Utility class to parse RSS stream
 */
public class XmlPullParserUtils {

    public static String readTag(XmlPullParser parser, String namespace, String tagName)
            throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, namespace, tagName);
        String content = readText(parser);
        parser.require(XmlPullParser.END_TAG, namespace, tagName);
        return content;
    }

    // For the tags title and description, extracts their text values.
    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }
}
