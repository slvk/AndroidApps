package com.mycompany.listviewdemo;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends ListActivity {
    DatabaseAdapter myDb;
    ArrayList<String> SubjectsList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

       // Log.v("MainActivity", "OnCreate event");
        openDB();
        //Log.v("MainActivity", "Database opened");
        myDb.deleteAll();
        //Log.v("MainActivity", "Table cleared");
        String message = "";
        // for the moment data is inserted here
        //but it should be loaded by special loader
        AddData();


        Log.v("MainActivity", "Rows inserted");

        //TextView textView = new TextView(this);
       // textView.setTextSize(40);



        Cursor cursor = myDb.getAllRows();
        int i = 0;
        String name;
        if (cursor.moveToFirst()) {
            do {
                // Process the data:
                int id = cursor.getInt(myDb.COL_ROWID);
                name = cursor.getString(myDb.COL_NAME);
                message += name+"\n";
                SubjectsList.add(name);
                i++;
            } while(cursor.moveToNext());
        }

        // Close the cursor to avoid a resource leak.
        cursor.close();


        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.activity_list_item, SubjectsList));

        //android.R.layout.simple_list_item_1
    }

    private void openDB() {
        myDb = new DatabaseAdapter(this);
        myDb.open();
        Log.v("MainActivity", "DB opened");
    }


    private void closeDB() {
        myDb.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void AddData(){
        long newId = myDb.insertRow("Терапія");
        newId = myDb.insertRow("Хірургія");
        newId = myDb.insertRow("Травматологія");
        newId = myDb.insertRow("Інфекційні хвороби");
        newId = myDb.insertRow("Фармакологія");
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

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "You have selected " + SubjectsList.get(position), Toast.LENGTH_LONG).show();
    }



}
