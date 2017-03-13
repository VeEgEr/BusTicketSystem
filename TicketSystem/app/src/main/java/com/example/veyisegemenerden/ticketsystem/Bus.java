package com.example.veyisegemenerden.ticketsystem;

/**
 * Created by veyisegemenerden on 10.12.2016.
 */

public class Bus  {
    private double price;
    private String departure;
    private String arrive;
    private String date;
    private int hours,hoursTwo;
    private int min,minTwo;
    private String type;
    private int busID;
    private Seat seat;
    public Bus(int hours ,int hoursTwo, int min,int minTwo, String type, double price) {

        this.type = type;
        this.price = price;
        this.hours = hours;
        this.minTwo=minTwo;
        this.hoursTwo = hoursTwo;
        this.min = min;

    }



    public Bus(int busID, String departure, String arrive, int hours , int hoursTwo, int min, int minTwo, String type, double price, String date){
        this.busID=busID;
        this.type = type;
        this.price = price;
        this.hours = hours;
        this.minTwo=minTwo;
        this.hoursTwo = hoursTwo;
        this.min = min;
        this.departure=departure;
        this.arrive=arrive;
        this.date=date;
    }

    public Bus(int busID,String departure,String arrive,int hours ,int hoursTwo, int min,int minTwo, String type, double price,String date ,Seat seat){
        this.busID=busID;
        this.type = type;
        this.price = price;
        this.hours = hours;
        this.minTwo=minTwo;
        this.hoursTwo = hoursTwo;
        this.min = min;
        this.departure=departure;
        this.arrive=arrive;
        this.date=date;
        this.seat=seat;
    }
    public Bus(){

    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrive() {
        return arrive;
    }

    public void setArrive(String arrive) {
        this.arrive = arrive;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }


    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return getHours()+""+ getHoursTwo()+":"+getMin()+getMinTwo()+"                     "+getType()+"                          "+getPrice();
    }


    public int getMinTwo() {
        return minTwo;
    }

    public void setMinTwo(int minTwo) {
        this.minTwo = minTwo;
    }

    public int getHoursTwo() {
        return hoursTwo;
    }

    public void setHoursTwo(int hoursTwo) {
        this.hoursTwo = hoursTwo;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }
}
