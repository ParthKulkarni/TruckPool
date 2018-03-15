package com.example.parth.truckpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RequestListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle bundle = getIntent().getExtras();

        listItems= (ArrayList<ListItem>) bundle.getSerializable("Items");
        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        int userid = Integer.parseInt(sharedPreferences2.getString("userid", ""));
        System.out.println(userid);

        int weight = Integer.parseInt(getIntent().getStringExtra("weight"));
        System.out.println(userid);
        adapter = new MyAdapter(listItems, this, userid, weight);

        recyclerView.setAdapter(adapter);

    }
}
