package com.example.parth.truckpool;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass);
        Intent intent = getIntent();

        final int userid = Integer.parseInt(intent.getStringExtra("userid") );
        Intent intent1 = new Intent(PassActivity.this, UserAdminTabbed.class);
        //PassActivity.this.startActivity(intent1);
    }
}
