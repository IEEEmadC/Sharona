package com.appestan.sharona.Managers;

import android.arch.lifecycle.MutableLiveData;

import com.appestan.sharona.Model.Clothes;


public class DataProvider {

    private DataProvider dataProvider;
    MutableLiveData<Clothes> clothesMutableLiveData;

    public DataProvider getInstance()
    {
        if (dataProvider == null)
        {
            dataProvider = new DataProvider();
        }

        return dataProvider;
    }

    private DataProvider()
    {

    }


    public void fetchLatestClothes()
    {

    }

//    public void
}
