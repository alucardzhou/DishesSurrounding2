package com.example.dishessurrounding2.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dishessurrounding2.R;
import com.example.dishessurrounding2.ViewModel.StoreViewModel;
import com.example.dishessurrounding2.model.StoreDetails;
import com.example.dishessurrounding2.ui.adapters.StoreFeedListAdapter;
import com.example.dishessurrounding2.utility.ObservableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;


public class HomeScreenActivity extends AppCompatActivity implements java.util.Observer, PopupMenu.OnMenuItemClickListener {

    StoreViewModel storeViewModel;
    Observer<List<StoreDetails>> storeFeedObserver;
    Observer<Boolean> isStoreFeedUpdateInProgressObserver;
    RecyclerView storeList;
    StoreFeedListAdapter storeFeedListAdapter;
    LayoutAnimationController controller;
    ImageView infoImage;
    TextView tInfo;
    public static final String INTENT_UPDATE_STORE = "UPDATE_STORE";
    public static final String INTENT_UPDATE_LIST = "UPDATE_LIST";
    // adding another sort order
    // public static final String ACTION_SORT_BY_PRICE = "SORT_PRICE";
    public static final String ACTION_SORT_BY_RATING = "SORT_RATING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_Base);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        storeViewModel = ViewModelProviders.of(this).get(StoreViewModel.class);
        storeList = findViewById(R.id.store_list);
        tInfo = findViewById(R.id.t_loading);
        infoImage = findViewById(R.id.i_loading);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        storeList.setLayoutManager(mLayoutManager);
        storeFeedListAdapter = new StoreFeedListAdapter(new ArrayList<StoreDetails>());
        controller = AnimationUtils.loadLayoutAnimation(storeList.getContext(), R.anim.layout_slide_from_bottom);
        storeList.setAdapter(storeFeedListAdapter);
        storeList.scheduleLayoutAnimation();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        storeFeedObserver = new Observer<List<StoreDetails>>() {
            @Override
            public void onChanged(@Nullable List<StoreDetails> storeDetails) {
                if(storeDetails!=null){
                    storeFeedListAdapter.setData(storeDetails);
                    storeFeedListAdapter.notifyDataSetChanged();
                    runLayoutAnimation(storeList);
                }else{
                    Log.e("Food details","null");
                }
            }
        };
        isStoreFeedUpdateInProgressObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean!=null && !aBoolean){
                    showProgress(false,true);
                    subscribeToFoodObserver();
                }else{
                    showProgress(true,false);
                }
            }
        };

        storeViewModel.isStoreFetchInProgress().observe(this, isStoreFeedUpdateInProgressObserver);
        ObservableObject.getInstance().addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_sort){
            showPopup(findViewById(R.id.action_sort));
        }
        return super.onOptionsItemSelected(item);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.actions, popup.getMenu());
        popup.setOnMenuItemClickListener(this);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if(menuItem.getItemId() == R.id.action_sort_rating){
            storeViewModel.sortStore(ACTION_SORT_BY_RATING);
        } // click another sort option
        /*else if(menuItem.getItemId() == R.id.action_sort_rating){
            storeViewModel.sortFood(ACTION_SORT_BY_RATING);
        }*/
        return false;
    }

    private void subscribeToFoodObserver() {
        if(!storeViewModel.getStoreDetailsMutableLiveData().hasObservers()) {
            storeViewModel.getStoreDetailsMutableLiveData().observe(HomeScreenActivity.this, storeFeedObserver);
        }
    }

    private void showProgress(boolean show, boolean showList) {
        storeList.setVisibility(showList? View.VISIBLE: View.GONE);
        tInfo.setVisibility(show? View.VISIBLE: View.GONE);
        infoImage.setVisibility(show? View.VISIBLE: View.GONE);
    }

    private void runLayoutAnimation(final RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_slide_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        if(recyclerView.getAdapter()!=null) {
            recyclerView.getAdapter().notifyDataSetChanged();
            recyclerView.scheduleLayoutAnimation();
        }
    }

    @Override
    protected void onDestroy() {
        storeViewModel.getStoreDetailsMutableLiveData().removeObserver(storeFeedObserver);
        storeViewModel.isStoreFetchInProgress().removeObserver(isStoreFeedUpdateInProgressObserver);
        ObservableObject.getInstance().deleteObserver(this);
        Glide.get(this).clearMemory();
        super.onDestroy();
    }

    @Override
    public void update(Observable observable, Object o) {
        Intent intent = (Intent)o;
        if(intent!=null && intent.getAction() != null) {
            if (intent.getAction().equals(INTENT_UPDATE_STORE)) {
                // nothing here
            }else if(intent.getAction().equals(INTENT_UPDATE_LIST)){
                storeFeedListAdapter.notifyDataSetChanged();
            }
        }
    }
}
