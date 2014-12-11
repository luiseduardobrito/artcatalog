package com.devnup.artcatalog.activity.profile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EView;
import org.androidannotations.annotations.UiThread;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EView
public class ProfileListView extends ListView {

    ProfileInfoData data;

    public ProfileListView(Context context) {
        super(context);
    }

    public ProfileListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initViews() {
        setDividerHeight(0);
        setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    @UiThread
    public void setData(ProfileInfoData d) {

        this.data = d;

        setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return data.getSectionTitles().length + 1;
            }

            @Override
            public Object getItem(int position) {
                return data.getSectionTitles()[position];
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (position == 0) {

                    return data.getInfoView(getContext(), convertView);

                } else {

                    return data.getCardContainer(position, convertView);

                }
            }
        });
    }

    public ProfileInfoData getData() {
        return this.data;
    }
}
