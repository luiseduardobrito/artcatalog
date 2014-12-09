package com.devnup.artcatalog.view.card;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * @author luiseduardobrito
 * @since 12/7/14.
 */
public class CardListAdapter extends BaseAdapter {

    private Context mContext;
    private List<CardView> mList;

    public CardListAdapter(Context mContext, List<CardView> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    public Context getContext() {
        return mContext;
    }

    public List<CardView> getList() {
        return mList;
    }

    public void setCardList(List<CardView> mList) {
        this.mList = mList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return mList.get(position);
    }
}
