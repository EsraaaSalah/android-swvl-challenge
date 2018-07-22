package com.example.esraa.androidchallenge;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class BookTrip extends AppCompatActivity {
    private String rideId;
    private String pickUpId;
    private String dropOffId;
    private Object[] pickUpCoordinates;
    private Object[] dropOffCoordinates;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trip);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        rideId = bundle.getString("rideId");
        pickUpId = bundle.getString("pickUpId");
        dropOffId = bundle.getString("dropOffId");
        pickUpCoordinates = (Object[]) bundle.getSerializable("pickUpCoordinates");
        dropOffCoordinates = (Object[]) bundle.getSerializable("dropOffCoordinates");

        Button buttonBook = (Button) findViewById(R.id.buttonBook);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(BookTrip.this,"Booking",Toast.LENGTH_SHORT).show();
            }
        });

        Log.e("Pickup", coordinatesToString(pickUpCoordinates));
        Log.e("DropOff", coordinatesToString(dropOffCoordinates));

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        url = "https://maps.googleapis.com/maps/api/staticmap?" +
                "&size=360x607&maptype=roadmap&scale=2" +
                "&markers=color:red%7C" + coordinatesToString(pickUpCoordinates)+
                "&markers=color:green%7C" + coordinatesToString(dropOffCoordinates);
        Picasso.with(BookTrip.this)
                .load(url)
                .into(imageView);

    }
    public static String coordinatesToString(Object[] arr)
    {
        return arr[0] + "," + arr[1];
    }
}
