package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConnectionHelper {

    private String mHost = "http://10.42.0.1";
    private int mPort;
    private String mURL;
    private String mTrainingURL;
    private String mRunURL;
    private String mLoginURL;
    private String mId;
    private Context mContext;
    private JSONParserHelper mParserService;
    private String mRunJsonString;

    public ConnectionHelper(Context context){
        /* set default values */
        mContext = context;
        mParserService = new JSONParserHelper(mContext);
    }


    /* this method download or upload training/stats */
    public void connectToServer(){
        /* initialize URLs */
        setConnectionVariables();
        /* check if update or download is needed */
        new DownloadHelper(mContext).execute(mTrainingURL);
//        new UploadTrainingHelper(mContext).execute(mURL);
    }

    public void saveRunData(Long duration, Float distance, Float pace, ArrayList<ArrayList<LatLng>> coordinates){
        /* data biegu */
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateTime = dateFormat.format( new Date());
        /* build jsonObject */
        mRunJsonString = mParserService.createRunDataJson(duration, distance, pace, coordinates, dateTime);
        /* start async-task to send json */
        new UploadRunDataHelper(mContext).execute(mRunURL);
    }

    public String putTrainingData(String arg) throws  IOException{
        InputStream inputStream = null;
        DataOutputStream outputStream;
        String result = "";
        String data = "";
        try {
            /* create URL */
            URL url = new URL(arg);
            /* create HttpURLConnection */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            /* set connection params */
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            /*get OutputStream */
            outputStream = new DataOutputStream(urlConnection.getOutputStream());
            /* get JSON to send */
             data = mParserService.getTrainingJsonFromSharedPreferences();
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

    public String getServerResponse(String arg) throws IOException{
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
            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 404){
                return Integer.toString(responseCode);
            }else if(responseCode == 200) {
                /* get inputStream */
                inputStream = urlConnection.getInputStream();
            /* convert an input to String */
                result = convertInputStreamToString(inputStream);
            /* disconnect */
                urlConnection.disconnect();
                return result;
            }
            return "";
        }finally {
            if(inputStream != null){
                inputStream.close();
            }
        }
    }

    public String sendRunData(String arg) throws  IOException{
        InputStream inputStream = null;
        DataOutputStream outputStream;
        String result = "";
        String data = "";
        try {
            /* create URL */
            URL url = new URL(arg);
            /* create HttpURLConnection */
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            /* set connection params */
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoInput(true);
            urlConnection.setChunkedStreamingMode(0);
            /*get OutputStream */
            outputStream = new DataOutputStream(urlConnection.getOutputStream());
            /* write JSON to stream */
            outputStream.writeBytes(mRunJsonString);
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

    public void setConnectionVariables(){
        mId = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.app_user_id_key, "1");
        mHost = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.settings_server_address_key, "10.42.0.1" );
        mPort = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_server_port_key, 8081 );
        mURL = "http://" + mHost + ":" + mPort;
        mTrainingURL = mURL + "/api/training/user/" + mId;
        mRunURL = mURL + "/api/run/user/" + mId;
        mLoginURL = mURL + "api/users/user";
    }
}
