package com.tev.tripping;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class NearByPlaces extends AsyncTask<Object, String, String> {

    private String url;
    private ArrayList<Place> places;
    private Loader isLoading;

    @Override
    protected String doInBackground(Object... objects) {
        places = (ArrayList<Place>) objects[0];
        url = (String)objects[1];
        isLoading = (Loader) objects[2];

        String rawData = "";

        // Make the list by calling the fetch
        try {
            Http http = new Http();

            rawData = http.fetch(url);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return rawData;
    }

    /**
     * This method is called after the google place api is done fetching from the server.
     * @param data
     */
    @Override
    protected void onPostExecute(String data){
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            jsonObject = new JSONObject(data);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Parsing the data from JsonObject to the Place class object
        dataParse(jsonArray);

        if(url.contains("type=zoo")){
            isLoading.setLoading(false);
        }
    }

    /**
     * This method is used to parse the JsonArray object and create a list of Place objects.
     * @param jsonArray
     */
    private void dataParse(JSONArray jsonArray){
        
        for (int i = 0; i < jsonArray.length(); i++){
            
            try {
                JSONObject  placeInfo = (JSONObject) jsonArray.get(i);
                
                Place place = createPlace(placeInfo);
                
                places.add(place);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Log.d("Mayank", "placeSize: "+ places.size());
    }

    /**
     * This Method is used to create a new Place object and add it to the main list.
     * @param placeInfo
     * @return
     */
    private Place createPlace(JSONObject placeInfo) {

        Place place = new Place();

        try {

            // Retrieve the data and put it into the Place object
            if (!placeInfo.isNull("name")) {
                place.name = placeInfo.getString("name");
            }
            if (!placeInfo.isNull("vicinity")) {
                place.vicinity = placeInfo.getString("vicinity");
            }
            if (!placeInfo.isNull("place_id")) {
                place.placeID = placeInfo.getString("place_id");
            }

            if (!placeInfo.isNull("rating")) {
                place.rating = Double.parseDouble(placeInfo.getString("rating"));
            }

            if (!placeInfo.isNull("user_ratings_total")) {
                place.totalRatings = Integer.parseInt(placeInfo.getString("user_ratings_total"));
            }

            place.reference = placeInfo.getString("reference");
            place.icon = placeInfo.getString("icon");
            place.latitutde = Double.parseDouble(placeInfo.getJSONObject("geometry").getJSONObject("location").getString("lat"));
            place.longitude = Double.parseDouble(placeInfo.getJSONObject("geometry").getJSONObject("location").getString("lng"));


            for(int i = 0; i < placeInfo.getJSONArray("types").length(); i++){
                place.types.add(placeInfo.getJSONArray("types").get(i).toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  place;
    }


}
