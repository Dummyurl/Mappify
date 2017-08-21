package com.example.karshima.mappifyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.apicall.JSONParser;
import com.example.karshima.mappifyapp.utility.CheckConnection;
import com.example.karshima.mappifyapp.utility.HelperClass;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.Validations;
import com.google.android.gms.vision.text.Line;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karshima on 3/8/2017.
 */

public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private Button submit;
    private EditText eotp, epassword;
    private String myemail,mypassword, myotp;
    private int success;

    private JSONParser jParser = new JSONParser();
    private JSONArray vjsonArray = null;
    private LinearLayout layout;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private SharedPreferences sharedpreferences;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);
        setUpview();
    }

    private void setUpview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Reset Password");

        myemail=getIntent().getStringExtra("email");

        epassword = (EditText) findViewById(R.id.editTextChangePassword);
        eotp = (EditText) findViewById(R.id.editTextChangeOTP);
        submit = (Button) findViewById(R.id.buttonChangeSubmit);
        submit.setOnClickListener(this);


    }




    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.buttonChangeSubmit:

                mypassword=epassword.getText().toString().trim();
                myotp=eotp.getText().toString().trim();


                if (!myotp.equals("") && !mypassword.equals("")) {

                    if (myotp.length() < 6) {

                        MyUtility.showSnack(layout, Validations.ENTER_VALID_OTP);


                    } else if (mypassword.length() < 6) {

                        MyUtility.showSnack(layout, Validations.ENTER_PASSWORD_GREATER);


                    } else {

                        if (MyUtility.isConnected(this)) {
                            passwordchange();



                        } else {

                            MyUtility.showSnack(layout, MyUtility.INTERNET_ERROR);

                        }

                    }


                }





                break;


        }
    }

    private void passwordchange() {

        Log.e("AT","onChange");



        Log.e("new_password","" + mypassword);
        Log.e("otp","" + myotp);
        Log.e("email",""+ myemail);


        //thats all foe webservice calling and data fetching

        //you can show progress dialog before webservice execution
        //Let RUN the project

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();


        //here we need to send parameters like email,phone name etc

        List<NameValuePair> parameters=new ArrayList<>();



        parameters.add(new BasicNameValuePair("new_password",mypassword));
        parameters.add(new BasicNameValuePair("otp",myotp));
        parameters.add(new BasicNameValuePair("email",myemail));

        GetData sendRequest=new GetData(parameters);
        sendRequest.setResultListener(new GetData.ResultListner() {
            @Override
            public void onSuccess(JSONObject json) {


                Log.e("new_password","" + mypassword);
                Log.e("otp","" + myotp);
                Log.e("email",""+ myemail);

                if(dialog.isShowing()){

                    dialog.dismiss();
                }

                //this method call if webservice success

                try{

                    int status=json.getInt("status");

                    //webservice working successfully here you have to read all data coming from webservice
                    //if status ==1 then redirect user to login

                    if(status==1){

                        MyUtility.showToast("Password has been changed.Please login here.",ChangePasswordActivity.this);
                        Intent intent=new Intent(ChangePasswordActivity.this,LoginActivity.class);
                        startActivity(intent);



                    }

                    Log.e("STATUS",status+"");

                }catch (Exception e){


                    e.printStackTrace();

                }
            }

            @Override
            public void onFailed() {

                if(dialog.isShowing()){

                    dialog.dismiss();
                }


                Log.e("Webservice","failed");

            }
        });
        sendRequest.execute("http://mappify.ccube9gs.com/app/User/check_otp_password_change");




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
