package com.example.parth.truckpool;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import java.util.List;
import java.util.Locale;

import static android.R.attr.data;
import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 9/28/2017.
 */

public class Tab1TodaysTrips extends Fragment {
    static final int PICK_CONTACT_REQUEST = 1;
    String number;
    EditText etPhoneNo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1todaystrips, container, false);
        Intent intent = getActivity().getIntent();

        final int userid = Integer.parseInt(intent.getStringExtra("userid") );

        final Spinner spType = (Spinner) rootView.findViewById(R.id.spType);
        final EditText etTruckNo = (EditText) rootView.findViewById(R.id.etTruckNo);

        etPhoneNo = (EditText) rootView.findViewById(R.id.etPhoneNo);
        //final EditText etFrom = (EditText) rootView.findViewById(R.id.etFrom);
        //final EditText etTo = (EditText) rootView.findViewById(R.id.etTo);
        final EditText etStart = (EditText) rootView.findViewById(R.id.etStartDate);
        final EditText etEnd = (EditText) rootView.findViewById(R.id.etEndDate);
        final EditText etTotWeight = (EditText) rootView.findViewById(R.id.etTotWeight);
        final EditText etOccuWeight = (EditText) rootView.findViewById(R.id.etOccuWeight);
        final EditText etRate = (EditText) rootView.findViewById(R.id.etRate);
        final Button btCreateTrip = (Button) rootView.findViewById(R.id.btCreateTrip);
        final ProgressBar pb3 = (ProgressBar) rootView.findViewById(R.id.progressBar3);

        final Spinner spinner3 = (Spinner) rootView.findViewById(R.id.spinner3);

        ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fromcities));

        myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner3.setAdapter(myAdapter1);

        final Spinner spinner4 = (Spinner) rootView.findViewById(R.id.spinner4);


        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tocities));

        myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner4.setAdapter(myAdapter2);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getContext(),
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
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

                new DatePickerDialog(getContext(), date, myCalendar
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
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date1, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        etPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                System.out.println("Hello");
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);

            }
        });
        btCreateTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                String trucktype = spType.getItemAtPosition(spType.getSelectedItemPosition()).toString();

                final String truckno = etTruckNo.getText().toString();
                if(TextUtils.isEmpty(truckno)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter truck no!", Toast.LENGTH_SHORT).show();

                    return;
                }


                //jhasfjhaevkjvaskjfvhdsvhasvhasbvfjashfvh

                final String fromcity = spinner3.getItemAtPosition(spinner3.getSelectedItemPosition()).toString();
                if(TextUtils.isEmpty(fromcity)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter city!", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String tocity = spinner4.getItemAtPosition(spinner4.getSelectedItemPosition()).toString();
                if(TextUtils.isEmpty(tocity)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter destination city!", Toast.LENGTH_SHORT).show();

                    return;
                }

                if(fromcity.equalsIgnoreCase(tocity))
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Destination and source city should be different", Toast.LENGTH_SHORT).show();

                    return;
                }

                final String startdate = etStart.getText().toString();
                if(TextUtils.isEmpty(startdate)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter start date!", Toast.LENGTH_SHORT).show();

                    return;
                }



                final String enddate = etEnd.getText().toString();
                if(TextUtils.isEmpty(enddate)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter end date!", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(enddate.compareTo(startdate) < 0)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter Valid end date!", Toast.LENGTH_SHORT).show();
                    return;
                }
                final String tot_weight = etTotWeight.getText().toString();
                if(TextUtils.isEmpty(tot_weight)){
                    Toast.makeText(getActivity().getApplicationContext(), "Please enter total weight!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int totweight = Integer.parseInt(tot_weight);



                final String occ_weight = etOccuWeight.getText().toString();

                if(TextUtils.isEmpty(occ_weight)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter occupied weight!", Toast.LENGTH_SHORT).show();

                    return;
                }
                if(Integer.parseInt(occ_weight) > totweight)
                {
                    Toast.makeText(getActivity().getApplicationContext(), "occupied weight cannot be greater than total weight", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int occweight = Integer.parseInt(occ_weight);

                String rates = etRate.getText().toString();

                if(TextUtils.isEmpty(rates)) {

                    Toast.makeText(getActivity().getApplicationContext(), "Please enter rate!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final int rate = Integer.parseInt(rates);

                Toast.makeText(getActivity().getApplicationContext(), "Creating new Trip...", Toast.LENGTH_SHORT).show();
                pb3.setVisibility(view.VISIBLE);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                ArrayAdapter<String> myAdapter = new ArrayAdapter<>(getContext(),
                                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.truck_type));

                                myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spType.setAdapter(myAdapter);

                                ArrayAdapter<String> myAdapter1 = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.fromcities));

                                myAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(getContext(),
                                        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.tocities));

                                myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                spinner4.setAdapter(myAdapter2);

                                spinner3.setAdapter(myAdapter1);
                                etTruckNo.setText("");
                                etTruckNo.setText("");
                                etPhoneNo.setText("");
                                etStart.setText("");
                                etEnd.setText("");
                                etTotWeight.setText("");
                                etOccuWeight.setText("");
                                etRate.setText("");
                                /*Intent intent = new Intent(getContext(),UserAdminTabbed.class);

                                intent.putExtra("user_id", userid+"");
                                getContext().startActivity(intent);
                                getActivity().finish();*/
                                pb3.setVisibility(view.INVISIBLE);

                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


                CreateTripRequest createTripRequest = new CreateTripRequest(userid, trucktype, truckno, number
                        , fromcity, tocity, startdate, enddate, totweight,
                        occweight, rate,"IN PROCESS", responseListener);
                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(createTripRequest);
            }
        });


        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getActivity().getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                number = cursor.getString(column);
                System.out.println(number);
                etPhoneNo.setText(number);
                // Do something with the phone number...
            }
        }
    }
}
