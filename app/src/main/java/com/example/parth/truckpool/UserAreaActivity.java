package com.example.parth.truckpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        final TextView tvWelcomeMessage = (TextView) findViewById(R.id.tvWelcomeMessage);
        final Button btSignOut = (Button) findViewById(R.id.btSignOut);
        final Button btTrip = (Button) findViewById(R.id.btTrip);
        final Button btRequest = (Button) findViewById(R.id.btRequest);
        final Button btMyRequests = (Button) findViewById(R.id.btMyRequests);

        Intent intent = getIntent();

        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        final String userid = sharedPreferences2.getString("userid", "");
        final String name = sharedPreferences2.getString("name", "");

        final String company = intent.getStringExtra("company");
        final String city = intent.getStringExtra("city");
        final String contact = intent.getStringExtra("contact");
        String message = name + ", welcome to Truck Pooling";
        tvWelcomeMessage.setText(message);



        btTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAreaActivity.this, CreateTripActivity.class);
                intent.putExtra("userid", userid);
                UserAreaActivity.this.startActivity(intent);
                finish();
            }
        });

        btSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAreaActivity.this, MainActivity.class);
                UserAreaActivity.this.startActivity(intent);
                finish();
            }
        });


        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserAreaActivity.this, RequestTripActivity.class);
                intent.putExtra("userid", userid);
                UserAreaActivity.this.startActivity(intent);
                finish();
            }
        });

        //Change this afterwards!! -->

        btMyRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
