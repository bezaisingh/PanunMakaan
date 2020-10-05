package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class LandList extends AppCompatActivity {


    CardView land_details, loc_details_land, nearby_facility_land, desc_land_listing;

Toolbar toolbar;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_land_listing);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandList.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

        land_details = findViewById(R.id.land_details);
        loc_details_land = findViewById(R.id.loc_details_land);
        nearby_facility_land = findViewById(R.id.nearby_facility_land);
        desc_land_listing = findViewById(R.id.desc_land_listing);

        land_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandList.this, LandDetails.class));
            }
        });
        loc_details_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandList.this, LocalityDetailsLand.class));
            }
        });
        nearby_facility_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandList.this, NearbyFacilityLand.class));
            }
        });
        desc_land_listing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandList.this, DescriptionLand.class));
            }
        });

    }
}
