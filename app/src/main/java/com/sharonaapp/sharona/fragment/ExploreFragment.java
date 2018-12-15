package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.ClothesExploreGridAdapter;
import com.sharonaapp.sharona.adapter.ClothesExploreRowAdapter;
import com.sharonaapp.sharona.interfaces.LikeItemClickListener;
import com.sharonaapp.sharona.interfaces.ListItemClickListener;
import com.sharonaapp.sharona.manager.DataManager;
import com.sharonaapp.sharona.manager.LikeManager;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.response.ClothesInnerResponse;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.facebook.shimmer.ShimmerFrameLayout;

public class ExploreFragment extends Fragment implements ListItemClickListener, LikeItemClickListener {

    private static final String TAG = "ExploreFragment";
    private Unbinder unbinder;
    @BindView(R.id.explore_clothes_list_gridView)
    GridView clothesListGridView;
    @BindView(R.id.explore_shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;

    // TODO: 10/5/18 these have to be removed
    ArrayList<Clothes> clothesArrayListForGrid = new ArrayList<>();
    ArrayList<Clothes> clothesArrayListForRow = new ArrayList<>();
    private ClothesExploreGridAdapter gridAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fetchData();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_explore, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        init();


    }


    private void fetchData()
    {
        decideOnFetchingClothesFromDataManagerOrNetwork();
    }


    private void decideOnFetchingClothesFromDataManagerOrNetwork()
    {
        if (!DataManager.getInstance().isExploreDataAvailable())
        {
            Call<ExploreClothesResponse> clothesCall = NetworkManager.getInstance().getEndpointApi(Api.class).getClothes();
            clothesCall.enqueue(new Callback<ExploreClothesResponse>() {
                @Override
                public void onResponse(Call<ExploreClothesResponse> call, Response<ExploreClothesResponse> response)
                {


                    if (response.isSuccessful())
                    {
                        if (shimmerFrameLayout != null)
                        {
                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            displayClothes(response.body().getData());
                        }

                        DataManager.getInstance().setExploreClothesResponse(response);

                    }
                    else
                    {
                        Toast.makeText(getContext(), "" + response.code() + response.message(), Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onFailure(Call<ExploreClothesResponse> call, Throwable t)
                {
                    Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                    if (shimmerFrameLayout != null)
                    {
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                    }
                }
            });
        }
    }

    private void init()
    {

        if (shimmerFrameLayout != null)
        {
            shimmerFrameLayout.setVisibility(View.VISIBLE);
            shimmerFrameLayout.startShimmer();
        }


        if (DataManager.getInstance().isExploreDataAvailable())
        {
            displayClothes(DataManager.getInstance().getExploreClothesResponse().body().getData());
        }

    }

    private boolean isDataAlreadyDisplaying()
    {
        return clothesArrayListForGrid.size() != 0;
    }

    private void displayClothes(List<Clothes> clothesList)
    {
        if (shimmerFrameLayout != null)
        {
            shimmerFrameLayout.stopShimmer();
            shimmerFrameLayout.setVisibility(View.GONE);
        }

        clothesArrayListForGrid.clear();

        for (Clothes clothes : clothesList)
        {
            clothesArrayListForGrid.add(clothes);
        }

        gridAdapter = new ClothesExploreGridAdapter(getContext(),
                this,
                this,
                clothesArrayListForGrid);
        clothesListGridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();


    }

    @Override
    public void onPause()
    {
        super.onPause();
//        parentLayout.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void itemInListClicked(int itemId)
    {
        Clothes clothes = clothesArrayListForGrid.get(itemId);
        goToInnerClothes(clothes);
    }

    @Override
    public void itemInListLiked(int i, int clothesId)
    {
        Toast.makeText(getContext(), "Liked Or disliked: " + clothesId, Toast.LENGTH_SHORT).show();

    }

    private void goToInnerClothes(Clothes clothes)
    {
        Bundle bundle = new Bundle();
        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);

        ((MainActivity) getActivity()).exploreNavController.navigate(R.id.action_to_item_clothes_inner, bundle);


//        Clothes clothes = clothesArrayListForGrid.get(itemId);
//       ((MainActivity) getActivity()).routeToClothesInnerFragment(this, clothes);
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);
//        ClothesInnerFragment clothesInnerFragment = new ClothesInnerFragment();
    }

    private void initWithMocKData()
    {
        ArrayList<Clothes> clothesArrayList = new ArrayList<>();
        for (int i = 0; i < 23; i++)
        {
            ClothesInnerResponse clothesInnerResponse = new ClothesInnerResponse();
            Clothes clothes = new Clothes();

            clothes.setType("Type " + i);
            clothes.setBrand("Brand " + i);
            clothes.setTitle("Title " + i);
            clothes.setRentPrice(String.valueOf(i));

            clothesArrayList.add(clothes);

            clothesArrayListForGrid.add(clothes);
            clothesArrayListForRow.add(clothes);
        }

        clothesListGridView.setAdapter(new ClothesExploreGridAdapter(getContext(),
                this,
                this,
                clothesArrayList));

        ClothesExploreRowAdapter adapter = new ClothesExploreRowAdapter(clothesArrayList);

    }
}
