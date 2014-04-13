package com.example.myapplication2.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

public class SavedProfiles extends Activity implements OnClickListener {
	
	public static String friendProfile;
	
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

        ll.setBackgroundResource(R.drawable.bg);

		TextView tv = new TextView(this);
		tv.setTextSize(30);
		tv.setGravity(Gravity.CENTER_HORIZONTAL);
		tv.setText("Cool People");
		//ll.setBackgroundResource(R.drawable.bg);
		ll.addView(tv);
			
		// EXISTING PROFILES
		File dir = getFilesDir();
	    File[] files = dir.listFiles();
	    FileReader filereader;
	    BufferedReader in;
	    char[] buffer = new char[1000];
	    String data;
	    for(File file : files) {
	        if(file.getName().contains("Profile"))
	        	continue;
	        filereader = new FileReader(file);
	        in = new BufferedReader(filereader);
	        in.read(buffer);
	        data = new String(buffer);
	        in.close();
	        
	        Button b = new Button(this);
			b.setText(file.getName());
			b.setOnClickListener(this);
			ll.addView(b);
	    }
	        
	    this.setContentView(sv);
	}
	    
	public void onClick(View v) {
	    	
	    // DETAILED PROFILE SELECTION
	    friendProfile = ((Button)v).getText().toString();
	    Intent intent = new Intent(this, DetailedFriendProfile.class);
	    startActivity(intent);
	    	
	}
}