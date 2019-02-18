package com.example.smartBuzz;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .name("howler.realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
