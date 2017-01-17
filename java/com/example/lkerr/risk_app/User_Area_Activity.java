package com.example.lkerr.risk_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import static android.R.attr.button;

public class User_Area_Activity extends AppCompatActivity {

    private Button mLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__area_);
        Firebase.setAndroidContext(this);

        mLogout = (Button) findViewById(R.id.bLogout);

        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logoutIntent = new Intent(User_Area_Activity.this, LogIn.class);
                logoutIntent.setFlags(logoutIntent.FLAG_ACTIVITY_NEW_TASK | logoutIntent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                finish();
            }
        });

    }
}
