package com.example.j1tth4.live500px.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.j1tth4.live500px.R;
import com.example.j1tth4.live500px.dao.PhotoItemCollectionDao;
import com.example.j1tth4.live500px.dao.PhotoItemDao;
import com.example.j1tth4.live500px.manager.Contextor;
import com.example.j1tth4.live500px.manager.PhotoListManager;
import com.example.j1tth4.live500px.view.PhotoListItem;
import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;

import org.w3c.dom.Text;

/**
 * Created by j1tth4 on 2/29/16.
 */
public class PhotoListAdapter extends BaseAdapter {

    PhotoItemCollectionDao dao;
    int lastPosition = -1;

    public void setDao(PhotoItemCollectionDao dao){
        this.dao = dao;
    }

    @Override
    public int getCount() {
        if (dao == null)
            return 0;
        if (dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    @Override
    public Object getItem(int position) {
        return dao.getData().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PhotoListItem photoListItem;
        if (convertView == null) {
            photoListItem = new PhotoListItem(parent.getContext());
        } else {
            photoListItem = (PhotoListItem) convertView;
        }

        PhotoItemDao dao = (PhotoItemDao) getItem(position);
        photoListItem.setNameText(dao.getCaption());
        photoListItem.setDescription(dao.getUsername() + "\n" + dao.getCamera());
        photoListItem.setImageUrl(dao.getImageUrl());

        if(position > lastPosition) {
            Animation anim = AnimationUtils.loadAnimation(parent.getContext(),
                    R.anim.up_from_bottom);
            photoListItem.startAnimation(anim);
            lastPosition = position;
        }
        return photoListItem;
    }

    public void increaseLastPosition(int amount){
        lastPosition += amount;
    }
}
