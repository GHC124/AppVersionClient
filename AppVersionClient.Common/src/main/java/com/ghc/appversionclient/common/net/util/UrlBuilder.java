package com.ghc.appversionclient.common.net.util;

import org.apache.http.NameValuePair;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 *
 */
public class UrlBuilder {
    public static String buildUrl(String url, List<NameValuePair> params) {
        return buildUrl(new StringBuilder(url), params, true);
    }

    public static String buildUrl(StringBuilder sb, List<NameValuePair> params, boolean encode) {
        boolean first = true;
        try {
            for (NameValuePair param : params) {
                sb.append(first ? '?' : '&');
                String name = param.getName();
                sb.append(encode ? URLEncoder.encode(name, "UTF-8") : name);
                sb.append('=');
                String value = param.getValue();
                sb.append(encode ? URLEncoder.encode(param.getValue(), "UTF-8") : value);
                first = false;
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }    
}
