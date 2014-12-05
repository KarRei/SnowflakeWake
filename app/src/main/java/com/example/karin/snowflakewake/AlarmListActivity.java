package com.example.karin.snowflakewake;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.app.ListActivity;

/**
 * Created by Karin on 2014-12-05.
 */
public class AlarmListActivity extends ListActivity {
    private DBHelper dbHelper = new DBHelper(this);
    private AlarmListAdapter mAdapter;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        requestWindowFeature(Window.FEATURE_ACTION_BAR);

        setContentView(R.layout.activity_list);

        mAdapter = new AlarmListAdapter(this, dbHelper.getAlarms());

        setListAdapter(mAdapter);

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
        switch (item.getItemId()) {
            case R.id.action_add_new_alarm: {
                startAlarmDetailsActivity(-1);
                break;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            mAdapter.setAlarms(dbHelper.getAlarms());
            mAdapter.notifyDataSetChanged();
        }
    }

    public void setAlarmEnabled(long id, boolean isEnabled) {
        AlarmModel model = dbHelper.getAlarm(id);
        model.isEnabled = isEnabled;
        dbHelper.updateAlarm(model);

        mAdapter.setAlarms(dbHelper.getAlarms());
        mAdapter.notifyDataSetChanged();

       // AlarmManagerHelper.setAlarms(this);
    }

    public void startAlarmDetailsActivity(long id) {
        Intent intent = new Intent(this, AlarmDetailActivity.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 0);
    }

    public void deleteAlarm(long id) {
        final long alarmId = id;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please confirm")
                .setTitle("Delete set?")
                .setCancelable(true)
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Cancel Alarms
                        //AlarmManagerHelper.cancelAlarms(mContext);
                        //Delete alarm from DB by id
                        dbHelper.deleteAlarm(alarmId);
                        //Refresh the list of the alarms in the adaptor
                        mAdapter.setAlarms(dbHelper.getAlarms());
                        //Notify the adapter the data has changed
                        mAdapter.notifyDataSetChanged();
                        //Set the alarms
                        //AlarmManagerHelper.setAlarms(mContext);
                    }
                }).show();
    }

}
