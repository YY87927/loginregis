package com.example.loginregis;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class bluetooth_detect extends Activity {

    final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private TextView textview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_detect);
        Button boton = (Button) findViewById(R.id.start_discovery);
        boton.setOnClickListener(new OnClickListener(){
            public void onClick(View v) {
                System.out.println("start discovery");
                textview.setText("");
                bluetoothScanning();
            }
        });
        textview = (TextView)findViewById(R.id.btList);
    }

    void bluetoothScanning(){
        System.out.println("start scanning");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        mBluetoothAdapter.startDiscovery();
    }
    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress(); // MAC address
                int  rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI,Short.MIN_VALUE);
                Log.i("RSSI", "device rssi: " + rssi);
                Log.i("Device Name: " , "device " + deviceName);
                Log.i("deviceHardwareAddress " , deviceHardwareAddress);
                textview.append("Address: " + deviceHardwareAddress + "  RSSI: " + rssi + "dB\n");
            }
            else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                System.out.println("discovery finished");
                mBluetoothAdapter.cancelDiscovery();
            }
        }
    };
}