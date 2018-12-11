package com.sharonaapp.sharona.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.TourPage;

import java.util.List;

public class TourAdapter extends PagerAdapter {

    Context context;
    List<TourPage> tourPageList;

    public TourAdapter(Context context, List<TourPage> tourPageList)
    {
        this.context = context;
        this.tourPageList = tourPageList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        TourPage tourPage = tourPageList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.item_tour, container, false);
        view.findViewById(R.id.item_tour_image_view).setBackground(tourPage.getImage());
        ((TextView) view.findViewById(R.id.item_tour_title_text_view)).setText(tourPage.getTitle());
        ((TextView) view.findViewById(R.id.item_tour_desc_text_view)).setText(tourPage.getDesc());



        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        container.removeView(((View) object));
    }

    @Override
    public int getCount()
    {
        return 4;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o)
    {
        return view == o;
    }



}
