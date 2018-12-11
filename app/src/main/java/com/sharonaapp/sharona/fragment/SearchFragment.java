package com.sharonaapp.sharona.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.ShortcutHelper;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.adapter.ClothesExploreRowAdapter;
import com.sharonaapp.sharona.model.response.Clothes;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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

        JSONObject jsonObject = new JSONObject();
        try
        {
            if (sizeEditText.getText() != null && sizeEditText.getText().toString().length() > 0)
            {
                jsonObject.put("size", sizeEditText.getText().toString());
            }

            if (typeEditText.getText() != null && typeEditText.getText().toString().length() > 0)
            {
                jsonObject.put("type", typeEditText.getText().toString());
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


//        JsonObjectRequest searchRequest = new JsonObjectRequest(Request.Method.GET, Url.SEARCH, jsonObject,
//                response -> {
//                    if (response != null && response.has("success"))
//                    {
//                        try
//                        {
//                            ArrayList<Clothes> searchedClothesArrayList = ClothesHelper.clothesJsonToClothesPojo(response.getJSONArray("data"));
//                            onSearchResultFetched(searchedClothesArrayList);
//                        }
//                        catch (JSONException e)
//                        {
//                            e.printStackTrace();
//                        }
//                    }
//                    else
//                    {
//
//                    }
//                },
//                error -> {
//
//                    DialogHelper.showDialogWithMessage(getContext(), "error");
//
//                }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError
//            {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json");
//                headers.put("Accept", "application/json");
//                headers.put("Authorization", "Bearer " + getToken());
//                return headers;
//            }
//        };
//        Volley.newRequestQueue(getContext()).add(searchRequest);
    }

    private void onSearchResultFetched(ArrayList<Clothes> list)
    {
        switchUiModeToSearchResult();

        displayFetchedSearchResult(list);
    }

    private void displayFetchedSearchResult(ArrayList<Clothes> list)
    {
        resultRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        resultRecyclerView.setAdapter(new ClothesExploreRowAdapter(list));
    }

    private void switchUiModeToSearchResult()
    {
        sizeEditText.setVisibility(View.GONE);
        typeEditText.setVisibility(View.GONE);
        searchButton.setVisibility(View.GONE);
        resultRecyclerView.setVisibility(View.VISIBLE);
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
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()));

//        MyModel myModel = new MyModel();
////////        myModel.setFirstString("first string");
////////        myModel.setSecondString("second string");
////////        myModel.setId(101);
////////        myModel.setName("name str");
////////
////////        ShortcutHelper shortcutHelper = new ShortcutHelper(getActivity());
////////        shortcutHelper.removeHomsescreenShortcut(myModel);


        final Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setComponent(new ComponentName(getActivity().getPackageName(), "MainActivity"));

        final Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
        intent.setComponent(new ComponentName(getActivity().getPackageName(), "MainActivity"));
        intent.setAction("com.android.launcher.action.UNINSTALL_SHORTCUT");

        getActivity().sendBroadcast(intent, null);

        new ShortcutHelper(getContext()).removeShortcut();


    }

    @Override
    public void onResume()
    {
        super.onResume();
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }
}
