package com.example.parth.truckpool;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 9/28/2017.
 */

public class Tab3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3, container, false);

        SharedPreferences sharedPreferences2= getActivity().getSharedPreferences("userid", Context.MODE_PRIVATE);
        String company = sharedPreferences2.getString("company", "");
        String city = sharedPreferences2.getString("city", "");
        String contact = sharedPreferences2.getString("contact", "");
        String name = sharedPreferences2.getString("name", "");
        final String user_id = sharedPreferences2.getString("userid", "");

        TextView tvNaam = (TextView) rootView.findViewById(R.id.tvs);
        TextView tvSheher = (TextView) rootView.findViewById(R.id.tvr);
        final TextView tvMobile = (TextView) rootView.findViewById(R.id.tvMobile);
        TextView tvWelcome = (TextView) rootView.findViewById(R.id.tvWelcome);

        final ProgressBar pb5 = (ProgressBar) rootView.findViewById(R.id.progressBar5);

        final EditText etUpdateContact = (EditText) rootView.findViewById(R.id.etUpdateContact);
        Button btContact = (Button) rootView.findViewById(R.id.btContact);
        final Button btYes = (Button) rootView.findViewById(R.id.btYes);

        tvWelcome.setText("Welcome, "+name);
        tvWelcome.setTextSize(25);
        tvWelcome.setPaintFlags(tvWelcome.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvNaam.setText(name);
        tvSheher.setText(city);
        tvMobile.setText(contact);

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUpdateContact.setVisibility(View.VISIBLE);
                btYes.setVisibility(View.VISIBLE);

            }
        });

        btYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String new_no = etUpdateContact.getText().toString();
                boolean flag = false;
                if(new_no.length()!=10 || new_no.charAt(0)<'7')
                {

                    Toast.makeText(getContext(), "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    for(int i=0;i<10;i++)
                    {
                        if(new_no.charAt(i)>'9' || new_no.charAt(i)<'0')
                        {
                            flag=true;
                            break;
                        }
                    }
                    if(flag==true)
                    {
                        Toast.makeText(getContext(), "Enter valid Phone Number", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                pb5.setVisibility(View.VISIBLE);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1)   );
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){

                                pb5.setVisibility(View.INVISIBLE);
                                etUpdateContact.setVisibility(View.INVISIBLE);
                                etUpdateContact.setText("");
                                btYes.setVisibility(View.INVISIBLE);
                                tvMobile.setText(new_no);
                                Toast.makeText(getContext(), "Contact no updated successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                pb5.setVisibility(View.INVISIBLE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Update failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                UpdateContactRequest registerRequest = new UpdateContactRequest(user_id, new_no, responseListener);

                RequestQueue queue = Volley.newRequestQueue(getContext());
                queue.add(registerRequest);

            }
        });

        return rootView;
    }
}
