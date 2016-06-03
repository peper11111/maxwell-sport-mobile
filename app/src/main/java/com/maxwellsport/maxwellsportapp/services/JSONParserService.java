package com.maxwellsport.maxwellsportapp.services;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParserService {

    private JSONObject mJSONObject;
    private JSONArray mTrainigArray;

    /* training tags */
    private static final String TAG_ID = "id";
    private static final String TAG_TRAINING_DATE = "trainingDate";
    private static final String TAG_NAME = "name";
    private static final String TAG_EXERCISE_LIST = "exersiseDTOList";
    private static final String TAG_USER_ID = "userId";
    private static final String TAG_DONE = "done";
    /* exercise tags */
    private static final String TAG_REPS = "repeatation";
    private static final String TAG_SETS = "series";
    private static final String TAG_WEIGHT = "weight";
    private static final String TAG_EXE_ID = "exerciseId";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_BODY_PART = "bodyPart";

    public JSONParserService(String data){

        try {
            mJSONObject = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* save JSON (as String) response to SharedPreferences */
    public static void saveJsonToSharedPreferences(String jsonString){

    }

    /* read JSON (String) from SharedPreferences */
    public static String getJsonFromSharedPreferences(){
        return "";
    }

    /* parse training JSON and save values to SharedPreferences */
    private void parseTrainingJson(){

    }

    /* create trainig JSON from SharedPreferences */
    private void createTrainingJson(){

    }

    /* get training list from SharedPreferences */
    private ArrayList getExerciseList(){
        return new ArrayList();
    }
}
