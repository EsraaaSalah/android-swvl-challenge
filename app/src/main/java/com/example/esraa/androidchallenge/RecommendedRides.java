package com.example.esraa.androidchallenge;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecommendedRides extends AppCompatActivity {
    private ArrayList<Ride> ridesList;
    private RideAdapter ra;
    private ListView ridesListView;
    final String getRecommendationsUrl = "http:" +
            "//private-fc685a-swvlandroidjuniorchallenge.apiary-mock.com/recommendations";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommended_rides);
        ridesListView = (ListView) findViewById(R.id.listView_rides);

    }
    protected void onStart() {
        super.onStart();
        ridesList = new ArrayList();
        FetchRides fr = new FetchRides();
        fr.execute();

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
                    ridesListView.setAdapter(ra);
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
