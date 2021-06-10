package com.example.l8440131.eventmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.l8440131.eventmanager.helpers.DBHelper;
import com.example.l8440131.eventmanager.models.Event;

import java.util.List;

public class EventAdapter extends BaseAdapter {
    Context context;
    List<Event>eventList;
    LayoutInflater inflter;
    DBHelper dbHelper;

    public EventAdapter(Context applicationContext, List<Event>eventList,DBHelper dbHelper) {
        this.context = context;
        this.eventList = eventList;
        inflter = (LayoutInflater.from(applicationContext));
        this.dbHelper=dbHelper;
    }
    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.event_list_item, null);
        TextView eventName = (TextView)view.findViewById(R.id.txtEventName);
        eventName.setText(eventList.get(i).getEventName());
        TextView eventDate = (TextView)view.findViewById(R.id.txtDateTime);
        eventDate.setText(eventList.get(i).getEndTime());
        ImageView imgDelete=(ImageView)view.findViewById(R.id.imgDelete);
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.deleteEvent(eventList.get(i));
                notifyDataSetChanged();
            }
        });
        return view;
    }
}
