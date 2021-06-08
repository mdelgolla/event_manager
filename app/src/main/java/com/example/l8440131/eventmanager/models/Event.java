package com.example.l8440131.eventmanager.models;

public class Event {
   private int id;
   private String eventName;
   private String location;
   private String description;
   private String eventDate;
   private String startTime;
   private String endTime;

   public Event(){}

   public Event(String eventName, String location, String description,String eventDate, String startTime, String endTime){
       this.eventName=eventName;
       this.location=location;
       this.description=description;
       this.eventDate=eventDate;
       this.startTime=startTime;
       this.endTime=endTime;
   }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
}
