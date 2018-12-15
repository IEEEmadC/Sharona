package com.sharonaapp.sharona.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.fragment.ExploreFragment;
import com.sharonaapp.sharona.manager.LoginLogoutStateManager;
import com.sharonaapp.sharona.model.general.Clothes;

import java.io.ByteArrayOutputStream;

import androidx.navigation.NavController;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static androidx.navigation.Navigation.findNavController;
import static com.sharonaapp.sharona.manager.CallManager.MY_PERMISSIONS_REQUEST_CALL_PHONE;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1002;
    public static final int LOGIN_SET = 2000;

    public static final int TAB_LOGIN = 3;


    @BindView(R.id.toolbar)
    public ConstraintLayout toolbarLayout;

    @BindView(R.id.main_activity_loading_view)
    public FrameLayout loadingLayout;
    @BindView(R.id.loading_spin_kit)
    public SpinKitView loadingSpinKit;

    public NavController addClothesNavController;
    public NavController exploreNavController;
    public NavController mapNavController;
    public NavController myClosetNavController;
    public NavController menuOriginatedNavController;

    @BindView(R.id.main_activity_bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.activity_main_add_clothes_nav_host_fragment)
    public View addClothesNavHostFragment;
    @BindView(R.id.activity_main_explore_nav_host_fragment)
    View exploreNavHostFragment;
    @BindView(R.id.activity_main_map_nav_host_fragment)
    View mapNavHostFragment;
    @BindView(R.id.activity_main_my_closet_nav_host_fragment)
    View myClosetNavHostFragment;
    @BindView(R.id.activity_main_menu_originated_items_nav_host_fragment)
    View menuOriginatedItemsNavHostFragment;


    @BindView(R.id.bmb)
    public BoomMenuButton boomMenuButton;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        addClothesNavHostFragment.setVisibility(View.GONE);
        menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);
        mapNavHostFragment.setVisibility(View.GONE);
        myClosetNavHostFragment.setVisibility(View.GONE);

        addClothesNavController = findNavController(this, R.id.activity_main_add_clothes_nav_host_fragment);
        exploreNavController = findNavController(this, R.id.activity_main_explore_nav_host_fragment);
        mapNavController = findNavController(this, R.id.activity_main_map_nav_host_fragment);
        myClosetNavController = findNavController(this, R.id.activity_main_my_closet_nav_host_fragment);
        menuOriginatedNavController = findNavController(this, R.id.activity_main_menu_originated_items_nav_host_fragment);


//        exploreNavController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
//            {
//                if(!isInStack(destination.getId()))
//                {
//                    destinationList.add(destination.getId());
//                }
//            }
//        });
        //setupWithNavController(bottomNavigationView, exploreNavController);

//        exploreNavController.navigate();


//        LocaleHelper.changeAppLocale(this, LOCALE_ENGLISH);
//        LocaleHelper.changeAppLocaleFromSharedPrefIfNeeded(this, true);
//        LocaleHelper.changeLayoutDirectionBasedOnLocale(getWindow().getDecorView());

        bottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId())
            {
                case (R.id.item_add_clothes):
                {
                    if (!LoginLogoutStateManager.getInstance().isUserLogedIn())
                    {
                        Toast.makeText(this, "Please Login or Sign up first!", Toast.LENGTH_SHORT).show();
                        routeToLoginFragment();
                        return false;
                    }
                    addClothesNavHostFragment.setVisibility(View.VISIBLE);
                    exploreNavHostFragment.setVisibility(View.GONE);
                    mapNavHostFragment.setVisibility(View.GONE);
                    myClosetNavHostFragment.setVisibility(View.GONE);
                    menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);
                    return true;
                }
                case (R.id.item_explore):
                {
                    exploreNavHostFragment.setVisibility(View.VISIBLE);
                    addClothesNavHostFragment.setVisibility(View.GONE);
                    mapNavHostFragment.setVisibility(View.GONE);
                    myClosetNavHostFragment.setVisibility(View.GONE);
                    menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);
                    return true;
                }

                case (R.id.item_map):
                {
                    mapNavHostFragment.setVisibility(View.VISIBLE);
                    exploreNavHostFragment.setVisibility(View.GONE);
                    addClothesNavHostFragment.setVisibility(View.GONE);
                    myClosetNavHostFragment.setVisibility(View.GONE);
                    menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);

                    return true;
                }

                case (R.id.item_closet):
                {
                    if (!LoginLogoutStateManager.getInstance().isUserLogedIn())
                    {
                        Toast.makeText(this, "Please Login or Sign up first!", Toast.LENGTH_SHORT).show();
                        routeToLoginFragment();
                        return false;
                    }

                    myClosetNavHostFragment.setVisibility(View.VISIBLE);
                    mapNavHostFragment.setVisibility(View.GONE);
                    exploreNavHostFragment.setVisibility(View.GONE);
                    addClothesNavHostFragment.setVisibility(View.GONE);
                    menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);

                    return true;
                }
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.item_explore);


        checkPermissions();

        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "onCreate: Refresh: " + token);

