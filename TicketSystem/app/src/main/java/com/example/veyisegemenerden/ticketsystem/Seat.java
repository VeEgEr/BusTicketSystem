package com.example.veyisegemenerden.ticketsystem;

import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by veyisegemenerden on 15.12.2016.
 */

public class Seat  {
    private int busID;
    private String gender;
    private int chosenSeat;
    private String state;


    static ArrayList<Seat> seatListFromDataBase= new ArrayList<>();
    private DatabaseReference databaseReference;
    public Seat(int busID,int chosenSeat,String gender,String state){
        this.busID=busID;
        this.chosenSeat=chosenSeat;
        this.gender=gender;
        this.state=state;

    }
    public Seat(){

    }

    public int getBusID() {
        return busID;
    }

    public void setBusID(int busID) {
        this.busID = busID;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getChosenSeat() {
        return chosenSeat;
    }

    public void setChosenSeat(int chosenSeat) {
        this.chosenSeat = chosenSeat;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Seat{" +
                "busID=" + busID +
                ", gender=" + gender + '\'' +
                ", chosenSeat=" + chosenSeat +
                ", state=" + state + '\'' +
                '}';
    }




    public void seatUpdate(int chosenSeat,String gender,String state){
        databaseReference= FirebaseDatabase.getInstance().getReference();

                Seat s = new Seat(BusListing.count, chosenSeat, gender, state);


                databaseReference.child("bus").child("BUSID=" +1).child("SEAT" + chosenSeat).setValue(s);



        }




    public void seatCreator(){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        for (int j = 1;j < 2; j++) {
            for (int i = 1; i < 26; i++) {
                Seat s = new Seat(1, i, "free", "free");


                databaseReference.child("bus").child("BUSID=" +j).child("SEAT" + i).setValue(s);

            }

        }
    }



}
