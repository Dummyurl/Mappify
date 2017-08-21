package com.example.karshima.mappifyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.karshima.mappifyapp.utility.PreferencesInterface;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.karshima.mappifyapp.utility.PreferencesInterface.MyPREFERENCES;
import static com.example.karshima.mappifyapp.utility.PreferencesInterface.userId;

/**
 * Created by Karshima on 2/8/2017.
 */

public class SplashScreenActivity extends Activity implements PreferencesInterface {

    private SharedPreferences sharedpreferences;

    private static final String TAG = SplashScreenActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        getHash();


        goTomain();

    }

    public void getHash(){

        MessageDigest md = null;
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        Log.e("SecretKey = ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
    }

    private void goTomain() {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                SharedPreferences sharedPreferences = getSharedPreferences(PreferencesInterface.MyPREFERENCES, Context.MODE_PRIVATE);

                if (sharedPreferences.contains(PreferencesInterface.userId)) {

                    Intent loginIntent = new Intent(SplashScreenActivity.this, CreateTripActivity.class);
                    startActivity(loginIntent);
                    finish();

                } else {

                    Intent loginIntent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }

            }
        }, 2000);
    }

  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e(TAG, "onActivityResult");

        goTomain();

    }*/





}
