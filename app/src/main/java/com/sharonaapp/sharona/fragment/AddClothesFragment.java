package com.sharonaapp.sharona.fragment;

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
import android.support.constraint.ConstraintLayout;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sharonaapp.sharona.BackButtonClickListenerImpl;
import com.sharonaapp.sharona.MyApplication;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.helper.AddClothesHelper;
import com.sharonaapp.sharona.model.WearingType;
import com.sharonaapp.sharona.network.AddClothesRequest;
import com.sharonaapp.sharona.network.AddClothesResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;
import com.sharonaapp.sharona.network.Url;
import com.sharonaapp.sharona.network.VolleyMultipartRequest;
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
import java.util.List;
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

import static android.app.Activity.RESULT_OK;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED;
import static com.sharonaapp.sharona.manager.SharedPreferencesManager.USER_HAS_HALF_WAY_UPLOADED_CLOTHES;
import static com.sharonaapp.sharona.network.NetworkManager.getToken;


public class AddClothesFragment extends Fragment {

    private static final String TAG = "AddClothesFragment";

    private Unbinder unbinder;

    //type
    @BindView(R.id.add_clothes_type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.add_clothes_type_text_input_layout)
    TextInputLayout typeTextInputLayout;
    @BindView(R.id.add_clothes_type_text_input_edit_text)
    AutoCompleteTextView typeTextInputEditText;
    //size
    @BindView(R.id.add_clothes_size_spinner)
    Spinner sizeSpinner;
    @BindView(R.id.add_clothes_size_text_input_layout)
    TextInputLayout sizeTextInputLayout;
    @BindView(R.id.add_clothes_size_text_input_edit_text)
    AutoCompleteTextView sizeTextInputEditText;
    //brand
    @BindView(R.id.add_clothes_brand_spinner)
    Spinner brandSpinner;
    @BindView(R.id.add_clothes_brand_text_input_layout)
    TextInputLayout brandTextInputLayout;
    @BindView(R.id.add_clothes_brand_text_input_edit_text)
    AutoCompleteTextView brandTextInputEditText;
    //color
    @BindView(R.id.add_clothes_color_spinner)
    Spinner colorSpinner;
    @BindView(R.id.add_clothes_color_text_input_layout)
    TextInputLayout colorTextInputLayout;
    @BindView(R.id.add_clothes_color_text_input_edit_text)
    AutoCompleteTextView colorTextInputEditText;

    //buy price
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
    @BindView(R.id.add_clothes_swap_condition_type_text_input_layout)
    TextInputLayout swapConditionDesiredTypeInputLayout;
    @BindView(R.id.add_clothes_swap_condition_type_text_input_edit_text)
    TextInputEditText swapConditionDesiredTypeEditText;
    @BindView(R.id.add_clothes_swap_condition_size_text_input_layout)
    TextInputLayout swapConditionDesiredSizeInputLayout;
    @BindView(R.id.add_clothes_swap_condition_size_text_input_edit_text)
    TextInputEditText swapConditionDesiredSizeEditText;
    @BindView(R.id.add_clothes_swap_condition_color_text_input_layout)
    TextInputLayout swapConditionDesiredColorInputLayout;
    @BindView(R.id.add_clothes_swap_condition_color_text_input_edit_text)
    TextInputEditText swapConditionDesiredColorEditText;
    @BindView(R.id.add_clothes_swap_condition_brand_text_input_layout)
    TextInputLayout swapConditionDesiredBrandInputLayout;
    @BindView(R.id.add_clothes_swap_condition_brand_text_input_edit_text)
    TextInputEditText swapConditionDesiredBrandEditText;

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
    @BindView(R.id.add_clothes_submit_button)
    Button submit_button;

    @BindView(R.id.add_clothes_details_layout)
    ConstraintLayout detailsLayout;
    @BindView(R.id.add_clothes_image_upload_layout)
    ConstraintLayout imageUploadLayout;

    ArrayList<Bitmap> selectedPhotosAsBitmap;
    private byte[] multipartBody;
    private String latestImageViewClickedForUploading;

    @BindView(R.id.add_clothes_sellable_checkbox)
    AppCompatCheckBox sellableCheckbox;
    @BindView(R.id.add_clothes_lendable_checkbox)
    AppCompatCheckBox lendableCheckbox;

    @BindView(R.id.step_view)
    StepView stepView;
    private int enteredTypeWearingType = -1;


    @OnCheckedChanged(R.id.add_clothes_sellable_checkbox)
    void _setSellableCheckbox(CompoundButton checkbox, boolean value)
    {
        sellableCheckboxChanged(value);
    }

    private void sellableCheckboxChanged(boolean value)
    {
        buyPriceTextInputLayout.setEnabled(value);
    }

