package com.example.l8440131.eventmanager;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.l8440131.eventmanager.helpers.DBHelper;
import com.example.l8440131.eventmanager.models.Event;
import com.example.l8440131.eventmanager.services.NotificationPublisher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateEventActivity extends AppCompatActivity {
    private EditText txtEvent, txtDescription, txtLocation, txtStartTime, txtEndTime;
    private CheckBox chkBox1,chkBox2,chkBox3;
    private Button btnSave;
    private String eventDate;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    final DBHelper db = new DBHelper(this);
    String startTimeText;
    String endTimeText;
    Calendar mcurrentTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
        txtEvent = (EditText) findViewById(R.id.txtEvent);
        txtDescription = (EditText) findViewById(R.id.txtDescription);
        txtLocation = (EditText) findViewById(R.id.txtLocation);
        txtStartTime = (EditText) findViewById(R.id.txtStartTime);
        txtEndTime = (EditText) findViewById(R.id.txtEndTime);
        btnSave = (Button) findViewById(R.id.btnSave);
        chkBox1=(CheckBox)findViewById(R.id.chkBox1);
        chkBox2=(CheckBox)findViewById(R.id.chkBox2);
        chkBox3=(CheckBox)findViewById(R.id.chkBox3);
        Intent intent = getIntent();
        eventDate = intent.getStringExtra("selected_date");
        txtStartTime.setText(new StringBuilder().append(eventDate));
        txtEndTime.setText(new StringBuilder().append(eventDate));


    }

    public void scheduleNotification(Notification notification, long delay) {
        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, delay, pendingIntent);
        if (chkBox1.isChecked()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, delay,
                    1000 * 60 * 5, pendingIntent);
        }
        if (chkBox2.isChecked()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, delay,
                    1000 * 60 * 30, pendingIntent);
        }
        if (chkBox3.isChecked()){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, delay,
                    1000 * 60 * 60, pendingIntent);
        }

    }

    public Notification getNotification(String eventTitle, String description) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle(eventTitle);
        builder.setContentText(description);
        builder.setSmallIcon(R.drawable.ic_stat_calendar_today);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

    boolean validateInputs() {
        return true;
    }

    public void showTimePicker(final boolean startTime) {

        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(CreateEventActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                setTime(selectedHour, selectedMinute, startTime);

            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle(startTime ? "Select Start Time" : "Select End Time");
        mTimePicker.show();
    }

    public void setTime(int hour, int min, boolean startTime) {
        String format = "";
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }


        if (startTime) {
            startTimeText = hour + ":" + min + " " + format;
            txtStartTime.setText(new StringBuilder().append(eventDate).append("  ").append(hour).append(":").append(min)
                    .append(" ").append(format));
        } else {
            endTimeText = hour + ":" + min + " " + format;
            txtEndTime.setText(new StringBuilder().append(eventDate).append("  ").append(hour).append(":").append(min)
                    .append(" ").append(format));
        }

    }

    public void showSuccessDailog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
        View mView = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        Button btn_cancel = (Button) mView.findViewById(R.id.buttonCancel);
        Button btn_okay = (Button) mView.findViewById(R.id.buttonOk);
        builder.setView(mView);
        final AlertDialog alertDialog = builder.create();

        alertDialog.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ViewEventListActivity.class);
                startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void setEndTime(View view) {
        showTimePicker(false);
    }

    public void setStartTime(View view) {
        showTimePicker(true);


    }

    public void addEvent(View view) {
        if (validateInputs()) {
            Event event = new Event(txtEvent.getText().toString(), txtLocation.getText().toString(), txtDescription.getText().toString(), eventDate, txtStartTime.getText().toString(), txtEndTime.getText().toString());
            if (db.insert(event)) {
                showSuccessDailog();
//                Date date = mcurrentTime.getTime() ;
                try {
                    final SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                    final Date dateObj = sdf.parse(startTimeText);

                    scheduleNotification(getNotification(txtEvent.getText().toString(), txtDescription.getText().toString()), dateObj.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void viewMyEvents(View view) {
        Intent intent = new Intent(getBaseContext(), ViewEventListActivity.class);
        startActivity(intent);
    }

    public void dismiss(View view) {
        finish();
    }
}
