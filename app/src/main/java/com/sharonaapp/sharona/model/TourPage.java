package com.sharonaapp.sharona.model;

import android.graphics.drawable.Drawable;

public class TourPage {

    Drawable image;
    String title;
    String desc;

    public TourPage(Drawable image, String title, String desc)
    {
        this.image = image;
        this.title = title;
        this.desc = desc;
    }

    public Drawable getImage()
    {
        return image;
    }

    public void setImage(Drawable image)
    {
        this.image = image;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }
}
