package com.devnup.artcatalog.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseActivity;
import com.devnup.artcatalog.view.image.ShowcaseImageView;
import com.devnup.artcatalog.view.list.ArtworkProfileListView;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtworkModel;
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
@EActivity(R.layout.activity_artwork)
public class ArtworkActivity extends BaseActivity {

    @Extra
    String mid;

    @Bean
    ArtRestService rest;

    @Extra
    VisualArtworkModel artwork;

    @ViewById(R.id.header_picture)
    ShowcaseImageView mHeaderPicture;

    @ViewById(R.id.info_list)
    ArtworkProfileListView mArtworkProfileListView;

    @ViewById(R.id.header)
    View mHeader;

    private static final String TAG = "NoBoringActionBarActivity";
    private int mActionBarHeight;
    private int mMinHeaderTranslation;
    private Drawable mActionBarBackgroundDrawable;

    private View mPlaceHolderView;

    private TypedValue mTypedValue = new TypedValue();

    @AfterViews
    void initViews() {

        // TODO: loading
        setTitle("Loading...");

        // Prepare header height and translation
        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        // Setup components
        setupActionBar();

        if (artwork == null && mid != null) {

            getArtworkForInit(mid);

        } else {

            // Setup List View
            setupListView();

            // Set title as artist name
            setTitle(artwork.getName());
            mToolbar.setTitle(artwork.getName());

            // Prepare images list
            List<String> images = new ArrayList<>();
            images.add(FreebaseUtil.getImageURL(artwork.getMid()));

            // Populate images
            mHeaderPicture.fillImageViews(images);

            // Prepare artist profile
            mArtworkProfileListView.setArtwork(artwork);
        }
    }

    @Background
    void getArtworkForInit(String mid) {
        notifyArtworkIsReady(rest.getVisualArtwork(mid));
    }

    @UiThread
    void notifyArtworkIsReady(VisualArtworkModel artwork) {

        if (artwork != null) {
            this.artwork = artwork;
            initViews();
        } else {
            Toast.makeText(getApplicationContext(), "Could not fetch artwork: error", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    private void setupListView() {

        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mArtworkProfileListView, false);
        mArtworkProfileListView.addHeaderView(mPlaceHolderView);

        mArtworkProfileListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        View c = mArtworkProfileListView.getChildAt(0);

        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mArtworkProfileListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;

        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    private void setToolbarAlpha(float alpha) {

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

    private void setupActionBar() {

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            mActionBarBackgroundDrawable = mToolbar.getBackground();
            setToolbarAlpha(0f);
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
}
