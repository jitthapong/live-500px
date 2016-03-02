package com.example.j1tth4.live500px.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.j1tth4.live500px.R;
import com.example.j1tth4.live500px.adapter.PhotoListAdapter;
import com.example.j1tth4.live500px.dao.PhotoItemCollectionDao;
import com.example.j1tth4.live500px.dao.PhotoItemDao;
import com.example.j1tth4.live500px.manager.Contextor;
import com.example.j1tth4.live500px.manager.HttpManager;
import com.example.j1tth4.live500px.manager.PhotoListManager;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    PhotoListAdapter photoListAdapter;
    PhotoListManager photoListManager;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        photoListManager = new PhotoListManager();

        listView = (ListView) rootView.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        photoListAdapter = new PhotoListAdapter();
        listView.setAdapter(photoListAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view,
                                 int firstVisibleItem,
                                 int visibleItemCount,
                                 int totalItemCount) {

                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
            }
        });
        refreshData();
    }

    private void refreshData(){
        if(photoListManager.getCount() == 0)
            reloadData();
        else
            loadDataNewer();
    }

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao>{

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;

        int mode;

        public PhotoListLoadCallback(int mode){
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if(response.isSuccess()){
                PhotoItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if(mode == MODE_RELOAD_NEWER)
                    photoListManager.insertDaoAtTopPosition(dao);
                else
                    photoListManager.setDao(dao);
                photoListAdapter.setDao(photoListManager.getDao());
                photoListAdapter.notifyDataSetChanged();

                if(mode == MODE_RELOAD_NEWER){
                    int additionalPosition =
                            (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    photoListAdapter.increaseLastPosition(additionalPosition);
                    listView.setSelectionFromTop(firstVisiblePosition + additionalPosition,
                            top);
                }
            }else{
                try {
                    Toast.makeText(Contextor.getInstance().getContext(),
                            response.errorBody().string(),
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Contextor.getInstance().getContext(),
                    t.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void loadDataNewer() {
        int maxId = photoListManager.getMaximumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD_NEWER));
    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoList();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
