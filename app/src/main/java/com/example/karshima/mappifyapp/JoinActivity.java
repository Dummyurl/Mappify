package com.example.karshima.mappifyapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.attr.onClick;

/**
 * Created by Karshima on 2/10/2017.
 */

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn;
    private TextView t3;
    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_activity);
        setUpview();
    }

    private void setUpview() {
        btn=(Button)findViewById(R.id.start);
        btn.setOnClickListener(this);

        t3=(TextView)findViewById(R.id.exist);
        t3.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start:
                Intent in=new Intent(JoinActivity.this,SignUpActivity.class);
                startActivity(in);
                break;
            case R.id.exist:
                Intent ie=new Intent(JoinActivity.this,LoginActivity.class);
                startActivity(ie);
                break;
        }
    }
}
