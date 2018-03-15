package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by parth on 1/10/17.
 */

public class UpdateContactRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/contact_update.php";
    private Map<String, String> params;

    public UpdateContactRequest(String user_id, String new_no, Response.Listener<String> listener)
    {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("userid", user_id);
        params.put("new_no", new_no);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
