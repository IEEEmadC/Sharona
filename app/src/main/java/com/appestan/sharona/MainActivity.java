package com.appestan.sharona;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appestan.sharona.Adapter.MainPagerAdapter;
import com.appestan.sharona.CustomUI.MainTabBar;
import com.appestan.sharona.CustomUI.NonSwipeableViewPager;
import com.appestan.sharona.Fragment.AddClothesFragment;
import com.appestan.sharona.Fragment.ClothesInnerFragment;
import com.appestan.sharona.Fragment.ExploreFragment;
import com.appestan.sharona.Fragment.HostFragment;
import com.appestan.sharona.Fragment.InboxOutBoxFragment;
import com.appestan.sharona.Fragment.LoginFragment;
import com.appestan.sharona.Fragment.MyClosetFragment;
import com.appestan.sharona.Fragment.ProfileFragment;
import com.appestan.sharona.Fragment.SearchFragment;
import com.appestan.sharona.Fragment.SettingFragment;
import com.appestan.sharona.Fragment.SignUpFragment;
import com.appestan.sharona.Helper.LocaleHelper;
import com.appestan.sharona.Interface.BackButtonClickListener;
import com.appestan.sharona.Managers.SharedPreferencesManager;
import com.appestan.sharona.Model.Clothes;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.ronash.pushe.Pushe;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.appestan.sharona.Helper.LocaleHelper.LOCALE_ENGLISH;
import static com.appestan.sharona.Helper.LocaleHelper.LOCALE_PERSIAN;
import static com.appestan.sharona.Managers.CallManager.MY_PERMISSIONS_REQUEST_CALL_PHONE;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = "MainActivity";


    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1000;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1002;
    public static final int LOGIN_SET = 2000;

    private int activeTab;
    public static final int TAB_ADD_CLOTHES_INDEX = 0;
    public static final int TAB_EXPLORE_INDEX = 1;
    public static final int TAB_CLOTHES_INNER_INDEX = 2;
    public static final int TAB_LOGIN = 3;

    private ExploreFragment exploreFragment;
    private AddClothesFragment addClothesFragment;
    private LoginFragment loginFragment;
    private SignUpFragment signUpFragment;
    private MyClosetFragment myClosetFragment;
    private ProfileFragment profileFragment;
    private FragmentTransaction ft;

    MainPagerAdapter mainPagerAdapter;

    @BindView(R.id.activity_main_non_swipeable_view_pager)
    NonSwipeableViewPager nonSwipeableViewPager;

    @BindView(R.id.tab_layout)
    MainTabBar mainTabBar;
    private BackButtonClickListener backButtonClickListener;
    private Fragment fragment;

