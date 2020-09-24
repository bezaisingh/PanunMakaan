package com.jacknjana.panunmaakan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
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
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.HttpStatus;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class RegistrationPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Toolbar toolbar;

    //Added on 18072020
    String TAG = "Bijay Self check";
    EditText etFirstName, etLastName, etMobileNumber, etEmail;
    Spinner spinRegType;
    String RegType, Password;

    //End

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

      //  spinRegType=findViewById(R.id.spinRegType);
        etFirstName = findViewById(R.id.etFirstName);
        etLastName= findViewById(R.id.etLastName);
        etMobileNumber= findViewById(R.id.etMobileNumber);
        etEmail= findViewById(R.id.etEmail);

/*        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_regType = ArrayAdapter.createFromResource(this,R.array.regTypeArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_regType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinRegType.setAdapter(adapter_regType);
        spinRegType.setOnItemSelectedListener(this);*/

        Password=getSaltString();

    }

    //Random Number generation for RegNo//
    public String nDigitRandomNo(int digits){
        int max = (int) Math.pow(10,(digits)) - 1; //for digits =8, max will be 99999999
        int min = (int) Math.pow(10, digits-1); //for digits = 8, min will be 10000000
        int range = max-min; //This is 8999999
        Random r = new Random();
        int x = r.nextInt(range);// This will generate random integers in range 0 - 89999999
        int nDigitRandomNo = x+min; //Our random number will be any random number x + min
        return String.valueOf(nDigitRandomNo);
    }
//End



    //Random String Generation for Password

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 6) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
    //




    @RequiresApi(api = Build.VERSION_CODES.N)
    public void RegisterBtn(View view){

        SendMessage1 sendMessage1= new SendMessage1();
        sendMessage1.execute();


        String regNo=nDigitRandomNo(8); // n is Integer type value, we convert it to String
        String regDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
       // String regDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
       // String regType=RegType;
        String regType="RegTypeRemoved";
        String firstName=etFirstName.getText().toString();
        String lastName=etLastName.getText().toString();
        String mobileNumber=etMobileNumber.getText().toString();
        String email=etEmail.getText().toString();
        String loginId=etMobileNumber.getText().toString();
        String password=Password;
        String regTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

        String type = "submit";

        if(firstName.isEmpty()){
            Toast.makeText(this, "Enter First Name....", Toast.LENGTH_SHORT).show();
        }else{
            RegistrationPage.BackgroundWorker_Registration backgroundWorker_registration = new BackgroundWorker_Registration(this);

            backgroundWorker_registration.execute(type, regNo, regDate, regType,firstName, lastName, mobileNumber, email, loginId, password, regTime);
        }

    }


//OTP or Password Method Starts............................................

   public class SendMessage1 extends AsyncTask {

       ProgressDialog progressDialog = new ProgressDialog(RegistrationPage.this);

       @Override
       protected Object doInBackground(Object[] objects) {

               int responseCode = -1;
               String responseBody = null;
             //  Random random = new Random();

               Log.v(TAG, "SMS KA Locha...........//////////////////////////////////////////////////////////...");

               try {

                   String firstName=etFirstName.getText().toString();
                   String lastName=etLastName.getText().toString();

                   String message = "Dear "+ firstName+ " " + lastName+ " Your Pannun Makaan Password is : " + Password;

                   String MobNumber = etMobileNumber.getText().toString();
                   String encodedMessage = URLEncoder.encode(message, "UTF-8");
                   String url = "https://www.fast2sms.com/dev/bulk?authorization=CsyojtnM140OwIUWfxmePDTKHZqibakrSplVGJ8YFzQ6EB2uXd5iaMswSrE6C3eYVJImGXxThjcR7tFq&sender_id=FSTSMS&message="
                           + encodedMessage + "&language=english&route=p&numbers=" + MobNumber + "";

                   Log.v(TAG, encodedMessage);
                   System.out.println(MobNumber);


                   HttpClient client = HttpClientBuilder.create().build();
                   HttpGet get = new HttpGet(url);
                   HttpResponse response = client.execute(get);
                   responseCode = response.getStatusLine().getStatusCode();

                   System.out.println(url);

                   if (responseCode == HttpStatus.SC_OK) {
                       System.out.println(responseBody);
                   } else {
                       System.out.println(responseBody);
                   }
               } catch (Exception e) {
                   System.out.println(e);
               }
               return null;
           }

       @Override
       protected void onPreExecute() {
           progressDialog.setMessage("Loading...");
           progressDialog.show();
       }

       @Override
       protected void onPostExecute(Object o) {
           progressDialog.dismiss();
       }
   }

//OTP or Password Method Ends............................................

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

   @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

       /* switch(parent.getId()) {
            case R.id.spinRegType:
                RegType = parent.getItemAtPosition(position).toString();
                break;
        }*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}



    //Background Worker Start//

    public class BackgroundWorker_Registration extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(RegistrationPage.this);

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_Registration(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url= getUrl+"saveUserRegistrationDetails";

            if (type.equals("submit")) {
                try {
                    String regNo= params[1];
                    String regDate= params[2];
                    String regType = params[3];
                    String firstName = params[4];
                    String lastName = params[5];
                    String mobileNumber = params[6];
                    String email = params[7];
                    String loginId=params[8];
                    String password = params[9];
                    String regTime = params[10];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"
                            + URLEncoder.encode("regDate", "UTF-8") + "=" + URLEncoder.encode(regDate, "UTF-8")+ "&"
                            + URLEncoder.encode("regType", "UTF-8") + "=" + URLEncoder.encode(regType, "UTF-8")+ "&"
                            + URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&"
                            + URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&"
                            + URLEncoder.encode("mobileNumber", "UTF-8") + "=" + URLEncoder.encode(mobileNumber, "UTF-8") + "&"
                            + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&"
                            + URLEncoder.encode("loginId", "UTF-8") + "=" + URLEncoder.encode(loginId, "UTF-8") + "&"
                            + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&"
                            + URLEncoder.encode("regTime", "UTF-8") + "=" + URLEncoder.encode(regTime, "UTF-8");


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

            progressDialog.setMessage("Loading...");
            progressDialog.show();

            Log.v(TAG, "Submit Status");
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Submit Status");
        }

        @Override
        protected void onPostExecute(String result) {

            progressDialog.dismiss();

            alertDialog.setMessage(result);

            if (result==null){
                Toast.makeText(RegistrationPage.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Registration Successful");
                alertDialog.show();
                //resetText();



                Toast.makeText(RegistrationPage.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
// Move to Next Intent with some delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent i=new Intent(RegistrationPage.this,LoginActivity.class);
                        startActivity(i);
                    }
                }, 5000);
//Delay ends here


                //homeIntentMethod();

                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Registration Failed!!!!");
                alertDialog.show();

                Toast.makeText(RegistrationPage.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//

}


