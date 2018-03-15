package com.example.parth.truckpool;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;

/**
 * Created by parth on 20/9/17.
 */

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    private int user_id;
    private String From;
    private int weight;

    public MyAdapter2(List<ListItem> listItems, Context context, int user_id){
        this.context = context;
        this.listItems = listItems;
        this.user_id = user_id;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item2, parent, false);

        return new ViewHolder(v, context, listItems);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ListItem listItem = listItems.get(position);

        holder.tvUser_id.setText("Truck no: "+listItem.getTruck_no());
        holder.tvRate.setText("Phone No: "+listItem.getPhone());

    }

    @Override
    public int getItemCount() {

        return listItems.size();

    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvUser_id;
        public TextView tvRate;
        public ProgressBar pb4;
        List<ListItem> listItems = new ArrayList<>();
        Context context;

        public ViewHolder(final View itemView, final Context context, final List<ListItem> listItems) {

            super(itemView);
            this.listItems = listItems;
            this.context = context;

            tvUser_id = (TextView) itemView.findViewById(R.id.tv_1);
            tvRate = (TextView) itemView.findViewById(R.id.tv_2);
            pb4 = (ProgressBar) itemView.findViewById(R.id.progressBar4);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    final ListItem listItem = listItems.get(pos);
                    int trip_id = Integer.parseInt(listItem.getUser_id());
                    String rate = listItem.getRate();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1)   );
                                boolean success = jsonResponse.getBoolean("success");

                                if(success){
                                    Intent intent1 = new Intent(context,DetailsActivity.class);
                                    intent1.putExtra("truck_no", listItem.getTruck_no().toString());
                                    intent1.putExtra("status", listItem.getStatus().toString());
                                    intent1.putExtra("from_city", listItem.getFrom().toString());
                                    intent1.putExtra("to_city", listItem.getTo().toString());
                                    intent1.putExtra("rate", listItem.getRate().toString());
                                    intent1.putExtra("phone_no", listItem.getPhone().toString());
                                    context.startActivity(intent1);
                                   // Toast.makeText(context,"From: "+ listItem.getFrom() +"\nTo: "+ listItem.getTo()  + "\nStatus: "+ listItem.getStatus(), Toast.LENGTH_LONG).show();

                                }
                                else{
                                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

                    AdminDisplayRequest tripConfirm = new AdminDisplayRequest(user_id+"", responseListener);
                    System.out.println("Shanti mili");
                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(tripConfirm);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();

                    final ListItem listItem = listItems.get(pos);
                    int trip_id = Integer.parseInt(listItem.getUser_id());
                    String rate = listItem.getRate();

                    AlertDialog.Builder alert = new AlertDialog.Builder(
                            context);
                    alert.setTitle("Alert!!");
                    alert.setMessage("Are you sure to delete record");

                    alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //do your work here


                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try{
                                        JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1)   );
                                        boolean success = jsonResponse.getBoolean("success");

                                        if(success){
                                            listItems.remove(getAdapterPosition());
                                            notifyDataSetChanged();
                                            Toast.makeText(context,"Deleted Successfully", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            DelteKaro tripConfirm = new DelteKaro(listItem.getTruck_no(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(context);
                            queue.add(tripConfirm);
                            dialog.dismiss();

                        }
                    });
                    alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    alert.show();

                    System.out.println(listItem.getTruck_no());
                    return false;
                }
            });


        }

        @Override
        public void onClick(View view) {

        }
    }
}
