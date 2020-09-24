package com.jacknjana.panunmaakan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class CommercialList extends AppCompatActivity {


    CardView prop_details_com, loc_details_com, price_details_com, Description_com;
    Toolbar toolbar;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_commercial_listing);

        prop_details_com = findViewById(R.id.prop_details_com);
        loc_details_com = findViewById(R.id.loc_details_com);
        price_details_com = findViewById(R.id.price_details_com);
        Description_com = findViewById(R.id.Description_com);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        prop_details_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialList.this, PropertyDetailsCom.class));
            }
        });
        loc_details_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialList.this, LocalityDetailsCom.class));
            }
        });
        price_details_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialList.this, PriceDetailsCom.class));
            }
        });
        Description_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialList.this, DescriptionCom.class));
            }
        });


    }
}
