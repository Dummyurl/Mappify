package com.example.karshima.mappifyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.PreferencesInterface;
import com.example.karshima.mappifyapp.utility.RoundedImageView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karshima on 3/8/2017.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private LinearLayout layout;
    private RelativeLayout relativeLayout,r,r1,r2;
    private String myyemail,myimage,myname,user_id;
    private RoundedImageView imageView;
    private TextView name,email;

    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);


       sharedPreferences=getSharedPreferences(PreferencesInterface.MyPREFERENCES, Context.MODE_PRIVATE);


        setUpview();

    }

    private void setUpview() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Account Settings");

        layout=(LinearLayout)findViewById(R.id.rearear);
        layout.setOnClickListener(this);
        relativeLayout=(RelativeLayout)findViewById(R.id.rtrr);
        relativeLayout.setOnClickListener(this);
        r=(RelativeLayout)findViewById(R.id.rt);
        r.setOnClickListener(this);
        r1=(RelativeLayout)findViewById(R.id.ryyyyyyyyyyy);
        r1.setOnClickListener(this);
        r2=(RelativeLayout)findViewById(R.id.ri);
        r2.setOnClickListener(this);

        imageView=(RoundedImageView)findViewById(R.id.user_imagee);
        imageView.setOnClickListener(this);
        name=(TextView)findViewById(R.id.user_name);
        email=(TextView)findViewById(R.id.user_email);

        name.setText(sharedPreferences.getString(PreferencesInterface.Name,""));
        email.setText(sharedPreferences.getString(PreferencesInterface.Email,""));


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rearear:


                Intent iter=new Intent(SettingActivity.this,SingleUserProfileActivity.class);
                startActivity(iter);
                break;

            case R.id.rtrr:
               Intent in=new Intent(SettingActivity.this,SendOTP.class);
                startActivity(in);
                break;


            case R.id.rt:
                Intent inn=new Intent(SettingActivity.this,NotificationActivity.class);
                startActivity(inn);
                break;


            case R.id.ryyyyyyyyyyy:
                Intent intent=new Intent(SettingActivity.this,ChatSettingActivity.class);
                startActivity(intent);
                break;

            case R.id.ri:
                Intent inteneet=new Intent(SettingActivity.this,AboutHelpActivity.class);
                startActivity(inteneet);
                break;


        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:

                onBackPressed();

                return  true;
        }

        return super.onOptionsItemSelected(item);
    }
}
