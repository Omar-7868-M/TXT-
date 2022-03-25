package com.example.ttt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class OTPAuthentication extends AppCompatActivity
{
    TextView mchangenumber;
    EditText mgetotp;
    android.widget.Button mverifyotp;
    String enteredotp;

    FirebaseAuth firebaseAuth;
    ProgressBar mprogressbarofotpauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpauthentication);

        mchangenumber=findViewById(R.id.changenumber);
        mverifyotp=findViewById(R.id.verifyotp);
        mgetotp=findViewById(R.id.getotp);
        mprogressbarofotpauth=findViewById(R.id.progressbarofotpauth);


        firebaseAuth=FirebaseAuth.getInstance();

        mchangenumber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent=new Intent(OTPAuthentication.this,MainActivity.class);

                startActivity(intent);

                //allows User to change number by redirecting them to main activity

            }
        });

        mverifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                enteredotp=mgetotp.getText().toString();
                if (enteredotp.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter Your One Time Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mprogressbarofotpauth.setVisibility(View.VISIBLE);
                    String coderecieved=getIntent().getStringExtra("otp");

                    PhoneAuthCredential credential= PhoneAuthProvider.getCredential(coderecieved,enteredotp);
                    signInWithPhoneCredential(credential);
                }

            }
        });



    }

    private void signInWithPhoneCredential(PhoneAuthCredential credential)
    {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(OTPAuthentication.this, setProfile.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    if (task.getException()instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        mprogressbarofotpauth.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "Login Failed :(", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }








}