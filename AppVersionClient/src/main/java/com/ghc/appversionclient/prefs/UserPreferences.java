package com.ghc.appversionclient.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserPreferences {
    private static UserPreferences INSTANCE;

    public static UserPreferences getInstance(Context context){
        if(INSTANCE == null){
            synchronized (UserPreferences.class){
                if(INSTANCE == null){
                    // User applicationContext here for Application
                    INSTANCE = new UserPreferences(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

	/** holds application shared preferences. */
	private final SharedPreferences mSharedPrefs;

	private UserPreferences(Context context) {
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

    public String getServerEndpoint() {
        return mSharedPrefs.getString(UserPreferencesConstants.SERVER_ENDPOINT, null);
    }

    public void setServerEndpoint(String server) {
        mSharedPrefs
                .edit()
                .putString(UserPreferencesConstants.SERVER_ENDPOINT,
                        server).apply();
    }

}
