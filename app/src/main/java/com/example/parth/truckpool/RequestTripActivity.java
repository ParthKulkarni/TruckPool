package com.example.parth.truckpool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class RequestTripActivity extends AppCompatActivity {


    ArrayList<HashMap<String, String>> tripsList;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_trip);

        Intent intent = getIntent();
        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        userid = sharedPreferences2.getString("userid", "");
        System.out.println(userid);


        final Spinner spType1 = (Spinner) findViewById(R.id.spType);
        final EditText etFrom = (EditText) findViewById(R.id.etFrom);
        final EditText etTo = (EditText) findViewById(R.id.etTo);
        final EditText etStart = (EditText) findViewById(R.id.etStart);
        final EditText etEnd = (EditText) findViewById(R.id.etEnd);
        final EditText etWeight = (EditText) findViewById(R.id.etWeight);
        final Button btRequest = (Button) findViewById(R.id.btRequest);
        final ProgressBar pb3 = (ProgressBar) findViewById(R.id.progressBar3);

        tripsList = new ArrayList<HashMap<String, String>>();

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RequestTripActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.truck_type));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType1.setAdapter(myAdapter);

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

                new DatePickerDialog(RequestTripActivity.this, date, myCalendar
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

                new DatePickerDialog(RequestTripActivity.this, date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

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

                final String weights = etWeight.getText().toString();
                if(TextUtils.isEmpty(weights)){
                    Toast.makeText(getApplicationContext(), "Please enter weight!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int weight = Integer.parseInt(weights);

                pb3.setVisibility(view.VISIBLE);

                Response.Listener<String> responseListener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            Log.d("JSONR", jsonResponse.toString());
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                JSONArray trips;
                                trips = jsonResponse.getJSONArray("trips");

                                List<ListItem> listItems = new ArrayList<>();

                                for(int i=0; i < trips.length(); i++){
                                    JSONObject c = trips.getJSONObject(i);
                                    String trip_id = c.getString("trip_id");
                                    String user_id = c.getString("user_id");
                                    String truck_type = c.getString("truck_type");
                                    String truck_no = c.getString("truck_no");
                                    String ph_no = c.getString("ph_no");
                                    String from_city = c.getString("from_city");
                                    String to_city = c.getString("to_city");
                                    String start_date = c.getString("start_date");
                                    String end_date = c.getString("end_date");
                                    String tot_weight = c.getString("tot_weight");
                                    String occ_weight = c.getString("occ_weight");
                                    String rate = c.getString("rate");
                                    String status = c.getString("status");


                                    ListItem listItem = new ListItem(trip_id, rate,from_city,to_city,truck_no,ph_no,status);
                                    listItems.add(listItem);

                                }



                                Intent intent = new Intent(RequestTripActivity.this, RequestListActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("Items", (Serializable) listItems);
                                intent.putExtras(bundle);

                                //intent.putExtra("user_id", userid);
                                intent.putExtra("weight", weights);
                                RequestTripActivity.this.startActivity(intent);
                                pb3.setVisibility(view.INVISIBLE);
                                finish();

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(RequestTripActivity.this);
                                builder.setMessage("No trips found!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                RequestTripRequest requestTripRequest = new RequestTripRequest(userid, fromcity, tocity,
                        startdate, enddate, weight, responseListener);

                RequestQueue queue = Volley.newRequestQueue(RequestTripActivity.this);
                queue.add(requestTripRequest);

            }
        });

    }
}
