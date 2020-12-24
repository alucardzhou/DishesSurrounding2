package com.example.dishessurrounding2.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity
public class StoreFeedResponse {

    @SerializedName("num_results")
    @Expose
    private Integer numResults;

    @SerializedName("next_offset")
    @Expose
    private Integer nextOffset;

    @SerializedName("stores")
    @Expose
    private List<StoreDetails> stores;

    public StoreFeedResponse(
            Integer numResults,
            Integer nextOffset,
            List<StoreDetails> stores) {
        this.numResults = numResults;
        this.nextOffset = nextOffset;
        this.stores = stores;
    }

    @NonNull
    public Integer getNumResults() {
        return numResults;
    }
    public void setNumResults(@NonNull Integer numResults) {
        this.numResults = numResults;
    }

    // if we want the next page. then load this value.
    // if nextOffset < numResults, then we are good to load the next page.
    public Integer getNextOffset() {
        return nextOffset;
    }
    public void setNextOffset(Integer nextOffset) {
        this.nextOffset = nextOffset;
    }

    public List<StoreDetails> getStores() {
        return stores;
    }
    public void setStores(List<StoreDetails> stores) {
        this.stores = stores;
    }
}
