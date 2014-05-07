package com.ghc.appversionclient.common.net.parser;

import com.ghc.appversionclient.common.net.Response.RestResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

/**
 *
 */
public interface ResponseParser {
   RestResponse parse(JSONObject jsonObj) throws JSONException, ParseException;
}
