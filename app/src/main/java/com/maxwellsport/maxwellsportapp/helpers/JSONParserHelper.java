package com.maxwellsport.maxwellsportapp.helpers;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.models.ExerciseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class JSONParserHelper {

    private Context mContext;
    private String mJsonString;
    private Set<String> mBodyPartSet;

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

    /* run tags */
    private static final String TAG_PACE = "pace";
    private static final String TAG_COORDINATES = "coordinates";
    private static final String TAG_DURATION = "duration";
    private static final String TAG_DATE_TIME = "dateTime";
    private static final String TAG_RUN_NAME = "name";
    private static final String TAG_DISTANCE = "distance";

    public JSONParserHelper(Context context){
        mContext = context;
        mBodyPartSet = new HashSet<>();
        if(!SharedPreferencesHelper.getBoolean(mContext, SharedPreferencesHelper.is_training_downloaded_key, false))
            mJsonString = getTrainingJsonFromSharedPreferences();

    }

    /* save JSON (as String) response to SharedPreferences */
    public void saveJsonToSharedPreferences(String jsonString){
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.downloaded_training_json_key, jsonString);
    }

    /* read JSON (String) from SharedPreferences */
    public String getTrainingJsonFromSharedPreferences(){
        return SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.downloaded_training_json_key, "");
    }

    /* update trainig JSON from SharedPreferences */
    public void updateTrainingJson(String date, ArrayList checkedList){

    }

    public String createRunDataJson(Long duration, Float distance, Float pace, ArrayList<ArrayList<LatLng>> coordinates, String dateTime){
        /* create json */
        JSONArray paceArray = new JSONArray();
        JSONArray coordinatesArray = new JSONArray();
        JSONArray pointsJSONArray;
        JSONObject jsonObject = new JSONObject();
        try {

            /* pace array*/
            paceArray.put(pace);

            jsonObject.put(TAG_PACE, paceArray);
            jsonObject.put(TAG_DURATION, duration);
            jsonObject.put(TAG_DATE_TIME, dateTime);
            jsonObject.put(TAG_NAME,"Run");
            jsonObject.put(TAG_DISTANCE, distance);

            /* coordinates array */
            for (ArrayList<LatLng> pointsArray: coordinates
                 ) {
                for (LatLng point: pointsArray
                     ) {
                    pointsJSONArray = new JSONArray();
                    pointsJSONArray.put(0, point.latitude);
                    pointsJSONArray.put(1, point.longitude);
                    coordinatesArray.put(pointsJSONArray);
                }
            }

            /* put coordinates */
            jsonObject.put(TAG_COORDINATES, coordinatesArray);

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    /* return exercise arraylist for current training */
    public  ArrayList<ExerciseModel> getExerciseListForCurrentTraining(){
        /* if training is not downloaded return one empty exercise */
        SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.is_training_downloaded_key, true);
        if(SharedPreferencesHelper.getBoolean(mContext, SharedPreferencesHelper.is_training_downloaded_key, false)){
            try {
                /* current training id */
                int currentTrainingID = SharedPreferencesHelper.getInt(mContext, SharedPreferencesHelper.current_training_number_key,0);
                mJsonString = SharedPreferencesHelper.getString(mContext, SharedPreferencesHelper.downloaded_training_json_key, "");
                /* json array with trainings for each day */
                Log.d("MAXWELL", mJsonString);
                JSONArray jsonArray = new JSONArray(mJsonString);
                /* json object witch current training */
                JSONObject training = jsonArray.getJSONObject(currentTrainingID);
                /* json array with exercises from current training */
                JSONArray exerciseArray = training.getJSONArray(TAG_EXERCISE_LIST);
                /* arraylist with exercises for current training */
                ArrayList<ExerciseModel> exercisesList = new ArrayList<>();
                String exerciseName;
                /* fill exercise list */
                for(int i=0; i<exerciseArray.length(); i++){
                    JSONObject exerciseJSON = exerciseArray.getJSONObject(i);
                    exerciseName = getExerciseNameById(exerciseJSON.getInt(TAG_EXE_ID));
                    mBodyPartSet.add(exerciseJSON.getString(TAG_BODY_PART));
                    exercisesList.add(new ExerciseModel(exerciseJSON.getInt(TAG_REPS), exerciseJSON.getInt(TAG_SETS), exerciseJSON.getInt(TAG_WEIGHT), exerciseName));
                }
//                SharedPreferencesService.putValue(mContext,SharedPreferencesService.training_body_part_key, mBodyPartSet.toString());
                return exercisesList;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
            ArrayList<ExerciseModel> exerciseArrayList = new ArrayList<>();
            exerciseArrayList.add(new ExerciseModel(0, 0, 0, "Pobierz trening"));
            return exerciseArrayList;
        }
        return null;
    }

    /* return exercise name from string arrays based on exercise id */
    @SuppressWarnings("ResourceType")
    private String getExerciseNameById(int id){
        String name = "";
        id++;
        int groupNumber, orderNumber, arrayId;
        /* set group number and order number in body part*/
        if(id == 0 || id == 1 || id > 43 || id < 0) {
            groupNumber = 0;
            orderNumber = 0;
        }else if(id == 43){
            groupNumber = 6;
            orderNumber = 5;
        }else{
            groupNumber = ((id - 1) / 7 + 1);
            orderNumber = id % 6;
        }
        /* get array id */
        switch (groupNumber){
            case 0:
                arrayId = R.array.atlas_exercise_biceps_name;
                break;
            case 1:
                arrayId = R.array.atlas_exercise_triceps_name;
                break;
            case 2:
                arrayId = R.array.atlas_exercise_shoulders_name;
                break;
            case 3:
                arrayId = R.array.atlas_exercise_chest_name;
                break;
            case 4:
                arrayId = R.array.atlas_exercise_back_name;
                break;
            case 5:
                arrayId = R.array.atlas_exercise_legs_name;
                break;
            case 6:
                arrayId = R.array.atlas_exercise_abs_name;
                break;
            default:
                arrayId = 0;
        }
        /* get exercise name from string array */
        TypedArray exerciseNameArray = mContext.getResources().obtainTypedArray(arrayId);
        name = exerciseNameArray.getString(orderNumber);
        exerciseNameArray.recycle();
        return name;
    }

    /* get body part list */
    public ArrayList<String> getCurrentBodyPartList(){
        /* get string from sharedprefs */
        return new ArrayList<>();
    }

}
