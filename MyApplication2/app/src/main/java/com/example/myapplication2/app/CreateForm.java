package com.example.myapplication2.app;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.view.View;
import com.example.myapplication2.app.FormConstants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateForm extends Activity {

    private int edittexts;
    public static ArrayList<String[]> fieldTerm = new ArrayList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.
        ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_create_form);
        edittexts = FormConstants.FIELD_START_VALUE;
        findViewById(R.id.eventname).setId(edittexts++);
        findViewById(R.id.name).setId(edittexts++);
        findViewById(R.id.phone).setId(edittexts++);
        findViewById(R.id.email).setId(edittexts++);
    }


    public void addField(View view)
    {
 /*       TextView tv = new TextView(this);

        tv.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                150, LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT;
        tv.setLayoutParams(params);
        tv.setTextSize(16);
        final ListView formlistview = (ListView) findViewById(R.id.formlistview);
        formlistview.addView(tv);
*/

        EditText tv = new EditText(this);
        tv.setLayoutParams(new LinearLayout.LayoutParams(500, LayoutParams.WRAP_CONTENT));
        tv.setHint("Additional Info " + Integer.toString(edittexts-1003));
        tv.setId(edittexts++);
        ((LinearLayout) findViewById(R.id.formlistview)).addView(tv);
    }

    public void sendProfile(View view) throws IOException, JSONException {
        for(int i = FormConstants.FIELD_START_VALUE; i < edittexts; i++) {
            EditText tv = (EditText) (findViewById(i));
            String[] FT = new String[2];
            FT[0] = tv.getHint().toString();
            FT[1] = tv.getText().toString();
            fieldTerm.add(FT);
        }
        NetworkThread.addProfiles();

        JSONObject json = new JSONObject();
        //Convert profile to JSON
        for(int i = 0; i < fieldTerm.size(); i++) {
            json.put(fieldTerm.get(i)[0], fieldTerm.get(i)[1]);
        }
        String FILENAME = fieldTerm.get(0)[0] + " Profile";
        FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(json.toString().getBytes());
        fos.close();
    }

}
