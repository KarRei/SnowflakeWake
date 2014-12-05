package com.example.karin.snowflakewake;

import android.provider.BaseColumns;

/**
 * Created by Karin on 2014-12-02.
 */
public final class AlarmContract {
    public AlarmContract() {}

    public static abstract class Alarm implements BaseColumns{
        public static final String TABLE_NAME = "alarm";
        //public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME_ALARM_NAME = "name";
        public static final String COLUMN_NAME_ALARM_HOUR = "hour";
        public static final String COLUMN_NAME_ALARM_MINUTE = "minute";
        public static final String COLUMN_NAME_ALARM_SNOWAMOUNT = "snowamount";
        public static final String COLUMN_NAME_ALARM_TIMEAMOUNT = "timeamount";
        public static final String COLUMN_NAME_ALARM_ENABLE = "enabled";
    }
}
