package com.jacknjana.panunmaakan;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;

public class PropertyDetailsRes extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Added on 22072020 Start//
    String TAG = "Bijay Self check";

    EditText etResPdHouseName,
             etResPdHouseNumber,
             etResPdOwnership,
             etResPdDate;

    Spinner spinresPdFloor,
            spinResPdAgeOfProperty,
            spinResPdKanal,
            spinResPdMarla,
            spinResPdBathroomAttach,
            spinResPdBathroomCommon,
            spinResPdHall,
            spinResPdParking,
            spinResPdKitchen,
            spinResPdFurnish,
            spinresPdBedrooms,
    spinResPdPropStatus;


    String  Floor,
            AgeOfProperty,
            Kanal,
            Marla,
            BathroomAttach,
            BathroomCommon,
            Hall,
            Parking,
            Kitchen,
            Furnish,
            BedRooms,
            PropertyStatus,
    RegNo;

    SharedPreferences prf;

    ImageView date_pic;

    //End//

    DatePickerDialog datePickerDialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details_res);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PropertyDetailsRes.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);


        etResPdDate = findViewById(R.id.etResPdDate);
        etResPdHouseName = findViewById(R.id.etResPdHouseName);
        etResPdHouseNumber = findViewById(R.id.etResPdHouseNumber);
        etResPdOwnership = findViewById(R.id.etResPdOwnership);
                spinresPdFloor = findViewById(R.id.spinresPdFloor);
                spinResPdAgeOfProperty = findViewById(R.id.spinResPdAgeOfProperty);
                spinResPdKanal = findViewById(R.id.spinResPdKanal);
                spinResPdMarla = findViewById(R.id.spinResPdMarla);
                spinResPdBathroomAttach = findViewById(R.id.spinResPdBathroomAttach);
                spinResPdBathroomCommon = findViewById(R.id.spinResPdBathroomCommon);
                spinResPdHall = findViewById(R.id.spinResPdHall);
                spinResPdParking = findViewById(R.id.spinResPdParking);
                spinResPdKitchen = findViewById(R.id.spinResPdKitchen);
                spinResPdFurnish = findViewById(R.id.spinResPdFurnish);
        spinresPdBedrooms= findViewById(R.id.spinresPdBedrooms);
        spinResPdPropStatus=findViewById(R.id.spinResPdPropStatus);

        date_pic = findViewById(R.id.date_pic);

        date_pic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(PropertyDetailsRes.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        etResPdDate.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, 0, 0, 0);
                datePickerDialog.show();
            }
        });

        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_floor = ArrayAdapter.createFromResource(this,R.array.floorNumberArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_ageOfProp = ArrayAdapter.createFromResource(this,R.array.ageOfPropertyArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_kanal = ArrayAdapter.createFromResource(this,R.array.kanalArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_marla = ArrayAdapter.createFromResource(this,R.array.marlaArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_bathroomAttach = ArrayAdapter.createFromResource(this,R.array.bathroomAttachArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_bathroomCommon = ArrayAdapter.createFromResource(this,R.array.bathroomCommonArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_hall = ArrayAdapter.createFromResource(this,R.array.hallNumberArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_parking = ArrayAdapter.createFromResource(this,R.array.parkingArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_kitchen = ArrayAdapter.createFromResource(this,R.array.kitchenTypeArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_furnish = ArrayAdapter.createFromResource(this,R.array.furnishingStatusArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_bedrooms = ArrayAdapter.createFromResource(this,R.array.bedroomArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_propStatus = ArrayAdapter.createFromResource(this,R.array.propStatusArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_ageOfProp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_kanal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_marla.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_bathroomAttach.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_bathroomCommon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_hall.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_parking.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_kitchen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_furnish.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_bedrooms.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_propStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinresPdFloor.setAdapter(adapter_floor);
        spinresPdFloor.setOnItemSelectedListener(this);

        spinResPdAgeOfProperty.setAdapter(adapter_ageOfProp);
        spinResPdAgeOfProperty.setOnItemSelectedListener(this);

        spinResPdKanal.setAdapter(adapter_kanal);
        spinResPdKanal.setOnItemSelectedListener(this);

        spinResPdMarla.setAdapter(adapter_marla);
        spinResPdMarla.setOnItemSelectedListener(this);

        spinResPdBathroomAttach.setAdapter(adapter_bathroomAttach);
        spinResPdBathroomAttach.setOnItemSelectedListener(this);

        spinResPdBathroomCommon.setAdapter(adapter_bathroomCommon);
        spinResPdBathroomCommon.setOnItemSelectedListener(this);

        spinResPdHall.setAdapter(adapter_hall);
        spinResPdHall.setOnItemSelectedListener(this);

        spinResPdParking.setAdapter(adapter_parking);
        spinResPdParking.setOnItemSelectedListener(this);

        spinResPdKitchen.setAdapter(adapter_kitchen);
        spinResPdKitchen.setOnItemSelectedListener(this);

        spinResPdFurnish.setAdapter(adapter_furnish);
        spinResPdFurnish.setOnItemSelectedListener(this);

        spinresPdBedrooms.setAdapter(adapter_bedrooms);
        spinresPdBedrooms.setOnItemSelectedListener(this);

        spinResPdPropStatus.setAdapter(adapter_propStatus);
        spinResPdPropStatus.setOnItemSelectedListener(this);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.spinresPdFloor:
                Floor = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdAgeOfProperty:
                AgeOfProperty = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdKanal:
                Kanal = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdMarla:
                Marla = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdBathroomAttach:
                BathroomAttach = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdBathroomCommon:
                BathroomCommon = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdHall:
                Hall = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdParking:
                Parking = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdKitchen:
                Kitchen = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdFurnish:
                Furnish = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinresPdBedrooms:
                BedRooms = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPdPropStatus:
                PropertyStatus = parent.getItemAtPosition(position).toString();
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void PropertyDetailsResBtn(View view) {

        String regNo=RegNo;
        String houseName=etResPdHouseName.getText().toString();
        String houseNumber = etResPdHouseNumber.getText().toString();
        String ownership=etResPdOwnership.getText().toString();
        String floor=Floor;
        String ageOfProp=AgeOfProperty;
        String landSizeKanal=Kanal;
        String landSizeMarla = Marla;
        String bedrooms=BedRooms;
        String bathroomsAttached=BathroomAttach;
        String bathroomsCommon=BathroomCommon;
        String hall= Hall;
        String parking= Parking;
        String posessionDate=etResPdDate.getText().toString();
        String kitchenType=Kitchen;
        String furnishingStatus=Furnish;
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
        String propStatus= PropertyStatus;


        String type = "submit";

        if(houseName.isEmpty()){
            Toast.makeText(this, "Enter Area....", Toast.LENGTH_SHORT).show();
        }else{
            BackgroundWorker_PropDetailres backgroundWorker_propDetailRes = new BackgroundWorker_PropDetailres(this);

            backgroundWorker_propDetailRes.execute(type, regNo,houseName, houseNumber, ownership,
                                                    floor, ageOfProp, landSizeKanal, landSizeMarla, bedrooms, bathroomsAttached,
                                                    bathroomsCommon, hall, parking,posessionDate, kitchenType, furnishingStatus, createDate, createTime,propStatus);
        }
    }

    //Background Worker Start//

    public class BackgroundWorker_PropDetailres extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_PropDetailres(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url = getUrl+"saveResPropertyDetails";

            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String houseName=params[2];
                    String houseNumber = params[3];
                    String ownership=params[4];
                    String floor=params[5];
                    String ageOfProp=params[6];
                    String landSizeKanal=params[7];
                    String landSizeMarla = params[8];
                    String bedrooms=params[9];
                    String bathroomsAttached=params[10];
                    String bathroomsCommon=params[11];
                    String hall=params[12];
                    String parking= params[13];
                    String posessionDate=params[14];
                    String kitchenType=params[15];
                    String furnishingStatus=params[16];
                    String createDate = params[17];
                    String createTime = params[18];
                    String propStatus= params[19];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("houseName", "UTF-8") + "=" + URLEncoder.encode(houseName, "UTF-8") + "&"
                            + URLEncoder.encode("houseNumber", "UTF-8") + "=" + URLEncoder.encode(houseNumber, "UTF-8") + "&"
                            + URLEncoder.encode("ownership", "UTF-8") + "=" + URLEncoder.encode(ownership, "UTF-8") + "&"
                            + URLEncoder.encode("floor", "UTF-8") + "=" + URLEncoder.encode(floor, "UTF-8") + "&"
                            + URLEncoder.encode("ageOfProp", "UTF-8") + "=" + URLEncoder.encode(ageOfProp, "UTF-8") + "&"
                            + URLEncoder.encode("landSizeKanal", "UTF-8") + "=" + URLEncoder.encode(landSizeKanal, "UTF-8") + "&"
                            + URLEncoder.encode("landSizeMarla", "UTF-8") + "=" + URLEncoder.encode(landSizeMarla, "UTF-8") + "&"
                            + URLEncoder.encode("bedrooms", "UTF-8") + "=" + URLEncoder.encode(bedrooms, "UTF-8") + "&"
                            + URLEncoder.encode("bathroomsAttached", "UTF-8") + "=" + URLEncoder.encode(bathroomsAttached, "UTF-8") + "&"
                            + URLEncoder.encode("bathroomsCommon", "UTF-8") + "=" + URLEncoder.encode(bathroomsCommon, "UTF-8") + "&"
                            + URLEncoder.encode("hall", "UTF-8") + "=" + URLEncoder.encode(hall, "UTF-8") + "&"
                            + URLEncoder.encode("parking", "UTF-8") + "=" + URLEncoder.encode(parking, "UTF-8") + "&"
                            + URLEncoder.encode("posessionDate", "UTF-8") + "=" + URLEncoder.encode(posessionDate, "UTF-8") + "&"
                            + URLEncoder.encode("kitchenType", "UTF-8") + "=" + URLEncoder.encode(kitchenType, "UTF-8") + "&"
                            + URLEncoder.encode("furnishingStatus", "UTF-8") + "=" + URLEncoder.encode(furnishingStatus, "UTF-8") + "&"
                            + URLEncoder.encode("createDate", "UTF-8") + "=" + URLEncoder.encode(createDate, "UTF-8") + "&"
                            + URLEncoder.encode("createTime", "UTF-8") + "=" + URLEncoder.encode(createTime, "UTF-8")+ "&"
                            + URLEncoder.encode("propStatus", "UTF-8") + "=" + URLEncoder.encode(propStatus, "UTF-8");

                    Log.v(TAG, "PostData: " + post_data);

                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    //  InputStream inputStream = httpURLConnection.getErrorStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.v(TAG, "Submit Status");
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Submit Status");
        }

        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);

            if (result==null){
                Toast.makeText(PropertyDetailsRes.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Status...");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(PropertyDetailsRes.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

              //  startActivity(new Intent(PropertyDetailsRes.this, LocalityDetailsRes.class));
                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Server Error...");
                alertDialog.show();

                Toast.makeText(PropertyDetailsRes.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//
}
