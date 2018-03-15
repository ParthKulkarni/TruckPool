package com.example.parth.truckpool;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends Activity {

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN =  "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[*!^@&£#+/$%]).{6,20})";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setBackgroundDrawableResource(R.drawable.backg1) ;
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etUser = (EditText) findViewById(R.id.etUser);
        final EditText etPassword = (EditText) findViewById(R.id.etPass);
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        final EditText etPassword1 = (EditText) findViewById(R.id.etPass1);
        etPassword1.setTypeface(Typeface.DEFAULT);
        etPassword1.setTransformationMethod(new PasswordTransformationMethod());
        final EditText etCity = (EditText) findViewById(R.id.etCity);
        final Button btSubmit = (Button) findViewById(R.id.btSubmit);
        final ProgressBar pb2 = (ProgressBar) findViewById(R.id.progressBar2);
        final EditText etContact = (EditText) findViewById(R.id.etContact);
        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.choice));

        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(myAdapter);

        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String company = spinner1.getItemAtPosition(spinner1.getSelectedItemPosition()).toString();


                final String name = etName.getText().toString();
                if(TextUtils.isEmpty(name)) {

                   Toast.makeText(getApplicationContext(), "Please enter name!", Toast.LENGTH_SHORT).show();

                    return;
                }
                final String username = etUser.getText().toString();
                if(TextUtils.isEmpty(username)) {

                    Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){

                    Toast.makeText(getApplicationContext(), "Enter valid email id!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String password = etPassword.getText().toString();
                if(TextUtils.isEmpty(password)) {

                    Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!isValidPassword(password)){

                    Toast.makeText(getApplicationContext(), "Password must contain minimum 6 characters, at least 1 uppercase alphabet, at least 1 lowercase character, 1 number and 1 special character", Toast.LENGTH_LONG).show();
                    return;
                }

                final String password1 = etPassword1.getText().toString();
                if(!password.equals(password1)){

                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                    return;
                }


                final String city = etCity.getText().toString();
                if(TextUtils.isEmpty(city)) {

                    Toast.makeText(getApplicationContext(), "Please enter city!", Toast.LENGTH_SHORT).show();
                    return;
                }

                final String contact = etContact.getText().toString();
                if(TextUtils.isEmpty(contact) || contact.length()<10 || contact.length()>10 || contact.charAt(0)<'7') {

                    Toast.makeText(getApplicationContext(), "Please enter contact no!", Toast.LENGTH_SHORT).show();
                    return;
                }

                pb2.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Registering to TruckPool", Toast.LENGTH_SHORT).show();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1)   );
                            boolean success = jsonResponse.getBoolean("success");

                            if(success){
                                etName.setText("");
                                etUser.setText("");
                                etPassword.setText("");
                                etCity.setText("");
                                etContact.setText("");
                                pb2.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                    finish();
                            }
                            else{
                                pb2.setVisibility(View.INVISIBLE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                builder.setMessage("Register failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }

                        }catch(JSONException e){
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, password, company, city, contact, responseListener);

                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });

    }
}
