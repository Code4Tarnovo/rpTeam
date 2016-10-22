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
    private double longtitude;
    private double latitude;

    public Bus() {
        // necessary for Firebase's deserializer.
    }

    public Bus(String uid, int busNumber, double longtitude, double latitude) {
        this.uid = uid;
        this.busNumber = busNumber;
        this.longtitude = longtitude;
        this.latitude = latitude;
    }


    public String getUid() {

        return uid;
    }

    public int getBusNumber() {
        return busNumber;
    }

    public double getLongtitude() {
        return longtitude;
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

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @JsonIgnore
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("busNumber", busNumber);
        result.put("longtitude", longtitude);
        result.put("latitude", latitude);

        return result;
    }
}
