package com.laurietrichet.whatsnew.net.client;

import android.os.Bundle;

import com.android.volley.Request;

/**
 * Describe webservice client
 *
 */
public abstract class AbstractVolleyWebServiceClient<T>{

    /**
     * called when the web service has processed a response
     * @param <T>
     */
    public interface WebServiceClientListener <T>{

        /**
         * called when the request has succeed
         * @param obj result object to process
         */
        public void onSuccess(T obj);

        /**
         * called when an error occurred during process
         * @param error error to process
         */
        public void onError(Error error);
    }

    /**
     * Implement here the call to webservice
     * @param entryPoint entryPoint of web service
     * @param params  params to pass to the request
     * @param listener will be call when client web service returns
     * @return -1 if an error occurred, 0 otherwise
     */
    public int get (String entryPoint, Bundle params, WebServiceClientListener <T> listener){
        return send(createRequest(entryPoint,params,listener));
    }

    /**
     * Implement the code to create a request
     * @param entryPoint entryPoint of web service
     * @param params  params to pass to the request
     * @param listener will be call when client web service returns
     * @return request
     */
    abstract protected Request createRequest (String entryPoint, Bundle params,
                                               WebServiceClientListener <T> listener);

    /**
     * Implement the code to send the request
     * @param request
     * @return -1 if an error occurred, 0 otherwise
     */
    abstract protected int send (Request request);
}
