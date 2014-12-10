package com.devnup.artcatalog.view.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.devnup.artcatalog.view.image.RoundedTransformation;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@EViewGroup(R.layout.view_card_contained)
public class ContainedCardView extends CardView {

    private static final int RADIUS = 4;

    @ViewById(R.id.title)
    TextView mTitle;

    @ViewById(R.id.image)
    ImageView mImage;

    public ContainedCardView(Context context) {
        super(context);
        this.setRadius(RADIUS);
    }

    public ContainedCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setRadius(RADIUS);
    }

    public ContainedCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setRadius(RADIUS);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setImageUrl(String url) {
        Picasso
                .with(getContext())
                .load(url)
                .fit()
                .centerCrop()
                .transform(new RoundedTransformation(RADIUS, 0))
                .into(mImage);
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }
}
