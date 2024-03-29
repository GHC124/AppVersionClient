package com.ghc.appversionclient.common.net.parser;

import com.ghc.appversionclient.common.net.response.AppResponse;
import com.ghc.appversionclient.common.net.response.RestResponse;
import com.ghc.appversionclient.common.net.data.AppData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AppResponseParser extends RestResponseParser implements ResponseParser {
    private static final String DATA_KEY = "data";
    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String ICON_URL_KEY = "iconUrl";
    private static final String DESCRIPTION_KEY = "description";
    private static final String LATEST_VERSION_KEY = "latestVersion";
    private static final String PLATFORM_ID_KEY = "platformId";

    @Override
    public RestResponse parse(JSONObject jsonObj) throws JSONException, ParseException {
        RestResponse restResponse = super.parse(jsonObj);
        AppResponse data = new AppResponse(restResponse);

        if(jsonObj.has(DATA_KEY)) {
            List<AppData> appData = new ArrayList<AppData>();
            JSONArray jsonArray = jsonObj.getJSONArray(DATA_KEY);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
                AppData app = new AppData();
                if (item.has(ID_KEY)) {
                    app.setId(item.getLong(ID_KEY));
                }
                if (item.has(NAME_KEY)) {
                    app.setName(item.getString(NAME_KEY));
                }
                if (item.has(ICON_URL_KEY)) {
                    app.setIconUrl(item.getString(ICON_URL_KEY));
                }
                if (item.has(DESCRIPTION_KEY)) {
                    app.setDescription(item.getString(DESCRIPTION_KEY));
                }
                if (item.has(LATEST_VERSION_KEY)) {
                    app.setLatestVersion(item.getString(LATEST_VERSION_KEY));
                }
                if (item.has(PLATFORM_ID_KEY)) {
                    app.setPlatformId(item.getLong(PLATFORM_ID_KEY));
                }
                appData.add(app);
			}
            data.setAppData(appData);
        }


        return data;
    }
}
