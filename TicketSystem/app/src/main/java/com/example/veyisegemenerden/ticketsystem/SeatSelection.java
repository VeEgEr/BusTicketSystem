package com.example.veyisegemenerden.ticketsystem;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SeatSelection extends AppCompatActivity {
    //Data fields.


   static String gender,state;
    static int seatNumber;
    private int[] imageButtonId =
            {R.id.SELECTACTIVITY_1_IMAGEBUTTON,R.id.SELECTACTIVITY_2_IMAGEBUTTON,R.id.SELECTACTIVITY_3_IMAGEBUTTON, R.id.SELECTACTIVITY_4_IMAGEBUTTON
            ,R.id.SELECTACTIVITY_5_IMAGEBUTTON,R.id.SELECTACTIVITY_6_IMAGEBUTTON,R.id.SELECTACTIVITY_7_IMAGEBUTTON,R.id.SELECTACTIVITY_8_IMAGEBUTTON
            ,R.id.SELECTACTIVITY_9_IMAGEBUTTON,R.id.SELECTACTIVITY_10_IMAGEBUTTON,R.id.SELECTACTIVITY_11_IMAGEBUTTON,R.id.SELECTACTIVITY_12_IMAGEBUTTON
            ,R.id.SELECTACTIVITY_13_IMAGEBUTTON,R.id.SELECTACTIVITY_14_IMAGEBUTTON,R.id.SELECTACTIVITY_15_IMAGEBUTTON,R.id.SELECTACTIVITY_16_IMAGEBUTTON
            ,R.id.SELECTACTIVITY_17_IMAGEBUTTON,R.id.SELECTACTIVITY_18_IMAGEBUTTON,R.id.SELECTACTIVITY_19_IMAGEBUTTON,R.id.SELECTACTIVITY_20_IMAGEBUTTON
            ,R.id.SELECTACTIVITY_21_IMAGEBUTTON,R.id.SELECTACTIVITY_22_IMAGEBUTTON,R.id.SELECTACTIVITY_23_IMAGEBUTTON,R.id.SELECTACTIVITY_24_IMAGEBUTTON,R.id.SELECTACTIVITY_25_IMAGEBUTTON};
    private List<ImageButton> buttonList = new ArrayList<>();



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectseat);
        ProgressDialog progressDialog= new ProgressDialog(this);

        init(buttonList,imageButtonId);

        progressDialog.setMessage("installing...");
        progressDialog.show();
        seatControllerDatabase();
        progressDialog.dismiss();

    }






    public void onClicked(View v) {

            for(int i = 0; i<imageButtonId.length; i++){
                if(v.getId() == imageButtonId[i]){
                    seatNumber=i+1;
                    dialogBox();
                    break;
                }
            }


    }
    public void dialogBox() {
        final  CharSequence[] items={ "man","woman"};
        new AlertDialog.Builder(SeatSelection.this)
                .setTitle("Please chose gender.")

                .setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gender=items[which].toString();




                    }
                })
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {


                        state="full";

                        Seat s=new Seat();
                        s.seatUpdate(seatNumber,gender,state);
                        new AlertDialog.Builder(SeatSelection.this)
                                .setTitle("Ticket")

                                .setMessage("What do you want? ")
                                .setPositiveButton(R.string.buy, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {


                                        Intent intent=new Intent(SeatSelection.this,BuyTicket.class);
                                        finish();
                                        startActivity(intent);


                                    }
                                })
                                .setNegativeButton(R.string.rezerve, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent(SeatSelection.this,BookingTicket.class);
                                        finish();
                                        startActivity(intent);
                                    }
                                })

                                .show();


                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                    }
                })

                .show();

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void seatConditionController(int ID){

        for(int i =0; i<Seat.seatListFromDataBase.size(); i++) {
            for(int k = 0; k<=buttonList.size(); k++ ) {
                if (Seat.seatListFromDataBase.get(i).getChosenSeat() == k && Seat.seatListFromDataBase.get(i).getBusID() == ID) {

                    if (Seat.seatListFromDataBase.get(i).getGender().equals("man")) {

                        buttonList.get(i).setImageResource(R.drawable.busseat2man);
                        buttonList.get(i).setEnabled(false);
                    }

                    if (Seat.seatListFromDataBase.get(i).getGender().equals("woman")) {
                        buttonList.get(i).setImageResource(R.drawable.busseat2woman);
                        buttonList.get(i).setEnabled(false);
                    }


                }
            }

        }

    }

    public void seatControllerDatabase(){




        FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference();
        databaseReference.child("bus").child("BUSID="+BusListing.count).addValueEventListener(new ValueEventListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children=dataSnapshot.getChildren();

                for(DataSnapshot child:children){

                    Seat sey = child.getValue(Seat.class);

                    Seat.seatListFromDataBase.add(sey);



                }


                 seatConditionController(BusListing.count);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public  void init(List<ImageButton> list,int[] idList){
        for(int i = 0; i< 25; i++ ){
           ImageButton clone = (ImageButton) findViewById(idList[i]);
            list.add(i,clone);
        }
    }
}

