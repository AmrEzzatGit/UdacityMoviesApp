package com.example.amrepanda.movies;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FavsAdapter extends BaseAdapter {
    Activity activity;
    ArrayList<String> posters;
    public FavsAdapter(Activity activity, ArrayList<String> posters) {
        this.activity = activity;
        this.posters=posters;
    }


    @Override
    public int getCount() { return posters.size(); }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View V=activity.getLayoutInflater().inflate(R.layout.favs_tamplate,null );
        ImageView imageView=(ImageView)V.findViewById(R.id.Favs_IV);

        Picasso.with(activity).load(posters.get(position)).into(imageView);

        return V;
    }
}
