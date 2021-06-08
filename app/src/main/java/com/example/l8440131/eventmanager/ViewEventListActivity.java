package com.example.l8440131.eventmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.l8440131.eventmanager.helpers.DBHelper;
import com.example.l8440131.eventmanager.models.Event;

import java.util.ArrayList;
import java.util.List;

public class ViewEventListActivity extends AppCompatActivity {
    final DBHelper db = new DBHelper(this);
    List<Event> eventList= new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_list);

        eventList=db.getAllEvents();

        EventAdapter adapter = new EventAdapter(this, eventList);

        ListView listView = (ListView) findViewById(R.id.event_list);
        listView.setAdapter(adapter);
    }

    public void goToHome(View view) {

    }
}
