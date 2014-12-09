package com.devnup.artcatalog.view.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.devnup.artcatalog.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/9/14.
 */
@EViewGroup(R.layout.view_card_container)
public class CardContainerView extends LinearLayout {

    @ViewById(R.id.container)
    LinearLayout container;

    @ViewById(R.id.title)
    TextView title;

    public CardContainerView(Context context) {
        super(context);
    }

    public CardContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CardContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setCardList(List<ContainedCardView> cards) {

        container.removeAllViews();
        int count = 0;

        for (ContainedCardView card : cards) {

            LayoutParams layoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f);

            if (count % 3 == 0) {
                layoutParams.setMargins(8, 0, 4, 8);
            } else if (count % 3 == 1) {
                layoutParams.setMargins(4, 0, 4, 8);
            } else {
                layoutParams.setMargins(4, 0, 8, 8);
            }

            card.setLayoutParams(layoutParams);
            card.setMinimumHeight(240);

            container.addView(card);
            count++;
        }
    }
}
