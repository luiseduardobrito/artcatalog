package com.devnup.artcatalog.view.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EViewGroup;

/**
 * @author luiseduardobrito
 * @since 12/8/14.
 */
@EViewGroup
public class CardShowcaseView extends LinearLayout {

    public CardShowcaseView(Context context) {
        super(context);
    }

    public CardShowcaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardShowcaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void init() {
        this.setOrientation(HORIZONTAL);
        this.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
    }

    public void addCard(CardView card) {

    }
}
