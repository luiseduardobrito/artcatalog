package com.devnup.artcatalog.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by luiseduardobrito on 12/8/14.
 */
public class ParallaxImageView extends ImageView {

    private int mCurrentTranslation;

    public ParallaxImageView(Context context) {
        super(context);
    }

    public ParallaxImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setCurrentTranslation(int currentTranslation) {
        mCurrentTranslation = currentTranslation;
        invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.save();
        canvas.translate(0, -mCurrentTranslation / 2);
        super.draw(canvas);
        canvas.restore();
    }
}