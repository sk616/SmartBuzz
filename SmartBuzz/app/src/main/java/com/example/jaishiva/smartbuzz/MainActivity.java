package com.example.jaishiva.smartbuzz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.jaishiva.smartbuzz.model.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<String> myDataset;
    private FloatingActionButton fab;
    private TextView dummy;
    private int hourOfDay,minute;
    private Realm realm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDataset=new ArrayList<String>();
        myDataset.add("10:10 AM");
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        fab=findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  timeDialog();
            }

        });

    }

    public void timeDialog()
    {
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                String AM_PM ;
                if(selectedHour >12) {
                    selectedHour=selectedHour-12;
                    AM_PM = "PM";
                }
                else if(selectedHour==12)
                    AM_PM="PM";
                else
                {
                    AM_PM = "AM";
                }
                writeToDB(Integer.toString(selectedHour),Integer.toString(selectedMinute));
                mAdapter.getTime(selectedHour,selectedMinute,AM_PM);
                startAlert(selectedHour,selectedMinute);
                writeToDB(Integer.toString(selectedHour),Integer.toString(selectedMinute));

            }
        }, hourOfDay, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
    public void startAlert(int selectedHour, int selectedMinute){
        Calendar cal_now = Calendar.getInstance();
        final Calendar cal_set= (Calendar)cal_now.clone();
        cal_set.set(Calendar.HOUR_OF_DAY,selectedHour);
        cal_set.set(Calendar.MINUTE,selectedMinute);
        cal_set.set(Calendar.SECOND,0);
        cal_set.set(Calendar.MILLISECOND,0);
        if(cal_set.compareTo(cal_now)<=0)
            cal_set.add(Calendar.DATE,1);
        Intent intent = new Intent(getBaseContext(), MyBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), 234324243, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_set.getTimeInMillis(),pendingIntent);

    }
    public void writeToDB(final String hour, final String minute)
    {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Time time = bgRealm.createObject(Time.class);
                time.setHour(hour);
                time.setMinute(minute);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Database","Database successfully updated");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Log.e("Database",error.getMessage());
            }
        });
    }
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }
}
