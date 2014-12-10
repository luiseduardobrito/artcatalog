package com.devnup.artcatalog.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseActivity;
import com.devnup.artcatalog.view.AlphaForegroundColorSpan;
import com.devnup.artcatalog.view.image.ShowcaseImageView;
import com.devnup.artcatalog.view.list.ArtistProfileListView;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.FreebaseReferenceModel;
import com.devnup.artcatalog.ws.model.VisualArtistModel;
import com.devnup.artcatalog.ws.service.ArtRestService;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
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
    String mid;

    @Bean
    ArtRestService rest;

    @Extra
    VisualArtistModel artist;

    @ViewById(R.id.header_picture)
    ShowcaseImageView mHeaderPicture;

    @ViewById(R.id.info_list)
    ArtistProfileListView mArtistProfileListView;

    @ViewById(R.id.header)
    View mHeader;

    private static final String TAG = "NoBoringActionBarActivity";
    private int mActionBarHeight;
    private int mMinHeaderTranslation;
    private Drawable mActionBarBackgroundDrawable;

    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private String currentTitle = "Loading...";

    private View mPlaceHolderView;

    private TypedValue mTypedValue = new TypedValue();

    @AfterViews
    void initViews() {

        // Prepare header height and translation
        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        // Prepare actionbar title for fade
        int mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        // Setup components
        setupActionBar();

        if (artist == null && mid != null) {

            getArtistForInit(mid);

        } else {

            // Setup List View
            setupListView();

            // Set title as artist name
            currentTitle = artist.getName();

            // Prepare images list
            List<String> images = new ArrayList<String>();
            for (FreebaseReferenceModel model : artist.getImage()) {
                images.add(FreebaseUtil.getImageURL(model.getId()));
            }

            // If there's many, remove first for profile
            if (images.size() > 1) {
                images.remove(0);
            }

            // Populate images
            mHeaderPicture.fillImageViews(images);

            // Prepare artist profile
            mArtistProfileListView.setArtist(artist);

            // Setup Action Bar
            setupActionBar();
        }
    }

    @Background
    void getArtistForInit(String mid) {
        notifyArtistIsReady(rest.getVisualArtist(mid));
    }

    @UiThread
    void notifyArtistIsReady(VisualArtistModel artist) {

        if (artist != null) {
            this.artist = artist;
            initViews();
        } else {
            Toast.makeText(getApplicationContext(), "Could not fetch artist: error", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void setupListView() {

        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mArtistProfileListView, false);
        mArtistProfileListView.addHeaderView(mPlaceHolderView);

        mArtistProfileListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                return;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                int scrollY = getScrollY(); //for verticalScrollView

                //sticky actionbar
                mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));

                //header_logo --> actionbar icon
                float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);

                // interpolate(mHeaderLogo, getActionBarIconView(), mSmoothInterpolator.getInterpolation(ratio));
                //actionbar title alpha
                //getActionBarTitleView().setAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
                //---------------------------------
                //better way thanks to @cyrilmottier
                setToolbarAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
            }
        });
    }

    public int getScrollY() {

        View c = mArtistProfileListView.getChildAt(0);

        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mArtistProfileListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;

        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private void setToolbarAlpha(float alpha) {

        SpannableString mSpannableString = SpannableString.valueOf(currentTitle);

        mAlphaForegroundColorSpan.setAlpha(alpha);
        mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(mSpannableString);

        int newAlpha = (int) (alpha * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);

        //float newY = -(mToolbar.getHeight() * alpha);
        //mToolbar.setY(newY);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    private void setupActionBar() {

        if (mToolbar != null) {
            mActionBarBackgroundDrawable = mToolbar.getBackground();
            mToolbar.setTitle(currentTitle);
            setToolbarAlpha(0f);
        }

        //getActionBarTitleView().setAlpha(0f);
    }

    public int getActionBarHeight() {
        if (mActionBarHeight != 0) {
            return mActionBarHeight;
        }
        getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue, true);
        mActionBarHeight = TypedValue.complexToDimensionPixelSize(mTypedValue.data, getResources().getDisplayMetrics());
        return mActionBarHeight;
    }
}
