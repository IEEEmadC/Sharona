package com.sharonaapp.sharona.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.sharonaapp.sharona.R;
import com.sharonaapp.sharona.activity.MainActivity;
import com.sharonaapp.sharona.model.request.GeoClothesRequest;
import com.sharonaapp.sharona.model.response.GeoClothesResponse;
import com.sharonaapp.sharona.network.Api;
import com.sharonaapp.sharona.network.NetworkManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.sharonaapp.sharona.activity.MainActivity.MY_PERMISSIONS_REQUEST_LOCATION;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final String TAG = "MapFragment";

    private Unbinder unbinder;

    @BindView(R.id.map_map_view)
    MapView mapView;
    private MapboxMap mapboxMap;
    private String MARKER_IMAGE = R.drawable.ic_custom_marker + "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(getActivity(), "pk.eyJ1Ijoic2hlcnZpbm94IiwiYSI6ImNqbmhvODBjYjBlbTEzdnFmdjQwZHRhZnYifQ.AJIyKUovRxF1jcFRtWjxow");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.view_map, container, false);
        unbinder = ButterKnife.bind(this, view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        dropPin();

        fetchGeoClothes();

        return view;
    }

    private void fetchGeoClothes()
    {
        ((MainActivity) getActivity()).showLoading();

        GeoClothesRequest geoClothesRequest = new GeoClothesRequest();
        geoClothesRequest.setLatValue("35.356789");
        geoClothesRequest.setLongValue("57.45688");
        NetworkManager.getInstance().getEndpointApi(Api.class).geoClothes(geoClothesRequest).enqueue(new Callback<GeoClothesResponse>() {
            @Override
            public void onResponse(Call<GeoClothesResponse> call, Response<GeoClothesResponse> response)
            {
                ((MainActivity) getActivity()).hideLoading();

                if (response.isSuccessful())
                {
                    displayFetchedGeoClothes(response.body());
                }
                else
                {
//                    Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeoClothesResponse> call, Throwable t)
            {
                ((MainActivity) getActivity()).hideLoading();

//                Toast.makeText(getContext(), "Connection Error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void displayFetchedGeoClothes(GeoClothesResponse body)
    {
        if (body != null)
        {
            if (body.getData() == null || body.getData().size() == 0)
            {
                Toast.makeText(getContext(), "No Clothes found in this area", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    }

    private void dropPin()
    {
        // Create drop pin using custom image
        ImageView dropPinView = new ImageView(getActivity());
        dropPinView.setImageResource(R.drawable.ic_close_24dp);

        // Statically Set drop pin in center of screen
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        float density = getResources().getDisplayMetrics().density;
        params.bottomMargin = (int) (12 * density);
        dropPinView.setLayoutParams(params);
        mapView.addView(dropPinView);
    }

//    private void addMarkers()
//    {
//        List<Feature> features = new ArrayList<>();
//        /* Source: A data source specifies the geographic coordinate where the image marker gets placed. */
//        features.add(Feature.fromGeometry(Point.fromLngLat(new double[]{51.45373618436247, 35.652866114008944})));
//        FeatureCollection featureCollection = FeatureCollection.fromFeatures(features);
//        GeoJsonSource source = new GeoJsonSource(MARKER_SOURCE, featureCollection);
//        mapboxMap.addSource(source);
//        /* Style layer: A style layer ties together the source and image and specifies how they are displayed on the map. */
//        SymbolLayer markerStyleLayer = new SymbolLayer(MARKER_STYLE_LAYER, MARKER_SOURCE)
//                .withProperties(
//                        PropertyFactory.iconAllowOverlap(true),
//                        PropertyFactory.iconImage(MARKER_IMAGE)
//                );
//        mapboxMap.addLayer(markerStyleLayer);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        initMap(savedInstanceState);

//        ReactiveLocationProvider locationProvider = new ReactiveLocationProvider(getContext());
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
            Toast.makeText(getContext(), "NEED PERMISSION", Toast.LENGTH_LONG).show();
            return;
        }
//        locationProvider.getLastKnownLocation()
//                .subscribe(location -> doSthImportantWithObtainedLocation(location));

    }

    private void doSthImportantWithObtainedLocation(Location location)
    {
        Toast.makeText(getContext(), "fetched: " + location.getLatitude(), Toast.LENGTH_SHORT).show();
    }

    private void initMap(Bundle savedInstanceState)
    {

//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(new OnMapReadyCallback() {
//            @Override
//            public void onMapReady(MapboxMap mapboxMap)
//            {
//
//                // Customize map with markers, polylines, etc.
//                LocationComponent locationComponent = mapboxMap.getLocationComponent();
//
//                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//                {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                    //                                          int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//
//                locationComponent.activateLocationComponent(getActivity());
//                locationComponent.setLocationComponentEnabled(true);
//
//            }
//        });
    }

    @Override
    public void onMapReady(MapboxMap mapboxMap)
    {
        this.mapboxMap = mapboxMap;

        mapboxMap.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker)
            {
                Log.d(TAG, "onMarkerClick: " + marker.getTitle());
                return false;
            }
        });



        /* Image: An image is loaded and added to the map. */
        Bitmap icon = BitmapFactory.decodeResource(
                MapFragment.this.getResources(), R.drawable.ic_custom_marker);
        mapboxMap.addImage(MARKER_IMAGE, icon);
//        addMarkers();
        addMarkersMine();

        ArrayList<LatLng> latLngs = new ArrayList<>();


        for (int i = 0; i < 10; i++)
        {
            latLngs.add(new LatLng(10.683 + ((double) i / 100), 53.874236));
            Log.d(TAG, "Add Marker: " + latLngs.get(latLngs.size() - 1));
            mapboxMap.addMarker(new MarkerOptions()
                    .position(latLngs.get(latLngs.size() - 1))
                    .title("Marker: " + i)
                    .snippet("Snipped"));
        }
//
//        LatLngBounds latLngBound = new LatLngBounds.Builder()
//                .include(latLngs.get(0))
//                .include(latLngs.get(latLngs.size() - 1))
//                .build();
//        mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBound, 200), 1000);

    }

    private void addMarkersMine()
    {
        Icon icon = IconFactory.getInstance(getActivity()).fromResource(R.drawable.ic_custom_marker);

        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(35.749622d, 51.3816002d)).snippet("Sharona")).setIcon(icon);
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(35.749623d, 51.3816002d)).snippet("Sharona")).setIcon(icon);
        mapboxMap.addMarker(new MarkerOptions().position(new LatLng(35.749621d, 51.3816002d)).snippet("Sharona").title("Shoes")).setIcon(icon);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        mapView.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }


}
