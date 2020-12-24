package com.example.dishessurrounding2.workers;

import android.os.AsyncTask;

import com.example.dishessurrounding2.dbUtilities.AppDatabase;
import com.example.dishessurrounding2.model.StoreDetails;

import java.util.ArrayList;
import java.util.List;

public class SaveStoreFeed extends AsyncTask<Void, Void, Void> {
    private AppDatabase db;
    private List<StoreDetails> storeDetails;
    public SaveStoreFeed(AppDatabase db, List<StoreDetails> storeDetails) {
        this.db = db;
        this.storeDetails = storeDetails;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if(db!=null){
            if(storeDetails !=null && storeDetails.size()>0) {
                List<String> storeIDList = new ArrayList<>();
                for(int i = 0; i< storeDetails.size(); i++){
                    storeIDList.add(storeDetails.get(i).getStoreId());
                }
                db.storeDetailsDao().save(storeDetails);
                // clean the previous data
                db.storeDetailsDao().deleteOtherStores(storeIDList);
            }else{
                // clean database
                db.storeDetailsDao().deleteAll();
            }
        }
        return null;
    }
}
