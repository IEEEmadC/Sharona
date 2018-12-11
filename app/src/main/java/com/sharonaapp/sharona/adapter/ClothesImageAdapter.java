package com.sharonaapp.sharona.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.R;

public class ClothesImageAdapter extends PagerAdapter {

    int imagesCount;

    String url0 = "https://mms-images.out.customink.com/mms/images/catalog/colors/04604/views/alt/front_medium_extended.png?design=djn0-00an-000p&placeMax=1&placeUseProduct=1&placeMaxPct=0.8";
    String url1 = "https://mms-images-prod.imgix.net/mms/images/catalog/271f962a217a8743ce242a18da753534/styles/4600/catalog_detail_image_large.jpg?ixlib=rails-2.1.4&w=700&h=700&fit=fill&bg=ffffff&dpr=1&q=60&fm=pjpg&auto=compress&trim=auto&trimmd=0";
    String url2 = "https://mms-images-prod.imgix.net/mms/images/catalog/0ab75b421fc037b55afdf107081882ac/styles/4600/supporting/1.jpg?ixlib=rails-2.1.4&w=425&h=495&fit=fill&bg=ffffff&dpr=1&q=39&fm=pjpg&auto=compress&trim=auto&trimmd=0";
    String url3 = "https://mms-images-prod.imgix.net/mms/images/catalog/1990105835219a32f557bfa82b781d4a/styles/4600/supporting/2.jpg?ixlib=rails-2.1.4&w=425&h=495&fit=fill&bg=ffffff&dpr=1&q=39&fm=pjpg&auto=compress&trim=auto&trimmd=0";
    String url4 = "https://mms-images-prod.imgix.net/mms/images/catalog/83fd2299d85b565b4af8517f179c1e2c/styles/4600/supporting/3.jpg?ixlib=rails-2.1.4&w=425&h=495&fit=fill&bg=ffffff&dpr=1&q=39&fm=pjpg&auto=compress&trim=auto&trimmd=0";

    public ClothesImageAdapter(int imagesCount)
    {
        this.imagesCount = imagesCount;
    }

    @Override
    public int getCount()
    {
        return imagesCount;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        ViewGroup layout = ((ViewGroup) LayoutInflater.from(container.getContext()).inflate(R.layout.item_clothes_image, null));
        if (position == 0)
        {

            Glide.with(container.getContext()).load(url0).into(((ImageView) layout.findViewById(R.id.item_clothes_image_inner)));
//            layout.findViewById(R.id.item_clothes_image_inner).setBackgroundColor(layout.getContext().getResources().getColor(R.color.colorAccent));

        }
        else if (position == 1)
        {
            Glide.with(container.getContext()).load(url1).into(((ImageView) layout.findViewById(R.id.item_clothes_image_inner)));

        }
        else if (position == 2)
        {
            Glide.with(container.getContext()).load(url2).into(((ImageView) layout.findViewById(R.id.item_clothes_image_inner)));

        }
        else if (position == 3)
        {
            Glide.with(container.getContext()).load(url3).into(((ImageView) layout.findViewById(R.id.item_clothes_image_inner)));

        }
        else if (position == 4)
        {
            Glide.with(container.getContext()).load(url4).into(((ImageView) layout.findViewById(R.id.item_clothes_image_inner)));

        }
        container.addView(layout);

        return layout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView(((View) object));
    }
}
