package com.ghc.appversionclient.common.net.response;

import com.ghc.appversionclient.common.net.data.AppData;

import java.util.List;

/**
 *
 */
public class AppResponse extends RestResponse {
    public AppResponse(RestResponse restResponse){
        super(restResponse);
    }


    private List<AppData> mAppData;

    public List<AppData> getAppData() {
        return mAppData;
    }

    public void setAppData(List<AppData> appData) {
        mAppData = appData;
    }
}
