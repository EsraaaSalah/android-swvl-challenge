package com.example.esraa.androidchallenge;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class RecommendedTrips extends AppCompatActivity {
    private ArrayList<Trip> tripsList;
    private TripAdapter tripAdapter;
    private ListView tripsListView;
    private String getRecommendationsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_trips);


        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Remove title
        ab.setDisplayShowTitleEnabled(false);

        ab.setHomeAsUpIndicator(R.mipmap.menu_icon);

    }
    protected void onStart() {
        super.onStart();

        buildUrl();

        tripsList = new ArrayList();
        FetchRides fr = new FetchRides();

        fr.execute();

    }
    // Build the url of the recommended trips get request
    public void buildUrl() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("private-fc685a-swvlandroidjuniorchallenge.apiary-mock.com")
                .appendPath("recommendations");
        getRecommendationsUrl = builder.build().toString();

    }
    public class FetchRides extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids)
        {
            // Create request queue
            RequestQueue queue = Volley.newRequestQueue(RecommendedTrips.this);

            // Create on Response method to be called in case of success
            Response.Listener<String> responseListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();

                    // Parse the returned JSON String to array of Trips
                    Trip[] tripsArray = gson.fromJson(response, Trip[].class);

                    // Add trips to trips list
                    tripsList.addAll(Arrays.asList(tripsArray));

                    // Create trip adapter with the list of trips
                    tripAdapter = new TripAdapter(tripsList, RecommendedTrips.this);

                    tripsListView = (ListView) findViewById(R.id.listView_rides);

                    // Set trip adapter to the list view
                    tripsListView.setAdapter(tripAdapter);

                    // Create method to be called when a trip is selected
                    tripsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Trip myTrip = (Trip) tripAdapter.getItem(i);

                            // Create new Intent
                            Intent intent = new Intent(RecommendedTrips.this, BookTrip.class);

                            // Add required Data to the intent
                            Bundle bundle = new Bundle();
                            bundle.putString("rideId", myTrip.getId());
                            bundle.putString("pickUpId", myTrip.getPickup().getId());
                            bundle.putString("dropOffId", myTrip.getDropoff().getId());
                            bundle.putSerializable("pickUpCoordinates", myTrip.getPickup()
                                                    .getCoordinates().toArray());
                            bundle.putSerializable("dropOffCoordinates", myTrip.getDropoff()
                                    .getCoordinates().toArray());
                            intent.putExtras(bundle);

                            // Start 'BookTrip' activity using the created intent
                            startActivity(intent);
                        }
                    });

                }
            };

            // Create on Error method to be called in case of failure
            Response.ErrorListener errorListener = new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error.Response", error.toString());

                }
            };

            // Create the get request
            StringRequest getRequest = new StringRequest(Request.Method.GET, getRecommendationsUrl,
                    responseListener,errorListener);

            // Add request to the request queue
            queue.add(getRequest);
            return null;
        }
    }
}
