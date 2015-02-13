package com.thomtrance.smokes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    String sqlNOW="";
    Calendar cal = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton btn = (ImageButton) findViewById(R.id.btn_smoke);
        btn.setOnClickListener(this);

        TextView tv = (TextView) findViewById(R.id.day_total);

        SimpleDateFormat nowSDF = new SimpleDateFormat("yyyy.MM.dd");
        Date nowDate =  new Date(cal.getTimeInMillis());
        sqlNOW = nowSDF.format(nowDate);

        TextView today = (TextView) findViewById(R.id.today_date);
        today.setText(sqlNOW);

        SmokesDBHelper sdbh = new SmokesDBHelper(this);

        SQLiteDatabase db = sdbh.getReadableDatabase();

       /* //get sqlnow
        String[] nowProj= {"date('"+sqlNOW+"')"};

       Cursor mNowcursor = db.query(SmokeEntry.TABLE_NAME,nowProj,null,null,null, null, null);


        if(mNowcursor.getCount()>0){
            mNowcursor.moveToFirst();
            sqlNOW = mNowcursor.getString(0);
        }*/



        //get current values
        String[] projection = {SmokeEntry.COLUMN_NAME_SMOKE_TIME};
        String selection="smoke_date = ?";
        String[] selectArgs={sqlNOW};


       Cursor c = db.query(SmokeEntry.TABLE_NAME,projection,selection,selectArgs,null, null, null);

        int total = c.getCount();
        String totStr = String.valueOf(total);
        tv.setText(totStr);

        c.close();
        db.close();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

       switch(id){


           case R.id.action_settings:

               return true;

           case R.id.action_chart:
               return false;

           case R.id.action_stat:

               Intent histIntent = new Intent(this, StatsActivity.class);
               startActivity(histIntent);
               return true;

           default:
               return super.onOptionsItemSelected(item);
       }



    }

    public void addSmoke(View v){

        //get datetime

       //Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        Date d = new Date(cal.getTimeInMillis());
        String dateStr = sdf.format(d);

       // SimpleDateFormat nowSDF = new SimpleDateFormat("yyyy.MM.dd");
       // Date nowDate =  new Date(cal.getTimeInMillis());
       // sqlNOW = nowSDF.format(nowDate);


        //get location here
        String location="not implemented";

        //add to db

        SmokesDBHelper sdbh = new SmokesDBHelper(this);

        SQLiteDatabase myDB = sdbh.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(SmokeEntry.COLUMN_NAME_SMOKE_TIME,dateStr);
        cv.put(SmokeEntry.COLUMN_NAME_SMOKE_LOCATION,location);
        cv.put(SmokeEntry.COLUMN_NAME_SMOKE_DATE,sqlNOW);


        long newrowid=0;

        newrowid=myDB.insert(SmokeEntry.TABLE_NAME,null,cv);


        Toast.makeText(this,"Smoke added! Row ID: "+newrowid, Toast.LENGTH_LONG).show();

        //get current values
        String[] projection = {SmokeEntry.COLUMN_NAME_SMOKE_TIME};
        String selection="smoke_date = ?";
        String[] selectArgs={sqlNOW};


        Cursor c = myDB.query(SmokeEntry.TABLE_NAME,projection,selection,selectArgs,null, null, null);

        int total = c.getCount();

        TextView totView = (TextView) findViewById(R.id.day_total);
        String totStr = String.valueOf(total);
        totView.setText(totStr);

        c.close();
        myDB.close();


    }



    @Override
    public void onClick(View v) {

        addSmoke(v);
    }
}
