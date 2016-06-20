package com.maxwellsport.maxwellsportapp.helpers;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by rafal on 19.06.16.
 */
public class LoginHelper extends AsyncTask<String, Void, String> {

    private Context mContext;
    private ConnectionHelper mConnectionHelper;

    public LoginHelper(Context context){
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
            Toast.makeText(mContext, "Bł", Toast.LENGTH_LONG).show();
        }

        /* saving json to SharedPreferences */
//        JSONParserHelper parserService = new JSONParserHelper(mContext);
//        parserService.saveJsonToSharedPreferences(json);

    }
}
