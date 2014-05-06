package com.ghc.appversionclient.common.net.manager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ghc.appversionclient.common.net.util.UrlBuilder;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AppVersionClientManager {
	public static final String USERNAME_PARAM = "j_username";
	public static final String PASSWORD_PARAM = "j_password";
	public static final String TOKEN_PARAM = "j_token";

	private static String sBaseURL;
	private static String sToken;
	private static String sUserName;

	private List<NameValuePair> mParams;
	private RequestQueue mRequestQueue;
	private JsonObjectRequest mRequest;

	public AppVersionClientManager(RequestQueue requestQueue, int requestType, String url, List<NameValuePair> params,
			JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		mRequestQueue = requestQueue;
		mRequest = buildRequest(requestType, url, params, jsonRequest, listener, errorListener);
	}

	public void execute() {
		mRequestQueue.add(mRequest);
	}

	public static String getBaseURL() {
		return sBaseURL;
	}

	public static void setBaseURL(String baseURL) {
		sBaseURL = baseURL;
	}

	public static String getToken() {
		return sToken;
	}

	public static void setToken(String token) {
		sToken = token;
	}

	public static String getUserName() {
		return sUserName;
	}

	public static void setUserName(String userName) {
		sUserName = userName;
	}

	private JsonObjectRequest buildRequest(int requestType, String url, List<NameValuePair> params,
			JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
		// add auth param
		if (params == null) {
			params = new ArrayList<NameValuePair>();
		}
		params.add(new BasicNameValuePair(USERNAME_PARAM, sUserName));
		params.add(new BasicNameValuePair(TOKEN_PARAM, sToken));

		String authUrl = UrlBuilder.buildUrl(sBaseURL + url, params);

		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(requestType, authUrl, jsonRequest, listener,
				errorListener);
		return jsonObjectRequest;
	}
}
