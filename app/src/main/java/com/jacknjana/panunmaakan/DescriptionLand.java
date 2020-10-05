package com.jacknjana.panunmaakan;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Locale;

public class DescriptionLand extends AppCompatActivity {
    Toolbar toolbar;

    //Added on 31072020
    EditText etLandPropDesc;
    String TAG = "Bijay Self check";
    String RegNo;
    SharedPreferences prf;
    int PropId = 121;

    //Ends

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_description_land);

        //----------------Action Bar Starts--------------//
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView name= findViewById(R.id.name);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DescriptionLand.this, MainActivity.class));
            }
        });
        //----------------Action Bar Ends--------------//

        etLandPropDesc = findViewById(R.id.etLandPropDesc);

        prf = getSharedPreferences("user_details", MODE_PRIVATE);
        RegNo = prf.getString("RegNo", null);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void DescriptionLandBtn(View view) {

        String regNo = RegNo;
        Integer propId = PropId;
        String description = etLandPropDesc.getText().toString();

        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());

        //   String type = "submit"; // used for ulrEncoder which ain't json

        //Json data send Starts
        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("regNo", regNo);
            post_dict.put("propId", propId);
            post_dict.put("description", description);
            post_dict.put("createDate", createDate);
            post_dict.put("createTime", createTime);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (post_dict.length() > 1) {

            SendJsonDataToServer sendJsonData = new SendJsonDataToServer();
            sendJsonData.execute(String.valueOf(post_dict));

            //new SendJsonDataToServer().execute(String.valueOf(post_dict));

        }
        //Json data send Ends
    }

    //add background inline class here Starts................
    class SendJsonDataToServer extends AsyncTask<String,String,String> {

        AlertDialog alertDialog;

        @Override
        protected String doInBackground(String... params) {

            String getUrl=getString(R.string.url);
            String JsonDATA = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
              //     URL url = new URL("http://192.168.43.91:8081/saveLandDesc");
                URL url = new URL(getUrl+"saveLandDesc");


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
            alertDialog = new AlertDialog.Builder(DescriptionLand.this).create();
            alertDialog.setTitle("Submit Status");
        }

        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);

            if (result==null){
                Toast.makeText(DescriptionLand.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Description Saved");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(DescriptionLand.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
                //    startActivity(new Intent(DescriptionRes.this, ResidentialList.class));
                Log.v(TAG, "Saved Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Server Error...");
                alertDialog.show();

                Toast.makeText(DescriptionLand.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }
        }
    }
//add background inline class Ends.......................

}
