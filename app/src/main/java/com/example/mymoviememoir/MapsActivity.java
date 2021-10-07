package com.example.mymoviememoir;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentlocation;
    private LatLng cinemaLocation;
    private LatLng cinemaLocation2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        String home_location = "-37.859393, 144.986495";
        String[] latlong= home_location.split(",");
        double latitude = Double.parseDouble(latlong[0]);
        double longitude = Double.parseDouble(latlong[1]);
        currentlocation = new LatLng(latitude, longitude);
        String the_astor_theatre = "-37.857381, 144.992016";
        String[] latitudelong = the_astor_theatre.split(",");
        double theatre1latitude = Double.parseDouble(latitudelong[0]);
        double theatre1longitude = Double.parseDouble(latitudelong[1]);
        cinemaLocation = new LatLng(theatre1latitude, theatre1longitude);
        String cinema_two = "-37.878800, 144.997643";
        String[] latitudelong2 = cinema_two.split(",");
        double theatre2latitude = Double.parseDouble(latitudelong2[0]);
        double theatre2longitude = Double.parseDouble(latitudelong[1]);
        cinemaLocation2 = new LatLng(theatre2latitude, theatre1longitude);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;


        mMap.addMarker(new MarkerOptions().position(currentlocation).title("Your Home Address"));
        mMap.addMarker(new MarkerOptions().position(cinemaLocation).title("The Astor Theatre"));
        mMap.addMarker(new MarkerOptions().position(cinemaLocation2).title("Barefoot Cinema - RipponLea Estate"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);
    }


}
