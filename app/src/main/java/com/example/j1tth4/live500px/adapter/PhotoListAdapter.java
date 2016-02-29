package com.example.j1tth4.live500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.j1tth4.live500px.manager.Contextor;
import com.example.j1tth4.live500px.view.PhotoListItem;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;

import org.w3c.dom.Text;

/**
 * Created by j1tth4 on 2/29/16.
 */
public class PhotoListAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return 10000;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 0 : 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position % 2 == 0) {
            PhotoListItem photoListItem;
            if (convertView == null) {
                photoListItem = new PhotoListItem(parent.getContext());
            } else {
                photoListItem = (PhotoListItem) convertView;
            }
            return photoListItem;
        }else{
            TextView textView;
            if(convertView == null){
                textView = new TextView(parent.getContext());
            }else{
                textView = (TextView) convertView;
            }
            textView.setText("Position: " + position);
            return textView;
        }
    }
}
