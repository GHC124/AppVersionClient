package com.ghc.appversionclient;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ghc.appversionclient.common.net.manager.AppVersionClientManager;
import com.ghc.appversionclient.prefs.UserPreferences;


public class SettingActivity extends Activity {
    private EditText mServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mServer = (EditText)findViewById(R.id.tvSettings_Server);

        UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();

        String serverEndpoint = userPreferences.getServerEndpoint();
        if(!TextUtils.isEmpty(serverEndpoint) && mServer != null) {
            mServer.setText(serverEndpoint);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    
    public void onSaveClick(View view){
        UserPreferences userPreferences = BaseApplication.getInstance().getUserPreferences();

        String serverEndpoint = mServer.getText().toString();
        if(!TextUtils.isEmpty(serverEndpoint)){
            userPreferences.setServerEndpoint(serverEndpoint);
            AppVersionClientManager.setBaseURL(serverEndpoint);
        }

        finish();
    }
}
