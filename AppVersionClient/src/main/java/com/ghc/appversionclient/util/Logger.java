package com.ghc.appversionclient.util;

import android.util.Log;

import com.ghc.appversionclient.BaseApplication;

/**
 *
 */
public class Logger {
	public static void d(String tag, String message) {
		if (BaseApplication.getInstance().loggingEnabled()) {
			Log.d(tag, message);
		}
	}

    public static void e(String tag, String message) {
        if (BaseApplication.getInstance().loggingEnabled()) {
            Log.e(tag, message);
        }
    }

    public static void i(String tag, String message) {
        if (BaseApplication.getInstance().loggingEnabled()) {
            Log.i(tag, message);
        }
    }

    public static void print(Exception e) {
        if (BaseApplication.getInstance().loggingEnabled()) {
            e.printStackTrace();
        }
    }
}
