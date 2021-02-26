package com.example.loginregis;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class register extends AppCompatActivity {
    EditText stuid ;
    EditText name ;
    EditText email ;
    EditText phone ;
    EditText password ;
    Switch disp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        phone = findViewById(R.id.editTextPhone);
        email = findViewById(R.id.mail);
        stuid = findViewById(R.id.editTextNumberPassword);
        stuid.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        password = findViewById(R.id.editTextTextPassword7);
        disp = findViewById((R.id.switch3));
    }


    public void display(View view){
        if(disp.getText().toString().equals("顯示")){
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            disp.setText("隱藏")  ;
        } else{
            password.setTransformationMethod(PasswordTransformationMethod.getInstance()); ;
            disp.setText("顯示")  ;
        }
    }
    public void finish(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 將資料寫入資料庫
                MysqlCon con = new MysqlCon();
                String[] data = {"","","","",""};
                data[0] = name.getText().toString();
                data[1] = phone.getText().toString();
                data[2] = email.getText().toString();
                data[3] = stuid.getText().toString();
                data[4] = password.getText().toString();
                con.insertData(data);
                // 讀取更新後的資料
                final String updata = con.getData();
                Log.v("OK", updata);
            }
        }).start();

        Toast.makeText(this,"OK",Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(register.this, verify.class);
        Bundle bundle = new Bundle();
        bundle.putString("id",stuid.getText().toString());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}