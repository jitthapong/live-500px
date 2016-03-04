package com.example.j1tth4.live500px.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
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
    Button btnNewPhoto;
    SwipeRefreshLayout swipeRefreshLayout;
    PhotoListAdapter photoListAdapter;
    PhotoListManager photoListManager;

    boolean isLoadingMore = false;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoListManager = new PhotoListManager();

        if(savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {
        btnNewPhoto = (Button) rootView.findViewById(R.id.btnNewPhoto);
        btnNewPhoto.setOnClickListener(newPhotoClickListener);

        listView = (ListView) rootView.findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);

        photoListAdapter = new PhotoListAdapter();
        photoListAdapter.setDao(photoListManager.getDao());
        listView.setAdapter(photoListAdapter);

        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener);
        listView.setOnScrollListener(onScrollListener);

        if(savedInstanceState == null)
            refreshData();
    }

    private void refreshData(){
        if(photoListManager.getCount() == 0)
            reloadData();
        else
            loadDataNewer();
    }

    private void loadDataBefore() {
        if (isLoadingMore)
            return;
        isLoadingMore = true;
        int minId = photoListManager.getMinimumId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getService()
                .loadPhotoListBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));
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
        outState.putBundle("photoListManager",
                photoListManager.onSaveInstanceState());
    }

    private void onRestoreInstanceState(Bundle savedInstanceState){
        photoListManager.onRestoreInstanceState(
                savedInstanceState.getBundle("photoListManager"));
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showButtonNewPhoto(){
        btnNewPhoto.setVisibility(View.VISIBLE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(), R.anim.zoom_fade_in);
        btnNewPhoto.setAnimation(anim);
    }

    public void hideButtonNewPhoto(){
        btnNewPhoto.setVisibility(View.GONE);
        Animation anim = AnimationUtils.loadAnimation(
                Contextor.getInstance().getContext(), R.anim.zoom_fade_out);
        btnNewPhoto.setAnimation(anim);
    }

    private void hidePullToRefreshIndicator(){
        swipeRefreshLayout.setRefreshing(false);
    }

    private void showToast(String text){
        Toast.makeText(Contextor.getInstance().getContext(),
                text,
                Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener newPhotoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnNewPhoto) {
                listView.smoothScrollToPosition(0);
                hideButtonNewPhoto();
            }
        }
    };

    SwipeRefreshLayout.OnRefreshListener swipeRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            refreshData();
        }
    };

    AbsListView.OnScrollListener onScrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        @Override
        public void onScroll(AbsListView view,
                             int firstVisibleItem,
                             int visibleItemCount,
                             int totalItemCount) {

            if(view == listView) {
                swipeRefreshLayout.setEnabled(firstVisibleItem == 0);
                if (firstVisibleItem + visibleItemCount >= totalItemCount) {
                    if (photoListManager.getCount() > 0) {
                        loadDataBefore();
                    }
                }
            }
        }
    };

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao>{

        public static final int MODE_RELOAD = 1;
        public static final int MODE_RELOAD_NEWER = 2;
        public static final int MODE_LOAD_MORE = 3;

        int mode;

        public PhotoListLoadCallback(int mode){
            this.mode = mode;
        }

        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            hidePullToRefreshIndicator();
            if(response.isSuccess()){
                PhotoItemCollectionDao dao = response.body();

                int firstVisiblePosition = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if(mode == MODE_RELOAD_NEWER) {
                    photoListManager.insertDaoAtTopPosition(dao);
                }else if(mode == MODE_LOAD_MORE) {
                    photoListManager.insertDaoAtBottomPosition(dao);
                    clearLoadingMoreFlagIfCapable(mode);
                }else {
                    photoListManager.setDao(dao);
                }
                photoListAdapter.setDao(photoListManager.getDao());
                photoListAdapter.notifyDataSetChanged();

                if(mode == MODE_RELOAD_NEWER){
                    int additionalPosition =
                            (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    photoListAdapter.increaseLastPosition(additionalPosition);
                    listView.setSelectionFromTop(firstVisiblePosition + additionalPosition,
                            top);
                    if(additionalPosition > 0){
                        showButtonNewPhoto();
                    }
                }
                showToast("Load Complete");
            }else{
                clearLoadingMoreFlagIfCapable(mode);
                try {
                    showToast(response.errorBody().toString());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            clearLoadingMoreFlagIfCapable(mode);
            swipeRefreshLayout.setRefreshing(false);
            showToast(t.toString());
        }

        private void clearLoadingMoreFlagIfCapable(int mode){
            if(mode == MODE_LOAD_MORE)
                isLoadingMore = false;
        }
    }
}
