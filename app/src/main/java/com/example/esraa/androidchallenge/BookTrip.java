package com.example.esraa.androidchallenge;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
                sendPostRequest();
            }
        });

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
    public void sendPostRequest()
    {
        String url = "http:" +
                "//private-fc685a-swvlandroidjuniorchallenge.apiary-mock.com/book";
        RequestQueue queue = Volley.newRequestQueue(BookTrip.this);
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.e("Response","Successfully booked");
                Toast.makeText(BookTrip.this,"Your Trip is successfully booked",
                        Toast.LENGTH_SHORT).show();
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error.Response", error.toString());
                Toast.makeText(BookTrip.this,"An Error Occurred",
                        Toast.LENGTH_SHORT).show();
            }
        };
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                return params;
            }
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
        queue.add(postRequest);
    }
}
