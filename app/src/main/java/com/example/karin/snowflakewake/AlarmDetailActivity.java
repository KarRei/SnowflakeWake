package com.example.karin.snowflakewake;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.karin.snowflakewake.R;

import org.w3c.dom.Text;


public class AlarmDetailActivity extends Activity implements NumberPicker.OnValueChangeListener {

    //create a new instance of AlarmModel
    private AlarmModel alarmDetails;
    private TextView snow;
    private TextView minutes;
    static Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm_detail);

        //initialize the new instance of AlarmModel
        alarmDetails = new AlarmModel();

        getActionBar().setTitle("ADD NEW ALARM");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // textview & button first page
        snow = (TextView) findViewById(R.id.SnowAmount);
        minutes = (TextView) findViewById(R.id.TimeAmount);
        Button weatherSettings = (Button) findViewById(R.id.WeatherButton);

        // what happens when you click on b?
        weatherSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the dialog shows
                show();
            }
        });
    }

    // Write the value of np
    @Override
    public  void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        Log.i("Value is", "" +newVal);
    }

    public void show()
    {
        // Create the dialog interface
        final Dialog d = new Dialog(AlarmDetailActivity.this);
        d.setTitle("Weather Options");
        d.setContentView(R.layout.dialog_weather_options);

        // The buttons in the dialog
        Button setOptions = (Button) d.findViewById(R.id.SetWeatherOptions);
        Button cancel = (Button) d.findViewById(R.id.CancelButton);

        // Numberpicker
        final NumberPicker snowPick = (NumberPicker) d.findViewById(R.id.SnowPicker);
        snowPick.setMaxValue(50);
        snowPick.setMinValue(0);
        snowPick.setWrapSelectorWheel(true);
        snowPick.setOnValueChangedListener(this);

        final NumberPicker minPick = (NumberPicker) d.findViewById(R.id.MinutePicker);
        minPick.setMaxValue(120);
        minPick.setMinValue(0);
        minPick.setWrapSelectorWheel(true);
        minPick.setOnValueChangedListener(this);

        // What happens when u click on b?
        setOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the value from the np in the textfield tv
                snow.setText(String.valueOf(snowPick.getValue()));
                minutes.setText(String.valueOf(minPick.getValue()));
                // Close the dialog
                d.dismiss();
            }
        });

        // what happens when u click on
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dialogen stängs bara ner
                d.dismiss();
            }
        });

        // Tillsist, Öppna dialog!
        d.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.alarm_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // enable the up button in the action bar,
        // we can respond to the up affordance click and close the activity
        switch (item.getItemId()){
            case android.R.id.home: {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
