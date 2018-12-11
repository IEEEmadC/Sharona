package com.sharonaapp.sharona.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.iid.FirebaseInstanceId;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.SimpleCircleButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.adapter.MainPagerAdapter;
import com.sharonaapp.sharona.custom_ui.MenuDialog;
import com.sharonaapp.sharona.fragment.AddClothesFragment;
import com.sharonaapp.sharona.fragment.ClothesInnerFragment;
import com.sharonaapp.sharona.fragment.ExploreFragment;
import com.sharonaapp.sharona.fragment.InboxOutboxFragment;
import com.sharonaapp.sharona.fragment.LoginFragment;
import com.sharonaapp.sharona.fragment.MyClosetFragment;
import com.sharonaapp.sharona.fragment.ProfileFragment;
import com.sharonaapp.sharona.fragment.SearchFragment;
import com.sharonaapp.sharona.fragment.SettingFragment;
import com.sharonaapp.sharona.fragment.SignUpFragment;
import com.sharonaapp.sharona.interfaces.BackButtonClickListener;
import com.sharonaapp.sharona.model.response.Clothes;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.FragmentNavigator;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static androidx.navigation.Navigation.findNavController;
import static androidx.navigation.ui.NavigationUI.setupWithNavController;
import static com.sharonaapp.sharona.manager.CallManager.MY_PERMISSIONS_REQUEST_CALL_PHONE;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";

//    private static final int RC_SIGN_IN = 123;


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1002;
    public static final int LOGIN_SET = 2000;

    private int activeTab;
    public static final int TAB_ADD_CLOTHES_INDEX = 0;
    public static final int TAB_EXPLORE_INDEX = 1;
    public static final int TAB_MY_CLOSET = 2;
    public static final int TAB_LOGIN = 3;

    private ExploreFragment exploreFragment;
    private AddClothesFragment addClothesFragment;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    private MyClosetFragment myClosetFragment;
    private ProfileFragment profileFragment;
    private FragmentTransaction ft;
    private List<Integer> destinationList = new ArrayList<>();

//    MainPagerAdapter mainPagerAdapter;

//    @BindView(R.id.activity_main_frame_layout)
//    public FrameLayout frameLayout;

//    @BindView(R.id.tab_layout)
//    public MainTabBar mainTabBar;
//    private BackButtonClickListener backButtonClickListener;
//    private Fragment fragment;

    @BindView(R.id.toolbar)
    public ConstraintLayout toolbarLayout;
//    private Resources resourcesForApplication;

    @BindView(R.id.main_activity_bottom_navigation_view)
    BottomNavigationView bottomNavigationView;
    private MainPagerAdapter mainPagerAdapter;

    private NavController navController;
    private NavController addClothesNavController;

//     ;

    @BindView(R.id.activity_main_add_clothes_nav_host_fragment)
    View addClothesNavHostFragment;
    @BindView(R.id.activity_main_nav_host_fragment)
    View mainNavHostFragment;


    @BindView(R.id.bmb)
    BoomMenuButton boomMenuButton;


    @OnClick(R.id.toolbar_menu_text_view_container_layout)
    public void onToolbarMenuClicked()
    {
        new MenuDialog(this).show();
    }


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

//        addClothesNavHostFragment = findViewById(R.id.activity_main_add_clothes_nav_host_fragment);


        navController = findNavController(this, R.id.activity_main_nav_host_fragment);
        addClothesNavController = findNavController(this, R.id.activity_main_add_clothes_nav_host_fragment);

//        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
//            @Override
//            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments)
//            {
//                if(!isInStack(destination.getId()))
//                {
//                    destinationList.add(destination.getId());
//                }
//            }
//        });
        //setupWithNavController(bottomNavigationView, navController);

//        navController.navigate();


//        LocaleHelper.changeAppLocale(this, LOCALE_ENGLISH);
//        LocaleHelper.changeAppLocaleFromSharedPrefIfNeeded(this, true);
//        LocaleHelper.changeLayoutDirectionBasedOnLocale(getWindow().getDecorView());

//        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), frameLayout);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case (R.id.item_add_clothes):
                    {
                        Log.d(TAG, "onNavigationItemSelected: ADD");
                        //mainPagerAdapter.switchTab(0);

//                        navigateTo(R.id.item_add_clothes);
                        addClothesNavHostFragment.setVisibility(View.VISIBLE);
                        mainNavHostFragment.setVisibility(View.GONE);

//                        getSupportFragmentManager().beginTransaction().add(frameLayout.getId(), mainPagerAdapter.getDefaultFragmentForTab(0)).commit();
                        return true;
                    }
                    case (R.id.item_explore):
                    {
                        Log.d(TAG, "onNavigationItemSelected: Explore");
                        //mainPagerAdapter.switchTab(1);

//                        navigateTo(R.id.item_explore);
                        mainNavHostFragment.setVisibility(View.VISIBLE);
                        addClothesNavHostFragment.setVisibility(View.GONE);
                        return true;
                    }

                    case (R.id.item_closet):
                    {
                        Log.d(TAG, "onNavigationItemSelected: Closet");
//                        mainPagerAdapter.switchTab(2);

                        navigateTo(R.id.item_closet);
                        return true;
                    }
                }
                return false;
            }
        });

