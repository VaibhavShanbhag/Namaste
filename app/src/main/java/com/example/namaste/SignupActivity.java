package com.example.namaste;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    EditText email, password, name;
    Button login, createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);

        login = findViewById(R.id.login);
        createAccount = findViewById(R.id.create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameText,emailText,pass;
                nameText = name.getText().toString();
                emailText =  email.getText().toString();
                pass = password.getText().toString();

                Users users = new Users();
                users.setEmail(emailText);
                users.setName(nameText);
                users.setPassword(pass);

                auth.createUserWithEmailAndPassword(emailText,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            db.collection("Users")
                                    .document().set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
    }
}