package com.example.dishessurrounding2.dbUtilities;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.dishessurrounding2.model.StoreDetails;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface StoreDetailsDao {

    @Insert(onConflict = REPLACE)
    void save(List<StoreDetails> storeDetailsList);

    @Insert(onConflict = REPLACE)
    void save(StoreDetails storeDetails);

    @Query("DELETE FROM storeDetails WHERE storeId NOT IN (:storeIdList)")
    void deleteOtherStores(List<String> storeIdList);

    @Query("DELETE FROM storeDetails")
    void deleteAll();

    @Query("SELECT * FROM storeDetails ORDER BY averageRating DESC")
    LiveData<List<StoreDetails>> getStoresByRating();

    @Query("SELECT * FROM storeDetails WHERE storeName = :storeName")
    LiveData<StoreDetails> getStore(String storeName);

    /* another sort order
    @Query("SELECT * FROM storeDetails ORDER BY price ASC")
    LiveData<List<StoreDetails>> getStoresByPrice();
    */
}
