package com.maxwellsport.maxwellsportapp.services;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectionService {

    public ConnectionService(){}

    public static String GETTraining(String arg) throws IOException{
        InputStream inputStream = null;
        String result = "";

        try {
            /* create URL */
            URL url = new URL(arg);
            /* create HttpURLConnection */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            /* set connection params */
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            /* connect */
            urlConnection.connect();
            /* get inputStream */
            inputStream = urlConnection.getInputStream();
            /* convert an input to String */
            result = convertInputStreamToString(inputStream);
            /* disconnect */
            urlConnection.disconnect();
            return result;
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
    }

    /* convert InputStream to String */
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
        inputStream.close();
        return result;

    }

    /* check if network is available */
    public boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
