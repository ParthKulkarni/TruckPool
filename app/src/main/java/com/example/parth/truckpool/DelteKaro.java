package com.example.parth.truckpool;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 10/10/2017.
 */

public class DelteKaro extends StringRequest {
    private static final String REGISTER_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/delete.php";
    private Map<String, String> params;

    public DelteKaro(String truck_no, Response.Listener<String> listener)
    {
        super(Request.Method.POST, REGISTER_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("truck_no",truck_no);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
