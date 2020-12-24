package com.example.dishessurrounding2.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.dishessurrounding2.R;
import com.example.dishessurrounding2.model.StoreDetails;
import com.example.dishessurrounding2.ui.RatingTextView;
import com.example.dishessurrounding2.utility.GlideApp;

import java.text.DecimalFormat;
import java.util.List;

import static com.example.dishessurrounding2.ui.HomeScreenActivity.INTENT_UPDATE_STORE;


public class StoreFeedListAdapter extends RecyclerView.Adapter<StoreFeedListAdapter.RecyclerViewHolders> {

    private List<StoreDetails> storeDetailsList;
    private Handler handler;
    final private String urlPrefix = "https://www.doordash.com";

    public void setData(List<StoreDetails> data) {
        this.storeDetailsList = data;
    }

    public class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tName,tPrice,tCount,iPriceRange;
        private ImageView iFood;
        private AppCompatImageView iDes;
        private RelativeLayout storeCard;
        private RatingTextView tRating;

        // the UI elements
        RecyclerViewHolders(View itemView) {
            super(itemView);
            storeCard = itemView.findViewById(R.id.store_card);
            tName = storeCard.findViewById(R.id.t_food_name);
            tPrice = storeCard.findViewById(R.id.t_price);
            tCount = storeCard.findViewById(R.id.t_count);
            tRating = storeCard.findViewById(R.id.t_rating);
            iDes = storeCard.findViewById(R.id.i_des);
            iPriceRange = storeCard.findViewById(R.id.i_price_range);
            iFood = storeCard.findViewById(R.id.i_food_image);
            storeCard.setOnClickListener(this);
        }

        // open details page of a store.
        // we can open the url webview for it.
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.store_card){
                String url = urlPrefix + storeDetailsList.get(getAdapterPosition()).getStoreUrl();

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(browserIntent);
            }
        }
    }

    private Intent getUpdateIntent(int position) {
        Intent i = new Intent(INTENT_UPDATE_STORE);
        i.putExtra("position",position);
        return i;
    }

    public StoreFeedListAdapter(List<StoreDetails> storeDetailsList) {
        this.storeDetailsList = storeDetailsList;
        this.handler = new Handler();
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        @SuppressLint("InflateParams") View layoutView =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_store_feed_item, null);
        return new RecyclerViewHolders(layoutView);
    }

    // binding data to view
    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolders holder, int position) {
        final StoreDetails storeDetails = storeDetailsList.get(holder.getAdapterPosition());
        holder.tName.setText(storeDetails.getStoreName());

        float dist = Float.valueOf(storeDetails.getDistanceFromConsumer());
        DecimalFormat decimalFormat = new DecimalFormat("0.000");
        holder.tPrice.setText("Dist: " + decimalFormat.format(dist) + " miles.");
        holder.tRating.setRating(storeDetails.getAverageRating().floatValue());
        LoadImage li = new LoadImage(holder.iFood,holder.iPriceRange,holder.iDes,holder.tCount,holder.getAdapterPosition());
        handler.post(li);
    }


    @Override
    public int getItemCount() {
        return this.storeDetailsList.size();
    }


    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public StoreDetails getItem(int position){
        if(position!= -1) {
            return storeDetailsList.get(position);
        }else {
            return null;
        }
    }

    private class LoadImage implements Runnable {
        ImageView imageView;
        TextView tCount, iPriceRange;
        AppCompatImageView iPlus;
        int position;

        LoadImage(ImageView imageView, TextView iPriceRange, AppCompatImageView iPlus, TextView tCount, int adapterPosition) {
            this.imageView = imageView;
            this.iPriceRange = iPriceRange;
            this.iPlus = iPlus;
            this.tCount = tCount;
            this.position = adapterPosition;
        }

        @Override
        public void run() {
            StoreDetails details = storeDetailsList.get(position);
            // set up image
            GlideApp.with(imageView.getContext()).load(details.getCoverImgUrl())
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .placeholder(R.drawable.ic_food)
                        .into(imageView);

            // set up number of review
            final int rateNum = details.getNumRatings();
            tCount.setText(rateNum + " Rates");

            // set up Price Range
            final Integer priceRange = details.getPriceRange();
            String sPriceRange = "?";
            switch (priceRange) {
                case 1:
                    sPriceRange = "$";
                    break;
                case 2:
                    sPriceRange = "$$";
                    break;
                case 3:
                    sPriceRange = "$$$";
                    break;
                default:
            }
            iPriceRange.setText(sPriceRange);

            // set up description
            iPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(view.getContext()).create();
                    alertDialog.setTitle("Description");
                    alertDialog.setMessage(storeDetailsList.get(position).getDescription());
                    alertDialog.show();
                }
            });
        }
    }
}
