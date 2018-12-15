package com.sharonaapp.sharona.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.rd.PageIndicatorView;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.adapter.ClothesImageAdapter;
import com.sharonaapp.sharona.model.general.Clothes;
import com.sharonaapp.sharona.model.general.Report;
import com.sharonaapp.sharona.model.request.LendRequest;
import com.sharonaapp.sharona.model.request.SellRequest;
import com.sharonaapp.sharona.model.response.LendResponse;
import com.sharonaapp.sharona.model.response.ReportResponse;
import com.sharonaapp.sharona.model.response.SellResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.utility.DialogHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClothesInnerFragment extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R.id.view_clothes_inner_view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_clothes_inner_page_indicator_view)
    PageIndicatorView pageIndicator;
    private int clothesToBeShownId;

    @BindView(R.id.clothes_inner_buy_price_value_text_view)
    TextView buyPriceValueTextView;
    @BindView(R.id.clothes_inner_rent_price_value_text_view)
    TextView rentPriceValueTextView;

    @BindView(R.id.clothes_inner_type_value_text_view)
    TextView typeValueTextView;
    @BindView(R.id.clothes_inner_size_value_text_view)
    TextView sizeValueTextView;
    @BindView(R.id.clothes_inner_brand_value_text_view)
    TextView brandValueTextView;
    @BindView(R.id.clothes_inner_color_value_text_view)
    TextView colorValueTextView;
    @BindView(R.id.clothes_inner_gender_value_text_view)
    TextView genderValueTextView;
    @BindView(R.id.clothes_inner_usage_status_value_text_view)
    TextView usageStatusValueTextView;
    @BindView(R.id.clothes_inner_description_value_text_view)
    TextView descriptionValueTextView;

    @BindView(R.id.clothes_inner_report_text_view)
    TextView reportTextView;
    private Clothes clothesToBeShown;
    //    @BindView(R.id.clothes_inner_original_price_value_text_view)
