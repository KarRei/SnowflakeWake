package com.example.karin.snowflakewake;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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


                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarm.timeHour);
                calendar.set(Calendar.MINUTE, alarm.timeMinute);
                calendar.set(Calendar.SECOND, 00);

                final int nowDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                final int nowHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                final int nowMinute = Calendar.getInstance().get(Calendar.MINUTE);

                setAlarm(context, calendar, pIntent);
                //boolean alarmSet = true;



            }

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
        intent.putExtra(ID, model.id);
        intent.putExtra(NAME, model.name);
        intent.putExtra(TIME_HOUR, model.timeHour);
        intent.putExtra(TIME_MINUTE, model.timeMinute);
        intent.putExtra(TONE, model.alarmTone);

        return PendingIntent.getService(context, (int) model.id, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }


}
