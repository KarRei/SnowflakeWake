package com.example.karin.snowflakewake;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

/**
 * Created by Karin on 2014-11-26.
 */
public class AlarmCRUD {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {DBHelper.COLUMN_ID,
            DBHelper.COLUMN_HOUR,
            DBHelper.COLUMN_MINUTE,
            DBHelper.COLUMN_TIMEAMOUNT,
            DBHelper.COLUMN_SNOWAMOUNT};

    public AlarmCRUD(Context context) { dbHelper = new DBHelper(context);}

    //open databas
    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    //close database
    public void close(){
        dbHelper.close();
    }
/*
    //create object
    public AlarmModel createAlarm()*/

}
