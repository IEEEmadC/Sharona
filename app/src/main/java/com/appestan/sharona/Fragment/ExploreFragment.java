package com.appestan.sharona.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.appestan.sharona.Adapter.ClothesExploreGridAdapter;
import com.appestan.sharona.Adapter.ClothesExploreRowAdapter;
import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.Interface.LikeItemClickListener;
import com.appestan.sharona.Interface.ListItemClickListener;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Model.Clothes;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.ClothesResponse;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment implements ListItemClickListener, LikeItemClickListener {

    private Unbinder unbinder;

    @BindView(R.id.explore_row_view_text_view)
    TextView rowViewTextView;
    @BindView(R.id.explore_grid_view_text_view)
    TextView gridViewTextView;
    @BindView(R.id.explore_clothes_list_gridView)
    GridView clothesListGridView;
    @BindView(R.id.explore_clothes_list_recycler_view)
    RecyclerView clothesListRecyclerView;
    @BindView(R.id.explore_shimmer_view_container)
    ShimmerFrameLayout shimmerFrameLayout;


    @OnClick(R.id.explore_row_view_text_view)
    void switchToRowMode()
    {
        clothesListGridView.setVisibility(View.GONE);
        clothesListRecyclerView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.explore_grid_view_text_view)
    void switchToGridMode()
    {
        clothesListGridView.setVisibility(View.VISIBLE);
        clothesListRecyclerView.setVisibility(View.GONE);
    }

    // TODO: 10/5/18 these have to be removed
    ArrayList<Clothes> clothesArrayListForGrid = new ArrayList<Clothes>();
    ArrayList<Clothes> clothesArrayListForRow = new ArrayList<Clothes>();

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
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()), this);

//        initWithMocKData();

        init();


    }


    private void init()
    {
        shimmerFrameLayout.startShimmer();
        fetchClothes();
    }

    private void fetchClothes()
    {
        Call<ClothesResponse> clothesCall = NetworkManager.getInstance().getEndpointApi(Api.class).getClothes();
        clothesCall.enqueue(new Callback<ClothesResponse>() {
            @Override
            public void onResponse(Call<ClothesResponse> call, Response<ClothesResponse> response)
            {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                if (response.isSuccessful())
                {
                    displayClothes(response.body());
                }
                else
                {
                    Toast.makeText(getContext(), "" + response.code() + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ClothesResponse> call, Throwable t)
            {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_LONG).show();
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void displayClothes(ClothesResponse clothesResponse)
    {
        for (ClothesResponse.Datum clothesDataResponse : clothesResponse.getData())
        {
            clothesArrayListForGrid.add(new Clothes(clothesDataResponse));
            clothesArrayListForRow.add(new Clothes(clothesDataResponse));
        }

        clothesListGridView.setAdapter(new ClothesExploreGridAdapter(getContext(),
                this,
                this,
                clothesArrayListForGrid));

        ClothesExploreRowAdapter adapter = new ClothesExploreRowAdapter(clothesArrayListForRow);

        clothesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clothesListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        clothesListRecyclerView.setAdapter(adapter);
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
//        ((MainActivity) getActivity()).routeToClothesInnerFragment(this, clothes);


        Bundle bundle = new Bundle();
        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);
        ClothesInnerFragment a2Fragment = new ClothesInnerFragment();
        FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_base_fragment_container, a2Fragment).commit();
    }

    @Override
    public void itemInListLiked(int position)
    {
        Toast.makeText(getContext(), "Liked: " + position, Toast.LENGTH_SHORT).show();
    }

    private void initWithMocKData()
    {
        ArrayList<Clothes> clothesArrayList = new ArrayList<>();
        for (int i = 0; i < 23; i++)
        {
            Clothes clothes = new Clothes();

            clothes.setType("Type " + i);
            clothes.setBrand("Brand " + i);
            clothes.setTitle("Title " + i);
            clothes.setRentalPrice(String.valueOf(i));

            clothesArrayList.add(clothes);

            clothesArrayListForGrid.add(clothes);
            clothesArrayListForRow.add(clothes);
        }

        clothesListGridView.setAdapter(new ClothesExploreGridAdapter(getContext(),
                this,
                this,
                clothesArrayList));

        ClothesExploreRowAdapter adapter = new ClothesExploreRowAdapter(clothesArrayList);

        clothesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clothesListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        clothesListRecyclerView.setAdapter(adapter);
    }
}
