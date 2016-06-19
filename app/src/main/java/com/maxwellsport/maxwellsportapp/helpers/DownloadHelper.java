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
        if(json.equals("404")){
            Toast.makeText(mContext, "B", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(mContext, json, Toast.LENGTH_LONG).show();

        /* saving json to SharedPreferences */
//        JSONParserHelper parserService = new JSONParserHelper(mContext);
//        parserService.saveJsonToSharedPreferences(json);

    }
}
