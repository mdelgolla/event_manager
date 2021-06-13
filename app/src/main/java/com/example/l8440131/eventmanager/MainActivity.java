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
                String selectedDate = date.getYear()+"-"+(date.getMonth())+"-"+date.getDay();
                Log.d("selectedDate","date "+selectedDate);
                intent.putExtra("selected_date", selectedDate);
                startActivity(intent);
            }
        });
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
                String[] date = dateList.get(i).split("-");
                Log.d("date","date: "+date[0]+"-"+date[1]+"-"+date[2]+" listDate: "+dateList.get(i));
                calendarView.markDate(new DateData(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, getResources().getColor(R.color.colorPink))));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}