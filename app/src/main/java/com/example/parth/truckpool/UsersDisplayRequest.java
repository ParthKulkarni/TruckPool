package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 9/28/2017.
 */

public class UsersDisplayRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/users_request.php";
    private Map<String, String> params;

    public UsersDisplayRequest(String user_id, Response.Listener<String> listener)
    {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("username", user_id);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
