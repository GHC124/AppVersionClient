package com.ghc.appversionclient.content.apps;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.ghc.appversionclient.R;
import com.ghc.appversionclient.common.net.response.AppResponse;
import com.ghc.appversionclient.common.net.response.RestResponse;
import com.ghc.appversionclient.common.net.data.AppData;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManagerFactory;
import com.ghc.appversionclient.common.net.parser.AppResponseParser;
import com.ghc.appversionclient.content.IContentDetailView;
import com.ghc.appversionclient.util.Logger;
import com.ghc.appversionclient.util.LoginError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppsFragment implements IContentDetailView {
	private Context mContext;
	private Fragment mFragment;
	private ProgressBar mLoading;
	private ListView mLvApps;

	@Override
	public View getView(Context context, LayoutInflater inflater, ViewGroup container, Fragment fragment) {
		mContext = context;
		mFragment = fragment;

		View layout = inflater.inflate(R.layout.content_apps, null);

		mLvApps = (ListView) layout.findViewById(R.id.ps_lvApps);
		mLoading = (ProgressBar) layout.findViewById(R.id.ps_pbApps_Loading);
		mLoading.setVisibility(View.VISIBLE);
        mLvApps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

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
				if (appResponse != null) {
					if (appResponse.getStatus().equals(RestResponse.SUCCESS)) {
						List<AppData> apps = appResponse.getAppData();
                        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
						for (AppData app : apps) {
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("title", app.getName());
							map.put("app", app);
							data.add(map);
						}
						SimpleAdapter simpleAdapter = new SimpleAdapter(mContext, data,
								android.R.layout.simple_list_item_1, new String[] { "title" },
								new int[] { android.R.id.text1 });
                        mLvApps.setAdapter(simpleAdapter);
                        mLvApps.setVisibility(View.VISIBLE);
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
		AppVersionClientManagerFactory.newGetAllApps(Volley.newRequestQueue(mContext), listener, errorListener)
				.execute();

		return layout;
	}

    @Override
    public void onPause() {

    }
}
