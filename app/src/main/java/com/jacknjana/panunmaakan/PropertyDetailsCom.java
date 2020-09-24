package com.jacknjana.panunmaakan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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

public class PropertyDetailsCom extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Toolbar toolbar;

    //Added on 13072020 Start//
    String TAG = "Bijay Self check";
    Button btnComPdSave;
    EditText etComPdArea;
    Spinner spinComPdPropType,spinComPdFloor,spinComPdTotalFloor,spinComPdAgOfProp,spinComPdFurnish,spinComPdPropStatus;

    String PropType, Floor, TotalFloor, AgeOfProperty, FurnishingStatus, RegNo, PropertyStatus;
    SharedPreferences prf;
    //End//

    EditText date_show;
    ImageView date_pic;
    DatePickerDialog datePickerDialog;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail_com);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        prf = getSharedPreferences("user_details",MODE_PRIVATE);
        RegNo= prf.getString("RegNo",null);

        spinComPdPropType = findViewById(R.id.spinComPdPropType);
        spinComPdFloor = findViewById(R.id.spinComPdFloor);
        spinComPdTotalFloor = findViewById(R.id.spinComPdTotalFloor);
        spinComPdAgOfProp = findViewById(R.id.spinComPdAgOfProp);
        spinComPdFurnish = findViewById(R.id.spinComPdFurnish);
        spinComPdPropStatus= findViewById(R.id.spinComPdPropStatus);

        etComPdArea= findViewById(R.id.etComPdArea);
        btnComPdSave =findViewById(R.id.btnComPdSave);


        date_show = findViewById(R.id.date_show);
        date_pic = findViewById(R.id.date_pic);
        date_pic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(PropertyDetailsCom.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date_show.setText(dayOfMonth + "/" + month + "/" + year);
                    }
                }, 0, 0, 0);
                datePickerDialog.show();
            }
        });


        /////////// Spinner ////////////
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_propType = ArrayAdapter.createFromResource(this,R.array.propertyTypeArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_floor = ArrayAdapter.createFromResource(this,R.array.floorNumberArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_totalFloor = ArrayAdapter.createFromResource(this,R.array.totalFloorArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_ageOfProp = ArrayAdapter.createFromResource(this,R.array.ageOfPropertyArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_furnishingStatus = ArrayAdapter.createFromResource(this,R.array.furnishingStatusArray, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapter_propStatus = ArrayAdapter.createFromResource(this,R.array.propStatusArray, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter_propType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_floor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_totalFloor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_ageOfProp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_furnishingStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter_propStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// Apply the adapter to the spinner
        spinComPdPropType.setAdapter(adapter_propType);
        spinComPdPropType.setOnItemSelectedListener(this);

        spinComPdFloor.setAdapter(adapter_floor);
        spinComPdFloor.setOnItemSelectedListener(this);

        spinComPdTotalFloor.setAdapter(adapter_totalFloor);
        spinComPdTotalFloor.setOnItemSelectedListener(this);

        spinComPdAgOfProp.setAdapter(adapter_ageOfProp);
        spinComPdAgOfProp.setOnItemSelectedListener(this);

        spinComPdFurnish.setAdapter(adapter_furnishingStatus);
        spinComPdFurnish.setOnItemSelectedListener(this);

        spinComPdPropStatus.setAdapter(adapter_propStatus);
        spinComPdPropStatus.setOnItemSelectedListener(this);
        //////////////////////*******///////////////////
    }


//Added on 13072020 start//
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void OnSubmit(View view) {

        String regNo=RegNo;
        String propType=PropType;
        String area = etComPdArea.getText().toString();
        String floor=Floor;
        String totalFloor=TotalFloor;
        String ageOfProperty=AgeOfProperty;
        String posessionDate=date_show.getText().toString();
        String furnishingStatus=FurnishingStatus;
        String createDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String createTime = new SimpleDateFormat("hh:mm:ss", Locale.getDefault()).format(new Date());
        String propStatus=PropertyStatus;


        String type = "submit";

        if(area.isEmpty()){
            Toast.makeText(this, "Enter Area....", Toast.LENGTH_SHORT).show();
        }else{
            BackgroundWorker_PropDetCom backgroundWorker_propDetCom = new BackgroundWorker_PropDetCom(this);

            backgroundWorker_propDetCom.execute(type, regNo,propType,area, floor,totalFloor,ageOfProperty,
                    posessionDate, furnishingStatus, createDate, createTime, propStatus);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {
            case R.id.spinComPdPropType:
                PropType = parent.getItemAtPosition(position).toString();
                break;

                case R.id.spinComPdFloor:
                    Floor = parent.getItemAtPosition(position).toString();
                    break;

                    case R.id.spinComPdTotalFloor:
                        TotalFloor = parent.getItemAtPosition(position).toString();
                        break;

                        case R.id.spinComPdAgOfProp:
                            AgeOfProperty = parent.getItemAtPosition(position).toString();
                            break;

                            case R.id.spinComPdFurnish:
                                FurnishingStatus = parent.getItemAtPosition(position).toString();
                                break;

            case R.id.spinResPdPropStatus:
                PropertyStatus = parent.getItemAtPosition(position).toString();
                break;
                        }
        }



    @Override
    public void onNothingSelected(AdapterView<?> parent) { }

//*********** To go back to the previous page using the back arrow
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        this.finish();
        return super.onOptionsItemSelected(item);
    }
//*************

    //End//

//Background Worker Start//

    public class BackgroundWorker_PropDetCom extends AsyncTask<String, String, String> {

        Context context;
        AlertDialog alertDialog;

        BackgroundWorker_PropDetCom(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String getUrl=getString(R.string.url);
            String type ="submit";
            String submit_url = getUrl+"saveComPropDetails";

            if (type.equals("submit")) {
                try {
                    String regNo = params[1];
                    String propType = params[2];
                    String area = params[3];
                    String floor = params[4];
                    String totalFloor = params[5];
                    String ageOfProperty = params[6];
                    String posessionDate = params[7];
                    String furnishingStatus = params[8];
                    String createDate = params[9];
                    String createTime = params[10];
                    String propStatus = params[11];



                    URL url = new URL(submit_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                    String post_data = URLEncoder.encode("regNo", "UTF-8") + "=" + URLEncoder.encode(regNo, "UTF-8")+ "&"

                            + URLEncoder.encode("propType", "UTF-8") + "=" + URLEncoder.encode(propType, "UTF-8") + "&"
                            + URLEncoder.encode("area", "UTF-8") + "=" + URLEncoder.encode(area, "UTF-8") + "&"
                            + URLEncoder.encode("floor", "UTF-8") + "=" + URLEncoder.encode(floor, "UTF-8") + "&"
                            + URLEncoder.encode("totalFloor", "UTF-8") + "=" + URLEncoder.encode(totalFloor, "UTF-8") + "&"
                            + URLEncoder.encode("ageOfProperty", "UTF-8") + "=" + URLEncoder.encode(ageOfProperty, "UTF-8") + "&"
                            + URLEncoder.encode("posessionDate", "UTF-8") + "=" + URLEncoder.encode(posessionDate, "UTF-8") + "&"
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
                Toast.makeText(PropertyDetailsCom.this, "No Internet ", Toast.LENGTH_SHORT).show();
            }

            else if(result.contains("Success")){
                alertDialog.setTitle("Status...");
                alertDialog.show();
                //resetText();

                // homeIntentMethod();
                Toast.makeText(PropertyDetailsCom.this, "Saved Successfull", Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Registration Successful");
                Log.v(TAG, result);
                //finish();
            }
            else if (result.contains("Error")) {
                alertDialog.setTitle("Registration Failed!!!!");
                alertDialog.show();

                Toast.makeText(PropertyDetailsCom.this, "Garbar hai baba....",Toast.LENGTH_SHORT).show();
                Log.v(TAG, "Result ka kuch nahi pata");
                Log.v(TAG, result);
            }

        }
        ///////////////////////////////////
    }

//Background Worker Ends//

}
