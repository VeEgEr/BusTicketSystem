package com.example.veyisegemenerden.ticketsystem;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    TabHost tabhostMain;
    private FirebaseAuth firebaseAuth;
    Button signOut,setProfil;
    EditText date_edt,profil_name,profil_surname,profil_mail;
    RadioGroup genderRadio;
    RadioButton maleRadio,famaleRadio;
    String profil_name_token,profil_surname_token,profil_email_token,profil_gender_token;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    EditText pnrToken,surnameToken;
    TextView myTicket,tv;
    private ProgressDialog progressDialog;
    Button search,suboffice,contact;
    static ArrayList<Ticket> ticketFromDatabase=new ArrayList<>();

    Spinner destinationStart, destinationFinish;
    ImageButton imageButton;
    String date_token, destinationStart_token, destinationFinish_token;
    ArrayAdapter<String> dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    progressDialog= new ProgressDialog(this);
        myTicket=(TextView) findViewById(R.id.myticket_textView);
        destinationStart = (Spinner) findViewById(R.id.search_fromwhere_spinner);
        destinationFinish = (Spinner) findViewById(R.id.search_to_where_spinner);
        date_edt = (EditText) findViewById(R.id.SEARCHACTIVITY_DATEINFORMATİON_EDITTEXT);
        imageButton=(ImageButton) findViewById(R.id.SEARCHACTIVITY_IMAGEBUTTON_DATE);
        signOut = (Button) findViewById(R.id.profile_signout_button);
        profil_name=(EditText) findViewById(R.id.profil_name_edit);
        profil_surname=(EditText) findViewById(R.id.profil_lastname_edit);
        profil_mail=(EditText) findViewById(R.id.profil_email_edit);
        setProfil=(Button) findViewById(R.id.profil_set_button);
        suboffice=(Button) findViewById(R.id.suboffice_button);
        contact=(Button) findViewById(R.id.phone_button);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Contact.class);
                finish();
                startActivity(intent);
            }
        });
        suboffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"We dont have suboffice",Toast.LENGTH_LONG).show();
            }
        });
        genderRadio=(RadioGroup) findViewById(R.id.profil_gender_radiogrup);
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.profil_male_button) {
                    profil_gender_token = "male";
                }if(checkedId==R.id.profil_female_button) {
                    profil_gender_token="famale";
                }

            }
        });

        firebaseAuth = FirebaseAuth.getInstance();





        if (firebaseAuth.getCurrentUser() == null) {
            startActivity(new Intent(this, Login.class));
        }





        tabhostMain = (TabHost) findViewById(android.R.id.tabhost);
        tabhostMain.setup();


        TabHost.TabSpec tabSpec;

        //tabhost installing

        tabSpec = tabhostMain.newTabSpec("A");

        tabSpec.setContent(R.id.searchtab);
        tabSpec.setIndicator("Search", null);

        tabhostMain.addTab(tabSpec);

        tabSpec = tabhostMain.newTabSpec("R");
        tabSpec.setContent(R.id.mytickettab);
        tabSpec.setIndicator("Myticket", null);
        tabhostMain.addTab(tabSpec);



        tabSpec = tabhostMain.newTabSpec("L");
        tabSpec.setContent(R.id.profil_tab);
        tabSpec.setIndicator("Profil", null);
        tabhostMain.addTab(tabSpec);

        tabSpec = tabhostMain.newTabSpec("L");
        tabSpec.setContent(R.id.extratab);
        tabSpec.setIndicator("Extra", null);
        tabhostMain.addTab(tabSpec);


            setField();


        //set to text color when i clicked tabWidget
        for (int i = 0; i < tabhostMain.getTabWidget().getChildCount(); i++) {

            tv = (TextView) tabhostMain.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs

            tv.setTextColor(Color.parseColor("#ffffff"));

        }
        tabhostMain.getTabWidget().getChildAt(0).isHovered();

        tv = (TextView) tabhostMain.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        tv.setTextColor(Color.parseColor("#9999ff"));
        tabhostMain.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < tabhostMain.getTabWidget().getChildCount(); i++) {

                    tv = (TextView) tabhostMain.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
                    tv.setTextColor(Color.parseColor("#ffffff"));

                }


                tv = (TextView) tabhostMain.getCurrentTabView().findViewById(android.R.id.title); //for Selected Tab
                tv.setTextColor(Color.parseColor("#9999ff"));


            }
        });



        dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Loading data
        LoadData("City.txt");


        destinationStart.setAdapter(dataAdapter);

        destinationStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            destinationStart_token=parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        destinationFinish.setAdapter(dataAdapter);

        destinationFinish.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                destinationFinish_token=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        pnrToken=(EditText) findViewById(R.id.myticket_pnrnumber);
        surnameToken=(EditText) findViewById(R.id.myticket_name_surname);
        search=(Button) findViewById(R.id.mytickettab_search_button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(pnrToken.getText())){
                    Toast.makeText(getApplication(),"Please write pnrCODE.",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(surnameToken.getText())){
                    Toast.makeText(getApplication(),"Please write surname.",Toast.LENGTH_LONG).show();
                    return;
                }



                    ticketControllerDatabase();

            

            }
        });





    }


    public void profileSignoutForUser(View v) {

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, Login.class));
    }


    public void searchBus(View v) {

        Intent intent= new Intent(this,BusListing.class);
        Bundle myDataBundle = new Bundle();

        date_token=date_edt.getText().toString();
        // data item
        myDataBundle.putString("fromWhere", destinationStart_token);
        myDataBundle.putString("toWhere", destinationFinish_token);
        myDataBundle.putString("date", date_token);

        // insert data
        intent.putExtras(myDataBundle);
        startActivity(intent);


    }

    public void setField() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("users").child(user.getUid()).addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Person p = dataSnapshot.getValue(Person.class);
                try{


                profil_name.setText(p.getName());
                profil_surname.setText(p.getSurname());
                profil_mail.setText(p.getEmail());
                }catch (NullPointerException io){
                    tabhostMain.setCurrentTab(2);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void setProfil(View v) {
        profil_name_token=profil_name.getText().toString();
        profil_surname_token=profil_surname.getText().toString();
        profil_email_token=profil_mail.getText().toString();


        profilSetMethod(profil_name_token,profil_surname_token,profil_email_token,profil_gender_token);
        tabhostMain.setCurrentTab(0);
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

                        dataAdapter.add(line);
                    }
                }


            } catch(IOException ioe){
                ioe.printStackTrace();
            }

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onImageButtonClicked(View v) {

        Calendar mcurrentTime = Calendar.getInstance();
        int year = mcurrentTime.get(Calendar.YEAR);//Güncel Yılı alıyoruz
        int month = mcurrentTime.get(Calendar.MONTH);//Güncel Ayı alıyoruz
        int day = mcurrentTime.get(Calendar.DAY_OF_MONTH);//Güncel Günü alıyoruz

        DatePickerDialog datePicker;//Datepicker objemiz
        datePicker = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                date_edt.setText(String.format("%d/%d/%d", dayOfMonth, monthOfYear + 1, year));//Ayarla butonu tıklandığında textview'a yazdırıyoruz

            }
        }, year, month, day);//başlarken set edilcek değerlerimizi atıyoruz

        datePicker.setTitle("Tarih Seçiniz");
        datePicker.setButton(DatePickerDialog.BUTTON_POSITIVE, "Ayarla", datePicker);
        datePicker.setButton(DatePickerDialog.BUTTON_NEGATIVE, "İptal", datePicker);

        datePicker.show();
    }

    public void ticketControllerDatabase() {
        progressDialog.setMessage("Searching...");
        progressDialog.show();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child("ticket").child(Integer.parseInt(pnrToken.getText().toString())+"").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Ticket tic = dataSnapshot.getValue(Ticket.class);
                try{
                if(tic.getSurname().equalsIgnoreCase(surnameToken.getText().toString().trim())) {

                        myTicket.setText(tic.toString());


                }
                }catch (NullPointerException o){
                    myTicket.setText("Ticket does not exist.");
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void profilSetMethod(String name,String surname,String email,String gender){
        databaseReference= FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
                Person p= new Person(name,surname,email,gender);


                databaseReference.child("users").child(user.getUid()).setValue(p);

            }

        }
