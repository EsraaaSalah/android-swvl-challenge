package com.example.esraa.androidchallenge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

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
    public View getView(int i, View view, ViewGroup viewGroup) {
        Ride myRide = rides.get(i);
        View v = inflater.inflate(R.layout.list_item_ride,null);

        TextView pickUpTextView = (TextView) v.findViewById(R.id.textViewPickUp);
        pickUpTextView.setText(myRide.getPickup().getTitle());
        TextView dropOffTextView = (TextView) v.findViewById(R.id.textViewDropOff);
        dropOffTextView.setText(myRide.getDropoff().getTitle());
        TextView lineNumberTextView = (TextView) v.findViewById(R.id.textViewLineNumber);
        lineNumberTextView.setText(myRide.getLineNumber());
        return v;
    }
}
