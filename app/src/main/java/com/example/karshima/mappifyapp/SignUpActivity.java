package com.example.karshima.mappifyapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.Validations;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Karshima on 2/10/2017.
 */

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText eEmail, eName, esurname, ePassword, eBirthday;
    private String date, time, type = "";
    private ImageView imageViewfemale, imageViewmale;
    private Button btn;
    Calendar myCalendar = Calendar.getInstance();
    private LinearLayout layout;
    private TextView female,male;


    String myemail,mypass,myname,mysur,mygender,mydob;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_up_activity);
        setUpview();

    }

    private void setUpview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign Up");


        eEmail = (EditText) findViewById(R.id.emailwew);
        eName = (EditText) findViewById(R.id.name);
        esurname = (EditText) findViewById(R.id.surname);
        ePassword = (EditText) findViewById(R.id.paswerd);
        btn = (Button) findViewById(R.id.register);
        btn.setOnClickListener(this);
        eBirthday = (EditText) findViewById(R.id.birthday);


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        eBirthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        imageViewfemale=(ImageView)findViewById(R.id.female);
        imageViewfemale.setOnClickListener(this);
        imageViewmale=(ImageView)findViewById(R.id.male);
        imageViewmale.setOnClickListener(this);


        imageViewfemale.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (MotionEvent.ACTION_UP == event.getAction()) {

                    mygender="Female";

                    Toast.makeText(SignUpActivity.this,"I am Female",Toast.LENGTH_LONG).show();
                }




                return true;
            }
        });




        imageViewmale.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                if (MotionEvent.ACTION_UP == event.getAction()) {

                    mygender="Male";

                    Toast.makeText(SignUpActivity.this,"I am Male",Toast.LENGTH_LONG).show();
                }


                return true;
            }
        });




    }

    private void updateLabel() {

        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        eBirthday.setText(sdf.format(myCalendar.getTime()));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:

                myemail = eEmail.getText().toString();
                mypass = ePassword.getText().toString();
                myname = eName.getText().toString();
                mysur = esurname.getText().toString();

                mydob=eBirthday.getText().toString();



                if (myemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(myemail).matches()) {

                    eEmail.setError("enter a valid email address");
                    eEmail.requestFocus();
                } else if (myemail.equalsIgnoreCase("")) {


                    eEmail.setError("enter email address");


                } else if (myname.equalsIgnoreCase("")){
                    eName.setError("enter Name");
                    eName.requestFocus();


                }else if(mysur.equalsIgnoreCase("")){
                    esurname.setError("enter Surname");
                    esurname.requestFocus();


                } else if (mypass.isEmpty() || mypass.length() < 4 || mypass.length() > 10) {


                    ePassword.setError("enter valid password");
                    ePassword.requestFocus();

                } else if (mypass.equalsIgnoreCase("")) {

                    ePassword.setError("enter password");

                }else if (MyUtility.isConnected(this)){


                    register();


                }
                else{
                    MyUtility.showSnack(layout, MyUtility.INTERNET_ERROR);
                }


            case R.id.donna:

                mygender="Female";

             //   Toast.makeText(SignUpActivity.this,"I am female",Toast.LENGTH_LONG).show();

                break;

            case R.id.uomo:

                mygender="Male";

               // Toast.makeText(SignUpActivity.this,"I am male",Toast.LENGTH_LONG).show();

                break;
        }
    }

    private void register() {
        Log.e("AT","onRegister");



        Log.e("fname",myname);
        Log.e("lname",mysur);
        Log.e("email",myemail);
        Log.e("password",mypass);
        Log.e("gender",mygender);
        Log.e("dob",mydob);
        //thats all foe webservice calling and data fetching

        //you can show progress dialog before webservice execution
        //Let RUN the project

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();


        //here we need to send parameters like email,phone name etc

        List<NameValuePair> parameters=new ArrayList<>();
        parameters.add(new BasicNameValuePair("fname",myname));
        parameters.add(new BasicNameValuePair("lname",mysur));
        parameters.add(new BasicNameValuePair("email",myemail));
        parameters.add(new BasicNameValuePair("password",mypass));
        parameters.add(new BasicNameValuePair("gender",mygender));
        parameters.add(new BasicNameValuePair("dob",mydob));


        //have you understand?s
        //okay let call our GetData calls pass all info

        GetData sendRequest=new GetData(parameters);
        sendRequest.setResultListener(new GetData.ResultListner() {
            @Override
            public void onSuccess(JSONObject json) {

                if(dialog.isShowing()){

                    dialog.dismiss();
                }

                //this method call if webservice success

                try{

                    int status=json.getInt("status");

                    //webservice working successfully here you have to read all data coming from webservice
                    //if status ==1 then redirect user to login

                    if(status==1) {

                        MyUtility.showToast("Registered Successfully...Login here", SignUpActivity.this);
                        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));


                    }


                    if (myemail.equals (json)){
                        Toast.makeText(getApplicationContext(), "Username already exist!", Toast.LENGTH_SHORT).show();

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
        sendRequest.execute("http://mappify.ccube9gs.com/app/User/user_registration");



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
}