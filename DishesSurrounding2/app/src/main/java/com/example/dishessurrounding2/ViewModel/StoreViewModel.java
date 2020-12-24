package com.example.dishessurrounding2.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;


import com.example.dishessurrounding2.dbUtilities.AppDatabase;
import com.example.dishessurrounding2.model.StoreDetails;
import com.example.dishessurrounding2.service.repository.StoreFeedRepository;

import java.util.List;

import static com.example.dishessurrounding2.ui.HomeScreenActivity.ACTION_SORT_BY_RATING;


public class StoreViewModel extends AndroidViewModel {

    private AppDatabase db;
    private MediatorLiveData<List<StoreDetails>> storeDetailsMediatorLiveData = new MediatorLiveData<>();
    private LiveData<List<StoreDetails>> storeDetailsLiveDataSortRating;
    private MutableLiveData<Boolean> isStoreFetchInProgress = new MutableLiveData<>();
    private static String DEFAULT_SORT = ACTION_SORT_BY_RATING;

    public StoreViewModel(@NonNull Application application) {
        super(application);
        init();
    }

    private void init() {
        db = AppDatabase.getDatabase(getApplication().getApplicationContext());
        updateStoreFeedList();
        subscribeToStoreChanges();
    }


    private void updateStoreFeedList() {
        isStoreFetchInProgress = StoreFeedRepository.getInstance().fetchStoreFeed(getApplication().getApplicationContext());
    }

    private void subscribeToStoreChanges() {
        if (DEFAULT_SORT.equals(ACTION_SORT_BY_RATING)) {
            storeDetailsLiveDataSortRating = db.storeDetailsDao().getStoresByRating();
            storeDetailsMediatorLiveData.addSource(
                    storeDetailsLiveDataSortRating,
                    new Observer<List<StoreDetails>>() {
                        @Override
                        public void onChanged(@Nullable List<StoreDetails> storeDetails) {
                            storeDetailsMediatorLiveData.setValue(storeDetails);
                        }
                    });
        } // sort by another order. for example: the distance, or the prise
        /*else if(DEFAULT_SORT.equals(ACTION_SORT_BY_RATING)){
            foodDetailsLiveDataSortRating = db.foodDetailsDao().getFoodsByRating();
            foodDetailsMediatorLiveData.addSource(foodDetailsLiveDataSortRating, new Observer<List<FoodDetails>>() {
                @Override
                public void onChanged(@Nullable List<FoodDetails> foodDetails) {
                    foodDetailsMediatorLiveData.setValue(foodDetails);
                }
            });
        }*/
    }

    public MediatorLiveData<List<StoreDetails>> getStoreDetailsMutableLiveData() {
        return storeDetailsMediatorLiveData;
    }

    public void sortStore(String action) {
        removeSource(DEFAULT_SORT);
        DEFAULT_SORT = action;
        subscribeToStoreChanges();
    }

    private void removeSource(String default_sort) {
        switch (default_sort) {
            case ACTION_SORT_BY_RATING:
                storeDetailsMediatorLiveData.removeSource(storeDetailsLiveDataSortRating);
                break;
            /* another sort order
            case ACTION_SORT_BY_PRICE:
                foodDetailsMediatorLiveData.removeSource(foodDetailsLiveDataSortPrice);
                break;*/
        }
    }

    public LiveData<Boolean> isStoreFetchInProgress() {
        return isStoreFetchInProgress;
    }

}