//        nonSwipeableViewPager.setAdapter(mainPagerAdapter);
//        nonSwipeableViewPager.addOnPageChangeListener(this);
//        nonSwipeableViewPager.setOffscreenPageLimit(3);
//        mainTabBar.setViewPager(nonSwipeableViewPager);
//
//        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout()
//            {
//                if (savedInstanceState == null)
//                {
//                    nonSwipeableViewPager.setCurrentItem(activeTab, false);
////                    handleIntent();
//                }
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                {
//                    getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                }
//
//            }
//        });

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


//        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
//            SimpleCircleButton.Builder builder = new SimpleCircleButton.Builder()
//                    .normalImageRes(R.drawable.ic_close);
//            boomMenuButton.addBuilder(builder);
//        }

        for (int i = 0; i < boomMenuButton.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalImageRes(R.drawable.ic_close)
                    .normalTextRes(R.string.app_name)
                    .subNormalTextRes(R.string.channel_name);
            boomMenuButton.addBuilder(builder);
        }



    }

    private boolean isInStack(int destinationId)
    {
        boolean isDestinationInStack = false;
        for (Integer id : destinationList)
        {
            if(id == destinationId)
            {
                isDestinationInStack = true;
                break;
            }
        }
        return isDestinationInStack;
    }

    private void navigateTo(int destinationId)
    {
        if(isInStack(destinationId))
        {
            navController.popBackStack(destinationId, false);
        }
        else
        {
            navController.navigate(destinationId);
        }
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
        if (initiatorFragment == null)
        {
            ft = getSupportFragmentManager().beginTransaction();
        }
        else
        {
            ft = initiatorFragment.getParentFragment().getChildFragmentManager().beginTransaction();
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);


        ClothesInnerFragment fragment = new ClothesInnerFragment();
        fragment.setArguments(bundle);
        ft.replace(R.id.fragment_base_fragment_container, fragment);
        ft.commit();
    }

    public void routeToSettingFragment()
    {
        openFragmentInOverLay(new SettingFragment());
    }

    public void routeToLoginFragment()
    {
//        mainPagerAdapter.addFragmentOnTop(new LoginFragment());
//        openFragmentInOverLay(new LoginFragment());
        navController.navigate(R.id.item_add_clothes);
    }

    public void routeToSignUpFragment()
    {
        openFragmentInOverLay(new SignUpFragment());
    }

    private void routeToMyClosetFragment()
    {
        openFragmentInOverLay(new MyClosetFragment());
    }

    public void routeToInboxOutBoxFragment()
    {
        openFragmentInOverLay(new InboxOutboxFragment());
    }

    public void routeToSearchFragment()
    {
        openFragmentInOverLay(new SearchFragment());
    }

    public void routeToProfileFragment()
    {
        openFragmentInOverLay(new ProfileFragment());
    }

    public void routeExploreFragment()
    {
        openFragment(new ExploreFragment());
    }


    @Override
    public void onPageScrolled(int i, float v, int i1)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
//        activeTab = position;
//
//        HostFragment hostFragment = (HostFragment) mainPagerAdapter.getItem(position);
//        if (!hostFragment.hasContent())
//        {
//            openFragmentNoTransition(mainPagerAdapter.getDefaultFragmentForTab(position));
//        }
//        else
//        {
//        }

    }

    @Override
    public void onPageScrollStateChanged(int i)
    {

    }

//    public MainPagerAdapter getMainPagerAdapter()
//    {
//        return mainPagerAdapter;
//    }

    public void openFragmentInOverLay(Fragment fragment) throws IllegalStateException
    {
//        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//        mainTabBar.setVisibility(View.GONE);
    }

    public void openFragment(Fragment fragment) throws IllegalStateException
    {
//        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
//        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//        mainTabBar.setVisibility(View.VISIBLE);

    }

    public void openFragmentNoTransition(Fragment fragment) throws IllegalStateException
    {
//        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commitAllowingStateLoss();
//        mainTabBar.setVisibility(View.VISIBLE);

    }

    public void setOnBackPressedListener(BackButtonClickListener backButtonClickListener, Fragment fragment)
    {
//        this.backButtonClickListener = backButtonClickListener;
//        this.fragment = mainPagerAdapter.getItem(activeTab);
    }

    public void setOnBackPressedListener(BackButtonClickListener backButtonClickListener)
    {
//        this.backButtonClickListener = backButtonClickListener;
//        this.fragment = mainPagerAdapter.getItem(activeTab);
    }

//    @Override
//    public void onBackPressed()
//    {
//        mainTabBar.setVisibility(View.VISIBLE);
//        toolbarLayout.setVisibility(View.VISIBLE);
//
//        if (backButtonClickListener != null
//                && fragment != null
//                && mainPagerAdapter.getItem(activeTab).isAdded()
//                && mainPagerAdapter.getItem(activeTab).getChildFragmentManager() != null
//                && mainPagerAdapter.getItem(activeTab).getChildFragmentManager().getBackStackEntryCount() > 1)
//        {
//            backButtonClickListener.onBackButtonClicked(fragment);
//        }
//        else
//        {
//            super.onBackPressed();
//        }
//    }


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

    public void hideActiveTab()
    {
//        ((SmoothTransition) mainPagerAdapter.getItem(activeTab)).hide();
//        switch (activeTab){
//            case (TAB_EXPLORE_INDEX):{
//
//            }break;
//            case (TAB_ADD_CLOTHES_INDEX):{
//
//            }break;
//        }
    }

    @Override
    public void onBackPressed() {
//        if(mainPagerAdapter.getActiveFragment.getBackStackEntryCount() == 0) {
//            super.onBackPressed();
//        }
//        else {
//            getFragmentManager().popBackStack();
//        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
    }
}


