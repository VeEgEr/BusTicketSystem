package com.example.veyisegemenerden.ticketsystem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by veyisegemenerden on 15.12.2016.
 */

public class Ticket  {

    private String name;
    private String surname;
    private Bus bus;
    private Seat seat;
    private int pnr;
    private boolean pay;

    static ArrayList<Ticket> ticketsList=new ArrayList<>();
    public Ticket(){

    }
    public Ticket(int pnr,String name,String surname,Bus bus ,Seat seat,Boolean pay) {
        this.pnr=pnr;
        this.name=name;
        this.surname=surname;
        this.bus=bus;
        this.seat=seat;
        this.pay=pay;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    public Seat getSeat() {
        return seat;
    }


    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getPnr() {
        return pnr;
    }

    public void setPnr(int pnr) {
        this.pnr = pnr;
    }


    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }


    public Bus getBus() {
        return bus;
    }

    public void setBus(Bus bus) {
        this.bus = bus;
    }


    @Override
    public String toString() {
        return "Ticket: " +"\n"
                +"Name: " +getName()+"\n"
                +"Surname: "+getSurname() +"\n"
                +"Bus: "+getBus() +"\n"
                +"Seat: " +getSeat()+ "\n"
                +"Pay: "+isPay();

    }
}
