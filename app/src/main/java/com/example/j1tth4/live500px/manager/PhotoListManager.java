package com.example.j1tth4.live500px.manager;

import android.content.Context;
import android.os.Bundle;

import com.example.j1tth4.live500px.dao.PhotoItemCollectionDao;
import com.example.j1tth4.live500px.dao.PhotoItemDao;
import com.example.j1tth4.live500px.fragment.MainFragment;

import java.util.ArrayList;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class PhotoListManager {

    private PhotoItemCollectionDao dao;

    public PhotoListManager() {
    }

    public void setDao(PhotoItemCollectionDao dao) {
        this.dao = dao;
    }

    public void insertDaoAtBottomPosition(PhotoItemCollectionDao dao){
        if(dao == null)
            dao = new PhotoItemCollectionDao();
        if(dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());
        this.dao.getData().addAll(this.dao.getData().size(), dao.getData());
    }

    public void insertDaoAtTopPosition(PhotoItemCollectionDao dao){
        if(dao == null)
            dao = new PhotoItemCollectionDao();
        if(dao.getData() == null)
            dao.setData(new ArrayList<PhotoItemDao>());
        this.dao.getData().addAll(0, dao.getData());
    }

    public PhotoItemCollectionDao getDao() {
        return dao;
    }

    public int getMinimumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if(dao.getData().size() == 0)
            return 0;
        int minId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            minId = Math.min(minId, dao.getData().get(i).getId());
        return minId;
    }

    public int getMaximumId(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        if(dao.getData().size() == 0)
            return 0;
        int maxId = dao.getData().get(0).getId();
        for (int i = 1; i < dao.getData().size(); i++)
            maxId = Math.max(maxId, dao.getData().get(i).getId());
        return maxId;
    }

    public int getCount(){
        if(dao == null)
            return 0;
        if(dao.getData() == null)
            return 0;
        return dao.getData().size();
    }

    public Bundle onSaveInstanceState(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("dao", dao);
        return bundle;
    }

    public void onRestoreInstanceState(Bundle savedInstanceState){
        dao = savedInstanceState.getParcelable("dao");
    }
}
