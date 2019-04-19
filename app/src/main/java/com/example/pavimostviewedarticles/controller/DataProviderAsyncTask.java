package com.example.pavimostviewedarticles.controller;

import android.os.AsyncTask;
import android.util.Log;

import com.example.pavimostviewedarticles.model.Constants;
import com.example.pavimostviewedarticles.model.MostViewedArticlesJsonResponse;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataProviderAsyncTask extends AsyncTask<Void, Void, String> {

    private DataRecieveListener dataRecieveListener;

    public DataProviderAsyncTask(DataRecieveListener dataRecieveListener){
        this.dataRecieveListener = dataRecieveListener;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String response = null;
        try {
            response = getResponse(Constants.URL.concat(Constants.API_KEY)); // calls method to get JSON object

            Log.d("Response: ", "Response: "+response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            if(dataRecieveListener!=null) {
                dataRecieveListener.onDataReceiveError();
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        try {
            if(dataRecieveListener!=null) {
                dataRecieveListener.onDataReceive(new Gson().fromJson(response, MostViewedArticlesJsonResponse.class));
            }
        }
        catch (Exception e){
            e.printStackTrace();
            if(dataRecieveListener!=null) {
                dataRecieveListener.onDataReceiveError();
            }
        }
    }


    public String getResponse(String urlString) throws Exception {

        HttpURLConnection urlConnection = null;

        URL url = new URL(urlString);

        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod("GET");
        //urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);

        urlConnection.setDoOutput(true);

        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

        char[] buffer = new char[1024];

        String jsonString = new String();

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        jsonString = sb.toString();

        System.out.println("JSON: " + jsonString);
        urlConnection.disconnect();

        return jsonString;
        //return new JSONObject(jsonString);
    }
}
