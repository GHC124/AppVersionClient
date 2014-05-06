package com.ghc.appversionclient.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {
	/** holds application shared preferences. */
	private final SharedPreferences mSharedPrefs;

	public UserPreferences(Context context) {
		mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
	}

	public SharedPreferences getSharedPreferences() {
		return mSharedPrefs;
	}

	public String getUserLoginToken() {
		return mSharedPrefs.getString(UserPreferencesConstants.USER_LOGIN_TOKEN, null);
	}

	public void setUserLoginToken(String token) {
		mSharedPrefs
				.edit()
				.putString(UserPreferencesConstants.USER_LOGIN_TOKEN,
						token).apply();
	}

    public String getUserLoginUserName() {
        return mSharedPrefs.getString(UserPreferencesConstants.USER_LOGIN_USERNAME, null);
    }

    public void setUserLoginUserName(String username) {
        mSharedPrefs
                .edit()
                .putString(UserPreferencesConstants.USER_LOGIN_USERNAME,
                        username).apply();
    }

}