    @OnCheckedChanged(R.id.add_clothes_lendable_checkbox)
    void _setLendableCheckbox(CompoundButton checkbox, boolean value)
    {
        lendableCheckboxChanged(value);
    }

    private void lendableCheckboxChanged(boolean value)
    {
        rentPriceTextInputLayout.setEnabled(value);
    }

    @OnCheckedChanged(R.id.add_clothes_swapable_checkbox)
    void _setSwapableCheckbox(CompoundButton checkbox, boolean value)
    {
        swapableCheckboxChanged(value);
    }

    private void swapableCheckboxChanged(boolean value)
    {
        swapConditionDesiredTypeInputLayout.setEnabled(value);
        swapConditionDesiredSizeInputLayout.setEnabled(value);
        swapConditionDesiredColorInputLayout.setEnabled(value);
        swapConditionDesiredBrandInputLayout.setEnabled(value);
        if (value)
        {
        }
        else
        {
        }
    }

    @OnClick({R.id.add_clothes_select_photo_0_image_view,
            R.id.add_clothes_select_photo_1_image_view,
            R.id.add_clothes_select_photo_2_image_view})
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
        ((MainActivity) getActivity()).setOnBackPressedListener(new BackButtonClickListenerImpl(getActivity()), this);

        initSellableAndRentableCheckBoxes();
        initAutoTextViewAdapters(view);
        initTextWatchers();
        initSpinners();

