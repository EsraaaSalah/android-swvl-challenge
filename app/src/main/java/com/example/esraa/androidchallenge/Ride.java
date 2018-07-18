package com.example.esraa.androidchallenge;

import com.google.gson.annotations.SerializedName;

public class Ride {
    private String id;
    private String date;
    private Pickup pickup;
    private Dropoff dropoff;
    @SerializedName("line_number")
    private String lineNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Pickup getPickup() {
        return pickup;
    }

    public void setPickup(Pickup pickup) {
        this.pickup = pickup;
    }

    public Dropoff getDropoff() {
        return dropoff;
    }

    public void setDropoff(Dropoff dropoff) {
        this.dropoff = dropoff;
    }

    public String getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber = lineNumber;
    }

}


