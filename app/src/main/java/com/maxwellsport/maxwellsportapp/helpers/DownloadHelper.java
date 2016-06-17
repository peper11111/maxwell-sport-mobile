package com.maxwellsport.maxwellsportapp.helpers;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.helpers.ConnectionHelper;

import java.io.IOException;

public class DownloadHelper extends AsyncTask<String, Void, String> {

    private Context mContext;

    public DownloadHelper(Context context){
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return ConnectionHelper.GET(urls[0]);
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
//        JSONParserHelper parserService = new JSONParserHelper(mContext);
//        parserService.saveJsonToSharedPreferences(json);

    }
}
