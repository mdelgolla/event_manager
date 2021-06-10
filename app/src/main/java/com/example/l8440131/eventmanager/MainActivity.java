package com.example.l8440131.eventmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.l8440131.eventmanager.helpers.DBHelper;
import com.example.l8440131.eventmanager.models.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import sun.bob.mcalendarview.MCalendarView;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnDateClickListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;

public class MainActivity extends AppCompatActivity {
    final DBHelper db = new DBHelper(this);
    MCalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (MCalendarView) findViewById(R.id.calander); // get the reference of CalendarView
        calendarView.setOnDateClickListener(new OnDateClickListener() {
            @Override
            public void onDateClick(View view, DateData date) {
                Intent intent = new Intent(getBaseContext(), CreateEventActivity.class);
                String selectedDate = date.getYear()+"-"+(date.getMonth() + 1)+"-"+date.getDay();
                Log.d("selectedDate","date "+selectedDate);
                intent.putExtra("selected_date", selectedDate);
                startActivity(intent);
            }
        });
//        long selectedDate = calendarView.getDate();
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                // Note that months are indexed from 0. So, 0 means January, 1 means february, 2 means march etc.
//                String selectedDate = year+"-"+(month + 1)+"-"+dayOfMonth;
//                Intent intent = new Intent(getBaseContext(), CreateEventActivity.class);
//                intent.putExtra("selected_date", selectedDate);
//                startActivity(intent);
//            }
//        });
        retrieveDates();

    }

    public void retrieveDates(){
        List<String> dateList= new ArrayList<>();
        dateList=db.getDateList();
        Log.d("eventList",""+dateList.size());
        ArrayList<DateData> dates=new ArrayList<>();

        for (int i=0;i<dateList.size();i++){
            SimpleDateFormat f = new SimpleDateFormat("yyyy-mm-dd");
            try {
                Date d = f.parse(dateList.get(i));
                Calendar c = Calendar.getInstance();
                //Set time in milliseconds
                c.setTimeInMillis(d.getTime());
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                calendarView.markDate(new DateData(mYear, mMonth, mDay).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, getResources().getColor(R.color.colorPrimary))));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }
}