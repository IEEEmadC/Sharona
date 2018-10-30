package com.appestan.sharona.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.appestan.sharona.Adapter.ClothesImageAdapter;
import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.CustomUI.ReportDialog;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Managers.CallManager;
import com.appestan.sharona.Model.Clothes;
import com.appestan.sharona.R;
import com.rd.PageIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ClothesInnerFragment extends BaseFragment {

    private Unbinder unbinder;

    @BindView(R.id.view_clothes_inner_view_pager)
    ViewPager viewPager;
    @BindView(R.id.view_clothes_inner_page_indicator_view)
    PageIndicatorView pageIndicator;
    @BindView(R.id.view_clothes_inner_call_owner_button)
    Button callOwnerButton;

    @OnClick(R.id.view_clothes_inner_call_owner_button)
    void callOwnerClicked()
    {
        CallManager.callPhoneNumber(getActivity(), "09122852117");
    }

    @BindView(R.id.view_clothes_inner_price_in_title_text_view)
    TextView priceInTitleTextView;
    @BindView(R.id.clothes_inner_type_value_text_view)
    TextView typeValueTextView;
    @BindView(R.id.clothes_inner_brand_value_text_view)
    TextView brandValueTextView;
    @BindView(R.id.clothes_inner_color_value_text_view)
    TextView colorValueTextView;
    @BindView(R.id.clothes_inner_size_value_text_view)
    TextView sizeValueTextView;
    @BindView(R.id.clothes_inner_report_text_view)
    TextView reportTextView;
//    @BindView(R.id.clothes_inner_original_price_value_text_view)
//    TextView originalPriceValueTextView;
//    @BindView(R.id.clothes_inner_rental_price_value_text_view)
//    TextView rentalPriceValueTextView;

    @OnClick(R.id.clothes_inner_report_text_view)
    void onReportTextViewClicked()
    {
        new ReportDialog(getContext(), "1001").show();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.freaking_test, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()), this);


        viewPager.setAdapter(new ClothesImageAdapter(3));
        pageIndicator.setCount(3);

        if (getArguments() != null &&
                getArguments().containsKey("CLOTHES_TO_BE_SHOWN") &&
                getArguments().getSerializable("CLOTHES_TO_BE_SHOWN") != null &&
                getArguments().getSerializable("CLOTHES_TO_BE_SHOWN") instanceof Clothes)
        {
            Clothes clothesToBeShown = ((Clothes) getArguments().getSerializable("CLOTHES_TO_BE_SHOWN"));

            displayClothes(clothesToBeShown);
        }
        else


        {
            Clothes clothes = new Clothes();
            clothes.setType("Jeans");
            clothes.setBrand("D&G");
            clothes.setColor("Dark Blue");
            clothes.setSize("41");
            clothes.setOriginalPrice("140");
            clothes.setRentalPrice("30");

            displayClothes(clothes);
        }

    }

    private void displayClothes(Clothes clothes)
    {
        if (clothes == null)
        {
            return;
        }

        safelySetTextToTextView(clothes.getRentalPrice() + "$ For 2 Days", priceInTitleTextView);

        safelySetTextToTextView(clothes.getType(), typeValueTextView);

        safelySetTextToTextView(clothes.getBrand(), brandValueTextView);

        safelySetTextToTextView(clothes.getColor(), colorValueTextView);

        safelySetTextToTextView(clothes.getSize(), sizeValueTextView);

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
