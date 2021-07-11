package com.example.parkme;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    GoogleMap map;
    FrameLayout mapView;
    ArrayList<LatLng> arrayList= new ArrayList<LatLng>();
    LatLng Bialik = new LatLng(32.020031,34.746639);
    LatLng GanEshkol = new LatLng(32.01301,34.74609);
    LatLng Haemdot = new LatLng(32.03025,34.7464);
    LatLng Haheshmonaim = new LatLng(32.01957,34.75138);
    ArrayList<String> titles=new ArrayList<String>();
    KmlLayer kmlLayer;
    private static final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    String longitude,latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Setting in arrays
        //Add Permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        getLocation();
        //lets add our array list
        arrayList.add(Bialik);
        arrayList.add(GanEshkol);
        arrayList.add(Haemdot);
        arrayList.add(Haheshmonaim);
        //lets add titles of our array list
        titles.add("Bialik");
        titles.add("Gan Eshkol");
        titles.add("Haemdot");
        titles.add("Haheshmonaim");

        //Initialize fragment
        Fragment fragment=new MapFragment(longitude,latitude);
        //open Fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }

    private void getLocation() {
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        // Check gps is enabled or not
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //Write Function to enable gps
            OnGPS();
        }
        else{
           //GPS is already On
          if (ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
              ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
            }
          else{
              Location LocationGps=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
              Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
              Location LocationPassive =locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
              if(LocationGps!=null){
                  double lat=LocationGps.getLatitude();
                  double longi=LocationGps.getLongitude();
                  latitude=String.valueOf(lat);
                  longitude=String.valueOf(longi);
              }
              else if(LocationNetwork!=null){
                  double lat=LocationNetwork.getLatitude();
                  double longi=LocationNetwork.getLongitude();
                  latitude=String.valueOf(lat);
                  longitude=String.valueOf(longi);
              }
              else if(LocationPassive!=null){
                  double lat=LocationPassive.getLatitude();
                  double longi=LocationPassive.getLongitude();
                  latitude=String.valueOf(lat);
                  longitude=String.valueOf(longi);
              }
          }
        }
    }

    private void OnGPS() {
    final AlertDialog.Builder builder= new AlertDialog.Builder(this);
    builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    });
    final AlertDialog alertDialog = builder.create();
    }

}