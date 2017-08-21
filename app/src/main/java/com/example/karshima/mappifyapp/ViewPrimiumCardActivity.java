package com.example.karshima.mappifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Karshima on 3/8/2017.
 */

public class ViewPrimiumCardActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewprimum_card);
        setUpview();
    }

    private void setUpview() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Premium Card");

        imageView=(ImageView)findViewById(R.id.category);
        imageView.setOnClickListener(this);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.category:

               // Intent intent=new Intent(ViewPrimiumCardActivity.this,CatagoriesActivity.class);
              //  startActivity(intent);
                break;
        }

    }
}
