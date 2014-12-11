package com.devnup.artcatalog.activity.base;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.TypedValue;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.activity.profile.ProfileInfoData;
import com.devnup.artcatalog.activity.profile.ProfileListView;
import com.devnup.artcatalog.view.AlphaForegroundColorSpan;
import com.devnup.artcatalog.view.image.ShowcaseImageView;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/10/14.
 */
@EActivity
public abstract class BaseProfileActivity extends BaseActivity {

    @ViewById(R.id.header_picture)
    protected ShowcaseImageView mHeaderPicture;

    @ViewById(R.id.info_list)
    protected ProfileListView mProfileListView;

    @ViewById(R.id.header)
    protected View mHeader;

    @ViewById(R.id.google_progress)
    protected ProgressBar mProgressBar;

    private int mActionBarHeight;
    private int mMinHeaderTranslation;
    private Drawable mActionBarBackgroundDrawable;

    private View mPlaceHolderView;
    private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
    private TypedValue mTypedValue = new TypedValue();

    @UiThread
    @Override
    @AfterViews
    protected void init() {

        super.init();

        // Prepare header height and translation
        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

        // Prepare actionbar title for fade
        int mActionBarTitleColor = getResources().getColor(R.color.actionbar_title_color);
        mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);

        // Setup components
        setupActionBar();

        if (getData() == null && getMid() != null) {

            mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setIndeterminateDrawable(new FoldingCirclesDrawable.Builder(this).build());

            getDataForInit(getMid());

        }
    }

    protected void notifyDataIsReady() {

        if (getData() != null) {

            // Setup Action Bar
            setupActionBar();

            // Prepare artist profile
            mProfileListView.setData(getData());

            // Populate images
            mHeaderPicture.fillImageViews(getData().getShowcaseImagesURL());

            // Setup List View
            setupListView();

        } else {

            Toast.makeText(this, "Unknown error", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    protected abstract void getDataForInit(String mid);

    protected abstract ProfileInfoData getData();

    protected abstract String getMid();

    @UiThread
    protected void setupListView() {

        if (mPlaceHolderView == null) {
            mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mProfileListView, false);
            mProfileListView.addHeaderView(mPlaceHolderView);

            mProfileListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
    }

    public int getScrollY() {

        View c = mProfileListView.getChildAt(0);

        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = mProfileListView.getFirstVisiblePosition();
        int top = c.getTop();

        int headerHeight = 0;

        if (firstVisiblePosition >= 1) {
            headerHeight = mPlaceHolderView.getHeight();
        }

        return -top + firstVisiblePosition * c.getHeight() + headerHeight;
    }

    @UiThread
    protected void setToolbarAlpha(float alpha) {

        if (getData() != null) {

            SpannableString mSpannableString = SpannableString.valueOf(getData().getTitle());
            mAlphaForegroundColorSpan.setAlpha(alpha);
            mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            getSupportActionBar().setTitle(mSpannableString);

        } else {

            getSupportActionBar().setTitle("");
        }

        int newAlpha = (int) (alpha * 255);
        mActionBarBackgroundDrawable.setAlpha(newAlpha);
        mToolbar.setBackground(mActionBarBackgroundDrawable);

        //float newY = -(mToolbar.getHeight() * alpha);
        //mToolbar.setY(newY);
    }

    public static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    @UiThread
    protected void setupActionBar() {

        if (mToolbar != null) {
            mActionBarBackgroundDrawable = mToolbar.getBackground();
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
