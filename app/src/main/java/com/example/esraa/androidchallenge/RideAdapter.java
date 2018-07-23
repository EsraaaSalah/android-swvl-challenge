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

public class RideAdapter extends BaseAdapter{
    List<Ride> rides;
    Context context;
    LayoutInflater inflater;
    public RideAdapter(List<Ride> rides,Context context)
    {
        this.rides = rides;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return rides.size();
    }

    @Override
    public Object getItem(int i) {
        return rides.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        Ride myRide = rides.get(i);
        // Get the list item view
        View v = inflater.inflate(R.layout.list_item_ride,null);

        // Set the pickup title
        TextView pickUpTextView = (TextView) v.findViewById(R.id.textViewPickUp);
        pickUpTextView.setText(myRide.getPickup().getTitle());

        // Set the dropoff title
        TextView dropOffTextView = (TextView) v.findViewById(R.id.textViewDropOff);
        dropOffTextView.setText(myRide.getDropoff().getTitle());

        // Set the line number
        TextView lineNumberTextView = (TextView) v.findViewById(R.id.textViewLineNumber);
        lineNumberTextView.setText(myRide.getLineNumber());

        //Set the formatted date
        TextView dateTextView = (TextView) v.findViewById(R.id.textViewDate);
        dateTextView.setText(getFormattedDate(myRide.getDate()));

        //Set the formatted time
        TextView timeTextView = (TextView) v.findViewById(R.id.textViewTime);
        timeTextView.setText(getFormattedTime(myRide.getDate()));
        return v;
    }
    public static String getFormattedDate(String originalDate)
    {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("EEE, MMM d");

        try {
            Date date = originalFormat.parse(originalDate);
            return targetFormat.format(date);
        }
        catch (Exception e)
        {
            return "";
        }
    }

    public static String getFormattedTime(String originalDate)
    {
        DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateFormat targetFormat = new SimpleDateFormat("hh:mm aaa");

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
