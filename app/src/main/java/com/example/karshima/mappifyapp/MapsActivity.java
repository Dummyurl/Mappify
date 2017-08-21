package com.example.karshima.mappifyapp;

import android.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;


import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.Constants;
import com.example.karshima.mappifyapp.utility.MyLocation;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.URLListner;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.vision.text.Text;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.graphics.PorterDuff.Mode;

import static android.R.attr.data;
import static android.R.attr.id;
import static com.example.karshima.mappifyapp.R.drawable.v;
import static com.example.karshima.mappifyapp.R.id.cancel_action;
import static com.example.karshima.mappifyapp.R.id.map;
//import static com.map.project.R.id.map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,View.OnClickListener {
    private GoogleMap mMap;
    private static final String TAG = "MapsActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private ImageView img,img1,img2,img3;
    private TextView t1,t2,t3;

    private MyLocation myLocation;
    private double latitude;
    private double longitude;


    protected LocationRequest mLocationRequest;
    protected Marker mCurrLocationMarker;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        setUpview();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


    }

    private void buildGoogleApiClient() {
    }


    private void setUpview() {
        img=(ImageView)findViewById(R.id.literary);
        img.setOnClickListener(this);
        img1=(ImageView)findViewById(R.id.activity);
        img1.setOnClickListener(this);
        img2=(ImageView)findViewById(R.id.profile);
        img2.setOnClickListener(this);
        img3=(ImageView)findViewById(R.id.primium);
        img3.setOnClickListener(this);

        t1=(TextView) findViewById(R.id.time);
        t1.setOnClickListener(this);

        t2=(TextView) findViewById(R.id.distance);
        t2.setOnClickListener(this);

        t3=(TextView) findViewById(R.id.duration);
        t3.setOnClickListener(this);


        img.setImageResource(R.drawable.greeniternary);
        img1.setImageResource(R.drawable.greyactivity);
        img2.setImageResource(R.drawable.greyprofile);
        img3.setImageResource(R.drawable.greypremium);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.literary:

                img.setImageResource(R.drawable.greeniternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(0);

                Intent i=new Intent(MapsActivity.this,MapsActivity.class);
                startActivity(i);
                break;


            case R.id.activity:
                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greenactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(1);
                Toast.makeText(MapsActivity.this,"Activity",Toast.LENGTH_LONG).show();

                break;
            case R.id.profile:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greenprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(2);
                Intent ir=new Intent(MapsActivity.this,UserProfileActivity.class);
                startActivity(ir);

                break;
            case R.id.primium:

                getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greenpremium);
                displayView(3);
                Intent in=new Intent(MapsActivity.this,PrimiumOptionActivity.class);
                startActivity(in);
                break;

        }

    }

    private void displayView(int i) {
    }

}


