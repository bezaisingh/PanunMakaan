package com.jacknjana.panunmaakan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity
                          implements NavigationView.OnNavigationItemSelectedListener,
                                     AdapterView.OnItemSelectedListener,
                                     FeedbackDialog.FeedbackDialogListener {

    Spinner spinnerPropStatus, spinnerFloor, spinDistrict;
    CardView my_profile;
    SharedPreferences prf;
    String Floor, PropStatus, District;
    Button btnListPropForFree, btnResident, btnCommercial, btnLand, customButton;

    private TextView textViewUsername;

    String TAG = "++++++++++++BIJAY SELF CHECK+++++++++++++++";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//



        textViewUsername = findViewById(R.id.textview_username);
        ////////
       // customButton = findViewById(R.id.custom_button);
        btnResident= findViewById(R.id.btnResident);
        btnCommercial = findViewById(R.id.btnCommercial);
        btnLand= findViewById(R.id.btnLand);





   /*     customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customButton.setSelected(!customButton.isSelected());

                if (customButton.isSelected()) {
                    //Handle selected state change
                    customButton.setEnabled(false);
                    btnResident.setEnabled(true);
                    btnCommercial.setEnabled(true);
                    btnLand.setEnabled(true);
                    btnResident.setSelected(!btnResident.isSelected());
                    btnCommercial.setSelected(!btnCommercial.isSelected());
                    btnLand.setSelected(!btnLand.isSelected());

                } else {
                    //Handle de-select state change
                }
            }
        });*/

        btnResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();

                btnResident.setSelected(!btnResident.isSelected());

                if (btnResident.isSelected()) {
                    //Handle selected state change

                    btnResident.setEnabled(false);
               //     customButton.setEnabled(true);
                    btnCommercial.setEnabled(true);
                    btnLand.setEnabled(true);
              //      customButton.setSelected(!customButton.isSelected());
                    btnCommercial.setSelected(false);
                    btnLand.setSelected(false);

                } else {
                    //Handle de-select state change
                }
            }
        });

        btnCommercial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Click", Toast.LENGTH_SHORT).show();

                btnCommercial.setSelected(!btnCommercial.isSelected());

                if (btnCommercial.isSelected()) {
                    //Handle selected state change
                    btnCommercial.setEnabled(false);
                    //customButton.setEnabled(true);
                    btnResident.setEnabled(true);
                    btnLand.setEnabled(true);
                  //  customButton.setSelected(!customButton.isSelected());
                    btnResident.setSelected(false);
                    btnLand.setSelected(false);

                } else {
                    //Handle de-select state change
                }
            }
        });

        btnLand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnLand.setSelected(!btnLand.isSelected());

                if (btnLand.isSelected()) {
                    //Handle selected state change

                    btnLand.setEnabled(false);
                //  customButton.setEnabled(true);
                    btnResident.setEnabled(true);
                    btnCommercial.setEnabled(true);
                //  customButton.setSelected(!customButton.isSelected());
                    btnResident.setSelected(false);
                    btnCommercial.setSelected(false);

                } else {
                    //Handle de-select state change
                }
            }
        });


        ////////
        btnListPropForFree = findViewById(R.id.btnListPropForFree);

