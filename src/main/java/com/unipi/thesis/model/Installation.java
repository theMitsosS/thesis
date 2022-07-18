package com.unipi.thesis.model;

import java.awt.*;

public class Installation {
    private String name;
    private String address;
    private Integer capacity;
    private Point coordinates;

    public Installation(String name, String address, Integer capacity, Point coordinates) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.coordinates = coordinates;
    }

    public Installation(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Point getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Point coordinates) {
        this.coordinates = coordinates;
    }
}
