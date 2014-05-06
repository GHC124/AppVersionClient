package com.ghc.appversionclient.common.net.manager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChungPV1 on 5/6/2014.
 */
public class AppVersionClientManagerFactory {

	private static final String POST_LOGIN_URL = "/users/login";

	public static AppVersionClientManager newPostUserLogin(RequestQueue requestQueue,
			Response.Listener<JSONObject> listener, Response.ErrorListener errorListener, String username,
			String password) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		if (password != null) {
			params.add(new BasicNameValuePair(AppVersionClientManager.PASSWORD_PARAM, password));
		}
		AppVersionClientManager manager = new AppVersionClientManager(requestQueue, Request.Method.POST,
				POST_LOGIN_URL, params, null, listener, errorListener);
        manager.setUserName(username);

		return manager;
	}
}
