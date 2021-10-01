package com.tev.tripping;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;


public class Place implements Comparable<Place> {

    public String placeID;
    public String name;
    public String icon;
    public String vicinity;
    public boolean isOpen;
    public double rating;
    public String reference;
    public ArrayList<String> types;
    public int totalRatings;
    public double latitutde;
    public double longitude;

    public Place(){
        placeID = "";
        name = "";
        icon = "";
        vicinity = "";
        isOpen = false;
        rating=0.0;
        reference = "";
        types = new ArrayList<String>();
        totalRatings = 0;
        latitutde = 0.0;
        longitude = 0.0;
    }

    /**
     * Debug Print logs
     */
    public void printData() {
        String s = placeID + " | " + name + " | " + vicinity + " | " + isOpen + " | " + rating + " | " + totalRatings;
        Log.d("Mayank", "PlaceInfo: "+s);
    }

    /**
     * Overiding the String method.
     * @return
     */
    @NonNull
    @Override
    public String toString() {
        String s = placeID + " | " + name + " | " + vicinity + " | " + isOpen + " | " + rating + " | " + totalRatings;
        return s;
    }

    /**
     * Create a ranking in the list based on the reviews and number of reviews.
     * @param p
     * @return
     */
    @Override
    public int compareTo(Place p) {
        double myRank = totalRatings * rating;
        double pRank = p.totalRatings * p.rating;

        if(myRank >= pRank){
            return -1;
        }
        return 1;
    }
}
