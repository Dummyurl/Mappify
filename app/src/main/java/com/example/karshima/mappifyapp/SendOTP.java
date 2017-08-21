package com.example.karshima.mappifyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.PreferencesInterface;
import com.example.karshima.mappifyapp.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karshima on 3/20/2017.
 */
public class SendOTP extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private Button btn;
    private EditText e1;
    String email;
    private LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_otp);
        setUpview();

    }

    private void setUpview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Send OTP");

        e1 = (EditText) findViewById(R.id.sendemail);

        btn = (Button) findViewById(R.id.sentotp);
        btn.setOnClickListener(this);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:

                onBackPressed();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {


        switch (view.getId()) {

            case R.id.sentotp:
                email = e1.getText().toString().trim();

                if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    e1.setError("enter a valid email address");
                    e1.requestFocus();
                } else if (email.equalsIgnoreCase("")) {


                    MyUtility.showSnack(layout, Validations.ENTER_EMAIL);
                } else if (MyUtility.isConnected(this)) {

                    sendotp();
                    //startActivity(new Intent(LoginActivity.this,CreateTripActivity.class));

                } else {

                    MyUtility.showSnack(layout, MyUtility.INTERNET_ERROR);
                }


                break;

        }
    }

    private void sendotp() {
        Log.e("AT", "onSend");

        Log.e("email", "" + email);

        //thats all foe webservice calling and data fetching

        //you can show progress dialog before webservice execution
        //Let RUN the project

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();


        //here we need to send parameters like email,phone name etc

        List<NameValuePair> parameters = new ArrayList<>();


        parameters.add(new BasicNameValuePair("email", email));

        GetData sendRequest = new GetData(parameters);
        sendRequest.setResultListener(new GetData.ResultListner() {
            @Override
            public void onSuccess(JSONObject json) {

                if (dialog.isShowing()) {

                    dialog.dismiss();
                }

                //this method call if webservice success

                try {

                    int status = json.getInt("status");

                    //webservice working successfully here you have to read all data coming from webservice
                    //if status ==1 then redirect user to login

                    if (status == 1) {

                        MyUtility.showToast("OTP Send,Check your mail!!", SendOTP.this);
                        // startActivity(new Intent(SendOTP.this,ChangePasswordActivity.class));

                        Intent i = new Intent(SendOTP.this, ChangePasswordActivity.class);
                        i.putExtra("email", email);
                        startActivity(i);


                    }

                    Log.e("STATUS", status + "");

                } catch (Exception e) {


                    e.printStackTrace();

                }
            }

            @Override
            public void onFailed() {

                if (dialog.isShowing()) {

                    dialog.dismiss();
                }


                Log.e("Webservice", "failed");

            }
        });
        sendRequest.execute("http://mappify.ccube9gs.com/app/user/send_otp");

    }



    }
