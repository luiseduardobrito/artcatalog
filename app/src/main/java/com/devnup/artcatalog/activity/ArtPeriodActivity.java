package com.devnup.artcatalog.activity;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.base.BaseActivity;
import com.devnup.artcatalog.view.AlphaForegroundColorSpan;
import com.devnup.artcatalog.view.image.ShowcaseImageView;
import com.devnup.artcatalog.view.list.ArtPeriodProfileListView;
import com.devnup.artcatalog.ws.FreebaseUtil;
import com.devnup.artcatalog.ws.model.VisualArtPeriodModel;
import com.devnup.artcatalog.ws.service.ArtRestService;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

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
@EActivity(R.layout.activity_artperiod)
public class ArtPeriodActivity extends BaseActivity {

    @Extra
    String mid;

    @Bean
    ArtRestService rest;

    @Extra
    VisualArtPeriodModel period;

    @ViewById(R.id.header_picture)
    ShowcaseImageView mHeaderPicture;

    @ViewById(R.id.info_list)
    ArtPeriodProfileListView mArtPeriodProfileListView;

    @ViewById(R.id.header)
    View mHeader;

    @ViewById(R.id.google_progress)
    ProgressBar mProgressBar;

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

        // TODO: loading
        setTitle("Loading...");

        // Prepare header height and translation
        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        // Prepare actionbar title for fade
        int mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        // Setup components
        setupActionBar();

        if (period == null && mid != null) {

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());

            getArtworkForInit(mid);

        } else {

            // Setup List View
            setupListView();

            // Set title as artist name
            currentTitle = period.getName();

            // Prepare images list
            List<String> images = new ArrayList<>();
            images.add(FreebaseUtil.getImageURL(period.getMid()));

            // Populate images
            mHeaderPicture.fillImageViews(images);

            // Prepare artist profile
            mArtPeriodProfileListView.setArtPeriod(period);
        }
    }

    @Background
    void getArtworkForInit(String mid) {
        notifyArtworkIsReady(rest.getVisualArtPeriod(mid));
    }

    @UiThread
    void notifyArtworkIsReady(VisualArtPeriodModel period) {

        if (period != null) {
            this.period = period;
            initViews();
        } else {
            Toast.makeText(getApplicationContext(), "Could not fetch art period: error", Toast.LENGTH_SHORT).show();
            this.finish();
        }

        mProgressBar.setVisibility(View.GONE);
    }

    private void setupListView() {

        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mArtPeriodProfileListView, false);
        mArtPeriodProfileListView.addHeaderView(mPlaceHolderView);

        mArtPeriodProfileListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

        View c = mArtPeriodProfileListView.getChildAt(0);

        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mArtPeriodProfileListView.getFirstVisiblePosition();
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
