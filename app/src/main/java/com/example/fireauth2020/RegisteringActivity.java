package com.example.fireauth2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class RegisteringActivity extends AppCompatActivity {
    EditText emailId, Password;
    Button register;
    TextView tvSignIn;

    FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registering);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        tvSignIn = findViewById(R.id.signin);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddressString = emailId.getText().toString();
                String passwordString = Password.getText().toString();

                if (emailAddressString.isEmpty()){
                    emailId.setError("provide your email address");
                    emailId.requestFocus();
                }
                else if (passwordString.isEmpty()){
                    Password.setError("enter a password");
                    Password.requestFocus();
                }
                else if (emailAddressString.isEmpty() && passwordString.isEmpty()){
                    Toast.makeText(RegisteringActivity.this, "Fill in the required fields", Toast.LENGTH_LONG).show();
                }
                else if (!(emailAddressString.isEmpty() && passwordString.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(emailAddressString, passwordString)
                            .addOnCompleteListener(RegisteringActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(RegisteringActivity.this, "Task unsuccessful", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Intent i = new Intent(RegisteringActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(RegisteringActivity.this, "Error Occurred", Toast.LENGTH_LONG ).show();
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisteringActivity.this, LoginActivity.class));
            }
        });

    }
}
