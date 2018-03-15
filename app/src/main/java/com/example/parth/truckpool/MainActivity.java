package com.example.parth.truckpool;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;



public class MainActivity extends AppCompatActivity{

    EditText etUser;
    EditText etPassword;
    Button btLogin;
    TextView tvRegister;
    ProgressBar pb;



    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etUser = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPass);
        etPassword.setTypeface(Typeface.DEFAULT);
        etPassword.setTransformationMethod(new PasswordTransformationMethod());
        btLogin = (Button) findViewById(R.id.btLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegister);
        tvRegister.setPaintFlags(tvRegister.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvRegister.setText("Register here");
        pb = (ProgressBar) findViewById(R.id.progressBar);

        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        int hasLoggedIn = sharedPreferences2.getInt("hasLoggedIn", 0);
        String company = sharedPreferences2.getString("company", "");
        String city = sharedPreferences2.getString("city", "");
        String contact = sharedPreferences2.getString("contact", "");
        String name = sharedPreferences2.getString("name", "");
        String userid = sharedPreferences2.getString("userid", "");

        if(hasLoggedIn == 1){

            if(company.equals("Customer")) {
                Intent intent = new Intent(MainActivity.this, TabbedUser.class);
                intent.putExtra("userid", userid);
                intent.putExtra("name", name);
                intent.putExtra("company", company);
                intent.putExtra("city", city);
                intent.putExtra("contact", contact);

                pb.setVisibility(View.INVISIBLE);
                MainActivity.this.startActivity(intent);
                finish();
            }
            else
            {
                Intent intent = new Intent(MainActivity.this, UserAdminTabbed.class);
                intent.putExtra("userid", userid);
                intent.putExtra("name", name);
                intent.putExtra("company", company);
                intent.putExtra("city", city);
                intent.putExtra("contact", contact);
                pb.setVisibility(View.INVISIBLE);
                MainActivity.this.startActivity(intent);
                finish();
            }

        }

        else{
            tvRegister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                    MainActivity.this.startActivity(registerIntent);


                }
            });


            btLogin.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    final String username = etUser.getText().toString();
                    final String password = etPassword.getText().toString();

                    if(username.equals("")){
                        Toast.makeText(getApplicationContext(), "Please enter email id!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
                        Toast.makeText(getApplicationContext(), "Enter valid email id!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SharedPreferences sharedPreferences1 = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("username", etUser.getText().toString());
                    editor.putString("password", etPassword.getText().toString());

                    editor.commit();

                    pb.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();

                    Response.Listener<String> responseListener = new Response.Listener<String>(){
                        @Override
                        public void onResponse(String response) {

                            try{
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){


                                    String userid = jsonResponse.getString("userid");
                                    String name = jsonResponse.getString("name");
                                    String company = jsonResponse.getString("company");
                                    String city = jsonResponse.getString("city");
                                    String contact = jsonResponse.getString("contact");

                                    SharedPreferences sharedPreferences1 = getSharedPreferences("userid", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                                    editor.putString("userid", userid);
                                    editor.putString("name", name);
                                    editor.putString("company", company);
                                    editor.putString("city", city);
                                    editor.putString("contact", contact);
                                    editor.putString("userid", userid);
                                    editor.putInt("hasLoggedIn", 1);

                                    editor.commit();
                                    if(company.equals("Customer")) {
                                        Intent intent = new Intent(MainActivity.this, TabbedUser.class);
                                        intent.putExtra("userid", userid);
                                        intent.putExtra("name", name);
                                        intent.putExtra("company", company);
                                        intent.putExtra("city", city);
                                        intent.putExtra("contact", contact);

                                        pb.setVisibility(View.INVISIBLE);
                                        MainActivity.this.startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Intent intent = new Intent(MainActivity.this, UserAdminTabbed.class);
                                        intent.putExtra("userid", userid);
                                        intent.putExtra("name", name);
                                        intent.putExtra("company", company);
                                        intent.putExtra("city", city);
                                        intent.putExtra("contact", contact);
                                        pb.setVisibility(View.INVISIBLE);
                                        MainActivity.this.startActivity(intent);
                                        finish();
                                    }

                                }else{
                                    pb.setVisibility(View.INVISIBLE);
                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                    builder.setMessage("Login failed")
                                            .setNegativeButton("Retry", null)
                                            .create()
                                            .show();
                                }

                            }catch(JSONException e){
                                e.printStackTrace();
                            }

                        }
                    };

                    LoginRequest loginRequest = new LoginRequest(username, password, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                    queue.add(loginRequest);
                }
            });
        }



    }


}
