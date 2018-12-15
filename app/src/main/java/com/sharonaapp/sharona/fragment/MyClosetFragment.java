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

import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.MyClosetAdapter;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.response.MyClosetResponse;
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

        fetchMyCloset();


    }


    private void fetchMyCloset()
    {
        ((MainActivity) getActivity()).showLoading();

        Call<MyClosetResponse> myClosetCall = NetworkManager.getInstance().getEndpointApi(Api.class).getMyCloset();
        myClosetCall.enqueue(new Callback<MyClosetResponse>() {
            @Override
            public void onResponse(Call<MyClosetResponse> call, Response<MyClosetResponse> response)
            {
                ((MainActivity) getActivity()).hideLoading();

                if (response != null && response.isSuccessful())
                {
                    if (response.body() != null && response.body().getData() != null)
                    {
                        responseSuccessful(response.body().getData());

                        return;

                    }
                }


            }

            @Override
            public void onFailure(Call<MyClosetResponse> call, Throwable t)
            {
                ((MainActivity) getActivity()).hideLoading();

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
