package com.example.karshima.mappifyapp;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.LeadingMarginSpan;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.karshima.mappifyapp.R.id.register;

/**
 * Created by Karshima on 3/9/2017.
 */

public class ChatSettingActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private LinearLayout linearLayout;
    private TextView t1, t2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chat_setting);
        setUpview();

    }

    private void setUpview() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chat");

        t1 = (TextView) findViewById(R.id.select);
        t1.setOnClickListener(this);
        t2 = (TextView) findViewById(R.id.size);
        t2.setOnClickListener(this);


    }


    @Override
    public void onClick(View view) {

    }


    public boolean onTouch(View v, MotionEvent event) {

        switch (v.getId()) {

            case R.id.select:

                if (MotionEvent.ACTION_UP == event.getAction()) {

                    language_Dialog();
                }


                return true;

            case R.id.size:

                if (MotionEvent.ACTION_UP == event.getAction()) {
                    size_Dialog();
                }


                return true;

        }
        return false;
    }

    public void onFocusChange(View v, boolean hasFocus) {


        switch (v.getId()) {

            case R.id.select:

                if (hasFocus) {

                    language_Dialog();

                }


                break;

            case R.id.size:

                if (hasFocus) {


                    size_Dialog();

                }


                break;
        }
    }

    private void language_Dialog() {

        final String[] items = new String[]{"English", "Spanish","French","Italian","Portuguese","German","Russian","Catalan"};


        AlertDialog.Builder ad = new AlertDialog.Builder(ChatSettingActivity.this);
        ad.setTitle("Select Language");
        ad.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                t1.setText(items[which]);
            }
        });
        ad.show();
    }

    private void size_Dialog() {

        final String[] items = new String[]{"Medium", "Small","Large"};

        AlertDialog.Builder ad = new AlertDialog.Builder(ChatSettingActivity.this);
        ad.setTitle("Select Size");
        ad.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                t2.setText(items[which]);
            }
        });
        ad.show();
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

