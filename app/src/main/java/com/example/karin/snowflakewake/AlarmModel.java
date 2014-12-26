package com.example.karin.snowflakewake;

import android.net.Uri;

/**
 * Created by Karin on 2014-11-23.
 */

//this class will define what data is stored for our alarm
public class AlarmModel {
    public long id = -1;
    public int timeHour;
    public int timeMinute;
    public String name;
    public String alarmTone;
    //public Uri alarmTone;
    // A Uniform Resource Identifier that identifies an abstract or physical resource
    // En variabel för att användaren egentligen ska kunna välja vilken ringsigna som ska ringa.
    // måste ändras från Uri till string, eftersom vi ska ha en statisk signal
    // alt. gå igenom tutorialen igen för att se över reserande kod för att det ska funka.
    public int snowAmount;
    public int timeAmount;
    public boolean isEnabled;

    //constructor (?)
    public AlarmModel(){ }

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getName() {return name;}
    public void  setName(String name) {this.name = name;}

    public int getTimeHour() {return timeHour;}
    public void setTimeHour(int timeHour) {this.timeHour = timeHour;}

    public int getTimeMinute() {return timeMinute;}
    public void setTimeMinute(int timeMinute) {this.timeMinute = timeMinute;}

    public int getSnowAmount() {return snowAmount;}
    public void setSnowAmount(int snowAmount) {this.snowAmount = snowAmount;}

    public int getTimeAmount() {return timeAmount;}
    public void setTimeAmount(int timeAmount) {this.timeAmount = timeAmount;}


}
