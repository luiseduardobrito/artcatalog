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

    public DrawerAdapter(Context context, String[] array) {
        this.context = context;
        this.list = new ArrayList<String>();
        Collections.addAll(this.list, array);
    }

    public DrawerAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
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

        DrawerItem item;

        if (convertView != null && convertView instanceof DrawerItem) {
            item = (DrawerItem) convertView;
        } else {
            item = DrawerItem_.build(context);
        }

        item.setLabel(list.get(position));
        return item;
    }
}
