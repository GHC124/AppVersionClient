package com.ghc.appversionclient.util;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.ghc.appversionclient.BaseApplication;
import com.ghc.appversionclient.LoginActivity;
import com.ghc.appversionclient.R;
import com.ghc.appversionclient.prefs.UserPreferences;

/**
 *
 */
public class LoginError implements Response.ErrorListener {
	private static final String AUTH_MESSAGE = "No authentication challenges found";

	private final Context mContext;
	private final Fragment mFragment;

	public LoginError(Context context, Fragment fragment) {
		mContext = context;
		mFragment = fragment;
	}

	@Override
	public void onErrorResponse(VolleyError error) {
		String message = error.getMessage();
		if (!TextUtils.isEmpty(message)) {
			if (message.contains(AUTH_MESSAGE)) {
				// clear preferences
				UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();
				userPreferences.setUserLoginUserName("");
				userPreferences.setUserLoginToken("");
				// close current activity
				if (mFragment != null && mFragment.getActivity() != null) {
					mFragment.getActivity().finish();
				}
				// show login activity
				Intent intent = new Intent(mContext, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				mContext.startActivity(intent);
			} else {
				AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
				alertDialog.setMessage(message);
				alertDialog.show();
			}
		}else{
            AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
            alertDialog.setMessage(mContext.getString(R.string.display_error));
            alertDialog.show();
        }
		Logger.print(error);
	}
}
