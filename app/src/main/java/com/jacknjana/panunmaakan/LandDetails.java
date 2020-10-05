package com.jacknjana.panunmaakan;

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
import android.widget.EditText;
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

public class LandDetails extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{

    //Added on 21072020
    String TAG = "Bijay Self check";
    EditText etLandLdExpectedPrice;
    Spinner spinLandLdLandSizeKanal, spinLandLdLandSizeMarla, spinLandLdNegotiable, spinLandLdUnderLoan;
    String Negotiable, UnderLoan, Kanal, Marla, RegNo;
    SharedPreferences prf;
    //

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.land_details_act);
        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LandDetails.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);

        etLandLdExpectedPrice= findViewById(R.id.etLandLdExpectedPrice);
        spinLandLdLandSizeKanal= findViewById(R.id.spinLandLdLandSizeKanal);
        spinLandLdLandSizeMarla= findViewById(R.id.spinLandLdLandSizeMarla);
        spinLandLdNegotiable=findViewById(R.id.spinLandLdNegotiable);
        spinLandLdUnderLoan= findViewById(R.id.spinLandLdUnderLoan);

        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_kanal = ArrayAdapter.createFromResource(this,R.array.kanalArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_marla = ArrayAdapter.createFromResource(this,R.array.marlaArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_negotiable = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_underLoan = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_kanal.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_marla.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_negotiable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_underLoan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinLandLdLandSizeKanal.setAdapter(adapter_kanal);
        spinLandLdLandSizeKanal.setOnItemSelectedListener(this);

        spinLandLdLandSizeMarla.setAdapter(adapter_marla);
        spinLandLdLandSizeMarla.setOnItemSelectedListener(this);

        spinLandLdNegotiable.setAdapter(adapter_negotiable);
        spinLandLdNegotiable.setOnItemSelectedListener(this);

        spinLandLdUnderLoan.setAdapter(adapter_underLoan);
        spinLandLdUnderLoan.setOnItemSelectedListener(this);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void LandDetailsLandBtn(View view){

        String regNo=RegNo;
        String landSize= Kanal + " " + Marla;
        String expectedPrice =etLandLdExpectedPrice.getText().toString();
        String negotiable=Negotiable;
        String underLoan=UnderLoan;
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());


        String type = "submit";

        if(expectedPrice.isEmpty()){
            Toast.makeText(this, "Enter Price....", Toast.LENGTH_SHORT).show();
        }else{
           BackgroundWorker_LandDetails backgroundWorkerLandDetails = new BackgroundWorker_LandDetails(this);

            backgroundWorkerLandDetails.execute(type, regNo,landSize, expectedPrice, negotiable, underLoan, createDate,createTime );
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        switch (parent.getId()) {

            case R.id.spinLandLdLandSizeKanal:
                Kanal = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandLdLandSizeMarla:
                Marla = parent.getItemAtPosition(position).toString();
                    break;

            case R.id.spinLandLdNegotiable:
                Negotiable = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinLandLdUnderLoan:
                UnderLoan = parent.getItemAtPosition(position).toString();
                break;

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    //Background Worker Start//

    public class BackgroundWorker_LandDetails extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_LandDetails(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url;
         //   submit_url = "http://192.168.43.91:8081/saveLandDetails";
            submit_url = getUrl+"saveLandDetails";


            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String landSize = params[2];
                    String expectedPrice = params[3];
                    String negotiable = params[4];
                    String underLoan = params[5];
                    String createDate = params[6];
                    String createTime = params[7];


                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("landSize", "UTF-8") + "=" + URLEncoder.encode(landSize, "UTF-8") + "&"
                            + URLEncoder.encode("expectedPrice", "UTF-8") + "=" + URLEncoder.encode(expectedPrice, "UTF-8") + "&"
                            + URLEncoder.encode("negotiable", "UTF-8") + "=" + URLEncoder.encode(negotiable, "UTF-8") + "&"
                            + URLEncoder.encode("underLoan", "UTF-8") + "=" + URLEncoder.encode(underLoan, "UTF-8") + "&"
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
                Toast.makeText(LandDetails.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Status...");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(LandDetails.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Saved Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Failed")) {
                alertDialog.setTitle("Failed!!!!");
                alertDialog.show();

                Toast.makeText(LandDetails.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//
}