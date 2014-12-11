package com.devnup.artcatalog.view.image;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.devnup.artcatalog.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.UiThread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
public class ShowcaseImageView extends FrameLayout {

    private static final String TAG = ShowcaseImageView.class.getSimpleName();

    private final Handler mHandler;
    List<ImageView> mImageViews;
    private int mActiveImageIndex = 0;

    private final Random random = new Random();
    private int mSwapMs = 7500;
    private int mFadeInOutMs = getResources().getInteger(
            android.R.integer.config_mediumAnimTime);

    private float maxScaleFactor = 1.50F;
    private float minScaleFactor = 1.25F;

    private Runnable mSwapImageRunnable = new Runnable() {
        @Override
        public void run() {
            swapImage();
            mHandler.postDelayed(mSwapImageRunnable, mSwapMs - mFadeInOutMs * 2);
        }
    };

    public ShowcaseImageView(Context context) {
        this(context, null);

        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageViews.size() > 1) {
                    swapImage();
                }
            }
        });
    }

    public ShowcaseImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageViews.size() > 1) {
                    swapImage();
                }
            }
        });
    }

    public ShowcaseImageView(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        mHandler = new Handler();

        setClickable(true);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mImageViews.size() > 1) {
                    swapImage();
                }
            }
        });
    }

    private void swapImage() {

        if(mImageViews.size() < 1) {
            return;
        }

        int inactiveIndex = mActiveImageIndex;
        mActiveImageIndex = (1 + mActiveImageIndex) % mImageViews.size();

        final ImageView activeImageView = mImageViews.get(mActiveImageIndex);
        activeImageView.setAlpha(0.0f);

        animate(activeImageView);

        if (inactiveIndex >= 0) {

            ImageView inactiveImageView = mImageViews.get(inactiveIndex);
            inactiveImageView.animate()
                    .alpha(0f)
                    .setDuration(mFadeInOutMs)
                    .setListener(null);

            activeImageView.animate()
                    .alpha(1f)
                    .setDuration(mFadeInOutMs)
                    .setListener(null);

        } else {

            activeImageView.setAlpha(0f);
            activeImageView.animate()
                    .alpha(1f)
                    .setDuration(mFadeInOutMs)
                    .setListener(null);
        }
    }

    private void start(View view, long duration, float fromScale, float toScale, float fromTranslationX, float fromTranslationY, float toTranslationX, float toTranslationY) {
        view.setScaleX(fromScale);
        view.setScaleY(fromScale);
        view.setTranslationX(fromTranslationX);
        view.setTranslationY(fromTranslationY);
        ViewPropertyAnimator propertyAnimator = view.animate().translationX(toTranslationX).translationY(toTranslationY).scaleX(toScale).scaleY(toScale).setDuration(duration);
        propertyAnimator.start();
        Log.d(TAG, "starting Ken Burns animation " + propertyAnimator);
    }

    private float pickScale() {
        return this.minScaleFactor + this.random.nextFloat() * (this.maxScaleFactor - this.minScaleFactor);
    }

    private float pickTranslation(int value, float ratio) {
        return value * (ratio - 1.0f) * (this.random.nextFloat() - 0.5f);
    }

    public void animate(View view) {
        float fromScale = pickScale();
        float toScale = pickScale();
        float fromTranslationX = pickTranslation(view.getWidth(), fromScale);
        float fromTranslationY = pickTranslation(view.getHeight(), fromScale);
        float toTranslationX = pickTranslation(view.getWidth(), toScale);
        float toTranslationY = pickTranslation(view.getHeight(), toScale);
        view.setVisibility(View.VISIBLE);
        start(view, this.mSwapMs, fromScale, toScale, fromTranslationX, fromTranslationY, toTranslationX, toTranslationY);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeCallbacks(mSwapImageRunnable);
    }

    private void startAnimation() {
        mHandler.post(mSwapImageRunnable);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = inflate(getContext(), R.layout.view_showcase, this);
        mImageViews = new ArrayList<ImageView>();
    }

    public void fillImageViews(List<String> images) {

        this.removeAllViews();
        mImageViews = new ArrayList<ImageView>();

        for (String image : images) {

            ImageView iv = new ImageView(getContext());
            iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            iv.setVisibility(View.INVISIBLE);
            mImageViews.add(iv);

            Picasso
                    .with(getContext())
                    .load(image)
                    .into(iv);

            this.addView(iv);
        }

        mHandler.removeCallbacks(mSwapImageRunnable);
        mHandler.post(mSwapImageRunnable);
    }
}
