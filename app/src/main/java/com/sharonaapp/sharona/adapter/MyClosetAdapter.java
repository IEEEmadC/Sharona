package com.sharonaapp.sharona.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.response.Clothes;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * The type My closet adapter.
 */
public class MyClosetAdapter extends RecyclerView.Adapter<MyClosetAdapter.MyClosetViewHolder> {

    private final List<Clothes> clothesList;

    /**
     * Instantiates a new My closet adapter.
     *
     * @param data the data
     */
    public MyClosetAdapter(List<Clothes> data)
    {
        this.clothesList = data;
    }

    @NonNull
    @Override
    public MyClosetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_closet_layout, viewGroup, false);
        MyClosetViewHolder myClosetViewHolder = new MyClosetViewHolder(view);
        return myClosetViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyClosetViewHolder viewHolder, int position)
    {
        Clothes clothes = clothesList.get(position);

        viewHolder.typeTextView.setText(clothes.getType());
        viewHolder.sizeTextView.setText(clothes.getSize());
        viewHolder.brandTextView.setText(clothes.getBrand());
        viewHolder.priceForBuyTextView.setText("Buy : " + clothes.getBuyPrice());
        viewHolder.priceForRentTextView.setText("Rent : " + clothes.getRentPrice());

        if (viewHolder.brandTextView.getContext() != null && clothes.getImages() != null && clothes.getImages().size() > 0)
        {
            Glide.with(viewHolder.brandTextView.getContext())
                    .load(clothes.getImages().get(0))
                    .apply(new RequestOptions().placeholder(R.drawable.image_upload_place_holder))
                    .into(viewHolder.clothesImageView);
        }
    }

    @Override
    public int getItemCount()
    {
        return clothesList.size();
    }


    /**
     * The type My closet view holder.
     */
    class MyClosetViewHolder extends RecyclerView.ViewHolder {

        /**
         * The Clothes image view.
         */
        @BindView(R.id.item_explore_row_layout_image_view)
        ImageView clothesImageView;
        /**
         * The Type text view.
         */
        @BindView(R.id.item_explore_row_type_text_view)
        TextView typeTextView;
        /**
         * The Size text view.
         */
        @BindView(R.id.item_explore_row_size_text_view)
        TextView sizeTextView;
        /**
         * The Brand text view.
         */
        @BindView(R.id.item_explore_row_brand_text_view)
        TextView brandTextView;
        /**
         * The Price for buy text view.
         */
        @BindView(R.id.item_explore_row_price_for_buy_text_view)
        TextView priceForBuyTextView;
        /**
         * The Price for rent text view.
         */
        @BindView(R.id.item_explore_row_price_for_rent_text_view)
        TextView priceForRentTextView;

        /**
         * Instantiates a new My closet view holder.
         *
         * @param itemView the item view
         */
        public MyClosetViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
