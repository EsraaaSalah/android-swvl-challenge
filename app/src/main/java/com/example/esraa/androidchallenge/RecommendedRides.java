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

public class RecommendedRides extends AppCompatActivity {
    private ArrayList<Ride> ridesList;
    private RideAdapter ra;
    private ListView ridesListView;
    private String getRecommendationsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_rides);


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
        ridesList = new ArrayList();
        FetchRides fr = new FetchRides();

        fr.execute();

    }
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
            RequestQueue queue = Volley.newRequestQueue(RecommendedRides.this);
            Response.Listener<String> responseListener = new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    Gson gson = new Gson();
                    Ride[] ridesArray = gson.fromJson(response, Ride[].class);
                    ridesList.addAll(Arrays.asList(ridesArray));
                    ra = new RideAdapter(ridesList, RecommendedRides.this);
                    ridesListView = (ListView) findViewById(R.id.listView_rides);
                    ridesListView.setAdapter(ra);
                    ridesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            Ride myRide = (Ride) ra.getItem(i);
                            Intent intent = new Intent(RecommendedRides.this, BookTrip.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("rideId", myRide.getId());
                            bundle.putString("pickUpId", myRide.getPickup().getId());
                            bundle.putString("dropOffId", myRide.getDropoff().getId());
                            bundle.putSerializable("pickUpCoordinates", myRide.getPickup()
                                                    .getCoordinates().toArray());
                            bundle.putSerializable("dropOffCoordinates", myRide.getDropoff()
                                    .getCoordinates().toArray());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                }
            };
            Response.ErrorListener errorListener = new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error.Response", error.toString());

                }
            };
            StringRequest getRequest = new StringRequest(Request.Method.GET, getRecommendationsUrl,
                    responseListener,errorListener);
            queue.add(getRequest);
            return null;
        }
    }
}
