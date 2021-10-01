package com.tev.tripping;


public class Loader {

    Boolean isLoading;

    public Loader(){
        isLoading = true;
    }

    public void setLoading(Boolean loading) {
        isLoading = loading;
    }

    public Boolean getLoading() {
        return isLoading;
    }
}
