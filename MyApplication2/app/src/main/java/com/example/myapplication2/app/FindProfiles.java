package com.example.myapplication2.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication2.app.MainActivity;
import com.example.myapplication2.app.NetworkThread;

public class FindProfiles extends Activity implements OnClickListener{
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
                ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);



        updateProfiles();
    }
	
	public void updateProfiles() {
		/*
        final ListView listview = new ListView(this);
        listview.setAdapter(MainActivity.bluetoothList);
        listview.setOnItemClickListener(new OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		String selected = ((TextView) view).getText().toString();
        		Toast toast = Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_SHORT);
        		toast.show();
        	}
        });
        */
    	ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);

		TextView tv = new TextView(this);
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setText("Cool People");
		//ll.setBackgroundResource(R.drawable.bg);
		ll.addView(tv);

		//ll.addView(listview);
		
		for (int i = 0; i < MainActivity.bluetoothList.size(); i++) {
			Button b = new Button(this);
			b.setText(MainActivity.bluetoothList.get(i)[1]);
			b.setOnClickListener(this);
			b.setId(i);
			ll.addView(b);
		}
		
		this.setContentView(sv);
    }
    
    public void onClick(View v) {
    	int id = v.getId();
    	String mac = MainActivity.bluetoothList.get(id)[0];
    	JSONObject json = NetworkThread.receiveProfile(mac);
    	
    	String FILENAME = MainActivity.bluetoothList.get(id)[1];

        File file = new File(getFilesDir(), FILENAME);
        FileWriter filewriter = null;
		try {
			filewriter = new FileWriter(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BufferedWriter out = new BufferedWriter(filewriter);
        try {
			out.write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	
}
