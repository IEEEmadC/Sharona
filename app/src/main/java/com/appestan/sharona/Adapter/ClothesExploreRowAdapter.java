package com.appestan.sharona.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestan.sharona.Model.Clothes;
import com.appestan.sharona.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClothesExploreRowAdapter extends RecyclerView.Adapter<ClothesExploreRowAdapter.ClothesViewHolder> {

    private ArrayList<Clothes> clothesArrayList;

    public ClothesExploreRowAdapter(ArrayList<Clothes> clothesArrayList)
    {
        this.clothesArrayList = clothesArrayList;
    }

    public ClothesExploreRowAdapter()
    {
        // if adapter is built through this constructor, make sure that at least one of addClothes methods
        //  is called before using the adapter
        // if (clothesArrayList == null){
        //            throw new NullPointerException("clothes can not be null");
        //        }
    }

    public void addClothes(Clothes clothes)
    {
        if (clothesArrayList == null)
        {
            clothesArrayList = new ArrayList<>();
        }

        clothesArrayList.add(clothes);
        this.notifyItemInserted(clothesArrayList.size() - 1);
    }

    public void addClothes(ArrayList<Clothes> givenClothesArrayList)
    {
        if (clothesArrayList == null)
        {
            clothesArrayList = new ArrayList<>();
        }

        for (Clothes clothes : givenClothesArrayList)
        {
            clothesArrayList.add(clothes);
            this.notifyItemInserted(clothesArrayList.size() - 1);
        }
    }

    @NonNull
    @Override
    public ClothesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clothes_browse, parent, false);
        return new ClothesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ClothesViewHolder holder, int position)
    {
        Clothes clothes = clothesArrayList.get(position);
        Glide.with(holder.clothesImageView.getContext()).load(clothes.getImageUrl()).into(holder.clothesImageView);

        holder.userNameTextView.setText(clothes.getUserName());
        holder.typeTextView.setText(clothes.getType());
        holder.sizeTextView.setText(clothes.getSize());

//                            holder.clothesImageView.setBackgroundColor(R.color.colorAccent);


//        Bitmap image = BitmapFactory.decodeResource(holder.clothesImageView.getResources(),
//                R.drawable.puma_shirt);
//        Palette.from(image).generate(new Palette.PaletteAsyncListener() {
//            public void onGenerated(Palette palette) {
//                Palette.Swatch vibrantSwatch = palette.getDarkVibrantSwatch();
//                if (vibrantSwatch != null) {
//                    holder.clothesImageView.setBackgroundColor(vibrantSwatch.getPopulation());
//                    holder.userNameTextView.setBackgroundColor(vibrantSwatch.getRgb());
//                    holder.typeTextView.setTextColor(vibrantSwatch.getBodyTextColor());
//                }
//            }
//        });

    }

    @Override
    public int getItemCount()
    {
        if (clothesArrayList == null)
        {
            return 0;
        }
        else
        {
            return clothesArrayList.size();
        }
    }


    public class ClothesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_explore_row_layout_image_view)
        ImageView clothesImageView;
        @BindView(R.id.item_explore_row_type_text_view)
        TextView typeTextView;
        @BindView(R.id.item_explore_row_size_text_view)
        TextView sizeTextView;
        @BindView(R.id.item_explore_row_username_text_view)
        TextView userNameTextView;
//        @BindView(R.id.item_brand)
//        TextView brandTextView;

        public ClothesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
