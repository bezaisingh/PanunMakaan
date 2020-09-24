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

public class PriceDetailsRes extends AppCompatActivity implements  AdapterView.OnItemSelectedListener{
    Toolbar toolbar;

    //Added on 22072020
    String TAG = "Bijay Self check";
    EditText etResPrDetExpectedPrice;
    Spinner spinResPrDetNegotiable, spinResPrDetUnderLoan;
    String Negotiable, UnderLoan, RegNo, PropId="123";
    SharedPreferences prf;
    //End

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_details_res);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);

        etResPrDetExpectedPrice= findViewById(R.id.etResPrDetExpectedPrice);
        spinResPrDetNegotiable= findViewById(R.id.spinResPrDetNegotiable);
        spinResPrDetUnderLoan= findViewById(R.id.spinResPrDetUnderLoan);


        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_negotiable = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_underLoan = ArrayAdapter.createFromResource(this,R.array.yesNoArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_negotiable.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_underLoan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinResPrDetNegotiable.setAdapter(adapter_negotiable);
        spinResPrDetNegotiable.setOnItemSelectedListener(this);

        spinResPrDetUnderLoan.setAdapter(adapter_underLoan);
        spinResPrDetUnderLoan.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()) {
            case R.id.spinResPrDetNegotiable:
                Negotiable = parent.getItemAtPosition(position).toString();
                break;

            case R.id.spinResPrDetUnderLoan:
                UnderLoan = parent.getItemAtPosition(position).toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void PriceDetailResBtn (View view) {

        String regNo=RegNo;
        String propId=PropId;
        String expectedPrice =etResPrDetExpectedPrice.getText().toString();
        String negotiable=Negotiable;
        String underLoan=UnderLoan;
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

        String type = "submit";

        if(expectedPrice.isEmpty()){
            Toast.makeText(this, "Enter Price....", Toast.LENGTH_SHORT).show();
        }else{
            BackgroundWorker_PriceDetailsRes backgroundWorkerPriceDetailsRes = new BackgroundWorker_PriceDetailsRes(this);

            backgroundWorkerPriceDetailsRes.execute(type, regNo,propId, expectedPrice, negotiable, underLoan, createDate, createTime);
        }

    }

    //Background Worker Start//

    public class BackgroundWorker_PriceDetailsRes extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_PriceDetailsRes(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url = getUrl+"saveResPriceDetails";

          //  submit_url =  "http://makaan.disgenonline.in:8081/saveResPriceDetails";


            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String propId = params[2];
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

                            + URLEncoder.encode("propId", "UTF-8") + "=" + URLEncoder.encode(propId, "UTF-8") + "&"
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
                Toast.makeText(PriceDetailsRes.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Status...");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(PriceDetailsRes.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Saved Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Failed")) {
                alertDialog.setTitle("Failed!!!!");
                alertDialog.show();

                Toast.makeText(PriceDetailsRes.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//
}
