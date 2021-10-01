package com.tev.tripping;

import java.util.ArrayList;



public class ApiController {

    private static ApiController instance = null;

    public static boolean isHelperMode = false;

    private PlaceApiList placeList;
    private double lat;
    private double lon;
    private int radius;
    Loader isLoading;

    public ApiController(double lat, double lon, int radius){
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
        isLoading = new Loader();
    }

    public static void switchMode(){
        if(isHelperMode){
            isHelperMode = false;
        } else {
            isHelperMode = true;
        }
    }

    public ApiController(){
        isLoading = new Loader();
    }


    public static ApiController getInstance(){
        if(instance == null){
            instance = new ApiController();
        }

        return instance;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setParam(double lat, double lon, int radius){
        this.lat = lat;
        this.lon = lon;
        this.radius = radius;
    }


    /**
     * Generate the api list to use in the tripmate system
     * @return
     */
    public ArrayList<Place> generateApiList() {
        placeList = null;
        placeList = new PlaceApiList(lat, lon, radius);

        // Set isLoading
        isLoading.setLoading(true);

        // Create places lists
        placeList.createGeneralPlaceList(isLoading);
        placeList.createHelperPlaceList();

        return placeList.getPlaceList();
    }

    /**
     * Get the loading flag to know if the data is loaded in the lists.
     * @return
     */
    public boolean getIsLoading() {
        return isLoading.getLoading();
    }

    /**
     * This is method is used to find if the place object exist in the list or not
     * @param newList
     * @param place
     * @return
     */
    private boolean findElement(ArrayList<Place> newList, Place place){
        for (Place p: newList){
            if(p.placeID.equals(place.placeID)){
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to remove the duplicates in the Place lists.
     * @param list
     * @return
     */
    private ArrayList<Place> removeDuplicates(ArrayList<Place> list)
    {
        // Create a new ArrayList
        ArrayList<Place> newList = new ArrayList<Place>();

        // Traverse through the list and find if the elements are duplicating
        for (Place place : list) {
            if (!findElement(newList, place)) {
                newList.add(place);
            }
        }

        // return the new list
        return newList;
    }

    /**
     * This method is used to get helper places in the list.
     * @return
     */
    public ArrayList<Place> getHelperPlaces(){
        placeList.rankList();
        return removeDuplicates(placeList.getHelperPlaces());
    }

    /**
     * This method rank the list and return the
     * @return
     */
    public ArrayList<Place> getApiList() {

        placeList.rankList();
        return removeDuplicates(placeList.getPlaceList());
    }

}
