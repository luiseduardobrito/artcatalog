package com.devnup.artcatalog.view;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.devnup.artcatalog.R;
import com.squareup.picasso.Picasso;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@EViewGroup(R.layout.view_card)
public class SampleCardView extends CardView {

    @ViewById(R.id.title)
    TextView mTitle;

    @ViewById(R.id.image)
    ImageView mImage;

    public SampleCardView(Context context) {
        super(context);
    }

    public SampleCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SampleCardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTitle(String title) {
        mTitle.setText(title);
    }

    public void setImageUrl(String url) {
        Picasso
                .with(getContext())
                .load(url)
                .into(mImage);
    }

    public String getTitle() {
        return mTitle.getText().toString();
    }
}
