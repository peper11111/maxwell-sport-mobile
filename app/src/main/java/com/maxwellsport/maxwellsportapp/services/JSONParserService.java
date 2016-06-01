package com.maxwellsport.maxwellsportapp.services;


import org.json.JSONException;
import org.json.JSONObject;

public class JSONParserService {

    private JSONObject mJSONObject;

    public JSONParserService(String data){

        try {
            mJSONObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* metody do parsowania jsonow dla traningu/ cardio/ logowania */
}
