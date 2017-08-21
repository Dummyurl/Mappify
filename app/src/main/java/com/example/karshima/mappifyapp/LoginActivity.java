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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.PreferencesInterface;
import com.example.karshima.mappifyapp.utility.Validations;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Karshima on 2/10/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,PreferencesInterface {
    private EditText email, password;
    private Button btn, btn2, register, guest, fb;
    private Toolbar toolbar;
    private TextView t1, forgot;

    private LinearLayout layout;
    //static JSONArray jsonArray = null;
    public static final String MyPREFERENCES = "MyPrefs" ;



    private String user_id, user_name, user_email,user_pass,myemail, mypass;
    private SharedPreferences sharedPreferences;

    private CallbackManager callbackManager;
    private LoginButton loginButton;

    private String social_name,
            social_email,
            social_id,
            social_image,
            social_type,
            login_by;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        setUpview();

    }

    private void setUpview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        layout = (LinearLayout) findViewById(R.id.parent);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);



        forgot = (TextView) findViewById(R.id.forgot);
        forgot.setOnClickListener(this);

        t1 = (TextView) findViewById(R.id.join);
        t1.setOnClickListener(this);


        btn = (Button) findViewById(R.id.login);
        btn.setOnClickListener(this);
        fb = (Button) findViewById(R.id.login_button);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginButton.performClick();

            }
        });

       facebook();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login:


                myemail = email.getText().toString().trim();
                mypass = password.getText().toString().trim();


                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(Password, mypass);
                editor.putString(Email, myemail);
                editor.commit();


                if (myemail.isEmpty()) {

                    email.setError("enter a valid email address");
                    email.requestFocus();
                } else if (myemail.equalsIgnoreCase("")) {


                    MyUtility.showSnack(layout, Validations.ENTER_EMAIL);


                } else if (mypass.isEmpty() || mypass.length() < 4 || mypass.length() > 10) {


                    password.setError("enter valid password");
                    password.requestFocus();

                } else if (mypass.equalsIgnoreCase("")) {

                    MyUtility.showSnack(layout, Validations.ENTER_PASSWORD);
                } else {


                    if (MyUtility.isConnected(this)) {

                        login();
                        //startActivity(new Intent(LoginActivity.this,CreateTripActivity.class));

                    } else {

                        MyUtility.showSnack(layout, MyUtility.INTERNET_ERROR);
                    }

                }

                break;
            case R.id.join:
                Intent ir = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(ir);
                break;

            case R.id.forgot:
                Intent iu = new Intent(LoginActivity.this, ForgotActivity.class);
                startActivity(iu);
                break;


        }

    }

    private void login() {
        Log.e("AT", "onLogin");

        Log.e("email",myemail);
        Log.e("password",mypass);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();

        List<NameValuePair> parameters = new ArrayList<>();
        parameters.add(new BasicNameValuePair("email", myemail));
        parameters.add(new BasicNameValuePair("password", mypass));

        GetData sendRequest = new GetData(parameters);
        sendRequest.setResultListener(new GetData.ResultListner() {
            @Override
            public void onSuccess(JSONObject json) {

                if (dialog.isShowing()) {

                    dialog.dismiss();
                }

                try {

                    int status = json.getInt("status");
                    Log.e("STATUS", status + "");

                    if (status == 1) {

                        JSONArray jsonArray = json.getJSONArray("user_login");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject c = jsonArray.getJSONObject(i);

                            SharedPreferences pref = getSharedPreferences(PreferencesInterface.MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString(PreferencesInterface.userId, c.getString("id"));
                            editor.putString(PreferencesInterface.Email, c.getString("email"));
                            editor.putString(PreferencesInterface.Name, c.getString("fname") +" "+c.getString("lname"));
                            editor.putString(PreferencesInterface.Image, c.getString("profile_image"));
                            editor.commit();

                            if (!pref.getString(PreferencesInterface.userId, "").equalsIgnoreCase("")) {
                               // startActivity(new Intent(LoginActivity.this, CreateTripActivity.class));
                                Intent im =new Intent(LoginActivity.this,CreateTripActivity.class);
                                im.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                im.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(im);
                                finish();

                            }





                        }

                    } else {

                        MyUtility.showToast("Incorrect Credentials", LoginActivity.this);
                    }
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
        sendRequest.execute("http://mappify.ccube9gs.com/app/User/user_login");


    }

    private void facebook() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_buttonf);
        loginButton.setReadPermissions(Arrays.asList("public_profile, email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {


            @Override
            public void onSuccess(final LoginResult loginResult) {


                Log.e("AT","onSuccess");

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                // Application code

                                JSONObject c = response.getJSONObject();

                                Log.e("AT",c.toString());
                                try {

                                    social_id = c.getString("id");
                                    social_name = c.getString("name");
                                    social_email = c.getString("email");
                                    social_image = "http://graph.facebook.com/" + c.getString("id") + "/picture?type=large";
                                    social_type = "2";

                                } catch (JSONException e) {
                                    e.printStackTrace();

                                }
                                login_by = "Facebook";





                                SocialRegister();


                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginActivity.this, "Login cancelled by user!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(LoginActivity.this, "Login unsuccessful!", Toast.LENGTH_LONG).show();

            }
        });


    }
   private void SocialRegister(){
       Log.e("AT","onSocialRegister");



       Log.e("social_id", "" + social_id);
       Log.e("name","" + social_name);
       Log.e("image","" +social_image);
       Log.e("email","" + social_email);


       final ProgressDialog dialog = new ProgressDialog(this);
       dialog.setMessage("Please wait...");
       dialog.show();

       List<NameValuePair> parameters=new ArrayList<>();
       parameters.add(new BasicNameValuePair("social_id",social_id));
       parameters.add(new BasicNameValuePair("name", social_name));
       parameters.add(new BasicNameValuePair("image", social_image));
       parameters.add(new BasicNameValuePair("email",social_email));


       GetData sendRequest = new GetData(parameters);
       sendRequest.setResultListener(new GetData.ResultListner() {




           @Override
           public void onSuccess(JSONObject json) {

               Log.e("social_id", "" + social_id);
               Log.e("name","" + social_name);
               Log.e("image","" +social_image);
               Log.e("email","" + social_email);


               if (dialog.isShowing()) {

                   dialog.dismiss();
               }

               try {

                   int status = json.getInt("status");
                   Log.e("STATUS", status + "");

                   if (status == 1) {

                       JSONArray jsonArray = json.getJSONArray("userinfo");

                       for (int i = 0; i < jsonArray.length(); i++) {





                     JSONObject c = jsonArray.getJSONObject(i);

                           SharedPreferences pref = getSharedPreferences(PreferencesInterface.MyPREFERENCES, Context.MODE_PRIVATE);
                           SharedPreferences.Editor editor = pref.edit();
                           editor.putString(PreferencesInterface.userSocial_id, c.getString("social_id"));
                           editor.putString(PreferencesInterface.Email, c.getString("email"));
                       editor.putString(PreferencesInterface.Name, c.getString("fname") );

                           editor.putString(PreferencesInterface.Image, c.getString("profile_image"));
                           editor.commit();

                          // if (!pref.getString(PreferencesInterface.userSocial_id, "").equalsIgnoreCase("")) {
                               // startActivity(new Intent(LoginActivity.this, CreateTripActivity.class));
                               Intent ime =new Intent(LoginActivity.this,CreateTripActivity.class);
                               ime.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               ime.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                               startActivity(ime);

                         //  }


                       }

                   } else {

                       MyUtility.showToast("Incorrect Credentials", LoginActivity.this);
                   }
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
       sendRequest.execute("http://mappify.ccube9gs.com/app/User/social_login");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
