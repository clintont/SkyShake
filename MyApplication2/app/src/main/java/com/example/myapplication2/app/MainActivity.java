package com.example.myapplication2.app;

import java.util.ArrayList;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;


import com.example.myapplication2.app.MyProfiles;
import com.example.myapplication2.app.NetworkThread;
import com.example.myapplication2.app.R;


public class MainActivity extends Activity {

    public static ArrayList<String[]> bluetoothList = new ArrayList<String[]>();
    private BluetoothAdapter mBluetoothAdapter;
    private final static int REQUEST_ENABLE_BT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //bluetoothList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        initialize();
        turnBTOn();
        discoverAndList();
    }

    public void initialize() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Log.e("Adapter", "Unable to obtain a BluetoothAdapter.");
        }
    }

    public void discoverAndList()
    {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
        if (mBluetoothAdapter.startDiscovery())
        {
            Log.d("Bluetooth", "Ran Discovery");
        }
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
                Log.d("Devices Detected: ", device.getAddress());
                String name = NetworkThread.hasName(device.getAddress());
                if (name.length() <= 0 || name.equals("null"))
                    return;
                Log.e("name", name);
                String[] newDevice = {device.getAddress(), name};
                bluetoothList.add(newDevice);

            }
        }
    };

    protected void turnBTOn() {
        // If Bluetooth is not on, request that it be enabled.
        Log.e("Startup", "Bluetooth activity started!");
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d("Adapter", "Bluetooth Enabled!");
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        } else {
            NetworkThread.BTAddress = mBluetoothAdapter.getAddress();
            Log.d("MAC", mBluetoothAdapter.getAddress());
            //Find
        }
    }

    public void findProfiles(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, FindProfiles.class);
        startActivity(intent);
    }

    public void myProfiles(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, MyProfiles.class);
        startActivity(intent);
    }

    public void savedProfiles(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, SavedProfiles.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
