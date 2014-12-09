package com.devnup.artcatalog.drawer;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devnup.artcatalog.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
@EViewGroup(R.layout.view_drawer_item)
public class DrawerItemView extends RelativeLayout {

    @ViewById(R.id.icon)
    ImageView mIcon;

    @ViewById(R.id.label)
    TextView mLabel;

    public DrawerItemView(Context context) {
        super(context);
    }

    public DrawerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLabel(String label) {
        mLabel.setText(label);
    }

    public void setIcon(int resId) {
        mIcon.setImageResource(resId);
    }
}
