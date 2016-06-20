package com.maxwellsport.maxwellsportapp.helpers;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

public class DownloadHelper extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ConnectionHelper mConnectionHelper;

    public DownloadHelper(Context context){
        this.mContext = context;
        mConnectionHelper = new ConnectionHelper(mContext);
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return mConnectionHelper.getServerResponse(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothig to show yet";
    }

    @Override
    protected void onPostExecute(String json) {

        /* test message */
        Toast.makeText(mContext, json, Toast.LENGTH_LONG).show();

        /* saving json to SharedPreferences */
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.downloaded_training_json_key, json);
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.is_training_downloaded_key, true);
    }
}
