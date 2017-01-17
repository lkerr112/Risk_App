package com.example.lkerr.risk_app;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText mNameField;
    private EditText mEmailField;
    private EditText mPasswordField;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
        Firebase.setAndroidContext(this);

        mAuth = FirebaseAuth.getInstance();
        mNameField = (EditText) findViewById(R.id.etName);
        mEmailField = (EditText) findViewById(R.id.etEmail);
        mPasswordField = (EditText) findViewById(R.id.etPassword);
        mRegisterButton = (Button) findViewById(R.id.bRegister);
        mProgress = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startRegister();
            }
        });
    }

    private void startRegister() {

        final String name = mNameField.getText().toString().trim().toLowerCase();
        final String email = mEmailField.getText().toString().trim().toLowerCase();
        final String password = mPasswordField.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            mProgress.setMessage("Signing you up now!");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        String user_id = mAuth.getCurrentUser().getUid();
                        DatabaseReference current_user = mDatabase.child(user_id);

                        current_user.child("name").setValue(name);
                        current_user.child("email").setValue(email);

                    }
                }
            });


        }
    }
}