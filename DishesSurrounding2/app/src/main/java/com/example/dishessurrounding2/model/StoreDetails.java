package com.example.dishessurrounding2.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity
public class StoreDetails {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    @NonNull
    private String storeId;

    @SerializedName("num_ratings")
    @Expose
    private Integer numRatings;

    @SerializedName("average_rating")
    @Expose
    private Double averageRating;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("cover_img_url")
    @Expose
    private String coverImgUrl;

    @SerializedName("header_img_url")
    @Expose
    private String headerImgUrl;

    @SerializedName("name")
    @Expose
    private String storeName;

    @SerializedName("url")
    @Expose
    private String storeUrl;
    // "/store/legends-pizza-palo-alto-763860/"
    // note the working one is: https://www.doordash.com/store/legends-pizza-palo-alto-763860/
    // so you need to to add https://www.doordash.com as prefix

    @SerializedName("next_close_time")
    @Expose
    private String nextCloseTime;

    @SerializedName("next_open_time")
    @Expose
    private String nextOpenTime;

    @SerializedName("distance_from_consumer")
    @Expose
    private String distanceFromConsumer;

    @SerializedName("price_range")
    @Expose
    private Integer priceRange = 0;

    // other might needed
    // val status: StoreStatus,

    public StoreDetails(@NonNull String storeId,
                        @Nullable Integer numRatings,
                        Double averageRating,
                        String description,
                        String coverImgUrl,
                        String headerImgUrl,
                        String storeName,
                        String storeUrl,
                        String nextCloseTime,
                        String nextOpenTime,
                        String distanceFromConsumer,
                        Integer priceRange) {
        this.storeId = storeId;
        this.numRatings = numRatings;
        this.averageRating = averageRating;
        this.description = description;
        this.coverImgUrl = coverImgUrl;
        this.headerImgUrl = headerImgUrl;
        this.storeName = storeName;
        this.storeUrl = storeUrl;
        this.nextCloseTime = nextCloseTime;
        this.nextOpenTime = nextOpenTime;
        this.distanceFromConsumer = distanceFromConsumer;
        this.priceRange = priceRange;
    }

    @NonNull
    public String getStoreId() {
        return storeId;
    }
    public void setStoreId(@NonNull String storeId) {
        this.storeId = storeId;
    }

    public Integer getNumRatings() {
        return numRatings;
    }
    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

    public Double getAverageRating() {
        return averageRating;
    }
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverImgUrl() {
        return coverImgUrl;
    }
    public void setCoverImgUrl(String coverImgUrl) {
        this.coverImgUrl = coverImgUrl;
    }

    public String getHeaderImgUrl() {
        return headerImgUrl;
    }
    public void setHeaderImgUrl(String headerImgUrl) {
        this.headerImgUrl = headerImgUrl;
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreUrl() {
        return storeUrl;
    }
    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }

    public String getNextCloseTime() {
        return nextCloseTime;
    }
    public void setNextCloseTime(String nextCloseTime) {
        this.nextCloseTime = nextCloseTime;
    }

    public String getNextOpenTime() {
        return nextOpenTime;
    }
    public void setNextOpenTime(String nextOpenTime) {
        this.nextOpenTime = nextOpenTime;
    }

    public String getDistanceFromConsumer() {
        return distanceFromConsumer;
    }
    public void setDistanceFromConsumer(String distanceFromConsumer) {
        this.distanceFromConsumer = distanceFromConsumer;
    }

    public Integer getPriceRange() {
        return priceRange;
    }
    public void setPriceRange(Integer priceRange) {
        this.priceRange = priceRange;
    }
}
