package com.devnup.artcatalog.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.drawer.DrawerItem;
import com.devnup.artcatalog.drawer.DrawerItem_;
import com.devnup.artcatalog.view.AlphaForegroundColorSpan;
import com.devnup.artcatalog.view.ShowcaseImageView;
import com.devnup.artcatalog.ws.model.ImageModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@EActivity(R.layout.activity_artist)
public class ArtistActivity extends BaseActivity {

    @Extra
    VisualArtistModel artist;

    @ViewById(R.id.header_picture)
    ShowcaseImageView mHeaderPicture;

    @ViewById(R.id.info_list)
    ListView mListView;

    @ViewById(R.id.header)
    View mHeader;

    private static final String TAG = "NoBoringActionBarActivity";
    private int mActionBarHeight;
    private int mMinHeaderTranslation;
    private Drawable mActionBarBackgroundDrawable;

    private View mPlaceHolderView;

    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private SpannableString mSpannableString;

    private TypedValue mTypedValue = new TypedValue();

    @AfterViews
    void initViews() {

        // initDrawer();

        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        // mHeaderPicture.setResourceIds(R.drawable.picture0, R.drawable.picture1);

        int mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);

        mSpannableString = new SpannableString(getString(R.string.noboringactionbar_title));
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        setupActionBar();
        setupListView();

        // Set title as artist name
        setTitle(artist.getName());
        mToolbar.setTitle(artist.getName());

        // Prepare images list
        List<String> images = new ArrayList<String>();
        for (ImageModel model : artist.getImage()) {
            images.add("https://usercontent.googleapis.com/freebase/v1/image" + model.getId() + "?maxwidth=225&maxheight=225&mode=fillcropmid");
        }

        // Populate images
        mHeaderPicture.fillImageViews(images);
    }

    private void setupListView() {

        final ArrayList<String> infoMap = new ArrayList<String>();

        infoMap.add("Freebase ID: " + artist.getId());
        infoMap.add("Name: " + artist.getType());

        StringBuilder artFormsString = new StringBuilder();

        for (String form : artist.getArtForms()) {
            artFormsString.append(form);
        }

        infoMap.add("Art Forms: " + artFormsString.toString());

        for(int i = 0; i < 10; i++) {
            infoMap.add("Empty entry #" + String.valueOf(i));
        }

        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);
        mListView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return infoMap.size();
            }

            @Override
            public Object getItem(int position) {
                return infoMap.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                DrawerItem view;

                if (convertView != null && convertView instanceof DrawerItem) {
                    view = (DrawerItem) convertView;
                } else {
                    view = DrawerItem_.build(ArtistActivity.this);
                }

                view.setLabel(infoMap.get(position));
                return view;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int scrollY = getScrollY();
                //sticky actionbar
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
                //header_logo --> actionbar icon
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
                // interpolate(mHeaderLogo, getActionBarIconView(), mSmoothInterpolator.getInterpolation(ratio));
                //actionbar title alpha
                //getActionBarTitleView().setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
                //---------------------------------
                //better way thanks to @cyrilmottier
                setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });
    }

    private void setTitleAlpha(float alpha) {

        //mAlphaForegroundColorSpan.setAlpha(1 - alpha);
        //mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //getSupportActionBar().setTitle(mSpannableString);

        int newAlpha = (int) (alpha * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);

        //float newY = -(mToolbar.getHeight() * alpha);
        //mToolbar.setY(newY);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    public int getScrollY() {
        View c = mListView.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;
        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setIcon(R.drawable.ic_transparent);
            mActionBarBackgroundDrawable = mToolbar.getBackground();
        }

        //getActionBarTitleView().setAlpha(0f);
    }

    /*private TextView getActionBarTitleView() {
        int id = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
        return (TextView) findViewById(id);
    }*/

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_artist;
    }
}
