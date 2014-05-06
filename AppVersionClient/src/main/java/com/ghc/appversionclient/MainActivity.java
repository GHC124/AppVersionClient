package com.ghc.appversionclient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ghc.appversionclient.animation.AnimationFactory;
import com.ghc.appversionclient.common.net.manager.AppVersionClientManager;
import com.ghc.appversionclient.content.ContentDetailFragment;
import com.ghc.appversionclient.content.ContentDetailsViewFactory.MenuItem;


public class MainActivity extends Activity {
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

        BaseApplication.getInstance().enableLog();
        AppVersionClientManager.setBaseURL("http://localhost:8088/AppVersion/rest");
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.display_loading));
        mProgressDialog.show();

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
                    old = findViewById(R.id.ps_rlMenuItem_Hot);
                    break;
                    break;
            }
        }
        moveSelectBox(old, v);
        switch (v.getId()) {
            case R.id.ps_rlMenuItem_Hot:
                mCurrentMenu = MenuItem.HOT;
                changeMenuItem(MenuItem.HOT);
                break;
        }
    }

    public void onMenuItemClick(MenuItem menuItem) {
        View newView = null;
        switch (menuItem) {
            case APPS:
                newView = findViewById(R.id.ps_rlMenuItem_Hot);
                break;
        }
        moveSelectBox(null, newView);
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

    }

    private void moveSelectBox(final View oldView, final View newView) {
        int[] fromLocation = new int[2];
        int[] toLocation = new int[2];
        if (oldView != null) {
            oldView.getLocationOnScreen(fromLocation);
            TextView oldText = (TextView) oldView
                    .findViewWithTag("menuItem_text");
            if (oldText != null) {
                oldText.setTextColor(Color.parseColor("#000000"));
            }
        }
        newView.getLocationOnScreen(toLocation);
        int offset = (int) getResources().getDimension(
                R.dimen.ps_dimen_height_8p7);
        fromLocation[1] = fromLocation[1] - mRlSelectBox.getHeight() - offset;
        toLocation[1] = toLocation[1] - mRlSelectBox.getHeight() - offset;

        float fromXDelta = fromLocation[0];
        float toXDelta = toLocation[0];
        float fromYDelta = fromLocation[1];
        float toYDelta = toLocation[1];

        Animation animation = AnimationFactory.TranslateAnimation(
                Animation.ABSOLUTE, fromXDelta, toXDelta, fromYDelta, toYDelta,
                500, 0, new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation1) {
                        TextView newText = (TextView) newView
                                .findViewWithTag("menuItem_text");
                        if (newText != null) {
                            newText.setTextColor(Color.parseColor("#e4e1bd"));
                        }
                        if (mRlSelectBox.getVisibility() != View.VISIBLE) {
                            mRlSelectBox.setVisibility(View.VISIBLE);
                        }
                        //mRlSelectBox.clearAnimation();
                    }
                }
        );
        mRlSelectBox.startAnimation(animation);
    }
}
