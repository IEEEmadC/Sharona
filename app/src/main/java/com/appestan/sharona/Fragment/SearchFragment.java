package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.appestan.sharona.Adapter.ClothesExploreRowAdapter;
import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.Helper.SearchParameterHelper;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Model.Clothes;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.ClothesResponse;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    private static final String TAG = "SearchFragment";
    Unbinder unbinder;

    @BindView(R.id.search_size_edit_text)
    EditText sizeEditText;
    @BindView(R.id.search_type_edit_text)
    EditText typeEditText;

    @BindView(R.id.search_search_button)
    Button searchButton;

    @BindView(R.id.search_result_recycler_view)
    RecyclerView resultRecyclerView;


    @OnClick(R.id.search_search_button)
    void searchClicked()
    {

        SearchParameterHelper searchParameterHelper = new SearchParameterHelper();
        if (sizeEditText.getText() != null && sizeEditText.getText().toString().length() > 0)
        {
            searchParameterHelper.setSize(sizeEditText.getText().toString());
        }

        if (typeEditText.getText() != null && typeEditText.getText().toString().length() > 0)
        {
            searchParameterHelper.setType(typeEditText.getText().toString());

        }

        Call<ClothesResponse> searchCall = NetworkManager.getInstance().getEndpointApi(Api.class).getSearch(searchParameterHelper.getSize(), searchParameterHelper.getType());
        searchCall.enqueue(new Callback<ClothesResponse>() {
            @Override
            public void onResponse(Call<ClothesResponse> call, Response<ClothesResponse> response)
            {
                Log.d(TAG, "onResponse: " + response);
                if (response != null && response.raw().code() == 200)
                {
                    onSearchResultFetched(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ClothesResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void onSearchResultFetched(List<ClothesResponse.Datum> list)
    {
        switchUiModeToSearchResult();

        displayFetchedSearchResult(list);
    }

    private void displayFetchedSearchResult(List<ClothesResponse.Datum> list)
    {
        ArrayList<Clothes> clothesArrayList = convertToArrayList(list);
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.setAdapter(new ClothesExploreRowAdapter(clothesArrayList));
    }

    private void switchUiModeToSearchResult()
    {
        sizeEditText.setVisibility(View.GONE);
        typeEditText.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);
        resultRecyclerView.setVisibility(View.VISIBLE);
    }

    private ArrayList<Clothes> convertToArrayList(List<ClothesResponse.Datum> list)
    {
        ArrayList<Clothes> clothesArrayList = new ArrayList<>();
        for (ClothesResponse.Datum datum : list)
        {
            clothesArrayList.add(new Clothes(datum));
        }
        return clothesArrayList;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_search, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));

    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}
