package com.ghc.appversionclient.common.net.response;

/**
 *
 */
public class RestResponse {
    public static final String FAIL = "FAIL";
    public static final String SUCCESS = "SUCCESS";

    private String mStatus;
    private String mMessage;

    public RestResponse() {
    }

    public RestResponse(RestResponse restResponse) {
        this(restResponse.getStatus(), restResponse.getMessage());
    }

    public RestResponse(String status, String message) {
        mStatus = status;
        mMessage = message;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    @Override
    public String toString(){
        return mStatus + ":" + mMessage;
    }
}
