package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class AboutUs extends AppCompatActivity {
    TextView tvAboutUs1, tvAboutUs2, tvAboutUs3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUs.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

        tvAboutUs1= findViewById(R.id.tvAboutUs1);
        tvAboutUs2= findViewById(R.id.tvAboutUs2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tvAboutUs1.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
            tvAboutUs2.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}