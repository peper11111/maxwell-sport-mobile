package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.client.methods.HttpPost;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ConnectionHelper {

    private int mPort;
    private String mURL, mTrainingURL, mRunURL, mLoginURL, mId, mHost;
    private Context mContext;
    private JSONParserHelper mParserService;

    public ConnectionHelper(Context context){
        /* set default values */
        mContext = context;
        mParserService = new JSONParserHelper(mContext);
        setConnectionVariables();
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
        String runJson = mParserService.createRunDataJson(duration, distance, pace, coordinates, dateTime);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.created_run_json_key, runJson);
        /* start async-task to send json */
        new UploadRunDataHelper(mContext).execute(mRunURL);
    }

    public String putTrainingData(String arg) throws  IOException{
        InputStream inputStream = null;
        DataOutputStream outputStream;
        String result = "";
        String data = "";
        setConnectionVariables();
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
//            inputStream = urlConnection.getInputStream();
            /* convert an input to String */
//            result = convertInputStreamToString(inputStream);
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
        setConnectionVariables();
        InputStream inputStream = null;
        String result = "";
        try {
            /* create URL */
            URL url = new URL(mTrainingURL);
            Log.d("MAXWELL", mTrainingURL);
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
        setConnectionVariables();
        InputStream inputStream = null;
        DataOutputStream outputStream;
        String result = "";
        String data = "";
        HttpURLConnection urlConnection = null;
        StringBuilder sb = new StringBuilder();

        try {
            /* create URL */
            URL url = new URL("http://10.42.0.1:8081/api/run/user/1");
            /* create HttpURLConnection */
            urlConnection = (HttpURLConnection) url.openConnection();
            /* set connection params */
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("POST");
//            urlConnection.setUseCaches(false);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setReadTimeout(10000);

            urlConnection.setChunkedStreamingMode(0);

            urlConnection.connect();

            Log.d("MAXWELL", SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.created_run_json_key, ""));
            Log.d("MAXWELL", mRunURL);

            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.created_run_json_key, ""));
            out.flush();
            out.close();

            int HttpResult =urlConnection.getResponseCode();
            if(HttpResult ==HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                Log.d("MAXWELL", "OK RESPONSE " + HttpResult + " " +sb.toString());

            }else{
                Log.d("MAXWELL", "NOT OK RESPONSE: "+ HttpResult + " "  + urlConnection.getResponseMessage());
            }


            return result;
        } finally {
            urlConnection.disconnect();
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
        /* --------------- */
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.app_user_id_key, "3");
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.app_username_key, "user");
        /* ------------------- */
        mId = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.app_user_id_key, "3");
        mHost = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.settings_server_address_key, "10.42.0.1" );
        mPort = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.settings_server_port_key, 8081 );
        mURL = "http://" + mHost + ":" + Integer.toString(mPort);
        mTrainingURL = mURL + "/api/training/user/" + mId;
        mRunURL = mURL + "/api/run/user/" + mId;
        mLoginURL = mURL + "api/users/" + SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.app_username_key, "user");
    }

}
