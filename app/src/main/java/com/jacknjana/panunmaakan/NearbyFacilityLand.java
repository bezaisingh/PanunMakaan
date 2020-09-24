package com.jacknjana.panunmaakan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

public class NearbyFacilityLand extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //Added On 22072020
String TAG = "Bijay Self check",
        Hospital,
        School,
        College,
        Gym,
        Park,
        ShoppingCenter,
        Playground,
        Pharmacy,
        Mosque,
    RegNo;

Spinner spinLandNfHospital,
        spinLandNfSchool,
        spinLandNfCollege,
        spinLandNfGym,
        spinLandNfPark,
        spinLandNfShoppingCenter,
        spinLandNfPlayGround,
        spinLandNfPharmacy,
        spinLandNfMosque;

SharedPreferences prf;
    //End

    Toolbar toolbar;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_facility_land);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);

        spinLandNfHospital= findViewById(R.id.spinLandNfHospital);
        spinLandNfSchool= findViewById(R.id.spinLandNfSchool);
        spinLandNfCollege= findViewById(R.id.spinLandNfCollege);
        spinLandNfGym= findViewById(R.id.spinLandNfGym);
        spinLandNfPark= findViewById(R.id.spinLandNfPark);
        spinLandNfShoppingCenter= findViewById(R.id.spinLandNfShoppingCenter);
        spinLandNfPlayGround= findViewById(R.id.spinLandNfPlayGround);
        spinLandNfPharmacy= findViewById(R.id.spinLandNfPharmacy);
        spinLandNfMosque= findViewById(R.id.spinLandNfMosque);


        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_hospital = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_school = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_college = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_gym = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_park = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_shoppingCenter = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_playGround = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_pharmacy = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_mosque = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_hospital.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_school.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_college.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_gym.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_park.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_shoppingCenter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_playGround.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_pharmacy.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_mosque.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinLandNfHospital.setAdapter(adapter_hospital);
        spinLandNfHospital.setOnItemSelectedListener(this);

        spinLandNfSchool.setAdapter(adapter_school);
        spinLandNfSchool.setOnItemSelectedListener(this);

        spinLandNfCollege.setAdapter(adapter_college);
        spinLandNfCollege.setOnItemSelectedListener(this);

        spinLandNfGym.setAdapter(adapter_gym);
        spinLandNfGym.setOnItemSelectedListener(this);

        spinLandNfPark.setAdapter(adapter_park);
        spinLandNfPark.setOnItemSelectedListener(this);

        spinLandNfShoppingCenter.setAdapter(adapter_shoppingCenter);
        spinLandNfShoppingCenter.setOnItemSelectedListener(this);

        spinLandNfPlayGround.setAdapter(adapter_playGround);
        spinLandNfPlayGround.setOnItemSelectedListener(this);

        spinLandNfPharmacy.setAdapter(adapter_pharmacy);
        spinLandNfPharmacy.setOnItemSelectedListener(this);

        spinLandNfMosque.setAdapter(adapter_mosque);
        spinLandNfMosque.setOnItemSelectedListener(this);

        //////////////////////*******///////////////////

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.spinLandNfHospital:
                Hospital = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfSchool:
                School = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfCollege:
                College = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfGym:
                Gym = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfPark:
                Park = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfShoppingCenter:
                ShoppingCenter = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfPlayGround:
                Playground = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfPharmacy:
                Pharmacy = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandNfMosque:
                Mosque = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void NearbyfacilityLandBtn(View view){

        String regNo="1234567";
        String propId="123";
        String hospital=Hospital;
        String school= School;
        String collegeUniversity=College;
        String gym= Gym;
        String park= Park;
        String shoppingCenter= ShoppingCenter;
        String playground= Playground;
        String pharmacy=Pharmacy;
        String mosque= Mosque;
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

        String type = "submit";

        if(hospital.isEmpty()){
            Toast.makeText(this, "Enter Hospital....", Toast.LENGTH_SHORT).show();
        }else{
         BackgroundWorker_LandNearby backgroundWorker_landNearby = new BackgroundWorker_LandNearby(this);

         backgroundWorker_landNearby.execute(type, regNo,propId,hospital, school,collegeUniversity,
                                             gym,park, shoppingCenter, playground, pharmacy, mosque,createDate,createTime);
        }

    }


//Background Worker Start//

    public class BackgroundWorker_LandNearby extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_LandNearby(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url = getUrl+"saveLandNearbyDetails";

            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String propId = params[2];
                    String hospital = params[3];
                    String school = params[4];
                    String collegeUniversity = params[5];
                    String gym = params[6];
                    String park = params[7];
                    String shoppingCenter = params[8];
                    String playground = params[9];
                    String pharmacy = params[10];
                    String mosque = params[11];
                    String createDate = params[12];
                    String createTime = params[13];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("propId", "UTF-8") + "=" + URLEncoder.encode(propId, "UTF-8") + "&"
                            + URLEncoder.encode("hospital", "UTF-8") + "=" + URLEncoder.encode(hospital, "UTF-8") + "&"
                            + URLEncoder.encode("school", "UTF-8") + "=" + URLEncoder.encode(school, "UTF-8") + "&"
                            + URLEncoder.encode("collegeUniversity", "UTF-8") + "=" + URLEncoder.encode(collegeUniversity, "UTF-8") + "&"
                            + URLEncoder.encode("gym", "UTF-8") + "=" + URLEncoder.encode(gym, "UTF-8") + "&"
                            + URLEncoder.encode("park", "UTF-8") + "=" + URLEncoder.encode(park, "UTF-8") + "&"
                            + URLEncoder.encode("shoppingCenter", "UTF-8") + "=" + URLEncoder.encode(shoppingCenter, "UTF-8") + "&"
                            + URLEncoder.encode("playground", "UTF-8") + "=" + URLEncoder.encode(playground, "UTF-8") + "&"
                            + URLEncoder.encode("pharmacy", "UTF-8") + "=" + URLEncoder.encode(pharmacy, "UTF-8") + "&"
                            + URLEncoder.encode("mosque", "UTF-8") + "=" + URLEncoder.encode(mosque, "UTF-8") + "&"
                            + URLEncoder.encode("createDate", "UTF-8") + "=" + URLEncoder.encode(createDate, "UTF-8") + "&"
                            + URLEncoder.encode("createTime", "UTF-8") + "=" + URLEncoder.encode(createTime, "UTF-8");

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
                Toast.makeText(NearbyFacilityLand.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Saved Successfully");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(NearbyFacilityLand.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Server Error...");
                alertDialog.show();

                Toast.makeText(NearbyFacilityLand.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//


}