/*        spinnerPropStatus = findViewById(R.id.spinnerPropStatus);
        spinnerFloor = findViewById(R.id.spinnerFloor);
        spinDistrict = findViewById(R.id.spinDistrict);



                /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_propStatus = ArrayAdapter.createFromResource(this, R.array.propStatusArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_floor = ArrayAdapter.createFromResource(this, R.array.floorNumberArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_district = ArrayAdapter.createFromResource(this, R.array.districtArray, android.R.layout.simple_spinner_item);


// Specify the layout to use when the list of choices appears
        adapter_propStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinnerPropStatus.setAdapter(adapter_propStatus);
        spinnerPropStatus.setOnItemSelectedListener(this);

        spinnerFloor.setAdapter(adapter_floor);
        spinnerFloor.setOnItemSelectedListener(this);

        spinDistrict.setAdapter(adapter_district);
        spinDistrict.setOnItemSelectedListener(this);

        ////////////////////////////////////////

*/
        prf = getSharedPreferences("user_details", MODE_PRIVATE);

        btnListPropForFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prf = getSharedPreferences("user_details", MODE_PRIVATE);
                String loginStatus = prf.getString("LoginId", "null");
                if (loginStatus.equals("null")) {
                    Log.v(TAG, "Shared Preference Data..........................: " + loginStatus);

                    Toast.makeText((MainActivity.this), "Redirecting to Login Page", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));

                } else {
                    Toast.makeText((MainActivity.this), "Redirecting to Create Listing Page", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity
                            .this, CreateListingActivity.class));
                }

            }
        });



 /*       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, RegistrationPage.class);
                startActivity(i);

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//Side NavigationView  Inflating
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//Setting Navigation Header name
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tvLoggedUserNameNavHeader);
        navUsername.setText("Hello " + prf.getString("RegNo", "User"));// Changed defValue from null to "User" on 25092020


        // my_profile = findViewById(R.id.my_profile);
        //        my_profile.setOnClickListener(new View.OnClickListener() {
        //        @Override
        //        public void onClick(View v) {
        //        startActivity(new Intent(MainActivity.this, Profile_Activity.class));
        //          }
        //        });


    }

    // For Feedback Starts

    public void openDialog() {
        FeedbackDialog exampleDialog = new FeedbackDialog();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");

        exampleDialog.setCancelable(false);// To prevent Dialog box to close if touched outside the box
    }
        @Override
        public void applyTexts(String username) {
            textViewUsername.setText(username);

        }

        // For Feedback ends

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
     /*   if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    //++++++++++++++++++++++++++++++


    //+++++++++++++++++++++++++++++++++

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_myProperty) {

        }

       /* else if (id == R.id.create_listing) {
            prf = getSharedPreferences("user_details", MODE_PRIVATE);
            String loginStatus = prf.getString("LoginId", "null");
            if (loginStatus.equals("null")) {
                Log.v(TAG, "Shared Preference Data..........................: " + loginStatus);
                Toast.makeText((this), "Redirecting to Login Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            } else {
                Toast.makeText((this), "Redirecting to Create Listing Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, CreateListingActivity.class));
            }

        } */


           else if (id == R.id.nav_shortListedProp) {
            prf = getSharedPreferences("user_details", MODE_PRIVATE);
            String loginStatus = prf.getString("LoginId", "null");
            if (loginStatus.equals("null")) {
                Log.v(TAG, "Shared Preference Data..........................: " + loginStatus);
                Toast.makeText((this), "Redirecting to Login Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));

            } else {
                Toast.makeText((this), "Redirecting to Create Listing Page", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, MyShortListProp.class));
            }

        }

        else if (id == R.id.nav_buyerPlan) {
            Toast.makeText((this), "Redirecting to Buyer Plans", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, BuyerPlan.class));

        } else if (id == R.id.nav_share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Checkout Panun Makaan, Jammu & Kashmir's first " +
                    "no brokerage real estate website, download from Playstore https://play.google.com/store/apps/details?id=com.nobroker.app");
            startActivity(Intent.createChooser(shareIntent, "Share Using..."));


        } else if (id == R.id.logout) {

            SharedPreferences.Editor editor = prf.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        } else if (id == R.id.about_us) {
            startActivity(new Intent(MainActivity.this, AboutUs.class));
        }

        else if (id == R.id.nav_feedback) {
            openDialog();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
   /*
        switch (parent.getId()) {
            case R.id.spinnerPropStatus:
                PropStatus = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinnerFloor:
                Floor = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinDistrict:
                District = parent.getItemAtPosition(position).toString();
                break;
        }

*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }


    public void searchLocality(View view) {
        Toast.makeText((MainActivity.this), "Redirecting to Search Results...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SearchResult.class));
    }
}