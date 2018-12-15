package com.sharonaapp.sharona.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.helper.AddClothesHelper;
import com.sharonaapp.sharona.model.WearingType;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.network.UploadClothesInfoPartRequest;
import com.sharonaapp.sharona.network.UploadClothesInfoPartResponse;
import com.sharonaapp.sharona.network.Url;
import com.sharonaapp.sharona.network.VolleyMultipartRequest;
import com.sharonaapp.sharona.utility.CommonHelper;
import com.sharonaapp.sharona.utility.DialogHelper;
import com.shuhart.stepview.StepView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;

import static android.app.Activity.RESULT_OK;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.USER_HAS_HALF_WAY_UPLOADED_CLOTHES;


public class AddClothesFragment extends Fragment {

    private static final String TAG = "AddClothesFragment";

    private Unbinder unbinder;

    // general details
    @BindView(R.id.add_clothes_type_text_view)
    TextView typeTextView;
    @BindView(R.id.add_clothes_size_text_view)
    TextView sizeTextView;
    @BindView(R.id.add_clothes_brand_text_view)
    TextView brandTextView;
    @BindView(R.id.add_clothes_color_text_view)
    TextView colorTextView;
    @BindView(R.id.add_clothes_gender_text_view)
    TextView genderTextView;
    @BindView(R.id.add_clothes_used_status_text_view)
    TextView usedStatusTextView;

    //buy price
    @BindView(R.id.add_clothes_sellable_checkbox)
    AppCompatCheckBox sellableCheckbox;
    @BindView(R.id.add_clothes_lendable_checkbox)
    AppCompatCheckBox lendableCheckbox;

    @BindView(R.id.add_clothes_buy_price_text_input_layout)
    TextInputLayout buyPriceTextInputLayout;
    @BindView(R.id.add_clothes_buy_price_text_input_edit_text)
    TextInputEditText buyPriceTextInputEditText;
    //rent price
    @BindView(R.id.add_clothes_rental_price_text_input_layout)
    TextInputLayout rentPriceTextInputLayout;
    @BindView(R.id.add_clothes_rental_price_text_input_edit_text)
    TextInputEditText rentPriceTextInputEditText;

    //swap
    @BindView(R.id.add_clothes_swapable_checkbox)
    AppCompatCheckBox swapableCheckbox;

    // swap details
    @BindView(R.id.add_clothes_swap_condition_type_text_view)
    TextView swapConditionTypeTextView;
    @BindView(R.id.add_clothes_swap_condition_size_text_view)
    TextView swapConditionSizeTextView;
    @BindView(R.id.add_clothes_swap_condition_brand_text_view)
    TextView swapConditionBrandTextView;
    @BindView(R.id.add_clothes_swap_condition_color_text_view)
    TextView swapConditionColorTextView;


    //description
    @BindView(R.id.add_clothes_description_text_input_layout)
    TextInputLayout descriptionTextInputLayout;
    @BindView(R.id.add_clothes_description_text_input_edit_text)
    TextInputEditText descriptionTextInputEditText;

    @BindView(R.id.add_clothes_select_photo_0_image_view)
    ImageView selectedPhoto0imageView;
    @BindView(R.id.add_clothes_select_photo_1_image_view)
    ImageView selectedPhoto1imageView;
    @BindView(R.id.add_clothes_select_photo_2_image_view)
    ImageView selectedPhoto2imageView;
    @BindView(R.id.add_clothes_select_photo_3_image_view)
    ImageView selectedPhoto3imageView;

    @BindView(R.id.add_clothes_proceed_button)
    Button proceed_button;

    //    @BindView(R.id.add_clothes_general_details_layout)
//    ConstraintLayout generalDetailsScrollView;
    @BindView(R.id.add_clothes_general_details_scroll_view)
    ScrollView generalDetailsScrollView;
    //    @BindView(R.id.add_clothes_pricing_details_layout)
//    ConstraintLayout pricingDetailsScrollView;
    @BindView(R.id.add_clothes_pricing_details_scroll_view)
    ScrollView pricingDetailsScrollView;
    //    @BindView(R.id.add_clothes_swap_details_layout)
//    ConstraintLayout swapDetailsScrollView;
    @BindView(R.id.add_clothes_swap_details_scroll_view)
    ScrollView swapDetailsScrollView;
    @BindView(R.id.add_clothes_image_upload_scroll_view)
    ScrollView imageUploadScrollView;

    ArrayList<Bitmap> selectedPhotosAsBitmap;
    private static String latestImageViewClickedForUploading;


    @BindView(R.id.step_view)
    StepView stepView;
    private int enteredTypeWearingType = -1;

