package com.example.karin.snowflakewake;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karin on 2014-11-26.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_ALARMMODELS = "alarmmodels";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_HOUR = "timeHour";
    public static final String COLUMN_MINUTE = "timeMintue";
    public static final String COLUMN_SNOWAMOUNT = "snowamount";
    public static final String COLUMN_TIMEAMOUNT = "timeamount";
    public static final String COLUMN_ENABLE = "isEnabled";
    public static final String DATABASE_NAME = "alarmmodels.db";
    public static final int DATABASE_VERSION = 1;

    //Database creation sql statement
    private static final String DATABASE_CREATE =
            "CREATE TABLE "
            + TABLE_ALARMMODELS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_NAME + " text not null,"
            + COLUMN_HOUR + " text not null,"
            + COLUMN_MINUTE + " text not null,"
            + COLUMN_SNOWAMOUNT + " text not null,"
            + COLUMN_TIMEAMOUNT + " text not null,"
            + COLUMN_ENABLE + " text not null "
            + ")";

    private static final String DATABASE_DELETE =
            "DROP TABLE IF EXTISTS " + TABLE_ALARMMODELS;

    public DBHelper(Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase database) { database.execSQL(DATABASE_CREATE); }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DATABASE_DELETE);
        onCreate(db);
    }


    //Map model from and to database
    private AlarmModel populateModel(Cursor c){

        AlarmModel model = new AlarmModel();
        model.id = c.getLong(c.getColumnIndex(COLUMN_ID));
        model.name = c.getString(c.getColumnIndex(COLUMN_NAME));
        model.timeHour = c.getInt(c.getColumnIndex(COLUMN_HOUR));
        model.timeMinute = c.getInt(c.getColumnIndex(COLUMN_MINUTE));
        model.snowAmount = c.getInt(c.getColumnIndex(COLUMN_SNOWAMOUNT));
        model.timeAmount = c.getInt(c.getColumnIndex(COLUMN_TIMEAMOUNT));
        model.isEnabled = c.getInt(c.getColumnIndex(COLUMN_ENABLE)) != 0;

        return model;

    }

    private ContentValues populateContent(AlarmModel model){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, model.name);
        values.put(COLUMN_HOUR, model.timeHour);
        values.put(COLUMN_MINUTE, model.timeMinute);
        values.put(COLUMN_SNOWAMOUNT, model.snowAmount);
        values.put(COLUMN_TIMEAMOUNT, model.timeAmount);

        return values;
    }

    //functions to create and read methodss
    public long createAlarm(AlarmModel model) {
        ContentValues values = populateContent(model);
        return getWritableDatabase().insert(TABLE_ALARMMODELS, null, values);
    }

    public AlarmModel getAlarm(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + TABLE_ALARMMODELS + " WHERE " + COLUMN_ID + " = " + id;

        Cursor c = db.rawQuery(select, null);

        if(c.moveToNext()){
            return populateModel(c);
        }

        return null;
    }

    // functions for update and delete
    public long updateAlarm(AlarmModel model){
        ContentValues values = populateContent(model);
        return getWritableDatabase().update(TABLE_ALARMMODELS, values, COLUMN_ID + " = ?", new String[] { String.valueOf(model.id)});
    }

    public int deleteAlarm(long id){
        return getWritableDatabase().delete(TABLE_ALARMMODELS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    //function to populate a list of alarm
    public List<AlarmModel> getAlarms() {
        SQLiteDatabase db = this.getReadableDatabase();

        String select = "SELECT * FROM " + TABLE_ALARMMODELS;

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

}
