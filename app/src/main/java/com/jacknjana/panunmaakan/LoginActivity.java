package com.jacknjana.panunmaakan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    TextView tvSignUp;
    Toolbar toolbar;
    //Added on 20072020//
    SharedPreferences pref;
    String TAG = "++++++++++++++BIJAY SELF CHECK+++++++++++++";
    EditText etLoginPassword, etLoginMobileNo;

    Intent intent, i;
    //End

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pref = getSharedPreferences("user_details",MODE_PRIVATE);

// Commented on 22-09-2020
/*        etLoginMobileNo= findViewById(R.id.etLoginMobileNo);
        etLoginPassword= findViewById(R.id.etLoginPassword);
*/
        tvSignUp = findViewById(R.id.tvSignUp);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, RegistrationPage.class);
                startActivity(intent);
            }
        });

    }

    public void LoginWithMobileNo(View view){
        startActivity(new Intent(this, LoginWithMobile.class));
    }

    public void LoginWithEmail(View view) {
        startActivity(new Intent(this, LoginWithEmail.class));
    }


    // Commented on 22-09-2020
/*
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
//Login Button Starts

    public void loginBtn(View view) {

        String LoginId = etLoginMobileNo.getText().toString();
        String LoginPassword = etLoginPassword.getText().toString();

        if (LoginPassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Password....", Toast.LENGTH_SHORT).show();
        } else {
            BackgroundWorker_Login backgroundWorker_login = new BackgroundWorker_Login(LoginActivity.this);

            backgroundWorker_login.execute(LoginId, LoginPassword);
        }


        }

//Login Button Ends

    public void LoginWithMobileNo(View view){
        startActivity(new Intent(this, LoginWithMobile.class));
    }

//Send Data to LogMaster Table

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void logMaster (String status){

        String LoginId = etLoginMobileNo.getText().toString();

        @SuppressLint("WifiManagerLeak") WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
        String LogDate = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "IN")).format(new Date());
        String LogTime = new SimpleDateFormat("hh:mm:ss", new Locale("en", "IN")).format(new Date());
        String LogIp = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        String LogStatus = "Active";

        Log.v(TAG, "LOG_TIME..........................: " + LogTime);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("LoginId", LoginId);
        editor.putString("RegNo", status);
        editor.commit();


        //Json data send Starts
        JSONObject post_dict = new JSONObject();

        try {
            post_dict.put("loginId", LoginId);
            post_dict.put("lastLogDate", LogDate);
            post_dict.put("lastLogTime", LogTime);
            post_dict.put("logIp", LogIp);
            post_dict.put("logStatus", LogStatus);

            Log.v(TAG, "LOG_DATE..........................: " + LogDate);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(status.equals("Fail")){
            Toast.makeText(LoginActivity.this, "Login Failed ....",Toast.LENGTH_SHORT).show();
        }

            else if (post_dict.length() > 1) {

                SendJsonDataToServer sendJsonData = new SendJsonDataToServer();
                sendJsonData.execute(String.valueOf(post_dict));
            }

            Log.v(TAG, "LOG_DATE..........................: " + LoginId);
            //Json data send Ends

    }

//Send Data to LogMaster Table  Ends

    //Background Worker Start//

    public class BackgroundWorker_Login extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_Login(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {

            String type ="submit";
            String getUrl=getString(R.string.url);

            String submit_url;
          submit_url = getUrl+"validateUserLogin";

          // submit_url = "http://makaan.disgenonline.in:8081/validateUserLogin";



            if (type.equals("submit")) {
                try {

                    String mobileNumber = params[0];
                    String password = params[1];

                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("mobileNumber", "UTF-8") + "=" + URLEncoder.encode(mobileNumber, "UTF-8")+ "&"

                                     + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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
            alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Login Status");

            progressDialog.setMessage("Logging in...");
            progressDialog.show();

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {

            if (result==null){
                Toast.makeText(LoginActivity.this, "No Internet ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }

            else if (result.contains("Fail")) {
                alertDialog.setTitle("Login Failed!!!!");
                alertDialog.setMessage("Check your User ID and Password");
                alertDialog.show();
                progressDialog.dismiss();
                logMaster("Fail");

                Toast.makeText(LoginActivity.this, "Login Failed ....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

          //  else if(result.contains("Success")){
            else if(!result.isEmpty()){
                progressDialog.dismiss();
                String Status=result;

                logMaster(result);
                //resetText();

                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Login Successful");
                Log.v(TAG, result);
                startActivity(new Intent(LoginActivity.this, CreateListingActivity.class));
                //finish();
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//


    //add background inline class here Starts................
    class SendJsonDataToServer extends AsyncTask<String,String,String>{

        AlertDialog alertDialog;

        @Override
        protected String doInBackground(String... params) {

            String JsonDATA = params[0];

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            try {
                  URL url = new URL("http://192.168.43.91:8081/saveLogMaster");
               // URL url = new URL("http://makaan.disgenonline.in:8081/saveLogMaster");


                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                // is output buffer writter
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
//set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                writer.write(JsonDATA);

                Log.v(TAG, "JsonData..........................: " + JsonDATA);
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
            alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
            alertDialog.setTitle("Submit Status");
        }

        @Override
        protected void onPostExecute(String result) {
            alertDialog.setMessage(result);

            if (result==null){
                Toast.makeText(LoginActivity.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Logged in Successfully");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(LoginActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();

 // Move to Next Intent with some delay
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        i=new Intent(LoginActivity.this,CreateListingActivity.class);
                        startActivity(i);
                    }
                }, 3000);
//Delay ends here
                Log.v(TAG, "Log Master data saved Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Registration Failed!!!!");
                alertDialog.show();

                Toast.makeText(LoginActivity.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }
        }
    }
//add background inline class Ends.......................



 */
//aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
@Override
public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
}
}
