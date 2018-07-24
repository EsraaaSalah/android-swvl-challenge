package com.example.esraa.androidchallenge;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class BookTrip extends AppCompatActivity {
    private String rideId;
    private String pickUpId;
    private String dropOffId;
    private Object[] pickUpCoordinates;
    private Object[] dropOffCoordinates;
    private String url;
    private Button buttonBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_trip);


        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);

        // Set ActionBar to my_toolbar
        setSupportActionBar(my_toolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        // Remove title
        ab.setDisplayShowTitleEnabled(false);

        ab.setHomeAsUpIndicator(R.mipmap.icons_back_arrow);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // Get data from the intent
        rideId = bundle.getString("rideId");
        pickUpId = bundle.getString("pickUpId");
        dropOffId = bundle.getString("dropOffId");
        pickUpCoordinates = (Object[]) bundle.getSerializable("pickUpCoordinates");
        dropOffCoordinates = (Object[]) bundle.getSerializable("dropOffCoordinates");

        // Set the on click method of the Book button
        buttonBook = (Button) findViewById(R.id.buttonBook);
        buttonBook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Disable the button during the request
                buttonBook.setEnabled(false);
                Log.e("Button","Button disabled");
                // Execute post request
                sendPostRequest();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        // Get dimensions of screen in dp
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int dpHeight = (int) ((displayMetrics.heightPixels) / displayMetrics.density);
        int dpWidth = (int) (displayMetrics.widthPixels / displayMetrics.density);

        // Create url to get the required map
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("maps.googleapis.com")
                .appendPath("maps")
                .appendPath("api")
                .appendPath("staticmap")
                .appendQueryParameter("size",dpWidth+"x"+dpHeight)
                .appendQueryParameter("maptype","roadmap")
                .appendQueryParameter("scale","2");

        url = builder.build().toString();
        url += "&markers=color:red%7C" + coordinatesToString(pickUpCoordinates);
        url += "&markers=color:green%7C" + coordinatesToString(dropOffCoordinates);

        // Load pic of the map into the imageView
        Picasso.with(BookTrip.this)
                .load(url)
                .into(imageView);

    }
    public static String coordinatesToString(Object[] arr)
    {
        return arr[0] + "," + arr[1];
    }
    public void sendPostRequest()
    {
        // Build url of the booking post request
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("private-fc685a-swvlandroidjuniorchallenge.apiary-mock.com")
                .appendPath("book");
        String bookUrl = builder.build().toString();

        // Create request queue
        RequestQueue queue = Volley.newRequestQueue(BookTrip.this);

        // Create on Response method to be called in case of success
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

                // Enable the button after the request
                buttonBook.setEnabled(true);
                Log.e("Button","Button enabled");
                Log.e("Response","Successfully booked");

                // Display a success message
                Toast.makeText(BookTrip.this,"Your Trip is successfully booked",
                        Toast.LENGTH_SHORT).show();
            }
        };

        // Create on Error method to be called in case of failure
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {

            @Override
            public void onErrorResponse(VolleyError error) {

                // Enable the button after the request
                buttonBook.setEnabled(true);
                Log.e("Button","Button enabled");
                Log.e("Error.Response", error.toString());

                // Display an error message
                Toast.makeText(BookTrip.this,"An Error Occurred",
                        Toast.LENGTH_SHORT).show();
            }
        };

        // Create the post request
        StringRequest postRequest = new StringRequest(Request.Method.POST, bookUrl,
                responseListener, errorListener) {

            // Set content type to application/json
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
            // Add the required data of the trip to the request body
            @Override
            public byte[] getBody() throws AuthFailureError {
                JSONObject body = new JSONObject();
                try {
                    body.put("ride_id", rideId);
                    body.put("pickup_id", pickUpId);
                    body.put("dropoff_id", dropOffId);
                } catch (JSONException e) {
                    Log.e("JSON Exception", e.toString());
                    Toast.makeText(BookTrip.this,"An Error Occurred",
                            Toast.LENGTH_SHORT).show();
                }
                return body.toString().getBytes();
            }
        };
        // Add request to the request queue
        queue.add(postRequest);
    }
}
