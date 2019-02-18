package com.example.smartBuzz;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartBuzz.model.Alarm;
import com.example.smartBuzz.model.Question;
import com.example.smartBuzz.utils.QuestionManager;

import io.realm.Realm;
import io.realm.RealmResults;

public class SnoozeActivity extends AppCompatActivity {
    private TextView questionTextView;
    private TextView option1;
    private TextView option2;
    private TextView option3;
    private TextView option4;
    private CheckBox checkbox1;
    private CheckBox checkbox2;
    private CheckBox checkbox3;
    private CheckBox checkbox4;
    private AlarmManager alarmManager;
    private Realm realm;
    private int requestCode;
    private MediaPlayer mediaPlayer;
    private Question question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snooze);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        realm = Realm.getDefaultInstance();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        questionTextView = findViewById(R.id.question);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        checkbox3 = findViewById(R.id.checkbox3);
        checkbox4 = findViewById(R.id.checkbox4);
        getQuestionObject();
        setQuestionAndAnswers(question);
    }

    private void setQuestionAndAnswers(Question question) {

        questionTextView.setText(question.getQuestion());
        option1.setText(question.getAnswer1());
        option2.setText(question.getAnswer2());
        option3.setText(question.getAnswer3());
        option4.setText(question.getAnswer4());
    }

    private void checkAnswer(TextView correctOption, CheckBox selectedCheckbox) {
        String correctAnswer = question.getCorrectAnswer();
        if (correctOption.getText().toString().equalsIgnoreCase(correctAnswer)) {
            Log.i("Selected Answer", correctOption.getText().toString());
            String setTime = getIntent().getStringExtra("Alarm time");
            Log.i("Retrieved time", setTime);
            realm.executeTransaction(realm -> {
                RealmResults<Alarm> results = realm.where(Alarm.class).equalTo("alarmTime", setTime).findAll();
                requestCode = results.first().getRequestCode();
                results.deleteAllFromRealm();
            });
            Log.i("Request Code", String.valueOf(requestCode));
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent myIntent = new Intent(getApplicationContext(), SnoozeActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    getApplicationContext(), requestCode, myIntent, 0);
            pendingIntent.cancel();
            alarmManager.cancel(pendingIntent);
            mediaPlayer.stop();
            Intent startAwakeActivity = new Intent(this, FinishActivity.class);
            //To navigate back to the main activity.
            TaskStackBuilder.create(this).addNextIntentWithParentStack(startAwakeActivity).startActivities();
        } else {
            Toast.makeText(this, "You still don't seem to wake up", Toast.LENGTH_LONG).show();
            selectedCheckbox.setChecked(false);
            getQuestionObject();
            setQuestionAndAnswers(question);
        }
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Ohh I am gonna make sure that you wake up.!", Toast.LENGTH_LONG).show();
    }


    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.checkbox1:
                if (checked)
                    checkAnswer(option1, checkbox1);
                break;
            case R.id.checkbox2:
                if (checked)
                    checkAnswer(option2, checkbox2);
                break;
            case R.id.checkbox3:
                if (checked)
                    checkAnswer(option3, checkbox3);
                break;
            default:
                checkAnswer(option4, checkbox4);

        }
    }

    public void getQuestionObject() {
        question = QuestionManager.supplyQuestionObject();
        Log.i("Question", question.getQuestion());
    }
}
