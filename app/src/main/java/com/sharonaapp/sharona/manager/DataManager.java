package com.sharonaapp.sharona.manager;

import com.sharonaapp.sharona.model.response.ExploreClothesResponse;

import retrofit2.Response;

public class DataManager {
    private static final DataManager ourInstance = new DataManager();
    private Response<ExploreClothesResponse> exploreClothesResponse;

    public static DataManager getInstance()
    {
        return ourInstance;
    }

    private DataManager()
    {
    }

    public boolean isExploreDataAvailable()
    {
        return exploreClothesResponse != null;
    }

    public Response<ExploreClothesResponse> getExploreClothesResponse()
    {
        return exploreClothesResponse;
    }

    public void setExploreClothesResponse(Response<ExploreClothesResponse> exploreClothesResponse)
    {
        this.exploreClothesResponse = exploreClothesResponse;
    }
}
