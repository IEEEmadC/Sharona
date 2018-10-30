package com.appestan.sharona.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestan.sharona.Interface.LikeItemClickListener;
import com.appestan.sharona.Interface.ListItemClickListener;
import com.appestan.sharona.Model.Clothes;
import com.appestan.sharona.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClothesExploreGridAdapter extends BaseAdapter {

    private final ArrayList<Clothes> clothesArrayList;
    private final Context context;
    private final ListItemClickListener listItemClickListener;
    private final LikeItemClickListener likeItemClickListener;

    public ClothesExploreGridAdapter(Context context,
                                     @Nullable ListItemClickListener listItemClickListener,
                                     @Nullable LikeItemClickListener likeItemClickListener,
                                     ArrayList<Clothes> clothesArrayList)
    {
        this.context = context;
        this.clothesArrayList = clothesArrayList;
        this.listItemClickListener = listItemClickListener;
        this.likeItemClickListener = likeItemClickListener;

    }

    @Override
    public int getCount()
    {
        return clothesArrayList.size();
    }

    @Override
    public Object getItem(int i)
    {
        return null;
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup)
    {

        final ImageView imageView;
        TextView brandTextView;
        TextView sizeTextView;
        TextView rentalPriceTexrView;
        final ImageView likeImageView;

        if (convertView == null)
        {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_explore_grid_layout, null);
        }

        imageView = convertView.findViewById(R.id.item_explore_grid_layout_image_view);
        brandTextView = convertView.findViewById(R.id.item_explore_grid_layout_brand_textView);
        sizeTextView = convertView.findViewById(R.id.item_explore_grid_layout_size_textView);
        rentalPriceTexrView = convertView.findViewById(R.id.item_explore_grid_layout_rental_price_textView);
        likeImageView = convertView.findViewById(R.id.item_explore_grid_layout_like_image_view);

        Clothes clothes = clothesArrayList.get(position);
        Glide.with(context).load(clothes.getImageUrl().get(0)).into(imageView);
        brandTextView.setText(clothes.getType() + " " + clothes.getBrand());
        sizeTextView.setText("Size " + String.valueOf(clothes.getSize()));
        rentalPriceTexrView.setText(String.valueOf(clothes.getRentalPrice()) + "$");

        if (listItemClickListener != null)
        {
            imageView.setOnClickListener(callListItemClickListenerWithPosition(position));
            brandTextView.setOnClickListener(callListItemClickListenerWithPosition(position));
            sizeTextView.setOnClickListener(callListItemClickListenerWithPosition(position));
            rentalPriceTexrView.setOnClickListener(callListItemClickListenerWithPosition(position));
        }

        if (likeItemClickListener != null)
        {
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    likeItemClickListener.itemInListLiked(position);
                    likeImageView.setColorFilter(null);
                }
            });
        }


        return convertView;
    }

    @NonNull
    private View.OnClickListener callListItemClickListenerWithPosition(final int position)
    {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                listItemClickListener.itemInListClicked(position);

            }
        };
    }
}
