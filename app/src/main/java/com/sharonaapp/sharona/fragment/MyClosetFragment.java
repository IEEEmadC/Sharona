package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.MyClosetAdapter;
import com.sharonaapp.sharona.model.response.Clothes;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyClosetFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.my_closet_clothes_recycler_view)
    RecyclerView myClosetRecyclerView;

    @OnClick(R.id.my_closet_button)
    void fragmentTest()
    {
        ((MainActivity) getActivity()).routeToLoginFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_my_closet_with_motion, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));


        fetchMyCloset();


    }


    private void fetchMyCloset()
    {
        Call<ExploreClothesResponse> myClosetCall = NetworkManager.getInstance().getEndpointApi(Api.class).getMyCloset();
        myClosetCall.enqueue(new Callback<ExploreClothesResponse>() {
            @Override
            public void onResponse(Call<ExploreClothesResponse> call, Response<ExploreClothesResponse> response)
            {
                if (response != null && response.isSuccessful())
                {
                    if (response.body() != null && response.body().getClothes() != null)
                    {
                        responseSuccessful(response.body().getClothes());

                        return;

                    }
                }


            }

            @Override
            public void onFailure(Call<ExploreClothesResponse> call, Throwable t)
            {

            }
        });
    }

    private void responseSuccessful(List<Clothes> data)
    {
        myClosetRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        myClosetRecyclerView.setAdapter(new MyClosetAdapter(data));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
