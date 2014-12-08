package com.devnup.artcatalog.material;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ikimuhendis.ldrawer.ActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import java.lang.reflect.Method;


public class SupportActionBarDrawerToggle extends ActionBarDrawerToggle {

    private static final String TAG = ActionBarDrawerToggle.class.getName();

    ActionBarActivity mSupportActivity;

    public SupportActionBarDrawerToggle(ActionBarActivity activity, DrawerLayout drawerLayout, int drawerImageRes, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImageRes, openDrawerContentDescRes, closeDrawerContentDescRes);
        mSupportActivity = activity;
        mActivity = activity;
    }

    public SupportActionBarDrawerToggle(ActionBarActivity activity, DrawerLayout drawerLayout, DrawerArrowDrawable drawerImage, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, drawerImage, openDrawerContentDescRes, closeDrawerContentDescRes);
        mSupportActivity = activity;
        mActivity = activity;
    }

    @Override
    protected void setActionBarDescription() {
        if (mSupportActivity != null && mSupportActivity.getSupportActionBar() != null) {
            try {
                Method setHomeActionContentDescription = ActionBar.class.getDeclaredMethod(
                        "setHomeActionContentDescription", Integer.TYPE);
                setHomeActionContentDescription.invoke(mSupportActivity.getSupportActionBar(),
                        mDrawerLayout.isDrawerOpen(GravityCompat.START) ? mOpenDrawerContentDescRes : mCloseDrawerContentDescRes);
                if (Build.VERSION.SDK_INT <= 19) {
                    mSupportActivity.getSupportActionBar().setSubtitle(mSupportActivity.getSupportActionBar().getSubtitle());
                }
            } catch (Exception e) {
                Log.e(TAG, "setActionBarUpIndicator", e);
            }
        }
    }

    @Override
    protected void setActionBarUpIndicator() {
        if (mSupportActivity != null) {
            try {
                Method setHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator",
                        Drawable.class);
                setHomeAsUpIndicator.invoke(mSupportActivity.getSupportActionBar(), mDrawerImage);
                return;
            } catch (Exception e) {
                Log.e(TAG, "setActionBarUpIndicator error", e);
            }

            final View home = mSupportActivity.findViewById(android.R.id.home);
            if (home == null) {
                return;
            }

            final ViewGroup parent = (ViewGroup) home.getParent();
            final int childCount = parent.getChildCount();
            if (childCount != 2) {
                return;
            }

            final View first = parent.getChildAt(0);
            final View second = parent.getChildAt(1);
            final View up = first.getId() == android.R.id.home ? second : first;

            if (up instanceof ImageView) {
                ImageView upV = (ImageView) up;
                upV.setImageDrawable(mDrawerImage);
            }
        }
    }
}
