package com.example.karshima.mappifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by Karshima on 2/23/2017.
 */

public class PrimiumOptionActivity extends AppCompatActivity implements View.OnClickListener {
private Toolbar toolbar;
    private RelativeLayout n,n1,n2,n3,n4,n5;
    private ImageView img,img1,img2,img3;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.primiumoptions);
        setUpview();
    }

    private void setUpview() {
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Premium");


        n=(RelativeLayout) findViewById(R.id.r1);
        n.setOnClickListener(this);

        n1=(RelativeLayout) findViewById(R.id.r2);
        n1.setOnClickListener(this);

        n2=(RelativeLayout) findViewById(R.id.r3);
        n2.setOnClickListener(this);

        n3=(RelativeLayout) findViewById(R.id.r4);
        n3.setOnClickListener(this);

        n4=(RelativeLayout) findViewById(R.id.r5);
        n4.setOnClickListener(this);

        n5=(RelativeLayout) findViewById(R.id.r6);
        n5.setOnClickListener(this);

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
        img2.setImageResource(R.drawable.greyprofile);
        img3.setImageResource(R.drawable.greenpremium);


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.r1:
                Intent intent=new Intent(PrimiumOptionActivity.this,AddShopActivity.class);
                startActivity(intent);
                break;

            case R.id.literary:

                img.setImageResource(R.drawable.greeniternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(0);



                Intent i=new Intent(PrimiumOptionActivity.this,MapsActivity.class);
                startActivity(i);
                break;


            case R.id.activity:
                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greenactivity);
                img2.setImageResource(R.drawable.greyprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(1);
                Toast.makeText(PrimiumOptionActivity.this,"Activity",Toast.LENGTH_LONG).show();

                break;
            case R.id.profile:
                img.setImageResource(R.drawable.greyiternary);
                img1.setImageResource(R.drawable.greyactivity);
                img2.setImageResource(R.drawable.greenprofile);
                img3.setImageResource(R.drawable.greypremium);
                displayView(2);

                Intent ir=new Intent(PrimiumOptionActivity.this,UserProfileActivity.class);
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
                Intent in=new Intent(PrimiumOptionActivity.this,PrimiumOptionActivity.class);
                startActivity(in);
                break;


        }

    }

    private void displayView(int i) {
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


