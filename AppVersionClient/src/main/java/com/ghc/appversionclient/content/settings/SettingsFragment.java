package com.ghc.appversionclient.content.settings;

import android.app.Fragment;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.ghc.appversionclient.BaseApplication;
import com.ghc.appversionclient.R;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManager;
import com.ghc.appversionclient.content.IContentDetailView;
import com.ghc.appversionclient.prefs.UserPreferences;

public class SettingsFragment implements IContentDetailView {
	private Context mContext;
	private Fragment mFragment;
	private ProgressBar mLoading;
	private EditText mServer;

	@Override
	public View getView(Context context, LayoutInflater inflater, ViewGroup container, Fragment fragment) {
		mContext = context;
		mFragment = fragment;

		View layout = inflater.inflate(R.layout.content_settings, null);

        mServer = (EditText)layout.findViewById(R.id.tvSettings_Server);

        UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();

        String serverEndpoint = userPreferences.getServerEndpoint();
        if(!TextUtils.isEmpty(serverEndpoint) && mServer != null) {
            mServer.setText(serverEndpoint);
        }

		return layout;
	}

    @Override
    public void onPause() {
        UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();

        String serverEndpoint = mServer.getText().toString();
        if(!TextUtils.isEmpty(serverEndpoint)){
            userPreferences.setServerEndpoint(serverEndpoint);
            AppVersionClientManager.setBaseURL(serverEndpoint);
        }
    }
}
