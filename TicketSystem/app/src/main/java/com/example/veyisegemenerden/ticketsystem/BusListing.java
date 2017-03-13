package com.example.veyisegemenerden.ticketsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by veyisegemenerden on 10.12.2016.
 */

public class BusListing extends AppCompatActivity {
   TextView fromWhere,toWhere,dateOfJourney;
    String busFromwhere,busToWhere,busDate;

    ListView listingBus;
    List<Bus> listForListView = new ArrayList<>();
   static  List<Bus> listForTicket =new ArrayList<>();
    static int count;
    ArrayAdapter<Bus> dataAdapter;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_time_selection_layout);
        progressDialog=new ProgressDialog(this);
        fromWhere = (TextView) findViewById(R.id.bus_time_selection_fromwhere_text);
        toWhere = (TextView) findViewById(R.id.bus_time_selection_towhere_text);
        dateOfJourney= (TextView) findViewById(R.id.bustimeselection_date_text);
        listingBus=(ListView) findViewById(R.id.bustimeselection_departuretime_listview);
        Intent myLocalIntent = getIntent();

        // take variable from firstactivity.
        Bundle myBundle = myLocalIntent.getExtras();
         busFromwhere = myBundle.getString("fromWhere");
         busToWhere = myBundle.getString("toWhere");
         busDate= myBundle.getString("date");




        //set textView

        fromWhere.setText(busFromwhere);
        toWhere.setText(busToWhere);
        dateOfJourney.setText(busDate);

        //Load Bus



          dataAdapter = new ArrayAdapter<Bus>(this, android.R.layout.simple_list_item_1,android.R.id.text1,listForListView);

        LoadData("BusList");
        listingBus.setAdapter(dataAdapter);

        listingBus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(BusListing.this)
                        .setTitle("Do you want to select "+ listForTicket.get(position).getHours()+listForTicket.get(position).getHoursTwo()+":"
                                +listForTicket.get(position).getMin()+listForTicket.get(position).getMinTwo()+" bus ?")
                        .setMessage("Departure : "+listForTicket.get(position).getDeparture()+"\n"+
                                    "Arrive : "+listForTicket.get(position).getArrive()+"\n"+"Type : "+listForTicket.get(position).getType()
                                    + "\n"+"Date and Time : "+listForTicket.get(position).getDate()+" "+listForTicket.get(position).getHours()+listForTicket.get(position).getHoursTwo()+":"
                                +listForTicket.get(position).getMin()+listForTicket.get(position).getMinTwo()+"\n"+"Price : "
                                +listForTicket.get(position).getPrice())

                        .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                progressDialog.setMessage("You are being redirected...");
                                progressDialog.show();

                                count=listForTicket.get(position).getBusID();

                                Intent intent = new Intent(BusListing.this,SeatSelection.class);
                                finish();
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })

                        .show();
            }
        });
    }

    public void LoadData(String inFile) {





        BufferedReader reader;

        try{
            final InputStream file = getAssets().open(inFile);
            reader = new BufferedReader(new InputStreamReader(file));
            String line = reader.readLine();

            while(line != null){

                line = reader.readLine();

                if(line != null){

                    if(line.split(" ")[0].trim().equals(busFromwhere) && line.split(" ")[1].trim().equals(busToWhere)) {
                       if(line.split(" ")[2].trim().equals(busDate)) {
                           String time = line.split(" ")[3].trim();
                            String hourString = time.split(":")[0].trim();
                           int hour = Integer.parseInt(hourString.split("-")[0]);
                           int hourTwo = Integer.parseInt(hourString.split("-")[1]);


                           int min = Integer.parseInt(time.split(":")[1].split("-")[0]);
                           int min2 = Integer.parseInt(time.split(":")[1].split("-")[1]);


                           String type = line.split(" ")[4].trim();

                           double price = Double.parseDouble(line.split(" ")[5]);

                           Bus pullBusForListView = new Bus(hour, hourTwo, min, min2, type, price);

                           Bus pullForTicket = new Bus(Integer.parseInt(line.split(" ")[6]),busFromwhere,busToWhere,hour, hourTwo, min, min2, type, price,busDate);
                            listForTicket.add(pullForTicket);
                           listForListView.add(pullBusForListView);
                       }
                    }

                }

            }


        } catch(IOException ioe){
            ioe.printStackTrace();
        }

    }


}
