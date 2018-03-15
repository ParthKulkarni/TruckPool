package com.example.parth.truckpool;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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

import static com.example.parth.truckpool.Tab1TodaysTrips.PICK_CONTACT_REQUEST;

public class UsertripInfo extends AppCompatActivity {
    private String truck_no,from,to;
    private String phone_no,rate,status,number;
    EditText etUpdateContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usertrip_info);

        SharedPreferences sharedPreferences2= getSharedPreferences("userid", Context.MODE_PRIVATE);
        final String userid = sharedPreferences2.getString("userid", "");

        Intent intent = getIntent();
        truck_no = intent.getStringExtra("truck_no");
        status = intent.getStringExtra("status");
        from = intent.getStringExtra("from_city");
        to = intent.getStringExtra("to_city");
        rate = intent.getStringExtra("rate");
        phone_no = intent.getStringExtra("phone_no");
        final ProgressBar pb5 = (ProgressBar) findViewById(R.id.progressBar5);

        etUpdateContact = (EditText) findViewById(R.id.etUpdateContact);
        Button btContact = (Button) findViewById(R.id.btContact);
        final Button btYes = (Button) findViewById(R.id.btYes);

        TextView tvrate = (TextView) findViewById(R.id.tvr);
        TextView tvstatus = (TextView) findViewById(R.id.tvs);
        final TextView tvMobile = (TextView) findViewById(R.id.tvMobile);
        TextView tvWelcome = (TextView) findViewById(R.id.tvWelcome);

        tvWelcome.setText("Trip Details  ");
        tvWelcome.setPaintFlags(tvWelcome.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvWelcome.setTextSize(25);
        tvrate.setText(rate);
        tvstatus.setText(status);
        tvMobile.setText(phone_no);

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etUpdateContact.setVisibility(View.VISIBLE);
                btYes.setVisibility(View.VISIBLE);

            }
        });

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:"+phone_no));
                startActivity(callIntent);

            }
        });

        etUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                etUpdateContact.setText(phone_no);
                System.out.println("Hello");
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                number = cursor.getString(column);
                System.out.println(number);
                // Do something with the phone number...
            }
        }
    }
}
