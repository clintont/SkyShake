package com.example.myapplication2.app;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import android.view.View;

public class NetworkThread {
    public static String BTAddress;
    public static String server = "http://107.170.192.5";

    public static void addProfiles() throws JSONException, UnsupportedEncodingException {
        JSONObject json = new JSONObject();
        //Convert profile to JSON
        for(int i = 0; i < CreateForm.fieldTerm.size(); i++) {
            json.put(CreateForm.fieldTerm.get(i)[0], CreateForm.fieldTerm.get(i)[1]);
        }

        getJSONfromURL(server + "/profile.php?bluetoothMAC="+BTAddress+"&data=" + URLEncoder.encode(json.toString(), "UTF-8"));
    }

    public static JSONObject receiveProfile(String MAC){
        String URL = server + "/requestProfile.php?bluetoothMAC=" + MAC;
        return getJSONfromURL(URL);
    }

    public static String hasName(String MAC)
    {
        String URL = server + "/search.php?bluetoothMAC=" + MAC;
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(URL);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (builder.toString() == "")
        {
            Log.e("No name", "No namuu");
            return null;
        }
        else
        {
            Log.e("Builder String", builder.toString());
            return builder.toString();
        }
    }

    public static void sendProfile(String user_ID) {

    }

    private static JSONObject getJSONfromURL(String url) {
        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jObject = null;
        Log.d("URL: ", url);
        Log.d("Builder String: " ,builder.toString());
        try {
            jObject = new JSONObject(builder.toString());
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        return jObject;
    }

}