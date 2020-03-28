package com.example.fireauth2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText Emailaddress, Password;
    Button Login;
    TextView Register_link;

    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final Context context = LoginActivity.this;

        mFirebaseAuth = FirebaseAuth.getInstance();
        Emailaddress = findViewById(R.id.email_login);
        Password = findViewById(R.id.password_login);
        Login = findViewById(R.id.login_button);
        Register_link = findViewById(R.id.register_link);

        mAuthStateListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(context, "logged in successful", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

                }

                else {
                    Toast.makeText(context, "Please LogIn", Toast.LENGTH_SHORT).show();
                }
            }
        };

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailAddressString = Emailaddress.getText().toString();
                String passwordString = Password.getText().toString();

                if (emailAddressString.isEmpty()){
                    Emailaddress.setError("provide your email address");
                    Emailaddress.requestFocus();
                }
                else if (passwordString.isEmpty()){
                    Password.setError("enter a password");
                    Password.requestFocus();
                }
                else if (emailAddressString.isEmpty() && passwordString.isEmpty()){
                    Toast.makeText(context, "Fill in the required fields", Toast.LENGTH_LONG).show();
                }
                else if (!(emailAddressString.isEmpty() && passwordString.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(emailAddressString, passwordString)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(context,"Login Error, Please Login Again", Toast.LENGTH_LONG ).show();
                            }
                            else{
                                Intent i = new Intent(context, MainActivity.class);
                                startActivity(i);
                            }

                        }
                    });

                }
                else{
                    Toast.makeText(context,"Error Occurred", Toast.LENGTH_LONG ).show();
                }
            }
        });

        Register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RegisteringActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
