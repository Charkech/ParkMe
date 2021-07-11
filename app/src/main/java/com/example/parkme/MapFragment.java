package com.example.parkme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.geojson.GeoJsonLayer;
import com.google.maps.android.kml.KmlLayer;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    KmlLayer layer;
    GeoJsonLayer geoJsonLayer = null;
    GoogleApiClient googleApiClient;
    GoogleMap MyMap;
    Context Mcontext;
    MarkerObject passObject;
    Fragment fragment;
    SupportMapFragment mapFrag;
    LocationRequest mLocationRequest;
    LocationManager locationMana;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationCallback locationCallback;
    FusedLocationProviderClient mFusedLocationClient;
    String mLongitude,mLatitude;
    ArrayList<MarkerObject> markersArray = new ArrayList<MarkerObject>();
    ArrayList<LatLng> arrayList= new ArrayList<LatLng>();

    //all locations
    LatLng Bialik = new LatLng(32.020031,34.746639);
    LatLng GanEshkol = new LatLng(32.01301,34.74609);
    LatLng Haemdot = new LatLng(32.03025,34.7464);
    LatLng Haheshmonaim = new LatLng(32.01957,34.75138);
    LatLng Heriya = new LatLng(32.01611,34.74113);
    LatLng OrtIsraelA = new LatLng(32.01452,34.75207);
    LatLng OrtIsraelB = new LatLng(32.01761,34.75547);
    LatLng GeneralKaning = new LatLng(32.00919,34.74576);
    LatLng Gabizon=new LatLng(32.02737,34.75715);
    LatLng Hanevihim = new LatLng(32.01061,34.74488);
    LatLng HeihalAspot = new LatLng(32.021,34.75709);
    LatLng mihlala = new LatLng(32.02228,34.75828);
    LatLng court = new LatLng(32.00787,34.74722);
    LatLng orotTora = new LatLng(32.00594,34.75418);
    LatLng country = new LatLng(32.0004,34.74533);
    LatLng hofTayo = new LatLng(32.01692,34.7386);
    LatLng hofNifrad = new LatLng(32.009705,34.73851);
    LatLng hofColonybich = new LatLng(32.01065,34.73825);
    LatLng nordeo = new LatLng(32.01528,34.74075);
    LatLng yerushalaim = new LatLng(32.028309,34.741436);
    LatLng migdalYamTihun = new LatLng(32.02888,34.74196);
    LatLng cplus=new LatLng(32.030673,34.741863);
    LatLng batyamun = new LatLng(32.00715,34.75616);
    LatLng heyhalmishpat = new LatLng(32.006805,34.748959);
    LatLng batyam = new LatLng(32.00633,34.75073);
    LatLng rotchild = new LatLng(32.02716,34.74684);
    LatLng shokstock = new LatLng(32.02705,34.74616);
    LatLng yoseftal = new LatLng(32.015648,34.756407);
    LatLng savyon = new LatLng(32.028648,34.749001);
    LatLng halfer=new LatLng(32.028179,34.745701);
    ArrayList<String> titles=new ArrayList<String>();
    ArrayList<String> addresslist =new ArrayList<String>();

    //all the addresses
    String BialikA = "ביאליק 22";
    String GanEshkolA = "בר אילן 40 מול 57";
    String HahemdotA = "העמדות 3";
    String HaheshmonaimA = "החשמונאים 27";
    String HeriyaA = "נורדאו 17";
    String OrtISA = "אורט ישראל מול 3 א";
    String OrtISB = "אורט ישראל מול 3 ל";
    String GeneralKeninA = " גנרל קניג 7";
    String GebizonA = "גביזון 3";
    String NevihimA = "הנביאים לכיוון ראשון לציון";
    String HeihalTarbotA = "סטרומה 1";
    String MihlalaA = " רהב 7";
    String BetMishpatA = " הסוללים מול 7";
    String OrotAtoraA = "אנה פרנק 22";
    String CountryA = "מנחם בגין";
    String hofTayoA = "דרך בן גוריון 62";
    String hofNifradA = "החוף הנפרד";
    String ColonyBeachA ="דרך בן גוריון 138";
    String nordeoA = "כניסה לחניון";
    String hofYerushalaimA = "מול חוף ירושלים";
    String migdalyamtihonA = " הים 2";
    String CplusA="מול חוף סי פאלאס";
    String BatYamonA="הרב ניסנבאום 35";
    String heyhalmishpatA= "ניסנבאום";
    String henyunBatYamA = "מוהליבר 11";
    String rotchildBatyamA= "רוטשילד 29";
    String shokstockA="רוטשילד 27, הלפר 26";
    String yoseftalA="מול תחנת רכבת בת ים יוספטל ";
    String savyonA ="שדרות העצמאות 75";
    String halferA = "הלפר 19";

    ArrayList<String> paymentArray = new ArrayList<String>();
    //payment or free
    String payment = "payment-freebatyam";
    String notpayment = "free";
    String fullpayment = "payment";
    String halfonefree = "halfonefree";
    String freeforcustomers ="customers";
    String freedogal="dogal";
    public MapFragment(String longitude, String latitude) {
        mLongitude=longitude;
        mLatitude=latitude;
    }

    @Override
    public void onStart() {
//        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
//        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Mcontext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Mcontext = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize view
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        //check if has permissions
//        if ((ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED)) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
//        }
//        getLocation();

        //add the payment methods
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(payment);
        paymentArray.add(halfonefree);
        paymentArray.add(fullpayment);
        paymentArray.add(notpayment);
        paymentArray.add(fullpayment);
        paymentArray.add(freeforcustomers);
        paymentArray.add(freedogal);
        paymentArray.add(fullpayment);
        paymentArray.add(fullpayment);

        //lets add our array list
        arrayList.add(Bialik);
        arrayList.add(GanEshkol);
        arrayList.add(Haemdot);
        arrayList.add(Haheshmonaim);
        arrayList.add(Heriya);
        arrayList.add(OrtIsraelA);
        arrayList.add(OrtIsraelB);
        arrayList.add(GeneralKaning);
        arrayList.add(Gabizon);
        arrayList.add(Hanevihim);
        arrayList.add(HeihalAspot);
        arrayList.add(mihlala);
        arrayList.add(court);
        arrayList.add(orotTora);
        arrayList.add(country);
        arrayList.add(hofTayo);
        arrayList.add(hofNifrad);
        arrayList.add(hofColonybich);
        arrayList.add(nordeo);
        arrayList.add(yerushalaim);
        arrayList.add(migdalYamTihun);
        arrayList.add(cplus);
        arrayList.add(batyamun);
        arrayList.add(heyhalmishpat);
        arrayList.add(batyam);
        arrayList.add(rotchild);
        arrayList.add(shokstock);
        arrayList.add(yoseftal);
        arrayList.add(savyon);
        arrayList.add(halfer);

        //lets add titles of our array list
        titles.add("חניון ביאליק");
        titles.add("חניון גן אשכול");
        titles.add("חניון העמדות");
        titles.add("חניון חשמונאים");
        titles.add("חניון בניין העירייה ");
        titles.add("חניון אורט ישראל 1");
        titles.add("חניון אורט ישראל 2");
        titles.add("חניון החברה הכלכלית (גנרל קנינג)");
        titles.add("חניון גינת גביזון");
        titles.add("חניון זמני (הנביאים) , לא מוסדר");
        titles.add("חניון היכל התרבות");
        titles.add("חניון המכללה");
        titles.add("חניון בתי המשפט");
        titles.add("חניון אורות התורה");
        titles.add("חניון הקאנטרי");
        titles.add("חניון חוף תאיו");
        titles.add("חניון החוף הנפרד");
        titles.add("חניון קולוני ביץ'");
        titles.add("חניון נורדאו");
        titles.add("חניון ירושלים");
        titles.add("חניון המגדלי הים התיכון");
        titles.add("חניון הסי פלאס");
        titles.add("חניון קניון בת ימון");
        titles.add("חניון היכל המשפט");
        titles.add("חניון בת ים");
        titles.add("חניון רוטשילד בת ים");
        titles.add("חניון של החנות שוק סטוק");
        titles.add("חניון תחנת רכבת בת ים יוספטל");
        titles.add("חניון סביון");
        titles.add("חניון חלפר");

        //initialize the addresses array
        addresslist.add(BialikA);
        addresslist.add(GanEshkolA);
        addresslist.add(HahemdotA);
        addresslist.add(HaheshmonaimA);
        addresslist.add(HeriyaA);
        addresslist.add(OrtISA);
        addresslist.add(OrtISB);
        addresslist.add(GeneralKeninA);
        addresslist.add(GebizonA);
        addresslist.add(NevihimA);
        addresslist.add(HeihalTarbotA);
        addresslist.add(MihlalaA);
        addresslist.add(BetMishpatA);
        addresslist.add(OrotAtoraA);
        addresslist.add(CountryA);
        addresslist.add(hofTayoA);
        addresslist.add(hofNifradA);
        addresslist.add(ColonyBeachA);
        addresslist.add(nordeoA);
        addresslist.add(hofYerushalaimA);
        addresslist.add(migdalyamtihonA);
        addresslist.add(CplusA);
        addresslist.add(BatYamonA);
        addresslist.add(heyhalmishpatA);
        addresslist.add(henyunBatYamA);
        addresslist.add(rotchildBatyamA);
        addresslist.add(shokstockA);
        addresslist.add(yoseftalA);
        addresslist.add(savyonA);
        addresslist.add(halferA);

        // Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.google_map);
        Mcontext = this.getContext();
        //Async Map
        supportMapFragment.getMapAsync(this::onMapReady);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //When map is loaded
        //Array for loading all our parking locations with all the info we need.
        MyMap = googleMap;
        for(int i=0;i<arrayList.size();i++){
            //temp marker object that we will use to make all the locations.
            MarkerObject tempMarker=new MarkerObject();

           MyMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(titles.get(i))).icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_park)));
           //here we will add the payment type for every object.
           tempMarker.setFree(paymentArray.get(i));
           //location for every object.
           tempMarker.setLatLng(arrayList.get(i));
           // title for every object.
           tempMarker.setTitle(String.valueOf(titles.get(i)));
           // address for every object.
           tempMarker.setAddress(addresslist.get(i));

           markersArray.add(tempMarker);

        }
        MyMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            geoJsonLayer = new GeoJsonLayer(MyMap, R.raw.parkpot, getContext());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        geoJsonLayer.addLayerToMap();
        if(mLatitude!=null && mLongitude!=null) {
            double LongDouble = Double.parseDouble(mLongitude);
            double LatiDouble = Double.parseDouble(mLatitude);
            LatLng currentUserLocation = new LatLng(LatiDouble, LongDouble);
            MyMap.addMarker(new MarkerOptions().position(currentUserLocation).title("Current Location").icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));
            MyMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentUserLocation, 15f));
        }
        else{
            LatLng BatYam = new LatLng(32.017136, 34.745441);
            googleMap.addMarker(new MarkerOptions().position(BatYam).title("Marker in BatYam").icon(BitmapDescriptorFactory.fromResource(R.drawable.person)));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(BatYam,15f));
        }
        MyMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if(findObject(marker.getPosition())!=null) {
//                    String markertitle = marker.getTitle();
                    passObject = findObject(marker.getPosition());
                    Intent i = new Intent(getContext(), DetailsActivity.class);
                    SaveData();
                    startActivity(i);
                }
                return false;
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationMana = (LocationManager) getActivity().getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
            locationMana.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, (LocationListener) getContext());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Toast.makeText(getContext(), "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT);
        try {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            String address = addresses.get(0).getAddressLine(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, locationCallback, Looper.myLooper());
                        MyMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getContext(), "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void SaveData(){
        //SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("sharedpreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = gson.toJson(passObject);
        editor.putString("markerObject",json);
        editor.apply();
    }
    public void LoadData(){
        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("sharedpreferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json = sharedPreferences.getString("markerObject",null);
        Type type = new TypeToken<MarkerObject>() {}.getType();
        passObject=gson.fromJson(json,type);
        if(passObject==null)
        {
            passObject = new MarkerObject();
        }
    }

     MarkerObject findObject(LatLng location) {
        for(MarkerObject markerObject : markersArray) {
            if(markerObject.getLatLng().equals(location)) {
                return markerObject;
            }
        }
        return null;
    }
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}