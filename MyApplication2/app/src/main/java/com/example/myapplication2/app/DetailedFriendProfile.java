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

public class DetailedFriendProfile extends Activity{

    public static String detailedProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            try {
                showDetailedProfile();
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void showDetailedProfile() throws IOException, JSONException {

        // DETAILED PROFILE SELECTION
        ScrollView sv = new ScrollView(this);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll);

        ll.setBackgroundResource(R.drawable.bg);

        File file = new File(getFilesDir(), SavedProfiles.friendProfile);
        FileReader filereader = null;
        try {
            filereader = new FileReader(file);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        BufferedReader in = new BufferedReader(filereader);
        char[] buffer = new char[1000];
        String data;
        try {
            in.read(buffer);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        data = new String(buffer);
        JSONObject json = null;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String[] keys = {"Event Name", "Name", "Phone", "E-mail", "Additional Info 1", "Additional Info 2", "Additional Info 3",
                "Additional Info 4", "Additional Info 5", "Additional Info 6"};

        for(String key : keys) {
            TextView tv = new TextView(this);
            tv.setTextSize(30);

            if(json.has(key)) {
                tv.setText(key + ": " + (String)(json.get(key)));
                ll.addView(tv);
            }
        }
		/*
		Iterator<String> iter = json.keys();
		while(iter.hasNext()) {
			String key = iter.next();
			if(key.equals("Event Name")) {
				TextView tv = new TextView(this);
				tv.setTextSize(15);
				try {
					tv.setText(key + ": " + (String)(json.get(key)));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//ll.setBackgroundResource(R.drawable.bg);
				ll.addView(tv);
				break;
			}
		}
		*/

        this.setContentView(sv);

    }
}