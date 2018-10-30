package com.appestan.sharona.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.appestan.sharona.BackButtonClickListenerImpl;
import com.appestan.sharona.MainActivity;
import com.appestan.sharona.Network.AddClothesImagePartRequest;
import com.appestan.sharona.Network.AddClothesRequest;
import com.appestan.sharona.Network.AddClothesResponse;
import com.appestan.sharona.Network.Api;
import com.appestan.sharona.Network.NetworkManager;
import com.appestan.sharona.R;
import com.appestan.sharona.Utility.CommonHelper;
import com.appestan.sharona.Utility.DialogHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class AddClothesFragment extends Fragment {

    private static final String TAG = "AddClothesFragment";

    private Unbinder unbinder;
    @BindView(R.id.add_clothes_username_edit_text)
    EditText usernameEditText;
    @BindView(R.id.add_clothes_type_edit_text)
    EditText typeEditText;
    @BindView(R.id.add_clothes_brand_edit_text)
    EditText brandEditText;
    @BindView(R.id.add_clothes_size_edit_text)
    EditText sizeEditText;
    @BindView(R.id.add_clothes_original_price_edit_text)
    EditText originalPriceEditText;
    @BindView(R.id.add_clothes_rental_price_edit_text)
    EditText rentalEditText;
    @BindView(R.id.add_clothes_description_edit_text)
    EditText descriptionEditText;
    @BindView(R.id.add_clothes_select_photo_0_image_view)
    ImageView selectedPhoto0imageView;
    @BindView(R.id.add_clothes_select_photo_1_image_view)
    ImageView selectedPhoto1imageView;
    @BindView(R.id.add_clothes_select_photo_2_image_view)
    ImageView selectedPhoto2imageView;
    @BindView(R.id.add_clothes_submit_button)
    Button submit_button;

    @BindView(R.id.add_clothes_status_text_view)
    TextView statusTextView;


    @BindView(R.id.add_clothes_details_layout)
    ConstraintLayout detailsLayout;
    @BindView(R.id.add_clothes_image_upload_layout)
    ConstraintLayout imageUploadLayout;

    ArrayList<Bitmap> selectedPhotosAsBitmap;
    private boolean detailPart = true;

    @OnClick({R.id.add_clothes_select_photo_0_image_view,
            R.id.add_clothes_select_photo_1_image_view,
            R.id.add_clothes_select_photo_2_image_view})
    void selectPhoto()
    {
        displayChooseGalleryOrCameraDialog();
    }

    private void displayChooseGalleryOrCameraDialog()
    {
        String choiceString[] = new String[]{"Gallery", "Camera"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setTitle("Select image from");
        dialog.setItems(choiceString,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
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
                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null)  // image selected from gallery
                {
                    String imagePath = CommonHelper.getRealPathFromURI(getContext(), selectedImageUri);

                    Bitmap bitmap = null;
                    try
                    {
                        bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), selectedImageUri);
                        setClothesPhoto(bitmap);


                        //create a file to write bitmap data
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

                        try
                        {
                            File file = new Compressor(getContext()).compressToFile(f);
                            addClothesImagePart(file);
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }







                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else     // image captured from camera
                {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                    Bitmap bitmap = null;
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

                    setClothesPhoto(bitmap);
                    onUserHasCapturedPhoto(bitmap);


                    File f = new File(Environment.getExternalStorageDirectory().toString());
                    for (File temp : f.listFiles())
                    {
//                        if (temp.getName().equals("imagename.jpg"))
                        {
                            f = temp;
                            try
                            {
                                File file = new Compressor(getContext()).compressToFile(f);
                                addClothesImagePart(file);
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }


                }
            }
            else
            {
                Log.d("==>", "Operation canceled!");
            }
        }
    }

    private void onUserHasCapturedPhoto(Bitmap bitmap)
    {
        if (bitmap == null)
        {
            return;
        }

        //create a file to write bitmap data
//        File f = new File(getContext().getCacheDir(), "image0");
//        f.createNewFile();
//
////Convert bitmap to byte array
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
//        byte[] bitmapdata = bos.toByteArray();
//
////write the bytes in file
//        FileOutputStream fos = new FileOutputStream(f);
//        fos.write(bitmapdata);
//        fos.flush();
//        fos.close();
//
//
//        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), bitmap);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("photo", "image0", requestFile);
//
//        RequestBody requestBodyid = RequestBody.create(MediaType.parse("multipart/form-data"), id);
//
//
//        Call<AddClothesResponse> addClothesResponseCall = NetworkManager.getInstance().getEndpointApi(Api.class).addClothesImagePart(image, id);


    }

    @OnClick(R.id.add_clothes_submit_button)
    public void submit()
    {

//        detailsLayout.setVisibility(View.GONE);
//        imageUploadLayout.setVisibility(View.VISIBLE);
        if (detailPart)
        {
            detailPart = false;

            AddClothesRequest clothes = new AddClothesRequest();
            clothes.setBrand("Ape");
            clothes.setColor("Red");
            clothes.setType("Hoody");
            clothes.setSize("32");
            clothes.setBuyPrice(111);
            clothes.setRentPrice(34);
            clothes.setDescription("its fantastic as new");


            addClothesDetailPart(clothes);

        }
        else
        {

//            addClothesImagePart(file);

        }

        // TODO: 10/5/18 uncomment the following block, commented to test the button for photo selecting
        // TODO: 9/27/18 should gather data from edittext(s), create clothes, and send to server
//        Editable usernameEditTextText = usernameEditText.getText();
//        Editable typeEditTextText = typeEditText.getText();
//        Editable brandEditTextText = brandEditText.getText();
//
//        if (usernameEditTextText != null && typeEditTextText != null && brandEditTextText != null)
//        {
//            Toast.makeText(getContext(),
//                    "" + usernameEditTextText.toString() + ": " + typeEditTextText.toString() + ", " + brandEditTextText.toString(),
//                    Toast.LENGTH_SHORT).show();
//
//            Clothes clothes = new Clothes();
//            clothes.setUserName(usernameEditTextText.toString());
//            clothes.setType(typeEditTextText.toString());
//            clothes.setBrand(brandEditTextText.toString());
//
//        }
    }

    private void addClothesImagePart(File file)
    {
//        DialogHelper.showLoading(getContext());

        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);

        RequestBody reqFile1 = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("images[]", file.getName() + ".jpeg", reqFile1);
        RequestBody methodType = RequestBody.create(MediaType.parse("text/plain"),"put");


        Call<AddClothesResponse> addClothesResponseCall = endpointApi.addClothesImagePart(methodType, part, 5);
        addClothesResponseCall.enqueue(new Callback<AddClothesResponse>() {
            @Override
            public void onResponse(Call<AddClothesResponse> call, Response<AddClothesResponse> response)
            {
                Log.d(TAG, "onResponse: " + response.body());
            }

            @Override
            public void onFailure(Call<AddClothesResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: " + t.getMessage());

            }
        });

    }

    private void addClothesDetailPart(AddClothesRequest addClothesRequest)
    {

        DialogHelper.showLoading(getContext());

        Api endpointApi = NetworkManager.getInstance().getEndpointApi(Api.class);
        Call<AddClothesResponse> addClothesResponseCall = endpointApi.addClothes(addClothesRequest);
        addClothesResponseCall.enqueue(new Callback<AddClothesResponse>() {
            @Override
            public void onResponse(Call<AddClothesResponse> call, Response<AddClothesResponse> response)
            {
                Log.d(TAG, "onResponse: " + response.body());
                DialogHelper.hideLoading();
                onUserHasTriedToAddClothesDetailPart(response);
            }

            @Override
            public void onFailure(Call<AddClothesResponse> call, Throwable t)
            {
                Log.d(TAG, "onFailure: " + t.getMessage());
                DialogHelper.hideLoading();
                onUserHasFailedToAddClothesDetailPart(t);
            }
        });
    }

    private void onUserHasTriedToAddClothesDetailPart(Response<AddClothesResponse> response)
    {
        if (response == null)
        {
            onUserHasFailedToAddClothesDetailPart(response);
            return;
        }

        if (response.body() != null && response.code() == 201)
        {
            Log.d(TAG, "onResponse: " + response.body().toString());

            onUserHasAddedClothesDetailPart(response);
        }
    }

    private void onUserHasAddedClothesDetailPart(Response<AddClothesResponse> response)
    {
        detailPart = false;
        statusTextView.setText("OK!");
        detailsLayout.setVisibility(View.GONE);
        imageUploadLayout.setVisibility(View.VISIBLE);
    }

    private void onUserHasFailedToAddClothesDetailPart(Response<AddClothesResponse> response)
    {
        detailPart = true;
        statusTextView.setText("FAILED");
        detailsLayout.setVisibility(View.VISIBLE);
        imageUploadLayout.setVisibility(View.GONE);
    }

    private void onUserHasFailedToAddClothesDetailPart(Throwable t)
    {
        detailPart = true;
        statusTextView.setText("FAILED");
        detailsLayout.setVisibility(View.VISIBLE);
        imageUploadLayout.setVisibility(View.GONE);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
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


    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
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
}
