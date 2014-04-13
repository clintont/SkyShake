package com.example.myapplication2.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MyProfiles extends Activity implements OnClickListener{
	
	public static String detailedProfile;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        try {
			showProfiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public void showProfiles() throws IOException {
    	ScrollView sv = new ScrollView(this);
		LinearLayout ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		sv.addView(ll);

		TextView tv = new TextView(this);
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setText("My Profiles");
		//ll.setBackgroundResource(R.drawable.bg);
		ll.addView(tv);

		// BUTTON FOR NEW PROFILE
		Button b = new Button(this);
		b.setText("Create New Profile");
		b.setOnClickListener(this);
		b.setId(99999);
		ll.addView(b);
		
		// EXISTING PROFILES
		File dir = getFilesDir();
        File[] files = dir.listFiles();
        FileReader filereader;
        BufferedReader in;
        char[] buffer = new char[1000];
        String data;
        for(File file : files) {
        	if(!file.getName().contains("Profile"))
        		continue;
        	filereader = new FileReader(file);
        	in = new BufferedReader(filereader);
        	in.read(buffer);
        	data = new String(buffer);
        	in.close();
        	
        	b = new Button(this);
			b.setText(file.getName());
			b.setOnClickListener(this);
			ll.addView(b);
        }
        
		this.setContentView(sv);
    }
    
    public void onClick(View v) {
    	
    	// NEW PROFILE BUTTON
    	int id = v.getId();
    	if(id == 99999) {
    		Intent intent = new Intent(this, CreateForm.class);
    		startActivity(intent);
    		return;
    	}
    	
    	// DETAILED PROFILE SELECTION
    	detailedProfile = ((Button)v).getText().toString();
    	Intent intent = new Intent(this, DetailedProfile.class);
    	startActivity(intent);
    	
    }
}