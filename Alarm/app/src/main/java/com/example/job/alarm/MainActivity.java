package com.example.job.alarm;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private ToggleButton alarmToggle;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;

    private static final String ACTION_NOTIFY =
            "com.example.job.alarm.ACTION_NOTIFY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent notifyIntent = new Intent(ACTION_NOTIFY);
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (this, NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        boolean alarmUp = (PendingIntent.getBroadcast(this, NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);


        alarmToggle = (ToggleButton) findViewById(R.id.alarmToggle);
        alarmToggle.setChecked(alarmUp);
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String toastMessage;
                if (isChecked){
                    toastMessage = getString(R.string.R_string_alarm_on_toast);
                    long triggerTime = SystemClock.elapsedRealtime();

                    long repeatInterval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;

//If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            triggerTime, repeatInterval, notifyPendingIntent);
                }else {
                    alarmManager.cancel(notifyPendingIntent);
                    mNotificationManager.cancelAll();
                    toastMessage = getString(R.string.R_string_alarm_off_toast);
                }
                Toast.makeText(MainActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

}
