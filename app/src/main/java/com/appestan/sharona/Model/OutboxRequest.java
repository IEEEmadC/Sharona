package com.appestan.sharona.Model;

public class OutboxRequest {

    String id;
    String requestType;
    String itemRequestedOn;
    String avatarUrl;
    String clothesType;
    boolean actedOn;

    public OutboxRequest()
    {
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRequestType()
    {
        return requestType;
    }

    public void setRequestType(String requestType)
    {
        this.requestType = requestType;
    }

    public String getItemRequestedOn()
    {
        return itemRequestedOn;
    }

    public void setItemRequestedOn(String itemRequestedOn)
    {
        this.itemRequestedOn = itemRequestedOn;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }

    public String getClothesType()
    {
        return clothesType;
    }

    public void setClothesType(String clothesType)
    {
        this.clothesType = clothesType;
    }

    public boolean isActedOn()
    {
        return actedOn;
    }

    public void setActedOn(boolean actedOn)
    {
        this.actedOn = actedOn;
    }
}
