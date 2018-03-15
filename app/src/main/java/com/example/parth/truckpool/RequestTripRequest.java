package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parth on 17/9/17.
 */

public class RequestTripRequest extends StringRequest {

    private static final String TRIP_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/trip_request.php";
    private Map<String, String> params;

    public RequestTripRequest(String userid, String fromcity, String tocity, String startdate,
                              String enddate, int weight, Response.Listener<String> listener)
    {
        super(Method.POST, TRIP_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("userid", userid);
        params.put("fromcity", fromcity);
        params.put("tocity", tocity);
        params.put("startdate", startdate);
        params.put("enddate", enddate);
        params.put("weight", weight + "");

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
