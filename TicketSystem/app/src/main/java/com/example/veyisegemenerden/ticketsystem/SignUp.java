package com.example.veyisegemenerden.ticketsystem;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by veyisegemenerden on 8.12.2016.
 */

public class SignUp extends AppCompatActivity {
  EditText emailEditText,passwordEditText;
  Button loginButton;
   private String emailToken,passwordToken;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog= new ProgressDialog(this);

        emailEditText= (EditText) findViewById(R.id.signup_email_edit);
        passwordEditText=(EditText) findViewById(R.id.signup_password_edit);

        loginButton=(Button) findViewById(R.id.signup_button);






    }

    public void signForUser(View v){
        emailToken=emailEditText.getText().toString();
        passwordToken=passwordEditText.getText().toString();


        if(TextUtils.isEmpty(emailToken)){
            Toast.makeText(getApplication(),"Please write email.",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(passwordToken)){
            Toast.makeText(getApplication(),"Please write password.",Toast.LENGTH_LONG).show();
            return;
        }

        if(passwordToken.length()<=5){
            Toast.makeText(getApplication(),"Password's length must be grater than 5.",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Registering user.");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(emailToken,passwordToken).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>()
                {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){

                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,"Register is successful.",Toast.LENGTH_LONG).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(SignUp.this,"Register is unseccessful.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void goLoginFromSignUp(View v){
        Intent intent = new Intent(SignUp.this,Login.class);
        startActivity(intent);
    }


}
