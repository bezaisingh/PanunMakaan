package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class ResidentialList extends AppCompatActivity {


    CardView prop_details, loc_details, pric_details, prop_image, near_fac, descrip;

    Toolbar toolbar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residential_listing);
        prop_details = findViewById(R.id.prop_details);
        loc_details = findViewById(R.id.loc_details);
        pric_details = findViewById(R.id.pric_details);
        prop_image = findViewById(R.id.prop_image);
        near_fac = findViewById(R.id.near_fac);
        descrip = findViewById(R.id.descrip);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//


        prop_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, PropertyDetailsRes.class));
            }
        });
        loc_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, LocalityDetailsRes.class));
            }
        });
        pric_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, PriceDetailsRes.class));
            }
        });

        prop_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, PropertyImage.class));
            }
        });

        descrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, DescriptionRes.class));
            }
        });
        near_fac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResidentialList.this, NearbyFacilityRes.class));
            }
        });


    }
}
