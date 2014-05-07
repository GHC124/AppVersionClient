package com.ghc.appversionclient;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghc.appversionclient.content.ContentDetailFragment;
import com.ghc.appversionclient.content.ContentDetailsViewFactory.MenuItem;

public class MainActivity extends  BaseActivity {
	private MenuItem mCurrentMenu = MenuItem.APPS;

	private ProgressDialog mProgressDialog;
	private RelativeLayout mRlSelectBox;
	private LinearLayout mLlMenuContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRlSelectBox = (RelativeLayout) findViewById(R.id.ps_rlMenuItem_SelectBox);
		mLlMenuContent = (LinearLayout) findViewById(R.id.ps_llMain_MenuContent);

		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage(getString(R.string.display_loading));

		prepareData();
	}


	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {

		}

		return super.onOptionsItemSelected(item);
	}

	public void onMenuItemClick(View v) {
		View old = null;
		if (mCurrentMenu != null) {
			switch (mCurrentMenu) {
			case APPS:
				old = findViewById(R.id.ps_rlMenuItem_Apps);
				break;
			}
		}
        changeSelectedMenu(old, v);
		switch (v.getId()) {
		case R.id.ps_rlMenuItem_Apps:
			mCurrentMenu = MenuItem.APPS;
			changeMenuItem(MenuItem.APPS);
			break;
		}
	}

	public void onMenuItemClick(MenuItem menuItem) {
		View newView = null;
		switch (menuItem) {
		case APPS:
			newView = findViewById(R.id.ps_rlMenuItem_Apps);
			break;
		}
        changeSelectedMenu(null, newView);
	}

	private void changeMenuItem(MenuItem item) {
		Fragment fg = new ContentDetailFragment(item);
		// get fragment
		if (fg != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			ft.replace(R.id.ps_flMain_Content, fg);
			ft.commit();
		}
	}

	// Get data from server
	private void prepareData() {
        mProgressDialog.dismiss();
        View view = findViewById(R.id.ps_rlMenuItem_Apps);
        onMenuItemClick(view);
	}

    private void changeSelectedMenu(final View oldView, final View newView){
        if (oldView != null) {
            TextView oldText = (TextView) oldView.findViewWithTag("menuItem_text");
            if (oldText != null) {
                oldText.setTextColor(Color.parseColor("#000000"));
            }
        }
        TextView newText = (TextView) newView.findViewWithTag("menuItem_text");
        if (newText != null) {
            newText.setTextColor(Color.parseColor("#e4e1bd"));
        }
    }
}
