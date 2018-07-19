package com.example.jaishiva.smartbuzz.model;

import io.realm.Realm;
import io.realm.RealmObject;

public class Time extends RealmObject{
    String hour;
    String minute;
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        return "Time{" +
                "hour='" + hour + '\'' +
                ", minute='" + minute + '\'' +
                '}';
    }
}
