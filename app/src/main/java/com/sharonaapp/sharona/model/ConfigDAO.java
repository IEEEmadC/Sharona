package com.sharonaapp.sharona.model;

import java.util.ArrayList;

public class ConfigDAO {

    ArrayList<WearingType> wearingTypesArrayList;
    ArrayList<String> clothesBrandsArrayList;
    ArrayList<String> clothesSizeArrayList;
    ArrayList<String> footwearSizeArrayList;
    ArrayList<String> colorArrayList;

    public ConfigDAO()
    {
    }

    public ArrayList<WearingType> getWearingTypesArrayList()
    {
        return wearingTypesArrayList;
    }

    public void setWearingTypesArrayList(ArrayList<WearingType> wearingTypesArrayList)
    {
        this.wearingTypesArrayList = wearingTypesArrayList;
    }

    public ArrayList<String> getWearingTypesNamesArrayList()
    {
        ArrayList<String> strings = new ArrayList<>();
        if (wearingTypesArrayList != null)
        {
            for (int i = 0; i < wearingTypesArrayList.size(); i++)
            {
                strings.add(wearingTypesArrayList.get(i).getName());
            }
        }
        return strings;
    }

    public ArrayList<String> getClothesBrandsArrayList()
    {
        return clothesBrandsArrayList;
    }

    public void setClothesBrandsArrayList(ArrayList<String> clothesBrandsArrayList)
    {
        this.clothesBrandsArrayList = clothesBrandsArrayList;
    }

    public ArrayList<String> getClothesSizeArrayList()
    {
        return clothesSizeArrayList;
    }

    public void setClothesSizeArrayList(ArrayList<String> clothesSizeArrayList)
    {
        this.clothesSizeArrayList = clothesSizeArrayList;
    }

    public ArrayList<String> getFootwearSizeArrayList()
    {
        return footwearSizeArrayList;
    }

    public void setFootwearSizeArrayList(ArrayList<String> footwearSizeArrayList)
    {
        this.footwearSizeArrayList = footwearSizeArrayList;
    }

    public ArrayList<String> getColorArrayList()
    {
        return colorArrayList;
    }

    public void setColorArrayList(ArrayList<String> colorArrayList)
    {
        this.colorArrayList = colorArrayList;
    }
}
