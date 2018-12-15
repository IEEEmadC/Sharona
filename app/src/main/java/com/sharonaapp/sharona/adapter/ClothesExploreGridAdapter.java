package com.sharonaapp.sharona.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.interfaces.LikeItemClickListener;
import com.sharonaapp.sharona.interfaces.ListItemClickListener;
import com.sharonaapp.sharona.manager.LikeManager;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.response.LikeResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sharonaapp.sharona.network.Url.BASE_URL;

public class ClothesExploreGridAdapter extends BaseAdapter {

    private static final String TAG = "ClothesExploreGridAdapt";

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
        if (clothes.getImages() != null && clothes.getImages().size() > 0)
        {
            Glide.with(context).load(BASE_URL + clothes.getImages().get(0).getPath()).into(imageView);
        }
        brandTextView.setText(clothes.getType() + " " + clothes.getBrand());
        sizeTextView.setText("Size " + String.valueOf(clothes.getSize()));
        rentalPriceTexrView.setText(String.valueOf(clothes.getRentPrice()) + "$");

        if (LikeManager.getInstance().isAlreadyLiked(clothes.getId()))
        {
            Glide.with(context).load(R.drawable.ic_like_liked_24dp).into(likeImageView);
        }
        else
        {
            Glide.with(context).load(R.drawable.ic_like_not_liked_24dp).into(likeImageView);

        }

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

//                    likeItemClickListener.itemInListLiked(position, clothesArrayList.get(position).getId());
//                    likeImageView.setColorFilter(null);
                    switchLike(LikeManager.getInstance().toggleLikeClothes(clothesArrayList.get(position).getId()), likeImageView);
                    triggerLikeServer(clothesArrayList.get(position).getId());


                }
            });
        }


        return convertView;
    }

    private void triggerLikeServer(int id)
    {
        NetworkManager.getInstance().getEndpointApi(Api.class).triggerLike(id).enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response)
            {
                Log.d(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: ");
            }
        });
    }

    private void switchLike(boolean isLiked, ImageView likeImageView)
    {
        if (isLiked)
        {
            Glide.with(context).load(R.drawable.ic_like_liked_24dp).into(likeImageView);
        }
        else
        {
            Glide.with(context).load(R.drawable.ic_like_not_liked_24dp).into(likeImageView);

        }
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
