package com.example.karin.snowflakewake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    //when the plus sign is clicked, a new intent is created and a new activity starts
    //We can know which action has been selected because this method passes in a menu
    //item that we can relate back to the Id of our item definition in the activity_list.xml file
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_new_alarm: {
                Intent intent = new Intent(this, AlarmDetailActivity.class);

                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
