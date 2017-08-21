package com.example.karshima.mappifyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karshima.mappifyapp.apicall.GetData;
import com.example.karshima.mappifyapp.utility.MyUtility;
import com.example.karshima.mappifyapp.utility.PreferencesInterface;
import com.example.karshima.mappifyapp.utility.RoundedImageView;
import com.example.karshima.mappifyapp.utility.Utils;
import com.facebook.internal.Utility;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.type;
import static com.example.karshima.mappifyapp.R.id.iv_singleplayerimage;
import static com.example.karshima.mappifyapp.utility.PreferencesInterface.userId;

/**
 * Created by Karshima on 3/8/2017.
 */

public class SingleUserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private Toolbar toolbar;
    private RoundedImageView img;
    private static final int SELECT_PICTURE = 1;
    private static final String TAG = "MainActivity";
    private LinearLayout layout;
    String user_id,user_image;
    private SharedPreferences sharedPreferences;
    String responseString = null;
    private static final int REQUEST_CAMERA = 100;
    private static final int SELECT_FILE = 200;
    private Uri fileUri;
    private String userChoosenTask;

    private TextView t1,t2;
   // private Uri filePath;
    private Button btn;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    private MixpanelAPI mixpanelAPI;
    private String filePath = null;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_user_profile);
        sharedPreferences=getSharedPreferences(PreferencesInterface.MyPREFERENCES, Context.MODE_PRIVATE);


  /*      if (sharedPreferences.contains(userId))
        {

            user_id=sharedPreferences.getString(userId,"");


        }else{

            finish();
            Intent i=new Intent(SingleUserProfileActivity.this,SplashScreenActivity.class);
            startActivity(i);

        }

        mixpanelAPI= MixpanelAPI.getInstance(this, Utils.MIXPANEL_TOKEN);

        try{

            mixpanelAPI.track("Picture Upload");

        }catch (Exception e){

            e.printStackTrace();
        }*/


        setUpview();

    }

    private void setUpview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Profile");


        img = (RoundedImageView) findViewById(iv_singleplayerimage);
        img.setOnClickListener(this);
        t1=(TextView)findViewById(R.id.gname);
        t2=(TextView)findViewById(R.id.gmail);
       btn=(Button)findViewById(R.id.upload);
       btn.setOnClickListener(this);

       /* SharedPreferences shre = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit=shre.edit();
        edit.putString("imagepath","/sdcard/");
        edit.commit();*/

        t1.setText(sharedPreferences.getString(PreferencesInterface.Name,""));
        t2.setText(sharedPreferences.getString(PreferencesInterface.Email,""));

        // Receiving the data from previous activity
      /*  Intent i = getIntent();

        // image or video path that is captured in previous activity
        filePath = i.getStringExtra("filePath");

        // boolean flag to identify the media type, image or video
        boolean isImage = i.getBooleanExtra("isImage", true);

        if (filePath != null) {
            // Displaying the image or video on the screen
            previewMedia(isImage);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
        }*/





    }


 /* Choose an image from Gallery */
  /* void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

  /*  public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // Get the path from the Uri
                    String path = getPathFromURI(selectedImageUri);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    img.setImageURI(selectedImageUri);
                }
            }
        }
    }*/

    /* Get the real path from the URI */
  /*public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }*/
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.upload:
           // openImageChooser();
                selectImage();


              /*  if (MyUtility.isConnected(this)){

                     UploadFileToServer();

                }else{

                    Toast.makeText(SingleUserProfileActivity.this, "Please check your internet connection", Toast.LENGTH_LONG).show();
                }*/

                    break;


        }
    }


    private void selectImage() {

        final String [] items=new String[]{

                "Camera",
                "Gallery"

        };

        AlertDialog.Builder ad=new AlertDialog.Builder(this);
        ad.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                switch (items[i]){

                    case "Camera":


                        boolean result = MyUtility.checkPermission(SingleUserProfileActivity.this);
                        userChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();

                        break;

                    case "Gallery":

                        boolean result1 = MyUtility.checkPermission(SingleUserProfileActivity.this);
                        userChoosenTask = "Choose from Library";
                        if (result1)
                            galleryIntent();

                        break;
                }
            }
        });

        ad.show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MyUtility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if(userChoosenTask.equals("Take Photo"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Choose from Library"))
                        galleryIntent();

                } else {
                    //code for deny
                }
                break;
        }
    }


    private void galleryIntent() {

        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);


    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = MyUtility.getOutputMediaFileUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {

                launchUploadActivity(true,fileUri.getPath());

            } else if (requestCode == SELECT_FILE) {

                Uri selectedImageUri = data.getData();
                String[] projection = { MediaStore.MediaColumns.DATA };
                Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                        null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);


                launchUploadActivity(true,selectedImagePath);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);

    }

    private void launchUploadActivity(boolean isImage,String path){

        //  Uri uri= Crop.getOutput(data);
        Intent i = new Intent(this, SettingActivity.class);
        i.putExtra("filePath", path);
        i.putExtra("isImage", true);
        i.putExtra("type", type);
        startActivity(i);

    }

    private void UploadFileToServer() {

        Log.e("AT","onUpload");

        Log.e("user_id","" +user_id);
        Log.e("photo","" +user_image);

        //thats all foe webservice calling and data fetching



        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Please wait...");
        dialog.show();


        //here we need to send parameters like email,phone name etc

        List<NameValuePair> parameters=new ArrayList<>();


        parameters.add(new BasicNameValuePair("user_id",user_id));
        parameters.add(new BasicNameValuePair("photo",user_image));

        GetData sendRequest=new GetData(parameters);
        sendRequest.setResultListener(new GetData.ResultListner() {
            @Override
            public void onSuccess(JSONObject json) {

                Log.e("user_id","" +user_id);
                Log.e("photo","" +user_image);


                if(dialog.isShowing()){

                    dialog.dismiss();
                }

                //this method call if webservice success

                try{

                    int status=json.getInt("status");

                    //webservice working successfully here you have to read all data coming from webservice
                    //if status ==1 then redirect user to login

                    if(status==1){

                        MyUtility.showToast("Image is Uploaded!!!",SingleUserProfileActivity.this);
                        startActivity(new Intent(SingleUserProfileActivity.this,LoginActivity.class));


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
        sendRequest.execute("http://mappify.ccube9gs.com/app/User/upload_user_profile");


    }


    /**
     * Displaying captured image/video on the screen
     * */
    private void previewMedia(boolean isImage) {
        // Checking whether captured media is image or video
        if (isImage) {
            img.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // down sizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

            img.setImageBitmap(bitmap);
        } else {
            img.setVisibility(View.GONE);

        }
    }



    /**
     * Method to show alert dialog
     * */
    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(responseString).setTitle("Message")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // do nothing

                        Intent  i=new Intent(SingleUserProfileActivity.this,LoginActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        i.putExtra("profile","update");

                        startActivity(i);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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