        stepView.getState().stepsNumber(5).commit();


    }

    private void initSpinners()
    {
        // type
        List<String> wearingTypesNamesArrayList = MyApplication.getConfig().getWearingTypesNamesArrayList();
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, wearingTypesNamesArrayList);
        typeSpinner.setPrompt("Type");
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeSpinnerAdapter);

        // size
        ArrayList<String> clothesSizeArrayList = MyApplication.getConfig().getClothesSizeArrayList();
        ArrayAdapter<String> sizeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, clothesSizeArrayList);
        sizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sizeSpinner.setPrompt("Size");
        sizeSpinner.setAdapter(sizeSpinnerAdapter);

        // brand
        ArrayList<String> clothesBrandsArrayList = MyApplication.getConfig().getClothesBrandsArrayList();
        ArrayAdapter<String> brandSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, clothesBrandsArrayList);
        brandSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandSpinner.setPrompt("Brand");
        brandSpinner.setAdapter(brandSpinnerAdapter);

        // color
        ArrayList<String> colorArrayList = MyApplication.getConfig().getColorArrayList();
        ArrayAdapter<String> colorSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, colorArrayList);
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setPrompt("Color");
        colorSpinner.setAdapter(colorSpinnerAdapter);

    }

    private void initAutoTextViewAdapters(@NonNull View view)
    {
        if (MyApplication.getConfig() == null)
        {
            return;
        }

        ArrayList<String> clothesTypesArrayList = MyApplication.getConfig().getWearingTypesNamesArrayList();
        ArrayList<String> clothesBrandsArrayList = MyApplication.getConfig().getClothesBrandsArrayList();
        ArrayList<String> clothesSizeArrayList = MyApplication.getConfig().getClothesSizeArrayList();

        ArrayAdapter<String> typesSuggestionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, clothesTypesArrayList);
        ArrayAdapter<String> brandsSuggestionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, clothesBrandsArrayList);
        ArrayAdapter<String> sizesSuggestionAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, clothesSizeArrayList);

        typeTextInputEditText.setAdapter(typesSuggestionAdapter);
        brandTextInputEditText.setAdapter(brandsSuggestionAdapter);
        sizeTextInputEditText.setAdapter(sizesSuggestionAdapter);
    }

    private void initTextWatchers()
    {
        typeTextInputEditText.addTextChangedListener(new TextWatcher() {
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
                if (enteredTypeIsValid(editable))
                {
                    selectedTypeFlagInitializer(editable);
                    activate(sizeTextInputEditText);
                }
                else
                {
                    disable(sizeTextInputEditText);
                }
            }
        });

        sizeTextInputEditText.addTextChangedListener(new TextWatcher() {
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
                if (enteredSizeIsValid(editable))
                {
                    activate(brandTextInputEditText);
                }
                else
                {
                    disable(brandTextInputEditText);
                }
            }
        });

        brandTextInputEditText.addTextChangedListener(new TextWatcher() {
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

    private void disable(AutoCompleteTextView textInputEditText)
    {
        textInputEditText.setText("");
        textInputEditText.setEnabled(false);

    }

    private void activate(AutoCompleteTextView textInputEditText)
    {
        textInputEditText.setEnabled(true);
    }

    private boolean enteredTypeIsValid(Editable editable)
    {
//        if (editable != null && MyApplication.getConfig().getWearingTypesArrayList().contains(editable.toString()))
        {
            return true;
        }
//        else
//        {
//            return false;
//        }

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
//                            File f = new File(android.os.Environment.getExternalStorageDirectory(), "imagename.jpg");
//                            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
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
//                    setImageToLatestImageViewSelected(bitmap);
                    selectedPhoto0imageView.setImageBitmap(bitmap);

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
                    uploadokhttp(f);
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
                    // for safety reasons you can
                    // use thumbnail if not retrieved full sized image
                    bitmap = (Bitmap) data.getExtras().get("data");
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

    @OnClick(R.id.add_clothes_submit_button)
    public void submit()
    {

        if (detailPartIsValid())
        {
            if (!userHasHalfWayUploadedClothes())
            {
                AddClothesRequest clothes = collectUserInputAsClothesToBeUploaded();
                addClothesDetailPart(clothes);
            }
            else
            {
//              addClothesImagePart();
//              uploadImage(selectedPhoto0imageView.)
            }
        }


    }

    private boolean detailPartIsValid()
    {
        if (!AddClothesHelper.isClothesTypeValid(typeTextInputEditText.getText()))
        {
            return false;
        }
        if (!AddClothesHelper.isClothesSizeValid(sizeTextInputEditText.getText()))
        {
            return false;
        }
        if (!AddClothesHelper.isClothesBrandValid(typeTextInputEditText.getText()))
        {
            return false;
        }
        if (!AddClothesHelper.isClothesColorValid(swapConditionDesiredColorEditText.getText()))
        {
            return false;
        }

        if (!AddClothesHelper.isClothesPriceValid(buyPriceTextInputEditText, rentPriceTextInputEditText))
        {
            return false;
        }

        return true;
    }

    private AddClothesRequest collectUserInputAsClothesToBeUploaded()
    {
        AddClothesRequest addClothesRequest = new AddClothesRequest();
//        addClothesRequest.setType();

        return addClothesRequest;

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
//        Call<AddClothesResponse> addClothesResponseCall = endpointApi.addClothesImagePart(methodType, part, 5);
//        addClothesResponseCall.enqueue(new Callback<AddClothesResponse>() {
//            @Override
//            public void onResponse(Call<AddClothesResponse> call, Response<AddClothesResponse> response)
//            {
//                Log.d(TAG, "onResponse: " + response.body());
//            }
//
//            @Override
//            public void onFailure(Call<AddClothesResponse> call, Throwable t)
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

    void addClothesDetailPartWithReftrofit()
    {
        DialogHelper.showLoading(getContext());

//        Call<AddClothesResponse> addClothesCall = NetworkManager.getInstance().getEndpointApi(Api.class).addClothes();

    }


    private void addClothesDetailPart(AddClothesRequest addClothesRequest)
    {

        DialogHelper.showLoading(getContext());

        JSONObject paramsJsonObject = new JSONObject();

        try
        {
            paramsJsonObject.put("title", addClothesRequest.getTitle());
            paramsJsonObject.put("brand", addClothesRequest.getBrand());
            paramsJsonObject.put("type", addClothesRequest.getType());
            paramsJsonObject.put("size", addClothesRequest.getSize());
            paramsJsonObject.put("color", addClothesRequest.getColor());
            paramsJsonObject.put("buy_price", addClothesRequest.getBuyPrice());
            paramsJsonObject.put("rent_price", addClothesRequest.getRentPrice());
            paramsJsonObject.put("description", addClothesRequest.getDescription());
            paramsJsonObject.put("sellable", addClothesRequest.isSellable());
            paramsJsonObject.put("lendable", addClothesRequest.isLendable());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url.ADD_CLOTHES, paramsJsonObject,
                response -> {
                    Log.d(TAG, "addClothesDetailPart: " + response);
                    DialogHelper.hideLoading();
                    try
                    {
                        if (response.has("success") && response.getBoolean("success"))
                        {
                            onUserHasAddedClothesDetailPart(response.getJSONObject("data").getString("id"));
                        }
                        else
                        {
                            onAddClothesRequestFailed();
                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }, error -> {
            DialogHelper.hideLoading();
//            onUserHasFailedToAddClothesDetailPart(t);
            Log.d(TAG, "addClothesDetailPart: " + error);
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError
            {

                HashMap<String, String> headerHashMap = new HashMap<>();
                headerHashMap.put("Authorization", "Bearer " + getToken());
                headerHashMap.put("Content-Type", "application/json");
                headerHashMap.put("Accept", "application/json");

                return headerHashMap;
            }

            @Override
            public String getBodyContentType()
            {
                return "application/json";
            }
        };

        queue.add(jsonObjectRequest);

    }

    private void onAddClothesRequestFailed()
    {
        Log.d(TAG, "onAddClothesRequestFailed: ");

    }

    private void onUserHasAddedClothesDetailPart(String clothesId)
    {
        persistIdOfClothesWhichItsDetailPartHasBeenUploaded(clothesId);
        detailsLayout.setVisibility(View.GONE);
        imageUploadLayout.setVisibility(View.VISIBLE);
    }

    private void persistIdOfClothesWhichItsDetailPartHasBeenUploaded(String clothesId)
    {
        MyApplication.getSharedPreferencesManager().persist(CLOTHES_ID_WHICH_ITS_DETAIL_PART_HAS_BEEN_UPLOADED, clothesId);
        MyApplication.getSharedPreferencesManager().persist(USER_HAS_HALF_WAY_UPLOADED_CLOTHES, true);
    }

    private void onUserHasFailedToAddClothesDetailPart
            (Response<AddClothesResponse> response)
    {
        detailsLayout.setVisibility(View.VISIBLE);
        imageUploadLayout.setVisibility(View.GONE);
    }

    private void onUserHasFailedToAddClothesDetailPart(Throwable t)
    {
        detailsLayout.setVisibility(View.VISIBLE);
        imageUploadLayout.setVisibility(View.GONE);

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

//        RequestBody requestBodyid = RequestBody.create(MediaType.parse("multipart/form-data"), id);
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

        AsyncTask<String, Void, Void> asyncTask = new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings)
            {
                RequestBody formBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("images[]", "image.jpeg",
                                RequestBody.create(MediaType.parse("multipart/form-data"), file))
//                        .addFormDataPart("_method", "put")
                        .build();
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijk2ZjkyZWVhZWM0NTQ4ZWQ1NWQzOWNiZDZjNTY1OWU1MWI2ZDViZGJkNjViMDcwYTA3ODExYzg3YmVhNTFjNDhiYjg1NTc4ZDQ0OGIxOTI1In0.eyJhdWQiOiIxIiwianRpIjoiOTZmOTJlZWFlYzQ1NDhlZDU1ZDM5Y2JkNmM1NjU5ZTUxYjZkNWJkYmQ2NWIwNzBhMDc4MTFjODdiZWE1MWM0OGJiODU1NzhkNDQ4YjE5MjUiLCJpYXQiOjE1NDA5MTc2MDUsIm5iZiI6MTU0MDkxNzYwNSwiZXhwIjoxNTcyNDUzNjA1LCJzdWIiOiIxIiwic2NvcGVzIjpbXX0.p_feRe1Yh0n_z64scsFCJRmbvdmY9JUqwChqanHXueB_Ieddts41MyHD-PJYe96xCDchIE8Q3bnc0rxfFonjlz19jFRe7Hy_f2wbscQ5eStS6kb5ZiRk9qHIeTilBPmPpzRRE5O3rgwvaPpZ_TzrEVyMthyBgFIDxPtH-WY7gGLPYx30vfM75yKw7RvqScsIp2C-D2o4pMi-u9SqadD7EwQUAr-8amQ6uCnQhwJGV_vJZbdHxq2bfCxX4SQkJCaRBO5ke7WZTWc25LoRHgf12YXTHX_-R-Ef_SwUIpQlBKZd4B-IkqSDJZG2fPQMdr2st9Fn83gDp7Gl00r2s_xOYftfIMspD7deDNFUFe-3_lRFkY9t0yZi7M4uePXOsOVJhLbFEOahlx6hzFhPWEUcxEP15HO8clfOg6ThLIo3N0Hfq_fGvR08OKBFf41XZboJjilhy-U6Ucqg73N-CyZtP4kDEoH23RUJ4xomhBrimD7nwvoltDefjh-wOFRiJfPgOZYZWoMWUmjGGMkZqtWTk-Omjtm-qYuIMv7rISwyICY3LboFisc9VZP9NRSOtuQFr8INbBdl9Z7-7oSzkx3QDxG0w2fh69TuWTjkAlbVUbKkpG-CEZ-64XlTIeXNHScOu0lUfb2Wi-PRaz_zujnR8rLJFEiGGywWzPkS9mfEBBA")
//                        .addHeader("Content-Type", "application/x-www-form-urlencode")
                        .addHeader("Content-Type", "multipart/form-data")
                        .addHeader("Accept", "application/json")
                        .addHeader("cache-control", "no-cache")
                        .addHeader("Connection", "close")
                        .addHeader("User-Agent", "Sharona")
                        .url(Url.BASE_URL + Url.CLOTHES + "/133").post(formBody).build();
                try
                {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.d(TAG, "doInBackground: " + response);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                return null;
            }

        };


        asyncTask.execute("Q11", null, null);

    }


    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

