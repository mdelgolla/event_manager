package com.example.l8440131.eventmanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

    public EventAdapter(Context context, List<Event>eventList,DBHelper dbHelper) {
        this.context = context;
        this.eventList = eventList;
        inflter = (LayoutInflater.from(context));
        this.dbHelper=dbHelper;
    }

    public void updateEventList(int item) {
        this.eventList.remove(item);
        this.notifyDataSetChanged();
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
                deleteEvent(eventList.get(i),i);

            }
        });
        return view;
    }

    public void deleteEvent(final Event event, final int item){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle("Delete Event");
        alertDialogBuilder.setMessage("Are you sure you want to delete the event");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                dbHelper.deleteEvent(event);
                                updateEventList(item);
                            }
                        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
