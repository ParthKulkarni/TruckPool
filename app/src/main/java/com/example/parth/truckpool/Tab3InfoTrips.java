package com.example.parth.truckpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.SearchManager;
import android.widget.EditText;
import android.widget.SearchView.OnQueryTextListener;

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

import static android.R.id.list;
import static com.example.parth.truckpool.R.id.recyclerView;

/**
 * Created by lenovo on 9/28/2017.
 */

public class Tab3InfoTrips extends Fragment{
    private RecyclerView mRecyclerView;
    public EditText search;
    private String userid;
    public SimpleAdapter mAdapter;
    private List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3infotrips, container, false);
        SharedPreferences sharedPreferences2= getActivity().getSharedPreferences("userid", Context.MODE_PRIVATE);
        userid = sharedPreferences2.getString("userid", "");
        search = (EditText) rootView.findViewById( R.id.search);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        countryList();
        mAdapter = new SimpleAdapter(list,getContext());
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapter);
        addTextListener();
        System.out.println("Mila");
        return rootView;
    }
    public void countryList(){

        /*list.add("Afghanistan");
        list.add("Albania");
        list.add("Algeria");
        list.add("Bangladesh");
        list.add("Belarus");
        list.add("Canada");
        list.add("Cape Verde");
        list.add("Central African Republic");
        list.add("Denmark");
        list.add("Dominican Republic");
        list.add("Egypt");
        list.add("France");
        list.add("Germany");
        list.add("Hong Kong");
        list.add("India");
        list.add("Iceland");*/
        list = new ArrayList<String>();
        final String url = "https://bobtail-chapter.000webhostapp.com/admins_request.php";

        final RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        System.out.println("jjj");

                        try{

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
                                    ListItem listItem = new ListItem(trip_id, rate,from_city,to_city,truck_no,ph_no,status);
                                    list.add(truck_no);

                                }
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
    public void addTextListener(){

        search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();



                final List<String> filteredList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {

                    final String text = list.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(list.get(i));
                    }
                }
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                mAdapter = new SimpleAdapter(filteredList, getContext());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }
}
