package com.example.veyisegemenerden.ticketsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by veyisegemenerden on 8.12.2016.
 */

public class Login extends AppCompatActivity {
EditText email_Login_editText,password_Login_editText;
    Button Login_button;
    TextView goRegisterFromLogin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


        goRegisterFromLogin=(TextView) findViewById(R.id.goRegister_login_textView);
        progressDialog=new ProgressDialog(this);
        email_Login_editText=(EditText) findViewById(R.id.login_email_dit);
        password_Login_editText=(EditText) findViewById(R.id.login_password_dit);
        firebaseAuth= FirebaseAuth.getInstance();

        Login_button=(Button) findViewById(R.id.login_button);



        if (firebaseAuth.getCurrentUser() != null){

            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }


    }

    public void loginForUser(View v){
        String  email_Login_token=email_Login_editText.getText().toString().trim();
        String password_Login_token=password_Login_editText.getText().toString().trim();


        if(TextUtils.isEmpty(email_Login_token)){
            Toast.makeText(getApplication(),"Please write email.",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password_Login_token)){
            Toast.makeText(getApplication(),"Please write password.",Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Login...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email_Login_token,password_Login_token).addOnCompleteListener(this,new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(getApplication(),"Register is unseccessful.",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public void goRegisterFromLogin(View v){
        Intent intent = new Intent(this,SignUp.class);
        finish();
        startActivity(intent);
    }
}
