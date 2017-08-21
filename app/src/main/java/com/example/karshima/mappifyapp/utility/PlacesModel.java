package com.example.karshima.mappifyapp.utility;

/**
 * Created by Prasad on 2/2/2017.
 */

public class PlacesModel {

    private String Name;
    private String latitude;
    private String longitude;

    public PlacesModel(String name, String latitude, String longitude) {
        Name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
