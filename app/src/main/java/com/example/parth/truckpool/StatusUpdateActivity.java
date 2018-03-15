package com.example.parth.truckpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class StatusUpdateActivity extends AppCompatActivity {


    private String status;
    private String truck_no;
    RadioGroup rg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_update);
        rg=(RadioGroup) findViewById(R.id.radioGroup);
        final Button btUpdate = (Button) findViewById(R.id.btUpdate);

        final ProgressBar pb5 =(ProgressBar)findViewById(R.id.progressBar5);

        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        final String userid = sharedPreferences2.getString("userid", "");

        Intent intent = getIntent();
        truck_no = intent.getStringExtra("truck_no");

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb5.setVisibility(View.VISIBLE);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{

                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1)   );
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                System.out.println("kxndd");
                                pb5.setVisibility(View.INVISIBLE);
                                Intent intent = new Intent(StatusUpdateActivity.this, UserAdminTabbed.class);
                                intent.putExtra("user_id",userid);
                                //StatusUpdateActivity.this.startActivity(intent);
                                Toast.makeText(StatusUpdateActivity.this, "Status updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(StatusUpdateActivity.this);
                                builder.setMessage("Update failed!")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                StatusUpdateRequest statusUpdateRequest = new StatusUpdateRequest(Integer.parseInt(userid), truck_no, status, responseListener);

                RequestQueue queue = Volley.newRequestQueue(StatusUpdateActivity.this);
                queue.add(statusUpdateRequest);
            }
        });


    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_complete:
                if (checked) {
                    status = "Complete";
                    System.out.println("shdsbv");
                    break;
                }
            case R.id.radio_dispatched:
                if (checked) {
                    status = "Dispatched";
                    System.out.println("2");
                    break;
                }
            case R.id.radio_incomplete:
                if (checked) {
                    status = "Incomplete";
                    System.out.println("3");
                    break;
                }
        }
    }
}
