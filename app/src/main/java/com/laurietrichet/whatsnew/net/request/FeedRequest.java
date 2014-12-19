package com.laurietrichet.whatsnew.net.request;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.laurietrichet.whatsnew.model.Feed;
import com.laurietrichet.whatsnew.parsers.IParser;
import com.laurietrichet.whatsnew.parsers.Parsers;

/**
 * Class to retreive a rss feed from an url.
 * Class extends {@link Request}.
 */
public class FeedRequest extends Request<Feed> {

    private final Response.Listener<Feed> feedListener;

    /**
     * Constructor
     * @param url
     * @param feedListener
     * @param errorListener
     */
    public FeedRequest(String url,
                       Response.Listener<Feed> feedListener, Response.ErrorListener errorListener){
        super(Method.GET, url, errorListener);
        this.feedListener = feedListener;
    }

    @Override
    protected Response<Feed> parseNetworkResponse(NetworkResponse networkResponse) {
        String xml;
        IParser <Feed> rssParser = Parsers.getParser(Parsers.PARSER_TYPE.RSS);
        Feed feed;
        try {
            xml = new String (networkResponse.data, "UTF-8");
            feed = rssParser.parse(xml);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.error(new VolleyError(e));
        }
        if (feed == null){
            return Response.error(
                    new VolleyError(new Exception("RSS feed does not respect mandatory fields")));
        }
        return Response.success(feed, HttpHeaderParser.parseCacheHeaders(networkResponse));
    }

    @Override
    protected void deliverResponse(Feed feed) {
        feedListener.onResponse(feed);
    }
}
