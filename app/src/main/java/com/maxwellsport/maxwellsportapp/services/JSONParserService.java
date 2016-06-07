package com.maxwellsport.maxwellsportapp.services;


import android.content.Context;

import com.maxwellsport.maxwellsportapp.models.Exercise;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class JSONParserService {

    private Context mContext;
    private String mJsonString;

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

    public JSONParserService(Context context){
        mContext = context;
        if(!SharedPreferencesService.getBoolean(mContext, SharedPreferencesService.is_training_downloaded_key, false))
            mJsonString = getJsonFromSharedPreferences();

    }

    /* save JSON (as String) response to SharedPreferences */
    public void saveJsonToSharedPreferences(String jsonString){
        SharedPreferencesService.putValue(mContext, SharedPreferencesService.downloaded_training_json_key, jsonString);
    }

    /* read JSON (String) from SharedPreferences */
    public String getJsonFromSharedPreferences(){
        return SharedPreferencesService.getString(mContext, SharedPreferencesService.downloaded_training_json_key, "");
    }

    /* update trainig JSON from SharedPreferences */
    public void updateTrainingJson(String date, ArrayList checkedList){

    }

    /* return string array with exercise names for current training */
    public  ArrayList<Exercise> getExerciseListForCurrentTraining(){

        /* if training is not downloaded return one empty exercise */
        if(SharedPreferencesService.getBoolean(mContext, SharedPreferencesService.is_training_downloaded_key, false)){
            try {
                int currentTrainingID = SharedPreferencesService.getInt(mContext, SharedPreferencesService.current_training_number_key,0);
                JSONArray jsonArray = new JSONArray(mJsonString);
                JSONObject training = jsonArray.getJSONObject(currentTrainingID);
                JSONArray exerciseArray = training.getJSONArray(TAG_EXERCISE_LIST);
                ArrayList<Exercise> exercisesList = new ArrayList();
                /* fill exercise list */
                for(int i=0; i<exerciseArray.length(); i++){

                }
                return exercisesList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /* return exercise names from string arrays based on exercise id */
    private String[] getExerciseNamesFromStrings(int [] idArray){
        String[] nameArray = {};

        return nameArray;
    }

    /* get body part list from  */
    private ArrayList<String> getCurrentBodyPartList(){
        return new ArrayList<>();
    }

}
