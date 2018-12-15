package com.sharonaapp.sharona.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.model.ConfigDAO;
import com.sharonaapp.sharona.model.WearingType;

import java.util.ArrayList;

import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CONFIG;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CONFIG_DATE;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.VIRGINITY_OF_TOUR;

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
//        Glide.with(this).load(R.drawable.anim_sharona_logo).into(((ImageView) findViewById(R.id.splash_image_view)));
    }

    private void fetchDataIfNeededThenStartApp()
    {
// TODO: 12/10/18 uncomment
//        if (shouldFetchNewConfig())
//        {
        ArrayList<WearingType> wearingTypes = new ArrayList<>();
        // Upper body
        WearingType tShirtWearingType = new WearingType("T-Shirt", 0);
        WearingType poloShirtWearingType = new WearingType("Polo-shirt", 0);
        WearingType shirtsWearingType = new WearingType("Shirts", 0);
        WearingType sweaterWearingType = new WearingType("Sweater", 0);
        WearingType hoodyWearingType = new WearingType("Hoody", 0);
        WearingType jacketWearingType = new WearingType("Jacket", 0);
        WearingType coatWearingType = new WearingType("Coat", 0);
        WearingType blazerWearingType = new WearingType("Blazer", 0);
        WearingType vestWearingType = new WearingType("Vest", 0);
        WearingType topWearingType = new WearingType("Top", 0);
        // Lower body
        WearingType pantWearingType = new WearingType("Pants", 1);
        WearingType jeansWearingType = new WearingType("Jeans", 1);
        WearingType skirtWearingType = new WearingType("Skirts", 1);
        // Full body
        WearingType dressWearingType = new WearingType("Dress", 1);
        WearingType suitWearingType = new WearingType("Suit", 1);
        // Footwear
        WearingType shoesWearingType = new WearingType("Shoes", 3);
        // Accessories
        WearingType beltWearingType = new WearingType("Belt", 4);
        WearingType watchesWearingType = new WearingType("Watches", 4);
        WearingType scarfWearingType = new WearingType("Scarfs", 4);
        WearingType HatsWearingType = new WearingType("Hats", 4);
        WearingType GlovesWearingType = new WearingType("Gloves", 4);
        WearingType sockesWearingType = new WearingType("Socks", 4);
        WearingType towelsWearingType = new WearingType("Towels", 4);
        WearingType swimwearWearingType = new WearingType("Swimwear", 4);
        WearingType tieWearingType = new WearingType("Tie", 4);
        WearingType bracesWearingType = new WearingType("Braces", 4);

        wearingTypes.add(tShirtWearingType);
        wearingTypes.add(poloShirtWearingType);
        wearingTypes.add(shirtsWearingType);
        wearingTypes.add(sweaterWearingType);
        wearingTypes.add(hoodyWearingType);
        wearingTypes.add(jacketWearingType);
        wearingTypes.add(coatWearingType);
        wearingTypes.add(blazerWearingType);
        wearingTypes.add(vestWearingType);
        wearingTypes.add(topWearingType);

        wearingTypes.add(pantWearingType);
        wearingTypes.add(jeansWearingType);
        wearingTypes.add(skirtWearingType);

        wearingTypes.add(dressWearingType);
        wearingTypes.add(suitWearingType);

        wearingTypes.add(shoesWearingType);

        wearingTypes.add(beltWearingType);
        wearingTypes.add(watchesWearingType);
        wearingTypes.add(scarfWearingType);
        wearingTypes.add(HatsWearingType);
        wearingTypes.add(GlovesWearingType);
        wearingTypes.add(sockesWearingType);
        wearingTypes.add(towelsWearingType);
        wearingTypes.add(swimwearWearingType);
        wearingTypes.add(tieWearingType);
        wearingTypes.add(bracesWearingType);

        ArrayList<String> clothesBrands = new ArrayList<>();
        clothesBrands.add("Other");
        clothesBrands.add("Nike");
        clothesBrands.add("Puma");
        clothesBrands.add("Salomon");
        clothesBrands.add("Diadora");
        clothesBrands.add("Asics");
        clothesBrands.add("Aldo");
        clothesBrands.add("Adidas");
        clothesBrands.add("Alfred Dunhill");
        clothesBrands.add("Antonio Banderas");
        clothesBrands.add("Alpina Watches");
        clothesBrands.add("Arnette");
        clothesBrands.add("Aab");
        clothesBrands.add("Boss");
        clothesBrands.add("Bluezoo");
        clothesBrands.add("Bourjois");
        clothesBrands.add("Bluekids");
        clothesBrands.add("BeYu");
        clothesBrands.add("Bata");
        clothesBrands.add("Bella Comoda");
        clothesBrands.add("Bvlgari");
        clothesBrands.add("Bentley");
        clothesBrands.add("Baldi");
        clothesBrands.add("Ben Sherman");
        clothesBrands.add("Brax");
        clothesBrands.add("Brosway");
        clothesBrands.add("Boss Orange");
        clothesBrands.add("Birkenstock");
        clothesBrands.add("Burberry");


        clothesBrands.add("Bershka");
        clothesBrands.add("GAP");
        clothesBrands.add("Massimo Dutti");
        clothesBrands.add("Michael Kors");
        clothesBrands.add("Pandora");
        clothesBrands.add("Pandora");
        clothesBrands.add("Reebok");
        clothesBrands.add("Saint Laurent");
        clothesBrands.add("Tommy Hilfiger");
        clothesBrands.add("Timberland");
        clothesBrands.add("Pull and Bear");
        clothesBrands.add("Zara");

        ArrayList<String> clothesSizes = new ArrayList<>();
        clothesSizes.add("XXS");
        clothesSizes.add("XS");
        clothesSizes.add("S");
        clothesSizes.add("M");
        clothesSizes.add("L");
        clothesSizes.add("XL");
        clothesSizes.add("XXL");
        clothesSizes.add("3XL");
        clothesSizes.add("4XL");
        clothesSizes.add("Free-size");

        ArrayList<String> footwearSizes = new ArrayList<>();
        footwearSizes.add("35");
        footwearSizes.add("35.5");
        footwearSizes.add("36");
        footwearSizes.add("36.5");
        footwearSizes.add("37");
        footwearSizes.add("37.5");
        footwearSizes.add("38");
        footwearSizes.add("38.5");
        footwearSizes.add("39");
        footwearSizes.add("40");
        footwearSizes.add("41");
        footwearSizes.add("41.5");
        footwearSizes.add("42");
        footwearSizes.add("42.5");
        footwearSizes.add("43");
        footwearSizes.add("44");
        footwearSizes.add("45.5");
        footwearSizes.add("46");
        footwearSizes.add("46.5");
        footwearSizes.add("47");


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
        colors.add("Violet");
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
        if (isUserTourVirgin())
        {
            new Handler().postDelayed(() -> SplashActivity.this.startActivity(new Intent(SplashActivity.this, TourActivity.class)), 2500);
        }
        else
        {
            new Handler().postDelayed(() -> SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class)), 2500);
        }
    }

    private boolean isUserTourVirgin()
    {
        if (MyApplication.getSharedPreferencesManager().read(VIRGINITY_OF_TOUR) == null ||
                !((Boolean) MyApplication.getSharedPreferencesManager().read(VIRGINITY_OF_TOUR)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
