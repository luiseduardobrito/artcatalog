package com.devnup.artcatalog.activity;

import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.card.CardContainerView;
import com.devnup.artcatalog.view.card.CardContainerView_;
import com.devnup.artcatalog.view.image.ShowcaseImageView;
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

    private TypedValue mTypedValue = new TypedValue();

    @AfterViews
    void initViews() {

        int mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();

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

        for (int i = 0; i < 10; i++) {
            infoMap.add("Empty entry #" + String.valueOf(i));
        }

        mPlaceHolderView = getLayoutInflater().inflate(R.layout.view_header_placeholder, mListView, false);
        mListView.addHeaderView(mPlaceHolderView);

        // Populate List View
        mListView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                CardContainerView view;

                if (convertView != null && convertView instanceof CardContainerView) {
                    view = (CardContainerView) convertView;
                } else {
                    view = CardContainerView_.build(ArtistActivity.this);
                }

                List<CardView> cards = new ArrayList<CardView>();

                for (int i = 0; i < 3; i++) {
                    cards.add(new CardView(ArtistActivity.this));
                }

                view.setTitle("Featured Artworks");
                view.setCardList(cards);
                return view;
            }
        });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
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
