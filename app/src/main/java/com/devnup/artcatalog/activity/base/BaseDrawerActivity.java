/*
 * Copyright (C) 2014 Antonio Leiva Gordillo.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.devnup.artcatalog.activity.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.drawer.DrawerAdapter;
import com.devnup.artcatalog.material.SupportActionBarDrawerToggle;
import com.ikimuhendis.ldrawer.DrawerArrowDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public abstract class BaseDrawerActivity extends ActionBarActivity {

    @ViewById(R.id.drawer_layout)
    protected DrawerLayout mDrawerLayout;

    @ViewById(R.id.drawer)
    protected ListView mDrawerList;

    @ViewById(R.id.toolbar)
    protected Toolbar mToolbar;

    protected SupportActionBarDrawerToggle mDrawerToggle;
    protected DrawerArrowDrawable drawerArrow;

    protected String[] drawerLabels = {
            "Featured",
            "Favorites",
            "Nearby",
            "Search",
            "Profile",
            "Preferences"
    };

    protected int[] drawerIcons = {
            R.drawable.ic_dashboard_grey,
            R.drawable.ic_favorite_grey600_36dp,
            R.drawable.ic_explore_grey600_36dp,
            R.drawable.ic_search_grey600_36dp,
            R.drawable.ic_supervisor_account_grey600_36dp,
            R.drawable.ic_settings_grey600_36dp
    };

    @AfterViews
    protected void initDrawer() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerArrow = new DrawerArrowDrawable(this) {
            @Override
            public boolean isLayoutRtl() {
                return false;
            }
        };
        mDrawerToggle = new SupportActionBarDrawerToggle(this, mDrawerLayout,
                drawerArrow, R.string.drawer_open,
                R.string.drawer_close) {

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        DrawerAdapter adapter = new DrawerAdapter(this, drawerLabels, drawerIcons);
        mDrawerList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
}