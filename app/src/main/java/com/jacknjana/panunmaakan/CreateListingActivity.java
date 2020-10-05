package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateListingActivity extends AppCompatActivity {

//Added on 28072020
    SharedPreferences prf;
TextView tvLoggedUser;
//
    TextView res, com, land;
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

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateListingActivity.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

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
