package com.firebaseio.httpstarnovotravel.tarnovotravel;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rostik on 22.10.16.
 */

public class Bus {

    private String uid;
    private int busNumber;
    private double longitude;
    private double latitude;

    public Bus() {
        // necessary for Firebase's deserializer.
    }

    public Bus(String uid, int busNumber, double longitude, double latitude) {
        this.uid = uid;
        this.busNumber = busNumber;
        this.longitude = longitude;
        this.latitude = latitude;
    }


    public String getUid() {

        return uid;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {

        return latitude;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setBusNumber(int busNumber) {
        this.busNumber = busNumber;
    }

    public void setLongitude(double longtitude) {
        this.longitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonIgnore
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("busNumber", busNumber);
        result.put("longitude", longitude);
        result.put("latitude", latitude);

        return result;
    }
}
