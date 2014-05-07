package com.ghc.appversionclient.content.apps;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ghc.appversionclient.R;
import com.ghc.appversionclient.common.net.Response.AppResponse;
import com.ghc.appversionclient.common.net.Response.RestResponse;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManagerFactory;
import com.ghc.appversionclient.common.net.parser.AppResponseParser;
import com.ghc.appversionclient.content.IContentDetailView;
import com.ghc.appversionclient.util.Logger;
import com.ghc.appversionclient.util.LoginError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class AppsFragment implements IContentDetailView {
	private Context mContext;
	private Fragment mFragment;
	private ProgressBar mLoading;

	@Override
	public View getView(Context context, LayoutInflater inflater,
			ViewGroup container, Fragment fragment) {
		mContext = context;
		mFragment = fragment;

        View layout = inflater.inflate(R.layout.content_apps, null);
        
        mLoading = (ProgressBar)layout.findViewById(R.id.ps_pbApps_Loading);
        mLoading.setVisibility(View.VISIBLE);

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                mLoading.setVisibility(View.INVISIBLE);
                AppResponse appResponse = null;
                try {
                    appResponse = (AppResponse) new AppResponseParser().parse(response);
                } catch (JSONException e) {
                    Logger.print(e);
                } catch (ParseException e) {
                    Logger.print(e);
                }
                if(appResponse != null){
                    if(appResponse.getStatus().equals(RestResponse.SUCCESS)){
                        Logger.d("ChungPV1", "App: " + appResponse);
                    }
                }
            }
        };
        Response.ErrorListener errorListener = new LoginError(mContext, mFragment) {

            @Override
            public void onErrorResponse(VolleyError error) {
                mLoading.setVisibility(View.INVISIBLE);
                super.onErrorResponse(error);
            }
        };
        AppVersionClientManagerFactory
                .newGetAllApps(Volley.newRequestQueue(mContext), listener, errorListener).execute();

		return layout;
	}
}
