package com.example.karin.snowflakewake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.karin.snowflakewake.AlarmContract.Alarm;

/**
 * Created by Karin on 2014-11-26.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "alarmclock.db";

    //Database creation sql statement
    private static final String SQL_CREATE_ALARM =
            "CREATE TABLE "
            + Alarm.TABLE_NAME + "("
            + Alarm._ID + " integer primary key autoincrement, "
            + Alarm.COLUMN_NAME_ALARM_NAME + " text,"
            + Alarm.COLUMN_NAME_ALARM_HOUR + " integer,"
            + Alarm.COLUMN_NAME_ALARM_MINUTE + " integer,"
            + Alarm.COLUMN_NAME_ALARM_SNOWAMOUNT + " integer,"
            + Alarm.COLUMN_NAME_ALARM_TIMEAMOUNT + " integer,"
            + Alarm.COLUMN_NAME_ALARM_ENABLE + " boolean "
            + ")";

    private static final String SQL_DELETE_ALARM =
            "DROP TABLE IF EXTISTS " + Alarm.TABLE_NAME;

    public DBHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) { database.execSQL(SQL_CREATE_ALARM); }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ALARM);
        onCreate(db);
    }


    //Map model from and to database
    private AlarmModel populateModel(Cursor c){

        AlarmModel model = new AlarmModel();
        model.id = c.getLong(c.getColumnIndex(Alarm._ID));
        model.name = c.getString(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_NAME));
        model.timeHour = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_HOUR));
        model.timeMinute = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_MINUTE));
        model.snowAmount = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_SNOWAMOUNT));
        model.timeAmount = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_TIMEAMOUNT));
        model.isEnabled = c.getInt(c.getColumnIndex(Alarm.COLUMN_NAME_ALARM_ENABLE)) != 0;

        return model;

    }

    private ContentValues populateContent(AlarmModel model){
        ContentValues values = new ContentValues();
        values.put(Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        values.put(Alarm.COLUMN_NAME_ALARM_HOUR, model.timeHour);
        values.put(Alarm.COLUMN_NAME_ALARM_MINUTE, model.timeMinute);
        values.put(Alarm.COLUMN_NAME_ALARM_SNOWAMOUNT, model.snowAmount);
        values.put(Alarm.COLUMN_NAME_ALARM_TIMEAMOUNT, model.timeAmount);

        return values;
    }

    //functions to create and read methodss
    public long createAlarm(AlarmModel model) {

        ContentValues values = populateContent(model);
        Log.d("err", "HEEEEPL");
        return getWritableDatabase().insert(Alarm.TABLE_NAME, null, values);
    }

    // functions for update and delete
    public long updateAlarm(AlarmModel model){
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(Alarm.TABLE_NAME, values, Alarm._ID + " = ?", new String[] { String.valueOf(model.id)});

    }

    public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME + " WHERE " + Alarm._ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if(c.moveToNext()){
            return populateModel(c);
        }

        db.close();

        return null;
    }

    //function to populate a list of alarm
    public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + Alarm.TABLE_NAME;

        Cursor c = db.rawQuery(select, null);

        List<AlarmModel> alarmList = new ArrayList<AlarmModel>();

        while (c.moveToNext()) {
            alarmList.add(populateModel(c));
        }

        if( !alarmList.isEmpty()){
            return alarmList;
        }
        return null;
    }

    public int deleteAlarm(long id){
        return getWritableDatabase().delete(Alarm.TABLE_NAME, Alarm._ID + " = ?", new String[]{String.valueOf(id)});
    }

}
