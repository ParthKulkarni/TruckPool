package com.example.parth.truckpool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CreateTripActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);
        Intent intent = getIntent();

        final int userid = Integer.parseInt(intent.getStringExtra("userid") );

        final Spinner spType = (Spinner) findViewById(R.id.spType);
        final EditText etTruckNo = (EditText) findViewById(R.id.etTruckNo);
        final EditText etPhoneNo = (EditText) findViewById(R.id.etPhoneNo);
        final EditText etFrom = (EditText) findViewById(R.id.etFrom);
        final EditText etTo = (EditText) findViewById(R.id.etTo);
        final EditText etStart = (EditText) findViewById(R.id.etStartDate);
        final EditText etEnd = (EditText) findViewById(R.id.etEndDate);
        final EditText etTotWeight = (EditText) findViewById(R.id.etTotWeight);
        final EditText etOccuWeight = (EditText) findViewById(R.id.etOccuWeight);
        final EditText etRate = (EditText) findViewById(R.id.etRate);
        final Button btCreateTrip = (Button) findViewById(R.id.btCreateTrip);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(CreateTripActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.truck_type));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(myAdapter);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etStart.setText(sdf.format(myCalendar.getTime()));
            }

        };

        etStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                new DatePickerDialog(CreateTripActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final Calendar myCalendar1 = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                etEnd.setText(sdf.format(myCalendar1.getTime()));
            }

        };

        etEnd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (v != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                // TODO Auto-generated method stub
                new DatePickerDialog(CreateTripActivity.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String trucktype = spType.getItemAtPosition(spType.getSelectedItemPosition()).toString();

                final String truckno = etTruckNo.getText().toString();
                if(TextUtils.isEmpty(truckno)) {

                    Toast.makeText(getApplicationContext(), "Please enter truck no!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String phno = etPhoneNo.getText().toString();
                if(TextUtils.isEmpty(phno)) {

                    Toast.makeText(getApplicationContext(), "Please enter phone no!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String fromcity = etFrom.getText().toString();
                if(TextUtils.isEmpty(fromcity)) {

                    Toast.makeText(getApplicationContext(), "Please enter city!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String tocity = etTo.getText().toString();
                if(TextUtils.isEmpty(tocity)) {

                    Toast.makeText(getApplicationContext(), "Please enter destination city!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String startdate = etStart.getText().toString();
                if(TextUtils.isEmpty(startdate)) {

                    Toast.makeText(getApplicationContext(), "Please enter start date!", Toast.LENGTH_SHORT).show();

                    return;
                }



                final String enddate = etEnd.getText().toString();
                if(TextUtils.isEmpty(enddate)) {

                    Toast.makeText(getApplicationContext(), "Please enter end date!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String tot_weight = etTotWeight.getText().toString();
                if(TextUtils.isEmpty(tot_weight)){
                    Toast.makeText(getApplicationContext(), "Please enter total weight!", Toast.LENGTH_SHORT).show();

                    return;
                }
                    final int totweight = Integer.parseInt(tot_weight);



                final String occ_weight = etOccuWeight.getText().toString();

                if(TextUtils.isEmpty(occ_weight)) {

                    Toast.makeText(getApplicationContext(), "Please enter occupied weight!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int occweight = Integer.parseInt(occ_weight);

                String rates = etRate.getText().toString();

                if(TextUtils.isEmpty(rates)) {

                    Toast.makeText(getApplicationContext(), "Please enter rate!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int rate = Integer.parseInt(rates);

                Toast.makeText(getApplicationContext(), "Creating new Trip...", Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                etTruckNo.setText("");
                                etTruckNo.setText("");
                                etPhoneNo.setText("");
                                etFrom.setText("");
                                etTo.setText("");
                                etStart.setText("");
                                etEnd.setText("");
                                etTotWeight.setText("");
                                etOccuWeight.setText("");
                                etRate.setText("");

                                Intent intent = new Intent(CreateTripActivity.this, UserAreaActivity.class);
                                CreateTripActivity.this.startActivity(intent);
                                finish();

                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateTripActivity.this);
                                builder.setMessage("Could not create new trip")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                };


                CreateTripRequest createTripRequest = new CreateTripRequest(userid, trucktype, truckno, phno
                        , fromcity, tocity, startdate, enddate, totweight,
                        occweight, rate,"INPROCESS", responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateTripActivity.this);
                queue.add(createTripRequest);
            }
        });

    }
}
