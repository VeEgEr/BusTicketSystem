package com.example.veyisegemenerden.ticketsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by veyisegemenerden on 19.12.2016.
 */

public class Contact extends AppCompatActivity {
    Button button;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact);
        button=(Button) findViewById(R.id.backTOMainFromContact);


    }

    public void goMain(View v){
        Intent intent=new Intent(this,MainActivity.class);
        finish();
        startActivity(intent);
    }
}
