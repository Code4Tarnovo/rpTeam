package com.firebaseio.httpstarnovotravel.tarnovotravel;

/**
 * Created by rostik on 22.10.16.
 */

public class Bus {

    private int busNumber;
    private double longtitude;
    private double latitude;

    public Bus() {
        // necessary for Firebase's deserializer.
    }

    public Bus(int busNumber, double longtitude, double latitude) {
        this.busNumber = busNumber;
        this.longtitude = longtitude;
        this.latitude = latitude;
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
}
