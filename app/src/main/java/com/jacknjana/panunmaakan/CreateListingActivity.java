package com.jacknjana.panunmaakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateListingActivity extends AppCompatActivity {

//Added on 28072020
    SharedPreferences prf;
TextView tvLoggedUser;
//
    CardView res, com, land;
    Toolbar toolbar;
    @Override
    public boolean onSupportNavigateUp() {

        startActivity(new Intent(CreateListingActivity.this, MainActivity.class));
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);
        com = findViewById(R.id.com_list);
        res = findViewById(R.id.res_list);
        land = findViewById(R.id.land_list);

        /*tvLoggedUser= findViewById(R.id.tvLoggedUser);*/

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
//        tvLoggedUser.setText("Hello: "+prf.getString("RegNo",null));

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateListingActivity.this, CommercialList.class));
            }
        });

        res.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateListingActivity.this, ResidentialList.class));
            }
        });

        land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateListingActivity.this, LandList.class));
            }
        });

    }
}
