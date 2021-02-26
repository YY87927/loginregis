package com.example.loginregis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button bluetooth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bluetooth = findViewById(R.id.bluetooth);
        bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, bluetooth_detect.class);
                startActivity(intent);
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, register.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, login.class);
        startActivity(intent);
    }


}