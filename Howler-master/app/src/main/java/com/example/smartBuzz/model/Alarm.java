package com.example.smartBuzz.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class Alarm extends RealmObject {

    @PrimaryKey
    private int id;
    private String alarmTime;
    private int requestCode;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public int getRequestCode() {
        return requestCode;
    }

}
