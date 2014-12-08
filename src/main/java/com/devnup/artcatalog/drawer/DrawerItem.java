package com.devnup.artcatalog.drawer;

import android.content.Context;
import android.util.AttributeSet;
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
public class DrawerItem extends RelativeLayout {

    @ViewById(R.id.label)
    TextView mLabel;

    public DrawerItem(Context context) {
        super(context);
    }

    public DrawerItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawerItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLabel(String label) {
        mLabel.setText(label);
    }
}