/*    @OnClick(R.id.bottom_navigation_item_2)
    public void item_2_browse_clicked()
    {
        findViewById(R.id.bottom_navigation_item_0).setSelected(false);
        findViewById(R.id.bottom_navigation_item_1).setSelected(false);
        findViewById(R.id.bottom_navigation_item_2).setSelected(true);

        findViewById(R.id.bottom_navigation_item_0_title).setVisibility(View.GONE);
        findViewById(R.id.bottom_navigation_item_1_title).setVisibility(View.GONE);
        findViewById(R.id.bottom_navigation_item_2_title).setVisibility(View.VISIBLE);

        Clothes clothes = new Clothes();
        clothes.setType("Socks");
        clothes.setBrand("Adidas");
        clothes.setColor("White");
        clothes.setSize("-1");
        clothes.setOriginalPrice("300");
        clothes.setRentalPrice("80");

        Bundle bundle = new Bundle();
        bundle.putSerializable("CLOTHES_TO_BE_SHOWN", clothes);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ClothesInnerFragment fragment = new ClothesInnerFragment();
        fragment.setArguments(bundle);

        ft.add(R.id.activity_fragment_place_holder_frame_layout, fragment).addToBackStack(null);
        ft.commit();
    }*/


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState)
    {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Pushe.initialize(this, true);

        LocaleHelper.changeAppLocale(this, LOCALE_ENGLISH);
        LocaleHelper.changeLayoutDirectionBasedOnLocale(getWindow().getDecorView());


        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        nonSwipeableViewPager.setAdapter(mainPagerAdapter);
        nonSwipeableViewPager.addOnPageChangeListener(this);
        nonSwipeableViewPager.setOffscreenPageLimit(3);
        mainTabBar.setViewPager(nonSwipeableViewPager);

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout()
            {
                if (savedInstanceState == null)
                {
                    nonSwipeableViewPager.setCurrentItem(activeTab, false);
//                    handleIntent();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                {
                    getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }

            }
        });

        checkPermissions();


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
        if (requestCode == 1001)
        {
            if (resultCode == RESULT_OK && data != null)
            {
                Uri selectedImageUri = data.getData();
                if (selectedImageUri != null)  // image selected from gallery
                {
                    String imagePath = getRealPathFromURI(this, selectedImageUri);
                }
                else     // image captured from camera
                {
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");

                    Bitmap bitmap = null;
                    Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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

                    addClothesFragment.setClothesPhoto(bitmap);

                }
            }
            else
            {
                Log.d("==>", "Operation canceled!");
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_settings:
            {
                routeToSettingFragment();
                break;
            }
            case R.id.action_login:
            {
                routeToLoginFragment();
                break;
            }

            case R.id.action_sign_up:
            {
                routeToSignUpFragment();
                break;
            }

            case R.id.action_profile:
            {
                routeToProfileFragment();
                break;
            }
            case R.id.action_my_closet:
            {
                routeToMyClosetFragment();
                break;
            }
            case R.id.action_inbox_outbox:
                routeToInboxOutBoxFragment();
                break;
            case R.id.action_search:
                routeToSearchFragment();
            default:
                break;
        }
        return true;
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
        openFragmentInOverLay(new LoginFragment());
    }

    public void routeToSignUpFragment()
    {
        openFragmentInOverLay(new SignUpFragment());
    }


    private void routeToMyClosetFragment()
    {
        openFragmentInOverLay(new MyClosetFragment());
    }

    private void routeToInboxOutBoxFragment()
    {
        openFragmentInOverLay(new InboxOutBoxFragment());
    }

    private void routeToSearchFragment()
    {
        openFragmentInOverLay(new SearchFragment());
    }

    private void routeToProfileFragment()
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
        activeTab = position;

        HostFragment hostFragment = (HostFragment) mainPagerAdapter.getItem(position);
        if (!hostFragment.hasContent())
        {
            openFragmentNoTransition(mainPagerAdapter.getDefaultFragmentForTab(position));
        }
        else
        {
        }

    }

    @Override
    public void onPageScrollStateChanged(int i)
    {

    }

    public MainPagerAdapter getMainPagerAdapter()
    {
        return mainPagerAdapter;
    }

    public void openFragmentInOverLay(Fragment fragment) throws IllegalStateException
    {
        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        mainTabBar.setVisibility(View.GONE);
    }

    public void openFragment(Fragment fragment) throws IllegalStateException
    {
        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        mainTabBar.setVisibility(View.VISIBLE);

    }

    public void openFragmentNoTransition(Fragment fragment) throws IllegalStateException
    {
        FragmentManager childFragmentManager = mainPagerAdapter.getItem(activeTab).getChildFragmentManager();
        FragmentTransaction fragmentTransaction = childFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_base_fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
        mainTabBar.setVisibility(View.VISIBLE);

    }

    public void setOnBackPressedListener(BackButtonClickListener backButtonClickListener, Fragment fragment)
    {
        this.backButtonClickListener = backButtonClickListener;
        this.fragment = mainPagerAdapter.getItem(activeTab);
    }

    public void setOnBackPressedListener(BackButtonClickListener backButtonClickListener)
    {
        this.backButtonClickListener = backButtonClickListener;
        this.fragment = mainPagerAdapter.getItem(activeTab);
    }

    @Override
    public void onBackPressed()
    {
        mainTabBar.setVisibility(View.VISIBLE);
        getSupportActionBar().show();

        if (backButtonClickListener != null
                && fragment != null
                && mainPagerAdapter.getItem(activeTab).isAdded()
                && mainPagerAdapter.getItem(activeTab).getChildFragmentManager() != null
                && mainPagerAdapter.getItem(activeTab).getChildFragmentManager().getBackStackEntryCount() > 1)
        {
            backButtonClickListener.onBackButtonClicked(fragment);
        }
        else
        {
            super.onBackPressed();
        }
    }
}
