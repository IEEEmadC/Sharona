package com.sharonaapp.sharona.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.ClothesExploreRowAdapter;
import com.sharonaapp.sharona.helper.AddClothesHelper;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.response.SearchResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.utility.DialogHelper;

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

    String searchConditionType;
    String searchConditionSize;
    String searchConditionBrand;
    String searchConditionColor;
    String searchConditionGender;

    boolean canSearch;

    @BindView(R.id.search_condition_layout)
    ConstraintLayout searchConditionsLayout;
    @BindView(R.id.search_type_text_view)
    TextView typeTextView;
    @BindView(R.id.search_size_text_view)
    TextView sizeTextView;
    @BindView(R.id.search_brand_text_view)
    TextView brandTextView;
    @BindView(R.id.search_color_text_view)
    TextView colorTextView;
    @BindView(R.id.search_gender_text_view)
    TextView genderTextView;

    @BindView(R.id.search_search_button)
    Button searchButton;

    @BindView(R.id.search_result_recycler_view)
    RecyclerView resultRecyclerView;

    @OnClick(R.id.search_type_text_view)
    void typeTextViewClicked()
    {
        ArrayList<String> typeArrayList = MyApplication.getConfig().getWearingTypesNamesArrayList();
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Type")
                .setAdapter(typeSpinnerAdapter, (dialog, which) -> {

                    searchConditionType = typeArrayList.get(which);
                    typeTextView.setText(typeArrayList.get(which));
                    userMaySearch();

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: Size
    @OnClick(R.id.search_size_text_view)
    void sizeTextViewClicked()
    {
        if (AddClothesHelper.AddClothesDTO.toBeUploadedClothesType == null ||
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesType.length() == 0)
        {
            DialogHelper.warnDialog(getActivity(), "Type is not selected", "First select type!");
            return;
        }

        ArrayList<String> sizeArrayList;

        if (AddClothesHelper.AddClothesDTO.toBeUploadedClothesType != null &&
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesType.length() > 0 &&
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesType.equalsIgnoreCase("Shoes"))
        {
            sizeArrayList = MyApplication.getConfig().getFootwearSizeArrayList();

        }
        else
        {
            sizeArrayList = MyApplication.getConfig().getClothesSizeArrayList();
        }

        ArrayAdapter<String> sizeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, sizeArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Size")
                .setAdapter(sizeSpinnerAdapter, (dialog, which) -> {

                    searchConditionSize = sizeArrayList.get(which);
                    sizeTextView.setText(sizeArrayList.get(which));
                    userMaySearch();

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    private void userMaySearch()
    {
        canSearch = true;
        searchButton.setEnabled(true);
    }

    // general details: Brand
    @OnClick(R.id.search_brand_text_view)
    void brandTextViewClicked()
    {
        ArrayList<String> brandArrayList = MyApplication.getConfig().getClothesBrandsArrayList();
        ArrayAdapter<String> brandSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, brandArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Brand")
                .setAdapter(brandSpinnerAdapter, (dialog, which) -> {

                    searchConditionBrand = brandArrayList.get(which);
                    brandTextView.setText(brandArrayList.get(which));
                    userMaySearch();

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: Color
    @OnClick(R.id.search_color_text_view)
    void colorTextViewClicked()
    {
        ArrayList<String> colorArrayList = MyApplication.getConfig().getColorArrayList();
        ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colorArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Color")
                .setAdapter(colorSpinnerAdapter, (dialog, which) -> {

                    searchConditionColor = colorArrayList.get(which);
                    colorTextView.setText(colorArrayList.get(which));
                    userMaySearch();

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: gender
    @OnClick(R.id.search_gender_text_view)
    void genderTextViewClicked()
    {
        ArrayList<String> genderArrayList = MyApplication.getConfig().getClothesGenderArrayList();
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genderArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Gender")
                .setAdapter(genderSpinnerAdapter, (dialog, which) -> {

                    searchConditionGender = genderArrayList.get(which);
                    genderTextView.setText(genderArrayList.get(which));
                    userMaySearch();

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }


    @OnClick(R.id.search_search_button)
    void searchClicked()
    {
        ((MainActivity) getActivity()).showLoading();
        NetworkManager.getInstance().getEndpointApi(Api.class)
                .search(searchConditionType,
                        searchConditionSize,
                        searchConditionBrand,
                        searchConditionColor,
                        searchConditionGender)
                .enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response)
                    {
                        Log.d(TAG, "onResponse: ");
                        ((MainActivity) getActivity()).hideLoading();
                        if (response.isSuccessful())
                        {
                            onSearchResultFetched(response.body());
                        }

                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t)
                    {
                        Log.d(TAG, "onFailure: ");
                        ((MainActivity) getActivity()).hideLoading();

                    }
                });
    }

    private void onSearchResultFetched(SearchResponse searchResponse)
    {
        if (searchResponse == null || searchResponse.getClothes() == null)
        {
            return;
        }
        else if (searchResponse.getClothes().size() == 0)
        {
            Toast.makeText(getContext(), "No Clothes found\nTry less conditions", Toast.LENGTH_SHORT).show();

            return;
        }


        switchUiModeToSearchResult();

        displayFetchedSearchResult(searchResponse.getClothes());
    }

    private void displayFetchedSearchResult(List<Clothes> list)
    {
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.setAdapter(new ClothesExploreRowAdapter(list));
    }

    private void switchUiModeToSearchResult()
    {
        searchConditionsLayout.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);
        resultRecyclerView.setVisibility(View.VISIBLE);
    }

    private void switchUiModeBack()
    {
        searchConditionsLayout.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        resultRecyclerView.setVisibility(View.GONE);
    }

    private ArrayList<Clothes> convertToArrayList(List<Clothes> list)
    {
        ArrayList<Clothes> clothesArrayList = new ArrayList<>();
//        for (ExploreClothesResponse.Datum clothes : list)
//        {
//            clothesArrayList.add(new com.sharonaapp.sharona.model.Clothes(clothes));
//        }
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

//        MyModel myModel = new MyModel();
////////        myModel.setFirstString("first string");
////////        myModel.setSecondString("second string");
////////        myModel.setId(101);
////////        myModel.setName("name str");
////////
////////        ShortcutHelper shortcutHelper = new ShortcutHelper(getActivity());
////////        shortcutHelper.removeHomsescreenShortcut(myModel);


    }

    @Override
    public void onResume()
    {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}
