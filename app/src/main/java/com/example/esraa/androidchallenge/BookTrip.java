package com.example.esraa.androidchallenge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BookTrip extends AppCompatActivity {
    private String rideId;
    private String pickUpId;
    private String dropOffId;
    private double[] pickUpCoordinates;
    private double[] dropOffCoordinates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trip);
        Intent intent = getIntent();
        rideId = intent.getStringExtra("rideId");
        pickUpId = intent.getStringExtra("pickUpId");
        dropOffId = intent.getStringExtra("dropOffId");
        pickUpCoordinates = intent.getDoubleArrayExtra("pickUpCoordinates");
        dropOffCoordinates = intent.getDoubleArrayExtra("dropOffCoordinates");
    }

}
