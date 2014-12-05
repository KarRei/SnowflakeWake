package com.example.karin.snowflakewake;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.karin.snowflakewake.R;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;


public class AlarmDetailActivity extends Activity implements NumberPicker.OnValueChangeListener {

    //create a new instance of AlarmModel
    private AlarmModel alarmDetails;

    private DBHelper dbHelper = new DBHelper(this);

    private TimePicker timePicker;

    private TextView snow;
    private int valueSnow;

    private TextView minutes;
    private int valueMinutes;

    //lilla texten efter siffrorna som dyker upp efter dialogrutan
    private TextView cm;
    private TextView min;

   // static Dialog d;
   // TimePicker timepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_alarm_detail);

        //initialize the new instance of AlarmModel
        alarmDetails = new AlarmModel();

        getActionBar().setTitle("ADD NEW ALARM");
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // timepicker

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        // textview & button first page
//        timePicker = (TimePicker) findViewById(R.id.timePicker);
        snow = (TextView) findViewById(R.id.SnowAmount);
        minutes = (TextView) findViewById(R.id.TimeAmount);
        Button weatherSettings = (Button) findViewById(R.id.WeatherButton);
        Button savealarm = (Button) findViewById(R.id.SaveAlarm);

        cm = (TextView) findViewById(R.id.centimeter);
        min = (TextView) findViewById(R.id.minutes);

        // what happens when you click on b?
        weatherSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the dialog shows
                show();
            }
        });

        long id = getIntent().getExtras().getLong("id");

        if (id == -1) {
            alarmDetails = new AlarmModel();
        } else {
            alarmDetails = dbHelper.getAlarm(id);

            timePicker.setCurrentMinute(alarmDetails.timeMinute);
            timePicker.setCurrentHour(alarmDetails.timeHour);

            snow.setText(String.valueOf(alarmDetails.snowAmount));
            minutes.setText(String.valueOf(alarmDetails.timeAmount));

            valueMinutes = alarmDetails.timeAmount;
            valueSnow = alarmDetails.snowAmount;
            //edtName.setText(alarmDetails.name);

        }

        //instructions for when save button is clicked
        savealarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateModelFromLayout();

                //DBHelper dbHelper = new DBHelper(getApplicationContext());
                if (alarmDetails.id < 0) {
                    dbHelper.createAlarm(alarmDetails);
                    Log.d("err", "XXXXXXXXXXXXX");

                } else {
                    dbHelper.updateAlarm(alarmDetails);
                }
                setResult(RESULT_OK);
                finish();
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
        snowPick.setValue(snowPick.getValue());
        snowPick.setWrapSelectorWheel(true);
        snowPick.setOnValueChangedListener(this);

        final NumberPicker minPick = (NumberPicker) d.findViewById(R.id.MinutePicker);
        minPick.setMaxValue(120);
        minPick.setMinValue(0);
        minPick.setValue(minPick.getValue());
        minPick.setWrapSelectorWheel(true);
        minPick.setOnValueChangedListener(this);


        // What happens when u click on b?
        setOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the value from the np in the textfield tv
                if((snowPick.getValue() != 0) || (minPick.getValue() != 0))  {
                    snow.setText(String.valueOf(snowPick.getValue()));

                    minutes.setText(String.valueOf(minPick.getValue()));
                    // Close the dialog

                    valueSnow = snowPick.getValue();
                    valueMinutes = minPick.getValue();

                    min.setText(" min");
                    cm.setText(" cm");
                }
                else
                {
                    cm.setText("");
                    min.setText("");
                    minutes.setText("");
                    snow.setText("");
                }


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

        // Tillsist, Öppna dialog!F
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

    private void updateModelFromLayout(){
        //TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmDetails.timeMinute = timePicker.getCurrentMinute();
        alarmDetails.timeHour = timePicker.getCurrentHour(); //.intValue() (?)

        alarmDetails.snowAmount = valueSnow;

        alarmDetails.timeAmount = valueMinutes;

        alarmDetails.isEnabled = true;

        alarmDetails.name = "Heeej";

        //Log.d("err", alarmDetails.timeHour);

    }

}
