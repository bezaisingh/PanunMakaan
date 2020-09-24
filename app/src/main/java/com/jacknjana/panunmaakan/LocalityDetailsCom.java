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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
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

public class LocalityDetailsCom extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //Added on 17072020
    String TAG = "Bijay Self check";
    Spinner spinComLdDistrict;
    EditText etComLdLocality,etComLdStreetAreaName,etComLdLandmark,etComLdPincode;
    String District, RegNo, PropId="101";
    SharedPreferences prf;
    //End

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locality_details_com);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);

        etComLdLocality= findViewById(R.id.etComLdLocality);
        etComLdStreetAreaName= findViewById(R.id.etComLdStreetAreaName);
        etComLdLandmark= findViewById(R.id.etComLdLandmark);
        etComLdPincode= findViewById(R.id.etComLdPincode);
        spinComLdDistrict=findViewById(R.id.spinComLdDistrict);

        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_district = ArrayAdapter.createFromResource(this,R.array.districtArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_district.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinComLdDistrict.setAdapter(adapter_district);
        spinComLdDistrict.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch(parent.getId()) {
            case R.id.spinComLdDistrict:
                District = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void LocalDetailComBtn(View view) {

        String regNo=RegNo;
        String propId=PropId;
        String district = District;
        String locality=etComLdLocality.getText().toString();
        String streetAreaName=etComLdStreetAreaName.getText().toString();
        String landmark=etComLdLandmark.getText().toString();
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

        String type = "submit";

        if(locality.isEmpty()){
            Toast.makeText(this, "Enter Area....", Toast.LENGTH_SHORT).show();
        }else{
            LocalityDetailsCom.BackgroundWorker_LocalityDetailsCom backgroundWorker_localDetailsCom = new BackgroundWorker_LocalityDetailsCom(this);

            backgroundWorker_localDetailsCom.execute(type, regNo,propId, district, locality, streetAreaName,landmark, createDate, createTime);
        }

    }


    //Background Worker Start//

    public class BackgroundWorker_LocalityDetailsCom extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_LocalityDetailsCom(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";

            String submit_url = getUrl+"saveComLocalityDetails";

            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String propId = params[2];
                    String district = params[3];
                    String locality = params[4];
                    String streetAreaName = params[5];
                    String landmark = params[6];
                    String createDate = params[7];
                    String createTime = params[8];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("propId", "UTF-8") + "=" + URLEncoder.encode(propId, "UTF-8") + "&"
                            + URLEncoder.encode("district", "UTF-8") + "=" + URLEncoder.encode(district, "UTF-8") + "&"
                            + URLEncoder.encode("locality", "UTF-8") + "=" + URLEncoder.encode(locality, "UTF-8") + "&"
                            + URLEncoder.encode("streetAreaName", "UTF-8") + "=" + URLEncoder.encode(streetAreaName, "UTF-8") + "&"
                            + URLEncoder.encode("landmark", "UTF-8") + "=" + URLEncoder.encode(landmark, "UTF-8") + "&"
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
                Toast.makeText(LocalityDetailsCom.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Status...");
                alertDialog.setMessage("Locality Details Saved Successfully");
              //  alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(LocalityDetailsCom.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Server Error...");
                alertDialog.show();

                Toast.makeText(LocalityDetailsCom.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//

}

