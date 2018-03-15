package com.example.parth.truckpool;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by lenovo on 9/28/2017.
 */

public class Tab2 extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog pDialog;
    private List<ListItem> listItems;
    private SwipeRefreshLayout swipeRefreshLayout1;
    private String userid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        SharedPreferences sharedPreferences2= getActivity().getSharedPreferences("userid", Context.MODE_PRIVATE);
        userid = sharedPreferences2.getString("userid", "");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //Bundle bundle = getActivity().getIntent().getExtras();

        //RequestQueue queue = Volley.newRequestQueue(getContext());


        swipeRefreshLayout1 = rootView.findViewById(R.id.swipeRefreshLayout1);
        swipeRefreshLayout1.setOnRefreshListener(this);


        loadData();

        swipeRefreshLayout1.post(new Runnable() {
            @Override
            public void run() {

                swipeRefreshLayout1.setRefreshing(true);

                loadData();

            }
        });

        return rootView;
    }

    @Override
    public void onRefresh() {
        loadData();
        Toast.makeText(getContext(), "Trips updated", Toast.LENGTH_SHORT).show();
    }

    private void loadData(){

        swipeRefreshLayout1.setRefreshing(true);

        final String url = "https://bobtail-chapter.000webhostapp.com/users_request.php";
        final RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                        try{
                            //System.out.println("Bhai Party");
                            JSONObject jsonResponse = new JSONObject(response);
                            //Log.d("Response", response);
                            Log.d("JSONR", jsonResponse.toString());


                            boolean success = jsonResponse.getBoolean("success");

                            if(success)
                            {
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
                                    ListItem listItem = new ListItem(trip_id, rate,from_city,to_city,truck_no,ph_no ,status);
                                    listItems.add(listItem);

                                }
                                adapter = new MyAdapter3(listItems,getContext(),Integer.parseInt(userid));
                                recyclerView.setAdapter(adapter);
                                swipeRefreshLayout1.setRefreshing(false);


                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("username",userid);
                return params;
            }
        };
        queue.add(postRequest);
    }
}
