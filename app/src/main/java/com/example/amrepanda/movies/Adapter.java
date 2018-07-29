package com.example.amrepanda.movies;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends BaseAdapter {

    Activity activity;
    ArrayList<String> links;
    public Adapter(Activity activity, ArrayList<String> links) {
        this.activity = activity;
        this.links = links;
    }


    @Override
    public int getCount() { return links.size(); }

    @Override
    public Object getItem(int position) { return null; }

    @Override
    public long getItemId(int position) { return 0; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View V=activity.getLayoutInflater().inflate(R.layout.home_tamplate,null );
        ImageView imageView=(ImageView)V.findViewById(R.id.Image);

        Picasso.with(activity).load(links.get(position)).into(imageView);

        return V;
    }
}
