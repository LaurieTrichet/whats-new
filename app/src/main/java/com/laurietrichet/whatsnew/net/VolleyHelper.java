package com.laurietrichet.whatsnew.net;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;

import java.util.Map;

/**
 * Singleton to wrap {@link com.android.volley.toolbox.Volley} library
 */
public enum  VolleyHelper {

    INSTANCE;

    private RequestQueue mRequestQueue = null;

    /**
     * Initialize and return the {@link com.android.volley.RequestQueue} to add volley request
     * @param context requested from the volley library
     * @param request request to add to the queue
     * @return the request given in parameter see method add {@link com.android.volley.RequestQueue}
     */
    public Request addToRequestQueue(Context context,Request request) {
        checkInit (context);
        return mRequestQueue.add(request);
    }

    /**
     * Returns the cache of the request queue, throws an exception if the queue is not initialized.
     * To do so the addToRequestQueue function has to be called.
     * @return Cache // the cache of the request queue
     * @throws Exception // if the request queue is null
     */
    public Cache getCache () throws Exception{
        if (mRequestQueue == null){
            throw new Exception("Request queue must be initialized.");
        }
        return mRequestQueue.getCache();
    }

    private void checkInit (Context context){
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    /**
     * Use to provide a cache fallback when no cache no store header is set to use app offline
     * @param response
     * @return cache entry
     */
    public static Cache.Entry parseCacheHeaders(NetworkResponse response) {
        long now = System.currentTimeMillis();

        Map<String, String> headers = response.headers;

        long serverDate = 0;
        long serverExpires = 0;
        long softExpire = 0;
        long maxAge = 0;
        boolean hasCacheControl = false;

        String serverEtag = null;
        String headerValue;

        headerValue = headers.get("Date");
        if (headerValue != null) {
            serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        headerValue = headers.get("Cache-Control");
        if (headerValue != null) {
            hasCacheControl = true;
            String[] tokens = headerValue.split(",");
            for (int i = 0; i < tokens.length; i++) {
                String token = tokens[i].trim();
                if (token.equals("no-cache") || token.equals("no-store")) {
                    continue;
                } else if (token.startsWith("max-age=")) {
                    try {
                        maxAge = Long.parseLong(token.substring(8));
                    } catch (Exception e) {
                    }
                } else if (token.equals("must-revalidate") || token.equals("proxy-revalidate")) {
                    maxAge = 0;
                }
            }
        }

        headerValue = headers.get("Expires");
        if (headerValue != null) {
            serverExpires = HttpHeaderParser.parseDateAsEpoch(headerValue);
        }

        serverEtag = headers.get("ETag");

        // Cache-Control takes precedence over an Expires header, even if both exist and Expires
        // is more restrictive.
        if (hasCacheControl) {
            softExpire = now + maxAge * 1000;
        } else if (serverDate > 0 && serverExpires >= serverDate) {
            // Default semantic for Expire header in HTTP specification is softExpire.
            softExpire = now + (serverExpires - serverDate);
        }

        Cache.Entry entry = new Cache.Entry();
        entry.data = response.data;
        entry.etag = serverEtag;
        entry.softTtl = softExpire;
        entry.ttl = entry.softTtl;
        entry.serverDate = serverDate;
        entry.responseHeaders = headers;

        return entry;
    }
}
