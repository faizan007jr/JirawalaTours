package com.example.pushpindersingh.jirawalatours;

public class HotelRecyclerItem {
    private int id;
    private String name;
    private int rent;
    private int av;

    public HotelRecyclerItem(int id, String name, int rent, int av) {
        this.id = id;
        this.name = name;
        this.rent = rent;
        this.av = av;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRent() {
        return rent;
    }

    public int getAv() {
        return av;
    }
}
