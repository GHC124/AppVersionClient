package com.ghc.appversionclient.common.net.Response;

import com.ghc.appversionclient.common.net.data.AppData;

import java.util.List;

/**
 *
 */
public class AppResponse extends RestResponse {
    private List<AppData> mAppData;

    public List<AppData> getAppData() {
        return mAppData;
    }

    public void setAppData(List<AppData> appData) {
        mAppData = appData;
    }
}
