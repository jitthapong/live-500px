package com.example.j1tth4.live500px;

import android.content.Context;

import com.example.j1tth4.live500px.manager.Contextor;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class SingletonTemplate {

    private static SingletonTemplate instance;

    public static  SingletonTemplate getInstance() {
        if (instance == null)
            instance = new SingletonTemplate();
        return instance;
    }

    private Context mContext;

    private SingletonTemplate() {
        mContext = Contextor.getInstance().getContext();
    }

}
