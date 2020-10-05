package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

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

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CommercialList.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

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
