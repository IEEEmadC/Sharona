package com.appestan.sharona.Model;

public class Transaction {
    String id;
    String type;
    String clothesId;
    String state;
    String userDealingWith;
    String amount;
    String date;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getClothesId()
    {
        return clothesId;
    }

    public void setClothesId(String clothesId)
    {
        this.clothesId = clothesId;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getUserDealingWith()
    {
        return userDealingWith;
    }

    public void setUserDealingWith(String userDealingWith)
    {
        this.userDealingWith = userDealingWith;
    }

    public String getAmount()
    {
        return amount;
    }

    public void setAmount(String amount)
    {
        this.amount = amount;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
