package com.example.esraa.androidchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class RecommendedRides extends AppCompatActivity {
    static List<Ride> mock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_rides);
        ListView mRidesList = (ListView) findViewById(R.id.listView_rides);
        mock = new ArrayList<>();
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());
        mock.add(new Ride());

        mRidesList.setAdapter(new RideAdapter(mock,RecommendedRides.this));
    }
}
