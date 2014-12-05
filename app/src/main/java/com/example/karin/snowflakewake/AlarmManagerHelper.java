package com.example.karin.snowflakewake;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.karin.snowflakewake.AlarmContract.Alarm;
import java.util.Calendar;
import java.util.List;


public class AlarmManagerHelper extends BroadcastReceiver{

    // Context = hotellpersonal som kommer med frukost till dig
    // du är theActivity, frukosten är resources, personalen är context

    @Override
    public void onRecieve(Context context, Intent intent)
    {
        setAlarms(context);
    }

    public static void setAlarms(Context context)
    {

        cancelAlarms(context);

        DBHelper dbHelper = new DBHelper(context);

        List<AlarmModel> alarms = dbHelper.getAlarms();


        for(AlarmModel alarm : alarms){

            if(alarm.isEnabled){

                PendingIntent pIntent = createPendingIntent(context, alarm);

                //Create calendar for the alarm
                Calendar alarmCal = Calendar.getInstance();
                alarmCal.set(Calendar.HOUR_OF_DAY, alarm.timeHour);
                alarmCal.set(Calendar.MINUTE, alarm.timeMinute);
                alarmCal.set(Calendar.SECOND, 00);

                //get values from this moment
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

                //make ints for comparisons
                int alarmDay = alarmCal.get(Calendar.DAY_OF_WEEK);
                int alarmHour = alarmCal.get(Calendar.HOUR_OF_DAY);
                int alarmMinute = alarmCal.get(Calendar.MINUTE);

                //if time has not already passed today
                if( (nowHour == alarmHour && nowMinute < alarmMinute) || nowHour < alarmHour)
                {
                    alarmCal.set(Calendar.DAY_OF_WEEK, alarmDay);//set alarmCal per usual
                    setAlarm(context, alarmCal, pIntent);

                }
                else //change alarmCal to next day
                {
                    if(alarmDay == 7)
                        alarmDay = 1;
                    else alarmDay++;

                    alarmCal.set(Calendar.DAY_OF_WEEK, alarmDay);

                    setAlarm(context, alarmCal, pIntent);

                }

            }

        }

    }

    @SuppressLint("NewApi")
    private static void setAlarm(Context context, Calendar calendar, PendingIntent pIntent) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pIntent);
        }
    }


    // we have to cancel the alarm if there is changes, to avoid losing reference to existing alarms
    // when canceling, create a new PendingIntent that is compared to the exisitng ones in the list
    public static void cancelAlarms(Context context)
    {
        DBHelper dbHelper = new DBHelper(context);

        List<AlarmModel> alarms = dbHelper.getAlarms();

        if(alarms != null)
        {
            // loop through the list
            for(AlarmModel alarm : alarms)
            {
                if(alarm.isEnabled)
                {
                    PendingIntent pIntent = createPendingIntent(context, alarm);

                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                    alarmManager.cancel(pIntent);
                }
            }
        }

    }
    // Creates an intent that starts the alarm at given time, even if our app is closed
    private static PendingIntent createPendingIntent(Context context, AlarmModel model)
    {
        Intent intent = new Intent(context, AlarmService.class);
        intent.putExtra(Alarm._ID, model.id);
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_NAME, model.name);
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_HOUR, model.timeHour);
        intent.putExtra(Alarm.COLUMN_NAME_ALARM_MINUTE, model.timeMinute);

        return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
