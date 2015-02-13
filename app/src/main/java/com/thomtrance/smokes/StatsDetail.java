package com.thomtrance.smokes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class StatsDetail extends ActionBarActivity {

    ListView lv;
    SimpleCursorAdapter adapter;
    Cursor c;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_detail);
    }


    @Override
    protected void onResume(){
        super.onResume();

        lv = (ListView) findViewById(R.id.detaillist);

        SmokesDBHelper sdbh = new SmokesDBHelper(this);

        db = sdbh.getReadableDatabase();

        String[] projection = {SmokeEntry._ID,SmokeEntry.COLUMN_NAME_SMOKE_TIME,SmokeEntry.COLUMN_NAME_SMOKE_DATE};
        String[] from = {SmokeEntry.COLUMN_NAME_SMOKE_DATE,SmokeEntry.COLUMN_NAME_SMOKE_TIME};
        int[] to = {android.R.id.text1,android.R.id.text2};
        c = db.query(SmokeEntry.TABLE_NAME,projection,null,null,null, null, null);


//        String[] projection = {SmokeEntry._ID,"COUNT(*) AS cnt",SmokeEntry.COLUMN_NAME_SMOKE_DATE};
//        String[] from = {SmokeEntry.COLUMN_NAME_SMOKE_DATE,"cnt"};
//        int[] to = {android.R.id.text1,android.R.id.text2};
//        String groupBy = SmokeEntry.COLUMN_NAME_SMOKE_DATE;
//        String orderBy = SmokeEntry.COLUMN_NAME_SMOKE_DATE;
//        c = db.query(SmokeEntry.TABLE_NAME,projection,null,null,groupBy, orderBy, null);



        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_2, c, from, to,0);

        lv.setAdapter(adapter);

        //fix this bit
        Intent pi = getIntent();
        String theDate="not found";
        String[] theExtras = pi.getExtras().getStringArray("date");
        if(theExtras!=null&& theExtras.length > 0 ){
            theDate = theExtras[0];
        }

        Toast.makeText(this,theDate,Toast.LENGTH_SHORT);

    }

    @Override
    public void onPause(){
        super.onPause();
        if(c!=null) {
            c.close();
        }

        if (db!=null){
            db.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stats_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
