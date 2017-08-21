package com.example.karshima.mappifyapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



/**
 * Created by Karshima on 2/20/2017.
 */


public class CreateTripActivity extends FragmentActivity implements View.OnClickListener {

    private GoogleMap mMap;
    private static final String TAG = "CreateTripActivity";
    protected GoogleApiClient mGoogleApiClient;
    protected Location mLastLocation;
    private ImageView img,img1,img2,img3,img4,img5;
    private Context context;
    private Toolbar toolbar;
    private Boolean exit = false;


    protected LocationRequest mLocationRequest;
    protected Marker mCurrLocationMarker;
    final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private int whichButton=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createtrip);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.


        setUpview();
    }


    @Override
    protected void onStart() {
        super.onStart();

        switch (whichButton){

            case 0:
                img.setImageResource(R.drawable.greeniternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                break;


            case 1:
                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greenactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);


                break;


            case 2:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greenprofile);
                img3.setImageResource(R.drawable.greypremium);


                break;


            case 3:


                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greenpremium);


                break;



        }
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
        img4=(ImageView)findViewById(R.id.addit);
        img4.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.literary:

                img.setImageResource(R.drawable.greeniternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);


                whichButton = 0;

                Intent i = new Intent(CreateTripActivity.this, MyActivity.class);
                startActivity(i);
                break;


            case R.id.activity:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greenactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);

                whichButton = 1;

                Toast.makeText(CreateTripActivity.this, "Activity", Toast.LENGTH_LONG).show();

                break;
            case R.id.profile:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greenprofile);
                img3.setImageResource(R.drawable.greypremium);


                whichButton = 2;

                Intent ir = new Intent(CreateTripActivity.this, UserProfileActivity.class);
                startActivity(ir);
                break;
            case R.id.primium:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greenpremium);

                whichButton = 3;

                Intent in = new Intent(CreateTripActivity.this, PrimiumOptionActivity.class);
                startActivity(in);
                break;

            case R.id.addit:
                Intent ddi = new Intent(CreateTripActivity.this, MyActivity.class);
                startActivity(ddi);
                break;
        }

    }



    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 1 * 1000);

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


}
