package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConnectionHelper {

    private String mHost = "http://10.42.0.1:10000";
    private String mURL = "http://10.42.0.1:10000/api/training/user/1";
    private String mId;
    private String mMethod = "GET";
    private String mInputStream;
    private String mRequestStringResult;
    private Context mContext;
    private JSONParserHelper mParserService;

    public ConnectionHelper(Context context){
        /* set default values */
        mContext = context;
        mParserService = new JSONParserHelper(mContext);
    }

    /* this method download or upload training/stats */
    public void connectToServer(){
        /* check if update or download is needed */
        new DownloadHelper(mContext).execute(mURL);
//        new UploadHelper(mContext).execute(mURL);
    }

    public String POST(String arg) throws  IOException{
        InputStream inputStream = null;
        DataOutputStream outputStream = null;
        String result = "";
        String data = "";

        try {
            /* create URL */
            URL url = new URL(arg);
            /* create HttpURLConnection */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            /* set connection params */
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            /*get OutputStream */
            outputStream = new DataOutputStream(urlConnection.getOutputStream());
            /* get JSON to send */
             data = mParserService.getJsonFromSharedPreferences();
            /* write JSON to stream */
            outputStream.writeBytes(data);
            outputStream.flush();
            outputStream.close();
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

    public static String GET(String arg) throws IOException{
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
    public static boolean isNetworkAvailable(ConnectivityManager connectivityManager) {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
