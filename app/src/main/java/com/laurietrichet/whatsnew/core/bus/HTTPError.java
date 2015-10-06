package com.laurietrichet.whatsnew.core.bus;

/**
 * Created by laurie on 23/07/15.
 * Represents an error that occurred while performing web services requests.
 */
public class HTTPError {

    private final String message;
    private String requestUrl;
    private int status;

    public static class Builder {
        private String requestUrl;
        private int status;
        private String message;

        public Builder setRequestUrl(String requestUrl) {
            this.requestUrl = requestUrl;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public HTTPError build (){
            return new HTTPError(
                    this.requestUrl,
                    this.message,
                    this.status);
        }
    }

    public HTTPError(String requestUrl,
              String message,
              int status) {
        this.requestUrl = requestUrl;
        this.message = message;
        this.status = status;
    }
}
