package com.example.pushpindersingh.jirawalatours;

public class HistoryRecyclerItem {
    private String uname;
    private String client;
    private String location;
    private String hotel;
    private String room;
    private String date;

    public HistoryRecyclerItem(String location, String hotel, String room, String uname, String client, String date) {
        this.uname = uname;
        this.client = client;
        this.location = location;
        this.hotel = hotel;
        this.room = room;
        this.date = date;
    }

    public String getUname() {
        return uname;
    }

    public String getClient() {
        return client;
    }

    public String getLocation() {
        return location;
    }

    public String getHotel() {
        return hotel;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }
}
