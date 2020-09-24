package com.jacknjana.panunmaakan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.time.LocalDate;
import java.util.Locale;

public class DescriptionRes extends AppCompatActivity {



//Added on 22072020
String TAG = "Bijay Self check";
EditText etResPropDesc;
String RegNo;
SharedPreferences prf;
int PropId= 121;
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
        setContentView(R.layout.activity_description_res);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etResPropDesc= findViewById(R.id.etResPropDesc);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DescriptionResBtn (View view){

        String regNo=RegNo;
        Integer propId=PropId;
        String description=etResPropDesc.getText().toString();

        String createDate =new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

     //   String type = "submit"; // used for ulrEncoder which ain't json

        //Json data send Starts
        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("regNo" , regNo);
            post_dict.put("propId", propId);
            post_dict.put("description", description);
            post_dict.put("createDate", createDate);
            post_dict.put("createTime", createTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 1) {

            SendJsonDataToServer sendJsonData= new SendJsonDataToServer();
            sendJsonData.execute(String.valueOf(post_dict));

            //new SendJsonDataToServer().execute(String.valueOf(post_dict));

        }
        //Json data send Ends

/*
        if(description.isEmpty()){
            Toast.makeText(this, "Enter Description....", Toast.LENGTH_SHORT).show();
        }else{
            BackgroundWorker_ResDescription backgroundWorker_resdescription = new BackgroundWorker_ResDescription(this);

            backgroundWorker_resdescription.execute(type, regNo,propId, description,createDate, createTime);
        }

 */

    }

//add background inline class here Starts................
    class SendJsonDataToServer extends AsyncTask<String,String,String>{

    AlertDialog alertDialog;

    @Override
        protected String doInBackground(String... params) {

            String JsonDATA = params[0];

            String getUrl=getString(R.string.url);

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
             //   URL url = new URL("http://192.168.43.91:8081/saveResPropDesc");
               // URL url = new URL("http://makaan.disgenonline.in:8081/saveResPropDesc");
               URL url = new URL(getUrl+"saveResPropDesc");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);

                Log.v(TAG, "PostData: " + JsonDATA);
// json data
                writer.close();
                InputStream inputStream = urlConnection.getInputStream();
//input stream
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String inputLine;
                while ((inputLine = reader.readLine()) != null)
                    buffer.append(inputLine + "\n");
                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                String JsonResponse = buffer.toString();
//response data
                Log.i(TAG,JsonResponse);
                //send to post execute
                return JsonResponse;

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "Submit Status");
        alertDialog = new AlertDialog.Builder(DescriptionRes.this).create();
        alertDialog.setTitle("Submit Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);

        if (result==null){
            Toast.makeText(DescriptionRes.this, "No Internet ", Toast.LENGTH_SHORT).show();
        }

        else if(result.contains("Success")){
            alertDialog.setTitle("Description Saved");
            alertDialog.show();
            //resetText();

            // homeIntentMethod();
            Toast.makeText(DescriptionRes.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
        //    startActivity(new Intent(DescriptionRes.this, ResidentialList.class));
            Log.v(TAG, "Registration Successful");
            Log.v(TAG, result);
            //finish();
        }
        else if (result.contains("Error")) {
            alertDialog.setTitle("Registration Failed!!!!");
            alertDialog.show();

            Toast.makeText(DescriptionRes.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
            Log.v(TAG, "Result ka kuch nahi pata");
            Log.v(TAG, result);
        }
    }
    }
//add background inline class Ends.......................




/*
    //Background Worker Start//

    public class BackgroundWorker_ResDescription extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_ResDescription(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String type ="submit";
            String submit_url;
            //submit_url = "http://makaan.disgenonline.in:8081/saveResPropDesc";
            submit_url = "http://192.168.43.91:8081/saveResPropDesc";


            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String propId = params[2];
                    String description = params[3];
                    String createDate=params [4];
                    String createTime=params[5];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("propId", "UTF-8") + "=" + URLEncoder.encode(propId, "UTF-8") + "&"
                            + URLEncoder.encode("description", "UTF-8") + "=" + URLEncoder.encode(description, "UTF-8") + "&"
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
                Toast.makeText(DescriptionRes.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Registration Successful");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(DescriptionRes.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Registration Failed!!!!");
                alertDialog.show();

                Toast.makeText(DescriptionRes.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//


 */
}
