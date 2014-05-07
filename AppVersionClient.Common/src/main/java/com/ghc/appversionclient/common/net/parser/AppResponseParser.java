package com.ghc.appversionclient.common.net.parser;

import com.ghc.appversionclient.common.net.Response.AppResponse;
import com.ghc.appversionclient.common.net.Response.RestResponse;
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
        super.parse(jsonObj);

        AppResponse data = new AppResponse();

        if(jsonObj.has(DATA_KEY)) {
            List<AppData> appData = new ArrayList<AppData>();
            JSONArray jsonArray = jsonObj.getJSONArray(DATA_KEY);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject item = jsonArray.getJSONObject(i);
                AppData app = new AppData();
                if (item.has(ID_KEY)) {
                    app.setId(jsonObj.getLong(ID_KEY));
                }
                if (item.has(NAME_KEY)) {
                    app.setName(jsonObj.getString(NAME_KEY));
                }
                if (item.has(ICON_URL_KEY)) {
                    app.setIconUrl(jsonObj.getString(ICON_URL_KEY));
                }
                if (item.has(DESCRIPTION_KEY)) {
                    app.setDescription(jsonObj.getString(DESCRIPTION_KEY));
                }
                if (item.has(LATEST_VERSION_KEY)) {
                    app.setLatestVersion(jsonObj.getString(LATEST_VERSION_KEY));
                }
                if (item.has(PLATFORM_ID_KEY)) {
                    app.setPlatformId(jsonObj.getLong(PLATFORM_ID_KEY));
                }
                appData.add(app);
			}
            data.setAppData(appData);
        }


        return data;
    }
}
