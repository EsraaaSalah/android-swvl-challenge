package com.example.esraa.androidchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TripAdapter extends BaseAdapter{
    List<Trip> trips;
    Context context;
    LayoutInflater inflater;
    public TripAdapter(List<Trip> trips, Context context)
    {
        this.trips = trips;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return trips.size();
    }

    @Override
    public Object getItem(int i) {
        return trips.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        Trip myTrip = trips.get(i);
        // Get the list item view
        View v = inflater.inflate(R.layout.list_item_trip,null);

        // Set the pickup title
        TextView pickUpTextView = (TextView) v.findViewById(R.id.textViewPickUp);
        pickUpTextView.setText(myTrip.getPickup().getTitle());

        // Set the dropoff title
        TextView dropOffTextView = (TextView) v.findViewById(R.id.textViewDropOff);
        dropOffTextView.setText(myTrip.getDropoff().getTitle());

        // Set the line number
        TextView lineNumberTextView = (TextView) v.findViewById(R.id.textViewLineNumber);
        lineNumberTextView.setText(myTrip.getLineNumber());

        //Set the formatted date
        TextView dateTextView = (TextView) v.findViewById(R.id.textViewDate);
        dateTextView.setText(formatDate(myTrip.getDate(),"EEE, MMM d"));

        //Set the formatted time
        TextView timeTextView = (TextView) v.findViewById(R.id.textViewTime);
        timeTextView.setText(formatDate(myTrip.getDate(),"hh:mm aaa"));
        return v;
    }
    public static String formatDate(String originalDate, String targetFormatString)
    {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat(targetFormatString);

        try {
            Date date = originalFormat.parse(originalDate);
            return targetFormat.format(date);
        }
        catch (Exception e)
        {
            return "";
        }
    }



}
