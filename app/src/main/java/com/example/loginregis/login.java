package com.example.loginregis;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Date;
import java.util.Hashtable;
import java.util.TimeZone;

public class login extends AppCompatActivity {

    EditText stuid ;
    EditText pas ;
    ImageView ivCode;
    Switch disp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        stuid = (EditText) findViewById(R.id.stid);
        stuid.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        pas = (EditText) findViewById(R.id.editTextPassword7);
        ivCode = (ImageView) findViewById(R.id.ivCode);
        disp = (Switch)  findViewById(R.id.switch1);
    }
    public void display(View view){
        if(disp.getText().toString().equals("顯示")){
            pas.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            disp.setText("隱藏")  ;
        } else{
            pas.setTransformationMethod(PasswordTransformationMethod.getInstance()); ;
            disp.setText("顯示")  ;
        }
    }
    public void fin(View view) throws InterruptedException {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dff.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String ee = dff.format(new Date());
        final String id_text = stuid.getText().toString();
        final String pass = pas.getText().toString();
        final boolean[] l = {false};
        final Object ck = new Object();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 將資料寫入資料庫
                synchronized (ck){
                    MysqlCon con = new MysqlCon();
                    l[0] = con.log_in(id_text,pass);
                    ck.notify();
                }
                // 讀取更新後的資料
            }
        });
        thread.start();
        synchronized (ck){
            Log.v("OK","waiting");
            ck.wait();
            Log.v("OK","complete");
        }
        if(l[0]){
            Toast.makeText(this,"登入成功",Toast.LENGTH_SHORT).show();
            String result = id_text + "\n" + ee;
            Hashtable hints = new Hashtable();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            BarcodeEncoder encoder = new BarcodeEncoder();
            try {
                Bitmap bit = encoder.encodeBitmap(result, BarcodeFormat.QR_CODE, 1000, 1000,hints);
                ivCode.setImageBitmap(bit);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }
        else{
            Log.e("DB", "登入失敗~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" );
            Toast.makeText(this,"帳號或密碼有誤",Toast.LENGTH_SHORT).show();
        }
    }

}