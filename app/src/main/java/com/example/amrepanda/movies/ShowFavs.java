package com.example.amrepanda.movies;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class ShowFavs extends AppCompatActivity {
    ImageView imageView;
    TextView Date,Vote,Title,Story,path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_favs);
        imageView=(ImageView)findViewById(R.id.Favs_IV);
        Date=(TextView)findViewById(R.id.Fav_Date);
        Vote=(TextView)findViewById(R.id.Fav_Vote);
        Title=(TextView)findViewById(R.id.Fav_Title);
        Story=(TextView)findViewById(R.id.Fav_Story);
        String get_FaveTitle=getIntent().getExtras().getString("Selected_fav");
        String get_FavPos=getIntent().getExtras().get("Selected_poster").toString();
        path=(TextView)findViewById(R.id.path);
        path.setText(get_FavPos);
            Picasso.with(ShowFavs.this).load(get_FavPos).into(imageView);
        Database db=new Database(ShowFavs.this);
        SQLiteDatabase SQL=db.getReadableDatabase();
        Cursor c=SQL.query(Database.TABLE_NAME,new String[]{Database.COLUMN_NAME,Database.COLUMN_OVERVIEW,Database.COULMN_Date,Database.COULMN_VOTE,Database.COLUMN_POSTER},Database.COLUMN_NAME+"=?",new String[]{get_FaveTitle},null,null,null,null);
        if (c.moveToFirst())
        {
            String get_Title=c.getString(c.getColumnIndex(Database.COLUMN_NAME));
            Title.setText(get_Title);
            String get_Overview=c.getString(c.getColumnIndex(Database.COLUMN_OVERVIEW));
            Story.setText(get_Overview);
            String get_Date=c.getString(c.getColumnIndex(Database.COULMN_Date));
            Date.setText(get_Date);
            String get_Rate=c.getString(c.getColumnIndex(Database.COULMN_VOTE));
            Vote.setText(get_Rate);

        }



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
        Intent back=new Intent(ShowFavs.this,Favorits.class);
        startActivity(back);
        finish();
    }
}
