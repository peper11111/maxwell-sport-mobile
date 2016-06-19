package com.maxwellsport.maxwellsportapp.helpers;


import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

public class UploadTrainingHelper extends AsyncTask<String, Void, String>{

    private Context mContext;
    private ConnectionHelper mConnectionHelper;

    public UploadTrainingHelper(Context context){
        this.mContext = context;
        mConnectionHelper = new ConnectionHelper(mContext);
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return mConnectionHelper.putTrainingData(urls[0]);
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
