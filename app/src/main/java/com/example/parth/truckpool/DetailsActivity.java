package com.example.parth.truckpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    private String truck_no,from,to;
    private String phone_no,rate,status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        final String userid = sharedPreferences2.getString("userid", "");

        Intent intent = getIntent();
        truck_no = intent.getStringExtra("truck_no");
        status = intent.getStringExtra("status");
        from = intent.getStringExtra("from_city");
        to = intent.getStringExtra("to_city");
        rate = intent.getStringExtra("rate");
        phone_no = intent.getStringExtra("phone_no");

        TextView tvrate = (TextView) findViewById(R.id.tvr);
        TextView tvstatus = (TextView) findViewById(R.id.tvs);
        final TextView tvMobile = (TextView) findViewById(R.id.tvMobile);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        tvWelcome.setText("Trip Details:");
        tvrate.setText(to);
        tvstatus.setText(from);
        tvMobile.setText(phone_no);



    }
}
