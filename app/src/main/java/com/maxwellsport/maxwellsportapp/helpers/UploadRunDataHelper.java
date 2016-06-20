package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by rafal on 19.06.16.
 */
public class UploadRunDataHelper extends AsyncTask<String, Void, String> {
    private Context mContext;
    private ConnectionHelper mConnectionHelper;

    public UploadRunDataHelper(Context context){
        this.mContext = context;
        mConnectionHelper = new ConnectionHelper(mContext);
    }

    @Override
    protected String doInBackground(String... urls) {
        try {
            return mConnectionHelper.sendRunData(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onPostExecute(String s) {
    }

}