//    TextView originalPriceValueTextView;
//    @BindView(R.id.clothes_inner_rental_price_value_text_view)
//    TextView rentalPriceValueTextView;

    @OnClick(R.id.view_clothes_inner_request_button)
    void onRequestButtonClicked()
    {
        String choiceString[] = new String[]{};
        if (clothesToBeShown.getSellable() && clothesToBeShown.getLendable())
        {
            choiceString = new String[]{"Buy", "Borrow"};
        }
        else if (clothesToBeShown.getSellable())
        {
            choiceString = new String[]{"Buy"};

        }
        else if (clothesToBeShown.getLendable())
        {
            choiceString = new String[]{"Borrow"};

        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Send Request");
        dialog.setItems(choiceString,
                (dialog1, which) -> {
                    if (which == 0)
                    {
                        if (clothesToBeShown.getSellable())
                        {
                            sendBuyRequest();
                        }
                        else
                        {
                            sendBorrowRequest();
                        }
                    }
                    if (which == 1)
                    {
                        if (clothesToBeShown.getSellable())
                        {
                            sendBorrowRequest();
                        }

                    }
                }).show();
    }

    private void sendBuyRequest()
    {
        SellRequest sellRequest = new SellRequest();
        sellRequest.setClothesId(clothesToBeShownId);
        Call<SellResponse> sell = NetworkManager.getInstance().getEndpointApi(Api.class).sell(sellRequest);
        sell.enqueue(new Callback<SellResponse>() {
            @Override
            public void onResponse(Call<SellResponse> call, Response<SellResponse> response)
            {
                if (response.isSuccessful())
                {
                    DialogHelper.warnDialog(getContext(), "Request Sent!", "Request is sent to the owner");
                }
                else
                {
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SellResponse> call, Throwable t)
            {
                Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendBorrowRequest()
    {

        LendRequest lendRequest = new LendRequest();
        lendRequest.setClothesId(clothesToBeShownId);
        Call<LendResponse> lend = NetworkManager.getInstance().getEndpointApi(Api.class).lend(lendRequest);
        lend.enqueue(new Callback<LendResponse>() {
            @Override
            public void onResponse(Call<LendResponse> call, Response<LendResponse> response)
            {
                if (response.isSuccessful())
                {
                    DialogHelper.warnDialog(getContext(), "Request Sent!", "Request is sent to the owner");
                }
                else
                {
                    Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LendResponse> call, Throwable t)
            {
                Toast.makeText(getContext(), "Connection Error!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.clothes_inner_report_text_view)
    void onReportTextViewClicked()
    {

        String choiceString[] = new String[]{"Image is offensive", "Details are wrong"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Report Abuse");
        dialog.setItems(choiceString,
                (dialog1, which) -> {
                    if (which == 0)
                    {
                        report("Image is offensive");
                    }
                    if (which == 1)
                    {
                        report("Details are wrong");
                    }
                }).show();
    }

    private void report(String reportString)
    {
        Report report = new Report();
        report.setClothesId(clothesToBeShownId);
        report.setReason(reportString);
        NetworkManager.getInstance().getEndpointApi(Api.class).report(report).enqueue(new Callback<ReportResponse>() {
            @Override
            public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response)
            {
                if (response.isSuccessful())
                {
                    Toast.makeText(getContext(), "Reported! we will check it asap", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ReportResponse> call, Throwable t)
            {
                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_clothes_inner, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);


        if (getArguments() != null &&
                getArguments().containsKey("CLOTHES_TO_BE_SHOWN") &&
                getArguments().getSerializable("CLOTHES_TO_BE_SHOWN") != null &&
                getArguments().getSerializable("CLOTHES_TO_BE_SHOWN") instanceof Clothes)
        {
            Clothes clothesToBeShown = ((Clothes) getArguments().getSerializable("CLOTHES_TO_BE_SHOWN"));

            if (clothesToBeShown != null && clothesToBeShown.getImages() != null)
            {
                viewPager.setAdapter(new ClothesImageAdapter(clothesToBeShown.getImages()));
                pageIndicator.setCount(clothesToBeShown.getImages().size());

                retainInformationForRequests(clothesToBeShown);
                displayClothes(clothesToBeShown);
            }

        }
/*        else
        {
            Clothes clothes = new Clothes();
            clothes.setType("Jeans");
            clothes.setBrand("D&G");
            clothes.setColor("Dark Blue");
            clothes.setSize("41");
            clothes.setOriginalPrice("140");
            clothes.setRentalPrice("30");

            displayClothes(clothes);
        }*/

    }

    private void retainInformationForRequests(Clothes clothesToBeShown)
    {
        clothesToBeShownId = clothesToBeShown.getId();
        this.clothesToBeShown = clothesToBeShown;
    }

    private void displayClothes(Clothes clothes)
    {
        if (clothes == null)
        {
            return;
        }

        safelySetTextToTextView(clothes.getBuyPrice() + "$", buyPriceValueTextView);
        safelySetTextToTextView(clothes.getRentPrice() + "$", rentPriceValueTextView);

        safelySetTextToTextView(clothes.getType(), typeValueTextView);
        safelySetTextToTextView(clothes.getSize(), sizeValueTextView);
        safelySetTextToTextView(clothes.getBrand(), brandValueTextView);
        safelySetTextToTextView(clothes.getColor(), colorValueTextView);
        safelySetTextToTextView(clothes.getDescription(), descriptionValueTextView);
        safelySetTextToTextView(clothes.getGender(), genderValueTextView);
        safelySetTextToTextView(clothes.getNewOrUsedStatus(), usageStatusValueTextView);


//        safelySetTextToTextView(clothes.getOriginalPrice(), originalPriceValueTextView);
//        safelySetTextToTextView(clothes.getRentalPrice(), rentalPriceValueTextView);

    }

    private void safelySetTextToTextView(@Nullable String text, TextView typeValueTextView)
    {
        if (text != null && !text.isEmpty())
        {
            typeValueTextView.setText(text);
        }
    }

    private void safelySetTextToTextView(int text, TextView typeValueTextView)
    {

        typeValueTextView.setText(String.valueOf(text));
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}
