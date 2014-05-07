package com.ghc.appversionclient.common.net.parser;

import com.ghc.appversionclient.common.net.response.RestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 *
 */
public class RestResponseParser implements ResponseParser {
	private static final String STATUS_KEY = "status";
	private static final String MESSAGE_KEY = "message";

	@Override
	public RestResponse parse(JSONObject jsonObj) throws JSONException, ParseException {
		RestResponse data = new RestResponse();

		if (jsonObj.has(STATUS_KEY)) {
			data.setStatus(jsonObj.getString(STATUS_KEY));
		}

		if (jsonObj.has(MESSAGE_KEY)) {
			data.setMessage(jsonObj.getString(MESSAGE_KEY));
		}

		return data;
	}
}
