package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class LoginWithMobile extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_mobile);
        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginWithMobile.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

    }

    public void LoginWithEmail(View view) {
        Intent intent=new Intent(this, LoginWithEmail.class);
        startActivity(intent);
    }

    public void submitForOTP(View view) {
        Intent intent=new Intent(this, EnterOTP.class);
        startActivity(intent);
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}