//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        if (auth.getCurrentUser() != null)
//        {
//            // already signed in
//            Log.d(TAG, "onCreate: already signed in");
//
//
//
//
//
//        }
//        else
//        {
//            // not signed in
//            Log.d(TAG, "onCreate: not signed in");
//        }

//        startActivityForResult(
//                // Get an instance of AuthUI based on the default app
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(Arrays.asList(
//                                new AuthUI.IdpConfig.GoogleBuilder().build()))
//                        .setTosAndPrivacyPolicyUrls("https://superapp.example.com/terms-of-service.html",
//                                "https://superapp.example.com/privacy-policy.html")
//                        .setIsSmartLockEnabled(false)
//                        .build(),
//                RC_SIGN_IN);

        initMenu();


    }


    private void initMenu()
    {

        HamButton.Builder loginBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_login)
                .normalText("Login")
                .subNormalText("Login to your account")
                .normalColor(R.color.colorPrimary);

        HamButton.Builder signUpBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_sign_up)
                .normalText("Sign up")
                .subNormalText("Create account")
                .pieceColor(R.color.colorPrimary);

        HamButton.Builder searchBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_search_24dp)
                .normalText("Search")
                .subNormalText("Search through clothes").highlightedColor(R.color.colorPrimary);

        HamButton.Builder profileBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_profile_24dp)
                .normalText("Profile")
                .subNormalText("Profile details");

        HamButton.Builder inboxOutboxBuilder = new HamButton.Builder()
                .normalImageRes(R.drawable.ic_inbox_outbox)
                .normalText("Inbox-Outbox")
                .subNormalText("Check your requests & requests sent to you");

        boomMenuButton.addBuilder(loginBuilder);
        boomMenuButton.addBuilder(signUpBuilder);
        boomMenuButton.addBuilder(searchBuilder);
        boomMenuButton.addBuilder(profileBuilder);
        boomMenuButton.addBuilder(inboxOutboxBuilder);

        boomMenuButton.setNormalColor(R.color.colorPrimary);


        boomMenuButton.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton)
            {
                switch (index)
                {
                    case 0:
                    {
                        menuOriginatedNavController.navigate(R.id.item_login);

                        setupVisibilityForMenuOriginatedItems();

                        break;
                    }
                    case 1:
                    {
                        menuOriginatedNavController.navigate(R.id.item_sign_up);

                        setupVisibilityForMenuOriginatedItems();
                        break;
                    }
                    case 2:
                    {
                        menuOriginatedNavController.navigate(R.id.item_search);

                        setupVisibilityForMenuOriginatedItems();

                        break;
                    }
                    case 3:
                    {
                        menuOriginatedNavController.navigate(R.id.item_profile);

                        setupVisibilityForMenuOriginatedItems();

                        break;
                    }

                    case 4:
                    {
                        menuOriginatedNavController.navigate(R.id.item_inbox_outbox);

                        setupVisibilityForMenuOriginatedItems();

                        break;
                    }


                }
            }

            @Override
            public void onBackgroundClick()
            {

            }

            @Override
            public void onBoomWillHide()
            {

            }

            @Override
            public void onBoomDidHide()
            {

            }

            @Override
            public void onBoomWillShow()
            {

            }

            @Override
            public void onBoomDidShow()
            {

            }
        });
    }

    private void setupVisibilityForMenuOriginatedItems()
    {
        menuOriginatedItemsNavHostFragment.setVisibility(View.VISIBLE);
        addClothesNavHostFragment.setVisibility(View.GONE);
        exploreNavHostFragment.setVisibility(View.GONE);
        mapNavHostFragment.setVisibility(View.GONE);
        myClosetNavHostFragment.setVisibility(View.GONE);
    }

    @Override
    protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void checkPermissions()
    {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            }
            else
            {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else
        {
            // Permission has already been granted
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
            {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                }
                else
                {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_CALL_PHONE:
            {

                return;
            }
            case MY_PERMISSIONS_REQUEST_LOCATION:
            {

                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == 12301)
        {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK)
            {
//                startActivity(SignedInActivity.createIntent(this, response));
                Log.d(TAG, "onActivityResult: Successfully signed in");
                finish();
            }
            else
            {
                // Sign in failed
                if (response == null)
                {
                    // User pressed back button
//                    showSnackbar(R.string.sign_in_cancelled);
                    Log.d(TAG, "onActivityResult: User pressed back button");
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK)
                {
//                    showSnackbar(R.string.no_internet_connection);
                    Log.d(TAG, "onActivityResult: no_internet_connection");
                    return;
                }

//                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }


//        if (requestCode == 1001)
//        {
//            if (resultCode == RESULT_OK && data != null)
//            {
//                Uri selectedImageUri = data.getClothesInner();
//                if (selectedImageUri != null)  // image selected from gallery
//                {
//                    String imagePath = getRealPathFromURI(this, selectedImageUri);
//                }
//                else     // image captured from camera
//                {
////                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//
//                    Bitmap bitmap = null;
//                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                            new String[]{MediaStore.Images.Media.DATA, MediaStore.Images.Media.DATE_ADDED,
//                                    MediaStore.Images.ImageColumns.ORIENTATION}, MediaStore.Images.Media.DATE_ADDED,
//                            null, "date_added DESC");
//                    if (cursor != null && cursor.moveToFirst())
//                    {
//                        Uri uri = Uri.parse(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
//                        String photoPath = uri.toString();
//                        cursor.close();
//                        if (photoPath != null)
//                        {
//                            bitmap = BitmapFactory.decodeFile(photoPath);
//                        }
//                    }
//
//                    if (bitmap == null)
//                    {
//                        // for safety reasons you can
//                        // use thumbnail if not retrieved full sized image
//                        bitmap = (Bitmap) data.getExtras().get("data");
//                    }
//
//                    addClothesFragment.setClothesPhoto(bitmap);
//
//                }
//            }
//            else
//            {
//                Log.d("==>", "Operation canceled!");
//            }
//        }

    }

    /**
     * get actual path from uri
     *
     * @param context    context
     * @param contentUri uri
     * @return actual path
     */
    public static String getRealPathFromURI(Context context, Uri contentUri)
    {
        Cursor cursor = null;
        try
        {
            String[] projection = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, projection, null, null, null);

            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        finally
        {
            if (cursor != null)
            {
                cursor.close();
            }
        }
    }


    public void routeToClothesInnerFragment(ExploreFragment initiatorFragment, Clothes clothes)
    {

//        Bundle bundle = new Bundle();
//        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);


//        ClothesInnerFragment fragment = new ClothesInnerFragment();
//        fragment.setArguments(bundle);

    }

    public void routeToLoginFragment()
    {
        menuOriginatedNavController.navigate(R.id.item_login);
        setupVisibilityForMenuOriginatedItems();

    }

    public void routeToSignUpFragment()
    {
        menuOriginatedNavController.navigate(R.id.item_sign_up);
        setupVisibilityForMenuOriginatedItems();

    }


    public void routeExploreFragment()
    {
        exploreNavController.navigate(R.id.item_explore);

        exploreNavHostFragment.setVisibility(View.VISIBLE);
        addClothesNavHostFragment.setVisibility(View.GONE);
        menuOriginatedItemsNavHostFragment.setVisibility(View.GONE);
    }


    public byte[] getFileDataFromDrawable(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void hideToolbar()
    {
        toolbarLayout.setVisibility(View.GONE);
    }

    public void showLoading()
    {
        if (loadingLayout != null)
        {
            loadingLayout.setVisibility(View.VISIBLE);
            loadingSpinKit.setVisibility(View.VISIBLE);
        }
    }

    public void hideLoading()
    {
        if (loadingLayout != null)
        {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {

                    // Stuff that updates the UI
                    loadingLayout.setVisibility(View.GONE);
                    loadingSpinKit.setVisibility(View.GONE);

                }
            });

        }
    }
//
//    @Override
//    public void onBackPressed()
//    {
//        if (!exploreNavController.navigateUp()){
//            super.onBackPressed();
//        }
////        if(mainPagerAdapter.getActiveFragment.getBackStackEntryCount() == 0) {
////            super.onBackPressed();
////        }
////        else {
////            getFragmentManager().popBackStack();
////        }
//    }

}


