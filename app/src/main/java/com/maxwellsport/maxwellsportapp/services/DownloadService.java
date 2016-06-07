package com.maxwellsport.maxwellsportapp.services;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

public class DownloadService extends AsyncTask<String, Void, String> {

    private Context mContext;

    public DownloadService(Context context){
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return ConnectionService.GET(urls[0]);
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
//        JSONParserService parserService = new JSONParserService(mContext);
//        parserService.saveJsonToSharedPreferences(json);

    }
}
