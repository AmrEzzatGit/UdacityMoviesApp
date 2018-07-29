package com.example.amrepanda.movies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class Favorits extends AppCompatActivity {
    GridView gridview;
    ArrayList<String> posters;
    ArrayList<String> Titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorits);
        gridview=(GridView)findViewById(R.id.FavsGV);
        posters=new ArrayList<String>();
        Titles=new ArrayList<String>();
        Database db=new Database(Favorits.this);
        SQLiteDatabase SQL=db.getReadableDatabase();
        Cursor c=SQL.query(Database.TABLE_NAME,new String[]{Database.COLUMN_POSTER,Database.COLUMN_NAME},null,null,null,null,null);
        if (c.moveToFirst())
        {
         do
         {
             String get_poster=c.getString(c.getColumnIndex(Database.COLUMN_POSTER));
             String Poster_link="http://image.tmdb.org/t/p/w500"+get_poster;
             String get_Titles=c.getString(c.getColumnIndex(Database.COLUMN_NAME));
             Titles.add(get_Titles);
             posters.add(Poster_link);
         }
         while (c.moveToNext());
            FavsAdapter adapter=new FavsAdapter(Favorits.this,posters);
            gridview.setAdapter(adapter);



        }

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Selected_Fav=Titles.get(position);
                String Selected_Poster=posters.get(position);
                Intent Show_fav=new Intent(Favorits.this,ShowFavs.class);
                Show_fav.putExtra("Selected_fav",Selected_Fav);
                Show_fav.putExtra("Selected_poster",Selected_Poster);
                startActivity(Show_fav);
                finish();

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
        Intent home=new Intent(Favorits.this,Home.class);
        startActivity(home);
        finish();
    }
}
