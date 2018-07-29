package com.example.amrepanda.movies;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Home extends AppCompatActivity {
    GridView gridView;
    ArrayList<String > links;
    ArrayList<String> Ori_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView=(GridView)findViewById(R.id.HomeGridView);
        links=new ArrayList<String>();
        Ori_Title=new ArrayList<String>();
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d04160312987af22a80ba27b59cd080c", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob=new JSONObject(response);
                    JSONArray A=ob.getJSONArray("results");
                    for (int i=0 ; i<A.length( ) ;i++)
                    {
                        JSONObject object=A.getJSONObject(i);
                        String poster_link=object.getString("poster_path");
                        String full_link="http://image.tmdb.org/t/p/w500"+poster_link;
                        links.add(full_link);
                        String get_ID=object.getString("original_title");
                        Ori_Title.add(get_ID);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Adapter adapter=new Adapter(Home.this,links);
                gridView.setAdapter(adapter);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Home.this,"Error while retriving data",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(request);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String selected_item=links.get(position);
                String Selected_OriTitle=Ori_Title.get(position);
                Intent MoveToDetails= new Intent(Home.this,Details.class);
                MoveToDetails.putExtra("Poster",selected_item);
                MoveToDetails.putExtra("Poster_OriTitle",Selected_OriTitle);
                startActivity(MoveToDetails);
                finish();



            }

        });




     }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.app_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Homemenu:
                Intent go_Home=new Intent(this,Home.class);
                startActivity(go_Home);
                finish();

            case R.id.FavMenu:
                Intent go_favs=new Intent(this,Favorits.class);
                startActivity(go_favs);
                finish();
                }
                return super.onOptionsItemSelected(item);
    }
}


