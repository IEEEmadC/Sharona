package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.ClothesExploreGridAdapter;
import com.sharonaapp.sharona.adapter.ClothesExploreRowAdapter;
import com.sharonaapp.sharona.interfaces.LikeItemClickListener;
import com.sharonaapp.sharona.interfaces.ListItemClickListener;
import com.sharonaapp.sharona.interfaces.SmoothTransition;
import com.sharonaapp.sharona.model.response.Clothes;
import com.sharonaapp.sharona.model.response.ClothesInnerResponse;
import com.sharonaapp.sharona.model.response.ExploreClothesResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.facebook.shimmer.ShimmerFrameLayout;

public class ExploreFragment extends Fragment implements ListItemClickListener, LikeItemClickListener, SmoothTransition {

    private static final String TAG = "ExploreFragment";
    private Unbinder unbinder;

    @BindView(R.id.view_explore_parent_layout)
    LinearLayout parentLayout;
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
    ArrayList<Clothes> clothesArrayListForGrid = new ArrayList<>();
    ArrayList<Clothes> clothesArrayListForRow = new ArrayList<>();

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
        parentLayout.setVisibility(View.VISIBLE);

        init();

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                Log.d(TAG, "onComplete: Refresh" + task.getResult().getToken());
            }
            else
            {
                Log.d(TAG, "onComplete: refresh failed");
            }

        });


    }


    private void init()
    {
        shimmerFrameLayout.startShimmer();
        fetchClothes();
    }


    private void fetchClothes()
    {
        Call<ExploreClothesResponse> clothesCall = NetworkManager.getInstance().getEndpointApi(Api.class).getClothes();
        clothesCall.enqueue(new Callback<ExploreClothesResponse>() {
            @Override
            public void onResponse(Call<ExploreClothesResponse> call, Response<ExploreClothesResponse> response)
            {
                if (shimmerFrameLayout != null)
                {
                    shimmerFrameLayout.stopShimmer();
                    shimmerFrameLayout.setVisibility(View.GONE);
                }
                if (response.isSuccessful())
                {
                    displayClothes(response.body().getClothes());
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

    private void displayClothes(List<Clothes> clothesList)
    {
        for (Clothes clothes : clothesList)
        {
            clothesArrayListForGrid.add(clothes);
            clothesArrayListForRow.add(clothes);
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
    public void onPause()
    {
        super.onPause();
        parentLayout.setVisibility(View.GONE);
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
        ClothesInnerFragment clothesInnerFragment = new ClothesInnerFragment();
        clothesInnerFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragment().getChildFragmentManager().beginTransaction();

        // Store the Fragment in stack
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_base_fragment_container, clothesInnerFragment).commit();
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

        clothesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        clothesListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        clothesListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void hide()
    {
        parentLayout.setVisibility(View.GONE);
    }

    @Override
    public void show()
    {
        parentLayout.setVisibility(View.VISIBLE);

    }
}
