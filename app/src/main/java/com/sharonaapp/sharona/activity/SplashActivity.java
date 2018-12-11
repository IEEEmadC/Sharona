package com.sharonaapp.sharona.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.ConfigDAO;
import com.sharonaapp.sharona.model.WearingType;

import java.util.ArrayList;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CONFIG;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CONFIG_DATE;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_splash);

        init();

        fetchDataIfNeededThenStartApp();
    }

    private void init()
    {
        Glide.with(this).load(R.drawable.anim_sharona_logo).into(((ImageView) findViewById(R.id.splash_image_view)));
    }

    private void fetchDataIfNeededThenStartApp()
    {
// TODO: 12/10/18 uncomment
//        if (shouldFetchNewConfig())
//        {

        ArrayList<WearingType> wearingTypes = new ArrayList<>();
        WearingType tShirtWearingType = new WearingType("T-Shirt", 0);
        WearingType poloShirtWearingType = new WearingType("Polo-Shirt", 0);
        WearingType beltWearingType = new WearingType("Belt", 4);
        WearingType shoesWearingType = new WearingType("Shoes", 3);
        WearingType pantWearingType = new WearingType("Pants", 1);
        wearingTypes.add(tShirtWearingType);
        wearingTypes.add(poloShirtWearingType);
        wearingTypes.add(beltWearingType);
        wearingTypes.add(shoesWearingType);
        wearingTypes.add(pantWearingType);

        ArrayList<String> clothesBrands = new ArrayList<>();
        clothesBrands.add("Nike");
        clothesBrands.add("Puma");
        clothesBrands.add("Salomon");
        clothesBrands.add("Diadora");
        ArrayList<String> clothesSizes = new ArrayList<>();
        clothesSizes.add("30");
        clothesSizes.add("32");
        clothesSizes.add("34");
        clothesSizes.add("36");
        clothesSizes.add("38");
        clothesSizes.add("40");
        clothesSizes.add("42");
        clothesSizes.add("44");
        clothesSizes.add("46");
        clothesSizes.add("30-38 Freesize");
        clothesSizes.add("38-44 Freesize");

        ArrayList<String> footwearSizes = new ArrayList<>();
        footwearSizes.add("32");
        footwearSizes.add("34");
        footwearSizes.add("36");
        footwearSizes.add("38");
        footwearSizes.add("30");
        footwearSizes.add("40");
        footwearSizes.add("42");
        footwearSizes.add("44");
        footwearSizes.add("46");
        footwearSizes.add("30-38 Freesize");
        footwearSizes.add("38-44 Freesize");


        ArrayList<String> colors = new ArrayList<>();
        colors.add("Black");
        colors.add("Blue");
        colors.add("Brown");
        colors.add("Gray");
        colors.add("Green");
        colors.add("Lemon");
        colors.add("Maroon");
        colors.add("Olive");
        colors.add("Orange");
        colors.add("Pink");
        colors.add("Mixed");
        colors.add("Red");
        colors.add("Silver");
        colors.add("Teal");
        colors.add("violet");
        colors.add("White");
        colors.add("Yellow");

        ConfigDAO configDAO = new ConfigDAO();
        configDAO.setWearingTypesArrayList(wearingTypes);
        configDAO.setClothesBrandsArrayList(clothesBrands);
        configDAO.setClothesSizeArrayList(clothesSizes);
        configDAO.setFootwearSizeArrayList(footwearSizes);
        configDAO.setColorArrayList(colors);
        MyApplication.getSharedPreferencesManager().persist(CONFIG, configDAO);
        MyApplication.getSharedPreferencesManager().persist(CONFIG_DATE, System.currentTimeMillis());
//        }

        StartApp();

    }

    private boolean shouldFetchNewConfig()
    {
        if (MyApplication.getSharedPreferencesManager().read(CONFIG_DATE) instanceof Long)
        {
            Long configLastPersistDateInMillis = (Long) MyApplication.getSharedPreferencesManager().read(CONFIG_DATE);
            long currentTimeMillis = System.currentTimeMillis();

            long delta = 2 * (1000 * 60 * 60 * 24); // delta in Days

            if (currentTimeMillis - delta > configLastPersistDateInMillis)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            // No Date found for config, it must be first time lunch
            return true;
        }
    }

    private void StartApp()
    {
        new Handler().postDelayed(() -> SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2500);
//        new Handler().postDelayed(() -> SplashActivity.this.startActivity(new Intent(SplashActivity.this, TourActivity.class)), 2500);
    }
}
