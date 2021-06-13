package com.example.l8440131.eventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.l8440131.eventmanager.models.Event;

public class ViewEventActivity extends AppCompatActivity {
    TextView txtEventTitle,txtDescription,txtLocation,txtStartTime,txtEndTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Bundle bundle = this.getIntent().getExtras();
        Event event = null;
        if (bundle != null) {
            event = (Event) bundle.getSerializable("event");
        }
        txtEventTitle=(TextView)findViewById(R.id.txtEventTitle);
        txtEventTitle.setText(event.getEventName());
        txtDescription=(TextView)findViewById(R.id.txtDescription);
        txtDescription.setText(event.getDescription());
        txtLocation=(TextView)findViewById(R.id.txtLocation);
        txtLocation.setText(event.getLocation());
        txtStartTime=(TextView)findViewById(R.id.txtStartTime);
        txtStartTime.setText(event.getStartTime());
        txtEndTime=(TextView)findViewById(R.id.txtEndTime);
        txtEndTime.setText(event.getEndTime());
    }

    public void goToHome(View view) {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}
