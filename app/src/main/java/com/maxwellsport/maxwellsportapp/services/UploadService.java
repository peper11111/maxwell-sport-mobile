package com.maxwellsport.maxwellsportapp.services;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

public class UploadService extends AsyncTask<String, Void, String>{

    private Context mContext;

    public UploadService(Context context){
        this.mContext = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return ConnectionService.POST(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothig to show yet";
    }

    @Override
    protected void onPostExecute(String s) {
        /* test message */
        Toast.makeText(mContext, s, Toast.LENGTH_LONG).show();
    }
}
