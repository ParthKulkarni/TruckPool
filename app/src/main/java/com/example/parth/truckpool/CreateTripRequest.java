package com.example.parth.truckpool;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by parth on 16/9/17.
 */

public class CreateTripRequest extends StringRequest{

    private static final String CREATE_TRIP_REQUEST_URL = "https://bobtail-chapter.000webhostapp.com/trip_create.php";
    private Map<String, String> params;

    public CreateTripRequest(int userid,String trucktype, String truckno, String phno,
                             String fromcity, String tocity, String startdate,
                             String enddate, int totweight, int occweight, int rate,String status,
                             Response.Listener<String> listener)
    {
        super(Method.POST, CREATE_TRIP_REQUEST_URL, listener, null );
        params = new HashMap<>();
        params.put("userid", userid + "");
        params.put("trucktype", trucktype);
        params.put("truckno", truckno);
        params.put("phno", phno);
        params.put("fromcity", fromcity);
        params.put("tocity", tocity);
        params.put("startdate", startdate);
        params.put("enddate", enddate);
        params.put("totweight", totweight + "");
        params.put("occweight", occweight + "");
        params.put("rate", rate + "");
        params.put("status",status);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
