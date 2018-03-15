package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parth on 26/9/17.
 */

public class TripConfirm extends StringRequest {

    private static final String CONFIRM_TRIP_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/accept.php";
    private Map<String, String> params;

    public TripConfirm(int userid, int tripid, int weight, Response.Listener<String> listener)

    {
        super(Method.POST, CONFIRM_TRIP_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("userid", userid + "");
        params.put("tripid", tripid + "");
        params.put("weight", weight + "");


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
