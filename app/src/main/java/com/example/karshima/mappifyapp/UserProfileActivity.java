package com.example.karshima.mappifyapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.karshima.mappifyapp.utility.Utils;
import com.facebook.login.LoginManager;

import static com.example.karshima.mappifyapp.LoginActivity.MyPREFERENCES;
import static com.example.karshima.mappifyapp.utility.PreferencesInterface.userId;

/**
 * Created by Karshima on 2/22/2017.
 */

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout nxt,nxt1,nxt2,nxt3,nxt4;
    private String u_id,email,mob_no,name,key;
    private SharedPreferences sharedPreferences;
    private static final String TAG ="TAG";
    private ImageView img,img1,img2,img3,img4,img5;
    private int whichButton=0;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userprofile);
        setUpview();

    }

    private void setUpview() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("User Profile");


        nxt=(RelativeLayout) findViewById(R.id.r10);
        nxt.setOnClickListener(this);

        nxt1=(RelativeLayout) findViewById(R.id.r7);
        nxt1.setOnClickListener(this);

        nxt2=(RelativeLayout)findViewById(R.id.rrrrrr);
        nxt2.setOnClickListener(this);

        nxt3=(RelativeLayout)findViewById(R.id.r9);
        nxt3.setOnClickListener(this);

        nxt4=(RelativeLayout)findViewById(R.id.r11);
        nxt4.setOnClickListener(this);


        img=(ImageView)findViewById(R.id.literary);
        img.setOnClickListener(this);


        img1=(ImageView)findViewById(R.id.activity);
        img1.setOnClickListener(this);
        img2=(ImageView)findViewById(R.id.profile);
        img2.setOnClickListener(this);
        img3=(ImageView)findViewById(R.id.primium);
        img3.setOnClickListener(this);


        img.setImageResource(R.drawable.greyiternary);
        img1.setImageResource(R.drawable.greyactivity);
        img2.setImageResource(R.drawable.greenprofile);
        img3.setImageResource(R.drawable.greypremium);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case  R.id.r7:

                Intent userIntent=new Intent(UserProfileActivity.this,SettingActivity.class);
                startActivity(userIntent);


                break;

            case R.id.rrrrrr:
                Intent usent=new Intent(UserProfileActivity.this,SaveIternaryActivity.class);
                startActivity(usent);

                break;

            case R.id.r9:
                Intent usnt=new Intent(UserProfileActivity.this,ViewPrimiumCardActivity.class);
                startActivity(usnt);
                break;


            case R.id.r10:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "http://www.cube9gs.com/mobapp-dev.php");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);


                break;

            case R.id.r11:
                Toast.makeText(UserProfileActivity.this," your ads",Toast.LENGTH_LONG).show();
                break;

            case R.id.literary:

                img.setImageResource(R.drawable.greeniternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(0);
                Intent i=new Intent(UserProfileActivity.this,MapsActivity.class);
                startActivity(i);
                break;


            case R.id.activity:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greenactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(1);
                Toast.makeText(UserProfileActivity.this,"Activity",Toast.LENGTH_LONG).show();

                break;
            case R.id.profile:

                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greenprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(2);

                Intent ir=new Intent(UserProfileActivity.this,SingleUserProfileActivity.class);
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
                
                Intent in=new Intent(UserProfileActivity.this,PrimiumOptionActivity.class);
                startActivity(in);
                break;



        }
    }

    private void displayView(int i) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();

                return  true;
        }


        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intentt = new Intent(UserProfileActivity.this,
                    SingleUserProfileActivity.class);
            startActivity(intentt);
        } else {
            if (id == R.id.logout) {

                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(key, name);
                editor.apply();
                Log.d(TAG, "Now log out and start the activity login");
                Intent intent = new Intent(UserProfileActivity.this,
                        LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                LoginManager.getInstance().logOut();

            }
        }



        return super.onOptionsItemSelected(item);
    }





}
