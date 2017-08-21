package com.example.karshima.mappifyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.internal.http.multipart.MultipartEntity;
import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.RoundedImageView;
import com.example.karshima.mappifyapp.utility.Validations;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karshima on 3/2/2017.
 */

public class AddShopActivity extends AppCompatActivity implements View.OnClickListener  {
    private Toolbar toolbar;
    private EditText eshop,eshopemail,shophone,address,website,discount;
    private CheckBox c;
    private Button confirm;
    String mshop,msemail,mphone,madders,mywebsite,mdis,maid,mapic;
    private LinearLayout layout;
    private RoundedImageView imageView;
    private static final int PICK_IMAGE = 1;
    private Bitmap bitmap;
    private ProgressDialog dialog;private static final int SELECT_PICTURE = 1;
    private static final String TAG = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addshop);
        setUpview();
    }

    private void setUpview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add Shop");

        eshop=(EditText)findViewById(R.id.nshop);
        eshopemail=(EditText)findViewById(R.id.semail);
        shophone=(EditText)findViewById(R.id.phoneshop);
        address=(EditText)findViewById(R.id.mapaddress);
        website=(EditText)findViewById(R.id.website);
        discount=(EditText)findViewById(R.id.discount);
        c=(CheckBox)findViewById(R.id.check);
        c.setOnClickListener(this);
        imageView=(RoundedImageView)findViewById(R.id.imageee);
        imageView.setOnClickListener(this);

        confirm=(Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.confirm:
                mshop = eshop.getText().toString().trim();
                msemail = eshopemail.getText().toString().trim();
                mphone = shophone.getText().toString().trim();
                madders = address.getText().toString().trim();
                mywebsite = website.getText().toString().trim();
                mdis = discount.getText().toString().trim();

                if (mshop.equalsIgnoreCase("")) {
                    MyUtility.showSnack(layout, Validations.ENTER_NAME);
                    eshop.requestFocus();

                } else if (msemail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(msemail).matches()) {

                    eshopemail.setError("enter a valid email address");
                    eshopemail.requestFocus();

                } else if (mphone.isEmpty() || mphone.length() < 4 || mphone.length() > 10) {
                    shophone.setError("enter valid phone number");
                    shophone.requestFocus();

                } else if (madders.equalsIgnoreCase("")) {
                    MyUtility.showSnack(layout, Validations.ENTER_ADDRESS);
                    address.setError("Enter Address");
                    address.requestFocus();

                } else if (mywebsite.equalsIgnoreCase("")) {


                    MyUtility.showSnack(layout, Validations.ENTER_WEBSITE);
                    website.setError("Enter website");
                    website.requestFocus();

                } else if (mdis.equalsIgnoreCase("")) {

                    MyUtility.showSnack(layout, Validations.ENTER_DISCOUNT);
                }
                if (MyUtility.isConnected(this)) {

                    addshop();
                    //startActivity(new Intent(LoginActivity.this,CreateTripActivity.class));

                } else {

                    MyUtility.showSnack(layout, MyUtility.INTERNET_ERROR);



                }
                break;

            case R.id.imageee:

                openImageChooser();


                break;


        }

    }

    private void addshop() {


        Log.e("AT","onInvite");



        Log.e("user_id","" + maid);
        Log.e("email","" + msemail);
        Log.e("phone","" + mphone);
        Log.e("shope_name","" + mshop);
        Log.e("photo","" + mapic);
        Log.e("address","" + madders);
        Log.e("website","" + mywebsite);
        Log.e("discount","" + mdis);

        //thats all foe webservice calling and data fetching

        //you can show progress dialog before webservice execution
        //Let RUN the project

        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();
        //here we need to send parameters like email,phone name etc

        List<NameValuePair> parameters=new ArrayList<>();
        parameters.add(new BasicNameValuePair("user_id",maid));
        parameters.add(new BasicNameValuePair("email",msemail));
        parameters.add(new BasicNameValuePair("phone",mphone));
        parameters.add(new BasicNameValuePair("shope_name",mshop));
        parameters.add(new BasicNameValuePair("photo",mapic));
        parameters.add(new BasicNameValuePair("address",madders));
        parameters.add(new BasicNameValuePair("website",mywebsite));
        parameters.add(new BasicNameValuePair("discount",mdis));






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

                    if(status==1){

                        MyUtility.showToast("Shope Added!!!!",AddShopActivity.this);
                        startActivity(new Intent(AddShopActivity.this,ViewPrimiumCardActivity.class));


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
        sendRequest.execute("http://mappify.ccube9gs.com/app/user/add_shope");



    }
    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
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
