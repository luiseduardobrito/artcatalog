package com.devnup.artcatalog.drawer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class DrawerAdapter extends BaseAdapter {

    Context context;
    List<String> list;
    int[] icons;

    public DrawerAdapter(Context context, String[] array, int[] icons) {

        this.context = context;
        this.icons = icons;

        this.list = new ArrayList<String>();
        Collections.addAll(this.list, array);
    }

    public DrawerAdapter(Context context, List<String> list, int[] icons) {
        this.context = context;
        this.list = list;
        this.icons = icons;
    }

    public DrawerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<String>();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DrawerItemView item;

        if (convertView != null && convertView instanceof DrawerItemView) {
            item = (DrawerItemView) convertView;
        } else {
            item = DrawerItemView_.build(context);
        }

        item.setIcon(icons[position]);
        item.setLabel(list.get(position));
        return item;
    }
}
