package com.example.amrepanda.movies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Details extends AppCompatActivity {
ImageView imageView;
TextView R_Date,V_Rate,Original_Name,Story,path;
Button Favs;
ArrayList<String> List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        List=new ArrayList<String>();
        path=(TextView)findViewById(R.id.path);
       imageView=(ImageView)findViewById(R.id.IV);
       R_Date=(TextView)findViewById(R.id.Release_Date);
       V_Rate=(TextView)findViewById(R.id.VoteRate);
       Original_Name=(TextView)findViewById(R.id.OriginalName);
       Story=(TextView)findViewById(R.id.Description);
       Favs=(Button)findViewById(R.id.Favorits);
       final String get_selected_OriginalTitle=getIntent().getExtras().getString("Poster_OriTitle");
        final String get_selected_item=getIntent().getExtras().getString("Poster");
        Picasso.with(Details.this).load(get_selected_item).into(imageView);
        RequestQueue queue= Volley.newRequestQueue(this);
        StringRequest request=new StringRequest(Request.Method.GET, "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=d04160312987af22a80ba27b59cd080c", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject ob=new JSONObject(response);
                    JSONArray Array=ob.getJSONArray("results");
                    for (int i=0; i<Array.length(); i++)
                    {
                        JSONObject object=Array.getJSONObject(i);
                        String fetch_OriginalTitle=object.getString("original_title");
                        if (get_selected_OriginalTitle.equals(fetch_OriginalTitle))

                        {
                            String Vote_Avg=object.getString("vote_average");
                            String Orig_title=object.getString("original_title");
                            String Releas_Date=object.getString("release_date");
                            String Overview=object.getString("overview");
                            String PosteR_Path=object.getString("poster_path");

                            R_Date.setText(Releas_Date);
                            V_Rate.setText(Vote_Avg);
                            Original_Name.setText(Orig_title);
                            Story.setText(Overview);
                            path.setText(PosteR_Path);

                        };


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Toast.makeText(Details.this,"Error while retreiving data",Toast.LENGTH_LONG).show();

            }
        });
        queue.add(request);

        final String OriginalName=Original_Name.getText().toString();


        Favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db=new Database(Details.this);
                SQLiteDatabase SQL=db.getWritableDatabase();
                Cursor C=SQL.query(Database.TABLE_NAME,new String[]{Database.COLUMN_NAME},null,null,null,null,null,null);
                if (C.moveToFirst())
                    do
                    {
                        String get_ori_name=C.getString(C.getColumnIndex(Database.COLUMN_NAME));
                        List.add(get_ori_name);

                    }
                    while (C.moveToNext());
                String Compare=Original_Name.getText().toString();
                if (List.contains(Compare))
                {
                    Toast.makeText(Details.this,"Movie already exist in your Favorits",Toast.LENGTH_LONG).show();
                }
                else {

                    String Save_Poster = path.getText().toString();
                    String Save_originalTitle = Original_Name.getText().toString();
                    String Save_OverView = Story.getText().toString();
                    String Save_ReleaseDate = R_Date.getText().toString();
                    String Save_VoteAvg = V_Rate.getText().toString();
                    ContentValues V = new ContentValues();
                    V.put(Database.COLUMN_NAME, Save_originalTitle);
                    V.put(Database.COLUMN_POSTER, Save_Poster);
                    V.put(Database.COLUMN_OVERVIEW, Save_OverView);
                    V.put(Database.COULMN_VOTE, Save_VoteAvg);
                    V.put(Database.COULMN_Date, Save_ReleaseDate);
                    SQL.insert(Database.TABLE_NAME, null, V);
                    Toast.makeText(Details.this, OriginalName + "Has been added to Favorits", Toast.LENGTH_LONG).show();
                }





            }
        });





    }
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
                Intent go_home=new Intent(this,Home.class);
                startActivity(go_home);
                finish();

            case R.id.FavMenu:
                Intent go_favs=new Intent(this,Favorits.class);
                startActivity(go_favs);
                finish();
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Intent back=new Intent(Details.this,Home.class);
        startActivity(back);
        finish();
    }
}