    // general details: Type
    @OnClick(R.id.add_clothes_type_text_view)
    void typeTextViewClicked()
    {
        ArrayList<String> typeArrayList = MyApplication.getConfig().getWearingTypesNamesArrayList();
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Type")
                .setAdapter(typeSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesType = typeArrayList.get(which);
                    if (typeTextView.getText() != null && !typeTextView.getText().toString().equalsIgnoreCase(typeArrayList.get(which)))
                    {
                        typeTextView.setText(typeArrayList.get(which));
                        AddClothesHelper.AddClothesDTO.toBeUploadedClothesSize = null;
                        sizeTextView.setText("Select Size");
                    }

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: Size
    @OnClick(R.id.add_clothes_size_text_view)
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

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesSize = sizeArrayList.get(which);
                    sizeTextView.setText(sizeArrayList.get(which));


                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: Brand
    @OnClick(R.id.add_clothes_brand_text_view)
    void brandTextViewClicked()
    {
        ArrayList<String> brandArrayList = MyApplication.getConfig().getClothesBrandsArrayList();
        ArrayAdapter<String> brandSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, brandArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Brand")
                .setAdapter(brandSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesBrand = brandArrayList.get(which);
                    brandTextView.setText(brandArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: Color
    @OnClick(R.id.add_clothes_color_text_view)
    void colorTextViewClicked()
    {
        ArrayList<String> colorArrayList = MyApplication.getConfig().getColorArrayList();
        ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colorArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Color")
                .setAdapter(colorSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesColor = colorArrayList.get(which);
                    colorTextView.setText(colorArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: gender
    @OnClick(R.id.add_clothes_gender_text_view)
    void genderTextViewClicked()
    {
        ArrayList<String> genderArrayList = MyApplication.getConfig().getClothesGenderArrayList();
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genderArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Gender")
                .setAdapter(genderSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesGender = genderArrayList.get(which);
                    genderTextView.setText(genderArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // general details: used status
    @OnClick(R.id.add_clothes_used_status_text_view)
    void usedStatusTextViewClicked()
    {
        ArrayList<String> usedStatusArrayList = MyApplication.getConfig().getUsedStatusArrayList();
        ArrayAdapter<String> usedStatusSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, usedStatusArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Usage Status")
                .setAdapter(usedStatusSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesUsedStatus = usedStatusArrayList.get(which);
                    usedStatusTextView.setText(usedStatusArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // Swap details: Type
    @OnClick(R.id.add_clothes_swap_condition_type_text_view)
    void swapConditionTypeTextViewClicked()
    {
        ArrayList<String> typeArrayList = MyApplication.getConfig().getWearingTypesNamesArrayList();
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Type")
                .setAdapter(typeSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType = typeArrayList.get(which);
                    if (swapConditionTypeTextView.getText() != null && !swapConditionTypeTextView.getText().toString().equalsIgnoreCase(typeArrayList.get(which)))
                    {
                        swapConditionTypeTextView.setText(typeArrayList.get(which));
                        AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionSize = null;
                        swapConditionSizeTextView.setText("Select Size Condition");
                    }

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // Swap details: Size
    @OnClick(R.id.add_clothes_swap_condition_size_text_view)
    void swapConditionSizeTextViewClicked()
    {
        if (AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType == null ||
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType.length() == 0)
        {
            DialogHelper.warnDialog(getActivity(), "Type is not selected", "First select type!");
            return;
        }

        ArrayList<String> sizeArrayList;

        if (AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType != null &&
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType.length() > 0 &&
                AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionType.equalsIgnoreCase("Shoes"))
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

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionSize = sizeArrayList.get(which);
                    swapConditionSizeTextView.setText(sizeArrayList.get(which));


                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // Swap details: Brand
    @OnClick(R.id.add_clothes_swap_condition_brand_text_view)
    void swapConditionbrandTextViewClicked()
    {
        ArrayList<String> brandArrayList = MyApplication.getConfig().getClothesBrandsArrayList();
        ArrayAdapter<String> brandSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, brandArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Brand")
                .setAdapter(brandSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionBrand = brandArrayList.get(which);
                    swapConditionBrandTextView.setText(brandArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }

    // Swap details: Color
    @OnClick(R.id.add_clothes_swap_condition_color_text_view)
    void swapConditionColorTextViewClicked()
    {
        ArrayList<String> colorArrayList = MyApplication.getConfig().getColorArrayList();
        ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, colorArrayList);

        new AlertDialog.Builder(getActivity())
                .setTitle("Select Color")
                .setAdapter(colorSpinnerAdapter, (dialog, which) -> {

                    AddClothesHelper.AddClothesDTO.toBeUploadedClothesSwapConditionColor = colorArrayList.get(which);
                    swapConditionColorTextView.setText(colorArrayList.get(which));

                    dialog.dismiss();
                }).setCancelable(true).create().show();
    }


    @OnCheckedChanged(R.id.add_clothes_sellable_checkbox)
    void _setSellableCheckbox(CompoundButton checkbox, boolean value)
    {
        if (!value && !lendableCheckbox.isChecked())
        {
            DialogHelper.warnDialog(getActivity(), "Invalid Selection", "At least one exchange method must be selected");
            sellableCheckbox.setChecked(true);
            return;
        }
        buyPriceTextInputLayout.setEnabled(value);
        AddClothesHelper.AddClothesDTO.clothesIsSellable = value;

    }


    @OnCheckedChanged(R.id.add_clothes_lendable_checkbox)
    void _setLendableCheckbox(CompoundButton checkbox, boolean value)
    {
        if (!value && !sellableCheckbox.isChecked())
        {
            DialogHelper.warnDialog(getActivity(), "Invalid Selection", "At least one exchange method must be selected");
            lendableCheckbox.setChecked(true);
            return;
        }
        rentPriceTextInputLayout.setEnabled(value);
        AddClothesHelper.AddClothesDTO.clothesIsRentable = value;

    }

    @OnCheckedChanged(R.id.add_clothes_swapable_checkbox)
    void _setSwapableCheckbox(CompoundButton checkbox, boolean value)
    {
        swapableCheckboxChanged(value);
    }

    private void swapableCheckboxChanged(boolean value)
    {
        AddClothesHelper.AddClothesDTO.clothesIsSwapable = value;
    }

    @OnClick({R.id.add_clothes_select_photo_0_image_view,
            R.id.add_clothes_select_photo_1_image_view,
            R.id.add_clothes_select_photo_2_image_view,
            R.id.add_clothes_select_photo_3_image_view,})
    void selectPhoto(View view)
    {
        displayChooseGalleryOrCameraDialog(view);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup
            container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.view_add_clothes, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initSellableAndRentableCheckBoxes();

        stepView.getState().stepsNumber(4).commit();

        initPricingTextWatchers();

    }

    private void initPricingTextWatchers()
    {
        buyPriceTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable != null)
                {
                    AddClothesHelper.AddClothesDTO.buyPrice = editable.toString();
                }
            }
        });

        rentPriceTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if (editable != null)
                {
                    AddClothesHelper.AddClothesDTO.rentPrice = editable.toString();
                }
            }
        });
    }


    private void selectedTypeFlagInitializer(Editable editable)
    {
        if (editable != null && editable.toString().length() > 0)
        {
            ArrayList<WearingType> wearingTypesArrayList = MyApplication.getConfig().getWearingTypesArrayList();
            for (int i = 0; i < wearingTypesArrayList.size(); i++)
            {
                if (editable.toString().equalsIgnoreCase(wearingTypesArrayList.get(i).name))
                {
                    enteredTypeWearingType = wearingTypesArrayList.get(i).bodyPart;
                }

            }
        }
    }

    private boolean enteredSizeIsValid(Editable editable)
    {
        if (enteredTypeWearingType == 0 || enteredTypeWearingType == 1 || enteredTypeWearingType == 2)
        {
            // wearing type is upper body or lower body or full body
            if (editable != null &&
                    editable.toString().length() > 0 &&
                    MyApplication.getConfig().getClothesSizeArrayList().contains(editable.toString()))
            {
                return true;
            }
        }
        else if (enteredTypeWearingType == 3)

        {
            // wearing type is foot wear

        }
        else

        {
            //  wearing type is accessories

        }
        return false;
    }


    private void initSellableAndRentableCheckBoxes()
    {
//        sellableCheckbox.setOnCheckedChangeListener((compoundButton, state) -> {
//            if (!state && !lendableCheckbox.isChecked())
//            {
//                sellableCheckbox.setChecked(true);
//                DialogHelper.showDialogWithMessage(getContext(), "At least one of Sellable/Rentable must be checked");
//            }
//        });
//
//        lendableCheckbox.setOnCheckedChangeListener((compoundButton, state) -> {
//            if (!state && !sellableCheckbox.isChecked())
//            {
//                lendableCheckbox.setChecked(true);
//                DialogHelper.showDialogWithMessage(getContext(), "At least one of Sellable/Rentable must be checked");
//            }
//        });
    }

    private void displayChooseGalleryOrCameraDialog(View view)
    {
        selectLatestImageViewClickedForUploading(view);

        String choiceString[] = new String[]{"Gallery", "Camera"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Select image from");
        dialog.setItems(choiceString,
                (dialog1, which) -> {
                    Intent intent;
                    if (which == 0)
                    {
                        intent = new Intent(
                                Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    else
                    {
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                    startActivityForResult(
                            Intent.createChooser(intent, "Select profile picture"), 1001);
                }).show();
    }

    private void selectLatestImageViewClickedForUploading(View view)
    {
        if (view != null && view.getTag() != null)
        {
            latestImageViewClickedForUploading = ((String) view.getTag());
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 1001 && resultCode == RESULT_OK && data != null)
        {

            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null)
            {
                // from gallery

                Uri imageUri = data.getData();
                try
                {
                    //getting bitmap object from uri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                    //displaying selected image to imageview
                    setImageToLatestImageViewSelected(bitmap);
//                    selectedPhoto0imageView.setImageBitmap(bitmap);

                    //calling the method uploadBitmap to upload image
//                uploadBitmap(bitmap);


                    // changing to File
                    File f = new File(getContext().getCacheDir(), "image0");
                    boolean newFile = f.createNewFile();

//Convert bitmap to byte array
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50 /*ignored for PNG*/, bos);
                    byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
                    FileOutputStream fos = new FileOutputStream(f);
                    fos.write(bitmapdata);
                    fos.flush();
                    fos.close();

//                    uploadFile(f);

                    //okhttp works only
                    uploadokhttp(f);
//                    uploadRetrofit(f);

//                uploadShit(f);
//                    uploadShitByPut(f);
//                uploafFateme(f);
//                    fuckUpload(f);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


            }
            else
            {
                // from camera

                Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                Cursor cursor = getActivity().getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED,
                                MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED,
                        null, "date_added DESC");
                if (cursor != null && cursor.moveToFirst())
                {
                    Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
                    String photoPath = uri.toString();
                    cursor.close();
                    if (photoPath != null)
                    {
                        bitmap = BitmapFactory.decodeFile(photoPath);
                    }
                }

                if (bitmap == null)
                {
                    // for safety reasons
                    bitmap = (Bitmap) data.getExtras().get("data");
                }

                setImageToLatestImageViewSelected(bitmap);

                try
                {
                    uploadokhttp(bitmapToFile(bitmap));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }


//                try
//                {
////                    fuckUpload(bitmapToFile(bitmap));
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }

            }

        }
    }

    private void setImageToLatestImageViewSelected(Bitmap bitmap)
    {
        switch (latestImageViewClickedForUploading)
        {
            case ("0"):
            {
                selectedPhoto0imageView.setImageBitmap(bitmap);
            }
            break;

            case ("1"):
            {
                selectedPhoto1imageView.setImageBitmap(bitmap);

            }
            break;
            case ("2"):
            {
                selectedPhoto2imageView.setImageBitmap(bitmap);

            }
            break;
            case ("3"):
            {
                selectedPhoto3imageView.setImageBitmap(bitmap);

            }
            break;
        }
    }

    private void onUserHasCapturedPhoto(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return;
        }

        //create a file to write bitmap data
        File f = new File(getContext().getCacheDir(), "image0");
        try
        {
            f.createNewFile();


            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }

    @OnClick(R.id.add_clothes_proceed_button)
    public void proceedButtonClicked()
    {

        // 0 -> General details
        // 1 -> Pricing details
        // 2 -> Swap details
        // 3 -> Image uploading

        switch (AddClothesHelper.AddClothesDTO.currentState)
        {
            case (0):
            {

                if (AddClothesHelper.generalDetailsValidation(getActivity()))
                {

                    goToLayout(1);
                    AddClothesHelper.AddClothesDTO.currentState = 1;
                }


                break;
            }

            case (1):
            {

                if (AddClothesHelper.pricingDetailsValidation(getActivity()))
                {
                    CommonHelper.hideKeyboard(getActivity(), getView());

                    AddClothesHelper.saveDescription(descriptionTextInputEditText);
                    goToLayout(2);
                    AddClothesHelper.AddClothesDTO.currentState = 2;
                }

                break;
            }
            case (2):
            {
                if (AddClothesHelper.swapDetailsValidation(getActivity()))
                {

                    uploadClothesInfoPart(AddClothesHelper.AddClothesDTO.getUploadClothesInfoPartRequest());

                    goToLayout(3);
                    AddClothesHelper.AddClothesDTO.currentState = 3;
                }

                break;
            }
            case (3):
            {
                if (AddClothesHelper.imageUploadValidation(getActivity()))
                {
                    Toast.makeText(getActivity(), "Successfully created!\n Add another?", Toast.LENGTH_LONG).show();
                    goToLayout(0);
                    AddClothesHelper.reset();
                    resetEverything();
                }
            }
        }

//                   if (detailPartIsValid())
//            {
//                if (!userHasHalfWayUploadedClothes())
//                {
//                    UploadClothesInfoPartRequest clothes = collectUserInputAsClothesToBeUploaded();
//                    addClothesDetailPart(clothes);
//                }
//                else
//                {
////              imagePartOfUploadClothes();
////              uploadImage(selectedPhoto0imageView.)


    }

    private void resetEverything()
    {
        typeTextView.setText("Select Type");
        sizeTextView.setText("Select Size");
        brandTextView.setText("Select Brand");
        colorTextView.setText("Select Color");
        genderTextView.setText("Select Gender");
        usedStatusTextView.setText("Select Status");

        buyPriceTextInputEditText.setText("");
        rentPriceTextInputEditText.setText("");

    }

    private void uploadClothesInfoPart(UploadClothesInfoPartRequest uploadClothesInfoPartRequest)
    {
        Call<UploadClothesInfoPartResponse> uploadClothesCall = NetworkManager.getInstance().getEndpointApi(Api.class).uploadClothes(uploadClothesInfoPartRequest);
        uploadClothesCall.enqueue(new Callback<UploadClothesInfoPartResponse>() {
            @Override
            public void onResponse(Call<UploadClothesInfoPartResponse> call, retrofit2.Response<UploadClothesInfoPartResponse> response)
            {
                if (response.isSuccessful() && response.body() != null)
                {
                    userHasUploadedClothesInfoPart(response.body());
                }
                else
                {
                    onUserHasFailedToAddClothesDetailPart(response);
                }

            }

            @Override
            public void onFailure(Call<UploadClothesInfoPartResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: uploadClothesInfoPart");
                onUserHasFailedToAddClothesDetailPart(t);


            }
        });
    }

    private void userHasUploadedClothesInfoPart(UploadClothesInfoPartResponse uploadClothesInfoPartResponse)
    {

        MyApplication.getSharedPreferencesManager().persist(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED, uploadClothesInfoPartResponse.getData().getId());
        MyApplication.getSharedPreferencesManager().persist(USER_HAS_HALF_WAY_UPLOADED_CLOTHES, true);
    }

    private void onUserHasFailedToAddClothesDetailPart(Throwable response)
    {
        onUserHasFailedToUploadClothesDetailPart();
    }

    private void onUserHasFailedToAddClothesDetailPart(retrofit2.Response<UploadClothesInfoPartResponse> t)
    {
        onUserHasFailedToUploadClothesDetailPart();

    }

    private void onUserHasFailedToUploadClothesDetailPart()
    {
        if (getView() != null && getView().getContext() != null)
        {
            DialogHelper.warnDialog(getView().getContext(), "Connection failed", "Pleas try again");
            goToLayout(2);
            AddClothesHelper.AddClothesDTO.currentState = 2;
        }
    }

    private void goToLayout(int index)
    {
        switch (index)
        {
            case 0:
            {
                stepView.go(0, true);

                generalDetailsScrollView.setVisibility(View.VISIBLE);
                pricingDetailsScrollView.setVisibility(View.GONE);
                swapDetailsScrollView.setVisibility(View.GONE);
                imageUploadScrollView.setVisibility(View.GONE);
                break;
            }
            case 1:
            {
                stepView.go(1, true);

                pricingDetailsScrollView.setVisibility(View.VISIBLE);
                generalDetailsScrollView.setVisibility(View.GONE);
                swapDetailsScrollView.setVisibility(View.GONE);
                imageUploadScrollView.setVisibility(View.GONE);
                break;
            }
            case 2:
            {
                stepView.go(2, true);

                swapDetailsScrollView.setVisibility(View.VISIBLE);
                generalDetailsScrollView.setVisibility(View.GONE);
                pricingDetailsScrollView.setVisibility(View.GONE);
                imageUploadScrollView.setVisibility(View.GONE);
                break;
            }

            case 3:
            {
                stepView.go(3, true);

                imageUploadScrollView.setVisibility(View.VISIBLE);
                generalDetailsScrollView.setVisibility(View.GONE);
                pricingDetailsScrollView.setVisibility(View.GONE);
                swapDetailsScrollView.setVisibility(View.GONE);
                break;
            }
        }
    }

    private boolean detailPartIsValid()
    {
//        if (!AddClothesHelper.isClothesTypeValid(typeTextInputEditText.getText()))
//        {
//            return false;
//        }
//        if (!AddClothesHelper.isClothesSizeValid(sizeTextInputEditText.getText()))
//        {
//            return false;
//        }
//        if (!AddClothesHelper.isClothesBrandValid(typeTextInputEditText.getText()))
//        {
//            return false;
//        }
//        if (!AddClothesHelper.isClothesColorValid(swapConditionDesiredColorEditText.getText()))
//        {
//            return false;
//        }
//
//        if (!AddClothesHelper.isClothesPriceValid(buyPriceTextInputEditText, rentPriceTextInputEditText))
//        {
//            return false;
//        }

        return true;
    }

    private UploadClothesInfoPartRequest collectUserInputAsClothesToBeUploaded()
    {
        UploadClothesInfoPartRequest uploadClothesInfoPartRequest = new UploadClothesInfoPartRequest();
//        uploadClothesInfoPartRequest.setType();

        return uploadClothesInfoPartRequest;

    }

    private void addClothesImagePart()
    {
        DialogHelper.showLoading(getContext());

        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);
//        RequestBody reqFile1 = RequestBody.create(MediaType.parse("image/jpeg"), file);
//        MultipartBody.Part part = MultipartBody.Part.createFormData("images[]", file.getName() + ".jpeg", reqFile1);
//        RequestBody methodType = RequestBody.create(MediaType.parse("text/plain"), "put");
//
//
//        Call<UploadClothesInfoPartResponse> addClothesResponseCall = endpointApi.imagePartOfUploadClothes(methodType, part, 5);
//        addClothesResponseCall.enqueue(new Callback<UploadClothesInfoPartResponse>() {
//            @Override
//            public void onResponse(Call<UploadClothesInfoPartResponse> call, Response<UploadClothesInfoPartResponse> response)
//            {
//                Log.d(TAG, "onResponse: " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<UploadClothesInfoPartResponse> call, Throwable t)
//            {
//                Log.d(TAG, "onFailure: " + t.getMessage());
//
//            }
//        });


    }


    //    private void uploadImage(byte[] fileByteArray)
    private void uploadImage(File file)
    {

//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            if (thumbnailBitmap != null)
//            {
//                thumbnailBitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
//            }
//
//            byte[] fileByteArray = byteArrayOutputStream.toByteArray();


        JSONObject paramsJsonObject = new JSONObject();

        try
        {
            paramsJsonObject.put("_method", "put");
            paramsJsonObject.put("images[]", file);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Url.BASE_URL + Url.CLOTHES + "/" + 22 + "", paramsJsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                Log.d(TAG, "onResponse: " + response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                Log.d(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "multipart/form-data");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk2ZjkyZWVhZWM0NTQ4ZWQ1NWQzOWNiZDZjNTY1OWU1MWI2ZDViZGJkNjViMDcwYTA3ODExYzg3YmVhNTFjNDhiYjg1NTc4ZDQ0OGIxOTI1In0.eyJhdWQiOiIxIiwianRpIjoiOTZmOTJlZWFlYzQ1NDhlZDU1ZDM5Y2JkNmM1NjU5ZTUxYjZkNWJkYmQ2NWIwNzBhMDc4MTFjODdiZWE1MWM0OGJiODU1NzhkNDQ4YjE5MjUiLCJpYXQiOjE1NDA5MTc2MDUsIm5iZiI6MTU0MDkxNzYwNSwiZXhwIjoxNTcyNDUzNjA1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.p_feRe1Yh0n_z64scsFCJRmbvdmY9JUqwChqanHXueB_Ieddts41MyHD-PJYe96xCDchIE8Q3bnc0rxfFonjlz19jFRe7Hy_f2wbscQ5eStS6kb5ZiRk9qHIeTilBPmPpzRRE5O3rgwvaPpZ_TzrEVyMthyBgFIDxPtH-WY7gGLPYx30vfM75yKw7RvqScsIp2C-D2o4pMi-u9SqadD7EwQUAr-8amQ6uCnQhwJGV_vJZbdHxq2bfCxX4SQkJCaRBO5ke7WZTWc25LoRHgf12YXTHX_-R-Ef_SwUIpQlBKZd4B-IkqSDJZG2fPQMdr2st9Fn83gDp7Gl00r2s_xOYftfIMspD7deDNFUFe-3_lRFkY9t0yZi7M4uePXOsOVJhLbFEOahlx6hzFhPWEUcxEP15HO8clfOg6ThLIo3N0Hfq_fGvR08OKBFf41XZboJjilhy-U6Ucqg73N-CyZtP4kDEoH23RUJ4xomhBrimD7nwvoltDefjh-wOFRiJfPgOZYZWoMWUmjGGMkZqtWTk-Omjtm-qYuIMv7rISwyICY3LboFisc9VZP9NRSOtuQFr8INbBdl9Z7-7oSzkx3QDxG0w2fh69TuWTjkAlbVUbKkpG-CEZ-64XlTIeXNHScOu0lUfb2Wi-PRaz_zujnR8rLJFEiGGywWzPkS9mfEBBA");
                return headers;
            }


        };


        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(jsonObjectRequest);
        queue.add(jsonObjectRequest);


    }

    private void onAddClothesRequestFailed()
    {
        Log.d(TAG, "onAddClothesRequestFailed: ");

    }

    private void onUserHasAddedClothesDetailPart(String clothesId)
    {
        persistIdOfClothesWhichItsDetailPartHasBeenUploaded(clothesId);
        generalDetailsScrollView.setVisibility(View.GONE);
        imageUploadScrollView.setVisibility(View.VISIBLE);
    }

    private void persistIdOfClothesWhichItsDetailPartHasBeenUploaded(String clothesId)
    {
        MyApplication.getSharedPreferencesManager().persist(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED, clothesId);
        MyApplication.getSharedPreferencesManager().persist(USER_HAS_HALF_WAY_UPLOADED_CLOTHES, true);
    }


    private boolean userHasHalfWayUploadedClothes()
    {
        if (MyApplication.getSharedPreferencesManager().read(USER_HAS_HALF_WAY_UPLOADED_CLOTHES) instanceof Boolean)
        {
            return ((Boolean) MyApplication.getSharedPreferencesManager().read(USER_HAS_HALF_WAY_UPLOADED_CLOTHES));
        }
        else
        {
            return false;
        }
    }


    public void setClothesPhoto(Bitmap bitmap)
    {
        if (selectedPhotosAsBitmap == null)
        {
            selectedPhotosAsBitmap = new ArrayList<>();
        }
        selectedPhotosAsBitmap.add(bitmap);

        displayInRelatedImageView();
    }

    private void displayInRelatedImageView()
    {
        if (selectedPhotosAsBitmap == null || selectedPhotosAsBitmap.size() == 0)
        {
            return;
        }
        if (selectedPhotosAsBitmap.size() == 1)
        {
            selectedPhoto0imageView.setImageBitmap(selectedPhotosAsBitmap.get(0));
        }
        else if (selectedPhotosAsBitmap.size() == 2)
        {
            selectedPhoto1imageView.setImageBitmap(selectedPhotosAsBitmap.get(1));
        }
        else if (selectedPhotosAsBitmap.size() == 3)
        {
            selectedPhoto2imageView.setImageBitmap(selectedPhotosAsBitmap.get(2));
        }

    }

    private void uploadBitmap(Bitmap bitmap)
    {

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT, Url.BASE_URL + Url.CLOTHES + "/24",
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response)
                    {
                        try
                        {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("_method", "put");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData()
            {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
//                params.put("images[]", new DataPart(imagename + ".jpeg", getFileDataFromDrawable(bitmap), "image/jpeg"));
                params.put("images[]", new DataPart("file_cover.jpeg", getFileDataFromDrawable(getContext(), selectedPhoto0imageView.getDrawable()), "image/jpeg"));

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("Accept", "application/json");
                headers.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk2ZjkyZWVhZWM0NTQ4ZWQ1NWQzOWNiZDZjNTY1OWU1MWI2ZDViZGJkNjViMDcwYTA3ODExYzg3YmVhNTFjNDhiYjg1NTc4ZDQ0OGIxOTI1In0.eyJhdWQiOiIxIiwianRpIjoiOTZmOTJlZWFlYzQ1NDhlZDU1ZDM5Y2JkNmM1NjU5ZTUxYjZkNWJkYmQ2NWIwNzBhMDc4MTFjODdiZWE1MWM0OGJiODU1NzhkNDQ4YjE5MjUiLCJpYXQiOjE1NDA5MTc2MDUsIm5iZiI6MTU0MDkxNzYwNSwiZXhwIjoxNTcyNDUzNjA1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.p_feRe1Yh0n_z64scsFCJRmbvdmY9JUqwChqanHXueB_Ieddts41MyHD-PJYe96xCDchIE8Q3bnc0rxfFonjlz19jFRe7Hy_f2wbscQ5eStS6kb5ZiRk9qHIeTilBPmPpzRRE5O3rgwvaPpZ_TzrEVyMthyBgFIDxPtH-WY7gGLPYx30vfM75yKw7RvqScsIp2C-D2o4pMi-u9SqadD7EwQUAr-8amQ6uCnQhwJGV_vJZbdHxq2bfCxX4SQkJCaRBO5ke7WZTWc25LoRHgf12YXTHX_-R-Ef_SwUIpQlBKZd4B-IkqSDJZG2fPQMdr2st9Fn83gDp7Gl00r2s_xOYftfIMspD7deDNFUFe-3_lRFkY9t0yZi7M4uePXOsOVJhLbFEOahlx6hzFhPWEUcxEP15HO8clfOg6ThLIo3N0Hfq_fGvR08OKBFf41XZboJjilhy-U6Ucqg73N-CyZtP4kDEoH23RUJ4xomhBrimD7nwvoltDefjh-wOFRiJfPgOZYZWoMWUmjGGMkZqtWTk-Omjtm-qYuIMv7rISwyICY3LboFisc9VZP9NRSOtuQFr8INbBdl9Z7-7oSzkx3QDxG0w2fh69TuWTjkAlbVUbKkpG-CEZ-64XlTIeXNHScOu0lUfb2Wi-PRaz_zujnR8rLJFEiGGywWzPkS9mfEBBA");
                return headers;

            }
        };
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

    public File bitmapToFile(Bitmap bitmap) throws IOException
    {
        //create a file to write bitmap data
        File f = new File(getContext().getCacheDir(), "filename.jpeg");
        f.createNewFile();

//Convert bitmap to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
        return f;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] getFileDataFromDrawable(Context context, int id)
    {
        Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * Turn drawable into byte array.
     *
     * @param drawable data
     * @return byte array
     */
    public static byte[] getFileDataFromDrawable(Context context, Drawable drawable)
    {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    public void uploadokhttp(File file)
    {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(59, TimeUnit.SECONDS)
                .writeTimeout(59, TimeUnit.SECONDS)
                .readTimeout(59, TimeUnit.SECONDS)
                .followRedirects(false)
                .followSslRedirects(false)
                .proxy(null)
                .cache(null)
                .addInterceptor(logging)
                .build();

        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings)
            {
                ((MainActivity) getActivity()).showLoading();


                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("images[]", "image.jpeg",
                                RequestBody.create(MediaType.parse("multipart/form-data"), file))
//                        .addFormDataPart("_method", "put")
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .addHeader("Authorization", "Bearer " + MyApplication.getSharedPreferencesManager().read("token"))
                        .addHeader("Content-Type", "multipart/form-data")
                        .addHeader("Accept", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Connection", "close")
                        .addHeader("User-Agent", "Sharona")
                        .url(Url.BASE_URL + Url.CLOTHES + "/" + MyApplication.getSharedPreferencesManager().read(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED)).post(formBody).build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e)
                    {
                        ((MainActivity) getActivity()).hideLoading();

                        Log.d(TAG, "onFailure: ");
                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException
                    {
                        ((MainActivity) getActivity()).hideLoading();

                        Log.d(TAG, "onResponse: ");
                        AddClothesHelper.AddClothesDTO.numberOfUploadedImages = AddClothesHelper.AddClothesDTO.numberOfUploadedImages + 1;
                    }
                });

                return null;
            }

        };


        asyncTask.execute("Q11", null, null);

    }

    public void uploadRetrofit(File file)
    {
        Integer id;

        if (MyApplication.getSharedPreferencesManager().read(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED) != null &&
                MyApplication.getSharedPreferencesManager().read(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED) instanceof Integer)
        {
            id = (Integer) MyApplication.getSharedPreferencesManager().read(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED);
        }
        else
        {
            return;
        }

        RequestBody formBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("images[]", "image.jpeg",
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
//                        .addFormDataPart("_method", "put")
                .build();


        MultipartBody.Part requestBodyPart = MultipartBody.Part.create(RequestBody.create(MediaType.parse("multipart/form-data"), file));

        Call<UploadClothesInfoPartResponse> imagePartCall = NetworkManager.getInstance().getEndpointApi(Api.class).
                imagePartOfUploadClothes(formBody, requestBodyPart, (Integer) MyApplication.getSharedPreferencesManager().read(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED));
        imagePartCall.enqueue(new Callback<UploadClothesInfoPartResponse>() {
            @Override
            public void onResponse(Call<UploadClothesInfoPartResponse> call, retrofit2.Response<UploadClothesInfoPartResponse> response)
            {
                Log.d(TAG, "onResponse: ");
            }

            @Override
            public void onFailure(Call<UploadClothesInfoPartResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: ");

            }
        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

