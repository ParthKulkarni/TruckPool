package com.example.parth.truckpool;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by parth on 10/9/17.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/new_connection.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, String password, String company, String city, String contact, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("company", company);
        params.put("city", city);
        params.put("contact", contact);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
