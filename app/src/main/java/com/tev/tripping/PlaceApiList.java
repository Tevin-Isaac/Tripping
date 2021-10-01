package com.tev.tripping;
import java.util.ArrayList;
import java.util.Collections;


public class PlaceApiList {
    ArrayList<Place> places;
    ArrayList<Place> helperPlaces;

    private String [] generalTypesList = new String[]{"amusement_park", "art_gallery", "museum", "shopping_mall", "tourist_attraction", "zoo"};
//    private String [] generalTypesList = new String[]{"tourist_attraction"};
    private String [] helperTypesList = new String[]{"airport", "embassy", "lodging", "restaurant", "subway_station"};

    private double lat;
    private double lon;
    private int radius;

    public PlaceApiList(double lat, double lon, int radius){
        this.lon = lon;
        this.lat = lat;
        this.radius = radius;
        places = new ArrayList<Place>();
        helperPlaces = new ArrayList<Place>();
    }

    /**
     * This method is used to sort the arrayList and make the new list with place of interests.
     */
    public void rankList(){
        Collections.sort(places);
        Collections.sort(helperPlaces);
    }

    /**
     * This method is used to create a list of the General places of interest that the user would
     * like to visit using the trip.
     * @param isLoading
     */
    public void createGeneralPlaceList(Loader isLoading) {

        places.clear();
        ArrayList<NearByPlaces> nearByPlaces = new ArrayList<NearByPlaces>();

        for(int i =0 ; i < generalTypesList.length; i++){
            NearByPlaces nearByPlace = new NearByPlaces();

            String url = getUrl(generalTypesList[i]);

            Object [] obj = new Object[3];
            obj[0] = places;
            obj[1] = url;
            obj[2] = isLoading;

            nearByPlace.execute(obj);

            nearByPlaces.add(nearByPlace);
        }
    }

    /**
     * This method is used to make the list that is used to helper needs of the user like
     * lodging, famous restaurants around, Airports, embassies, subway station, etc
     */
    public void createHelperPlaceList() {

        helperPlaces.clear();
        ArrayList<NearByPlaces> nearByPlaces = new ArrayList<NearByPlaces>();

        for(int i =0 ; i < helperTypesList.length; i++){
            NearByPlaces nearByPlace = new NearByPlaces();

            String url = getUrl(helperTypesList[i]);

            Object [] obj = new Object[3];
            obj[0] = helperPlaces;
            obj[1] = url;
            obj[2] = new Loader();
            nearByPlace.execute(obj);

            nearByPlaces.add(nearByPlace);
        }
    }

    /**
     * This method is used to make create the URL based on the location and radius and type of requests.
     * @param nearbyPlace
     * @return
     */
    private String getUrl(String nearbyPlace) {

        StringBuilder googlePlaceApiUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceApiUrl.append("location="+lat+","+lon);
        googlePlaceApiUrl.append("&radius="+radius);
        googlePlaceApiUrl.append("&type="+nearbyPlace);
        googlePlaceApiUrl.append("&sensor=true");
        googlePlaceApiUrl.append("&key="+"AIzaSyBFK4UfJqDbEsEf9OXG48xmwGtiGWflzHs");

        return googlePlaceApiUrl.toString();
    }

    /**
     * This method returns the list of places that the used would be interested to visit.
     * @return
     */
    public ArrayList<Place> getPlaceList(){
        return places;
    }

    /**
     * This method returns the list of places that the used would like to use during the trip.
     * @return
     */
    public ArrayList<Place> getHelperPlaces(){
        return helperPlaces;
    }

}
