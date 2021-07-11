package com.example.parkme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    TextView titleTV,phoneTV,addressTV,freeTV;
    MapView mapView;
    MarkerObject passObject;
    GoogleMap map;
    ImageButton wazebtn;
    String baseWazeUrl = "https://waze.com/ul?ll=";
    MarkerObject markerObject;
    String searchString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        LoadData();

        titleTV=findViewById(R.id.title_text);
        mapView=(MapView)findViewById(R.id.map_view);
        addressTV=findViewById(R.id.address_tv);
        freeTV=findViewById(R.id.free_text);
        mapView.onCreate(savedInstanceState);
        wazebtn=findViewById(R.id.waze_btn);

        mapView.getMapAsync(this);
//        freeTV.setText(passObject.getFree());
        pickPayment();
        pickAddress();
        pickTitle();
//        addressTV.setText(passObject.getAddress());
//        titleTV.setText(passObject.getTitle());

        wazebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAddress();
            }
        });
    }
    public void pickAddress(){
        switch (passObject.getAddress()){
            case "ביאליק 22":
                addressTV.setText(getString(R.string.bialik22));
                break;
            case "בר אילן 40 מול 57":
                addressTV.setText(getString(R.string.barilan40));
                break;
            case "העמדות 3":
                addressTV.setText(getString(R.string.hahemdot3));
                break;
            case "החשמונאים 27":
                addressTV.setText(getString(R.string.haheshmonaim27));
                break;
            case "נורדאו 17":
                addressTV.setText(getString(R.string.nordeo17));
                break;
            case "אורט ישראל מול 3 א":
                addressTV.setText(getString(R.string.ort1));
                break;
            case "אורט ישראל מול 3 ל":
                addressTV.setText(getString(R.string.ort2));
                break;
            case " גנרל קניג 7":
                addressTV.setText(getString(R.string.generalkaning7));
                break;
            case "גביזון 3":
                addressTV.setText(getString(R.string.gabizon3));
                break;
            case "הנביאים לכיוון ראשון לציון":
                addressTV.setText(getString(R.string.thenevihim));
                break;
            case "סטרומה 1":
                addressTV.setText(getString(R.string.stroma1));
                break;
            case " רהב 7":
                addressTV.setText(getString(R.string.rahav7));
                break;
            case " הסוללים מול 7":
                addressTV.setText(getString(R.string.solelim7));
                break;
            case "אנה פרנק 22":
                addressTV.setText(getString(R.string.anafrank22));
                break;
            case "מנחם בגין":
                addressTV.setText(getString(R.string.menahembegin));
                break;
            case "דרך בן גוריון 62":
                addressTV.setText(getString(R.string.derehbengurion));
                break;
            case "החוף הנפרד":
                addressTV.setText(getString(R.string.hofnifrad));
                break;
            case "דרך בן גוריון 138":
                addressTV.setText(getString(R.string.derehbengurion138));
                break;
            case  "כניסה לחניון":
                addressTV.setText(getString(R.string.knisahenyon));
                break;
            case "מול חוף ירושלים":
                addressTV.setText(getString(R.string.molhofyerusha));
                break;
            case " הים 2":
                addressTV.setText(getString(R.string.thesea));
                break;
            case "מול חוף סי פאלאס":
                addressTV.setText(getString(R.string.cpalas));
                break;
            case "הרב ניסנבאום 35":
                addressTV.setText(getString(R.string.nisenbaum));
                break;
            case "ניסנבאום":
                addressTV.setText(getString(R.string.nisenbaumx));
                break;
            case "מוהליבר 11":
                addressTV.setText(getString(R.string.mohaliver11));
                break;
            case "רוטשילד 29":
                addressTV.setText(getString(R.string.rotchild29));
                break;
            case "רוטשילד 27, הלפר 26":
                addressTV.setText(getString(R.string.rotchild27));
                break;
            case "מול תחנת רכבת בת ים יוספטל ":
                addressTV.setText(getString(R.string.yoseftal));
                break;
            case "שדרות העצמאות 75":
                addressTV.setText(getString(R.string.hazmaot75));
                break;
            case "הלפר 19":
                addressTV.setText(getString(R.string.halfer19));
                break;
        }
    }
    public void pickTitle(){
        switch (passObject.getTitle()){
            case("חניון ביאליק"):
                titleTV.setText(getString(R.string.bialik));
                break;
            case("חניון גן אשכול"):
                titleTV.setText(getString(R.string.ganeshkol));
                break;
            case("חניון העמדות"):
                titleTV.setText(getString(R.string.hahemdot));
                break;
            case("חניון חשמונאים"):
                titleTV.setText(getString(R.string.heshmonahim));
                break;
            case("חניון בניין העירייה "):
                titleTV.setText(getString(R.string.townbuilding));
                break;
            case("חניון אורט ישראל 1"):
                titleTV.setText(getString(R.string.ort1s));
                break;
            case("חניון אורט ישראל 2"):
                titleTV.setText(getString(R.string.ort2s));
                break;
            case("חניון החברה הכלכלית (גנרל קנינג)"):
                titleTV.setText(getString(R.string.kalkalit));
                break;
            case("חניון גינת גביזון"):
                titleTV.setText(getString(R.string.gabizongina));
                break;
            case("חניון זמני (הנביאים) , לא מוסדר"):
                titleTV.setText(getString(R.string.tempnevihim));
                break;
            case("חניון היכל התרבות"):
                titleTV.setText(getString(R.string.halloftarbut));
                break;
            case("חניון המכללה"):
                titleTV.setText(getString(R.string.collegehana));
                break;
            case("חניון בתי המשפט"):
                titleTV.setText(getString(R.string.courthana));
                break;
            case("חניון אורות התורה"):
                titleTV.setText(getString(R.string.orottorah));
                break;
            case("חניון הקאנטרי"):
                titleTV.setText(getString(R.string.countrys));
                break;
            case("חניון חוף תאיו"):
                titleTV.setText(getString(R.string.hoftayoh));
                break;
            case("חניון החוף הנפרד"):
                titleTV.setText(getString(R.string.hofnifradh));
                break;
            case("חניון קולוני ביץ'"):
                titleTV.setText(getString(R.string.colonyh));
                break;
            case("חניון נורדאו"):
                titleTV.setText(getString(R.string.nordeoh));
                break;
            case("חניון ירושלים"):
                titleTV.setText(getString(R.string.jerusalemh));
                break;
            case("חניון המגדלי הים התיכון"):
                titleTV.setText(getString(R.string.migdalh));
                break;
            case("חניון הסי פלאס"):
                titleTV.setText(getString(R.string.cpalasc));
                break;
            case("חניון קניון בת ימון"):
                titleTV.setText(getString(R.string.batyamonh));
                break;
            case("חניון בת ים"):
                titleTV.setText(getString(R.string.batyamh));
                break;
            case("חניון רוטשילד בת ים"):
                titleTV.setText(getString(R.string.rotchildh));
                break;
            case("חניון של החנות שוק סטוק"):
                titleTV.setText(getString(R.string.shokstock));
                break;
            case("חניון תחנת רכבת בת ים יוספטל"):
                titleTV.setText(getString(R.string.yoseftalh));
                break;
            case("חניון סביון"):
                titleTV.setText(getString(R.string.savyonh));
                break;
            case("חניון חלפר"):
                titleTV.setText(getString(R.string.halferh));
                break;
        }
    }
    public void pickPayment(){
        switch (passObject.getFree()){
            case "payment-freebatyam":
                freeTV.setText(R.string.freeforbatyam);
                break;
            case "free":
                freeTV.setText(R.string.free);
                break;
            case "payment":
                freeTV.setText(R.string.payment);
            case "halfonefree":
                freeTV.setText(R.string.onehourhalffree);
                break;
            case "customers":
                freeTV.setText(R.string.freeforcustomer);
                break;
            case "dogal":
                freeTV.setText(R.string.freemotor);
                break;
        }
    }
    public void SaveData(){
        //SharedPreferences preferences = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences sharedPreferences=getSharedPreferences("sharedpreferences",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Gson gson=new Gson();
        String json = gson.toJson(passObject);
        editor.putString("markerObject",json);
        editor.apply();
    }
    public void LoadData(){
        SharedPreferences sharedPreferences=getSharedPreferences("sharedpreferences",MODE_PRIVATE);
        Gson gson=new Gson();
        String json = sharedPreferences.getString("markerObject",null);
        Type type = new TypeToken<MarkerObject>() {}.getType();
        passObject=gson.fromJson(json,type);
        if(passObject==null)
        {
            passObject = new MarkerObject();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng SelectedMarker = new LatLng(passObject.getLatLng().latitude, passObject.getLatLng().longitude);
        googleMap.addMarker(new MarkerOptions().position(SelectedMarker).title(passObject.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_desti)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SelectedMarker,15f));
        mapView.onResume();
//        map.getUiSettings().setMyLocationButtonEnabled(false);
//        map.moveCamera(CameraUpdateFactory.newLatLng(passObject.getLatLng()));
    }
    public void searchAddress(){
        searchString = baseWazeUrl+passObject.getLatLng().latitude+", "+passObject.getLatLng().longitude+"&z=10";
        try
        {
            // Launch Waze to look for our address:
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( searchString ) );
            startActivity( intent );
        }
        catch ( ActivityNotFoundException ex  )
        {
            // If Waze is not installed, open it in Google Play:
            Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "market://details?id=com.waze" ) );
            startActivity(intent);
        }
    }
}