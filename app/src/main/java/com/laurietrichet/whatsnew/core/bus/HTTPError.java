package com.laurietrichet.whatsnew.core.bus;

/**
 * Created by laurie on 23/07/15.
 * Represents an error that occurred while performing web services requests.
 */
public class HTTPError {

    private String requestUrl;
    private String responseUrl;
    private int status;

    public static class Builder {
        private String requestUrl;
        private String responseUrl;
        private int status;

        public Builder setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public Builder setResponseUrl(String responseUrl) {
            this.responseUrl = responseUrl;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public HTTPError build (){
            return new HTTPError(
                    this.requestUrl,
                    this.responseUrl,
                    this.status);
        }
    }

    private HTTPError(String requestUrl,
              String responseUrl,
              int status) {
        this.requestUrl = requestUrl;
        this.responseUrl = responseUrl;
        this.status = status;
    }
}
