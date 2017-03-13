package com.example.veyisegemenerden.ticketsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


/**
 * Created by veyisegemenerden on 10.12.2016.
 */

public class BookingTicket extends AppCompatActivity {
    EditText customerName,customerSurname;
    TextView display;
    Button btnDisplayTicket;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rezerve_customer_information);


        customerName=(EditText) findViewById(R.id.rezerve_customerınformation_name_edit);
        customerSurname=(EditText) findViewById(R.id.rezerve_customerınformation_lastname_edit);
        display=(TextView) findViewById(R.id.rezerve_customerınformation_view);


        btnDisplayTicket = (Button)  findViewById(R.id.rezerve_customerınformation_bookingbutton);
        btnDisplayTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(customerName.getText())){
                    Toast.makeText(getApplication(),"Please write name.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(customerSurname.getText())){
                    Toast.makeText(getApplication(),"Please write surname.",Toast.LENGTH_LONG).show();
                    return;
                }


                setSeatOfTicket();
                display.setText(Ticket.ticketsList.get(0).toString());
            }
        });


    }
    public void setSeatOfTicket(){
        Random rnd=new Random();
        int pnr= rnd.nextInt(100)+200;
        String name= customerName.getText().toString();
        String surname=customerSurname.getText().toString();
        int busID=BusListing.count;
        String departure=BusListing.listForTicket.get(BusListing.count).getDeparture();
        String arrive=BusListing.listForTicket.get(BusListing.count).getArrive();
        int hours=BusListing.listForTicket.get(BusListing.count).getHours();
        int hoursTwo=BusListing.listForTicket.get(BusListing.count).getHoursTwo();
        int min=BusListing.listForTicket.get(BusListing.count).getMin();
        int minTwo=BusListing.listForTicket.get(BusListing.count).getMinTwo();
        String type=BusListing.listForTicket.get(BusListing.count).getType();
        double price=BusListing.listForTicket.get(BusListing.count).getPrice();
        String date=BusListing.listForTicket.get(BusListing.count).getDate();
        boolean pay=false;
        for(int i =0; i<Seat.seatListFromDataBase.size(); i++) {
            if(BusListing.count==Seat.seatListFromDataBase.get(i).getBusID() && SeatSelection.seatNumber==Seat.seatListFromDataBase.get(i).getChosenSeat()) {
                Seat chosenSeat = new Seat(Seat.seatListFromDataBase.get(i).getBusID(),Seat.seatListFromDataBase.get(i).getChosenSeat(),SeatSelection.gender,"free");
                Bus chosenBus= new Bus(busID, departure, arrive, hours, hoursTwo, min, minTwo, type, price, date);
                Ticket withSeat = new Ticket(pnr,name, surname, chosenBus, chosenSeat,pay);

                Ticket.ticketsList.add(withSeat);
                ticketCreator(withSeat);
            }
        }


            new AlertDialog.Builder(BookingTicket.this)
                    .setTitle("Ticket PNR CODE")

                    .setMessage("PAY ATTANTION!!! Your PNR number is :"+pnr+"Please note that!" )
                    .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {



                        }
                    })


                    .show();

        }

    public void ticketCreator(Ticket t){
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference();




        databaseReference.child("ticket").child(t.getPnr()+"").setValue(t);

    }

    public void goBackMainFromBooking(View v){

        Intent intent=new Intent(BookingTicket.this,MainActivity.class);
        finish();
        startActivity(intent);
    }

    }


