package com.ghc.appversionclient.content.apps;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ghc.appversionclient.content.IContentDetailView;

public class AppsFragment implements IContentDetailView {
	private Context mContext;
	private Fragment mFragment;
	private ProgressBar mLoading;

	@Override
	public View getView(Context context, LayoutInflater inflater,
			ViewGroup container, Fragment fragment) {
		mContext = context;
		mFragment = fragment;

		return null;
	}
}
