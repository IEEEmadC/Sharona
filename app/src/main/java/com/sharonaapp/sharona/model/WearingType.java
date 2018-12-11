package com.sharonaapp.sharona.model;

public class WearingType {
    public String name;
    public int bodyPart;

    // 0 -> Upper-body, 1 -> Lower-body, 2 -> Full-body, 3 -> Foot-wear, 4 -> Accessories

    public WearingType(String name, int bodyPart)
    {
        this.name = name;
        this.bodyPart = bodyPart;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBodyPart()
    {
        return bodyPart;
    }

    public void setBodyPart(int bodyPart)
    {
        this.bodyPart = bodyPart;
    }
}
