package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 9/30/2017.
 */

public class StatusUpdateRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/update_status.php";
    private Map<String, String> params;

    public StatusUpdateRequest(int userid, String truck_no, String status, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("userid",userid+"");
        params.put("truck_no", truck_no);
        params.put("status",status);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}