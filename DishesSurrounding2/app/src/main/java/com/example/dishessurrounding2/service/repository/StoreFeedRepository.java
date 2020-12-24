package com.example.dishessurrounding2.service.repository;


import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.dishessurrounding2.dbUtilities.AppDatabase;
import com.example.dishessurrounding2.model.StoreFeedResponse;
import com.example.dishessurrounding2.service.APIClient;
import com.example.dishessurrounding2.service.StoreFeedAPIServices;
import com.example.dishessurrounding2.workers.SaveStoreFeed;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreFeedRepository {

    private static StoreFeedRepository instance;
    private static final String TAG = StoreFeedRepository.class.getName();

    private StoreFeedAPIServices storeFeedAPIServices = APIClient.getClient().create(StoreFeedAPIServices.class);

    public MutableLiveData<Boolean> fetchStoreFeed(final Context context){

        final MutableLiveData<Boolean> isStoreFeedFetching = new MutableLiveData<>();
        isStoreFeedFetching.setValue(true);

        // change the parameter to fetch more
        final String DEFAULT_LAT = "37.422740";
        final String DEFAULT_LNG = "-122.139956";
        // we can change the page to the next/previous by keeping the current offset
        final int DEFAULT_OFFSET = 0;
        final int DEFAULT_LIMIT = 50; //
        storeFeedAPIServices.fetchStoreFeed(
                DEFAULT_LAT,
                DEFAULT_LNG,
                DEFAULT_OFFSET,
                DEFAULT_LIMIT
                ).enqueue(new Callback<StoreFeedResponse>() {
            @Override
            public void onResponse(Call<StoreFeedResponse> call, Response<StoreFeedResponse> response) {
                if(response.isSuccessful()) {
                    new SaveStoreFeed(AppDatabase.getDatabase(context), response.body().getStores()).execute();
                    isStoreFeedFetching.setValue(false);
                }else{
                    Log.e(TAG,"response not successful");
                }
            }

            @Override
            public void onFailure(Call<StoreFeedResponse> call, Throwable t) {
                Log.e(TAG,"Response Failure:" + t);
            }
        });
        return isStoreFeedFetching;
    }

    public static StoreFeedRepository getInstance() {
        if(instance == null){
            synchronized (StoreFeedRepository.class){
                if(instance == null){
                    instance = new StoreFeedRepository();
                }
            }
        }
        return instance;
    }
}
