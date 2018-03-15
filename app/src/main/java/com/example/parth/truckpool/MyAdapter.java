package com.example.parth.truckpool;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by parth on 20/9/17.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;
    private int user_id;
    NotificationCompat.Builder notification;
    private static final int uniqueID = 45612;
    private int weight;

    public MyAdapter(List<ListItem> listItems, Context context, int user_id, int weight) {
        this.listItems = listItems;
        this.context = context;
        this.user_id = user_id;
        this.weight = weight;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);

        return new ViewHolder(v, context, listItems);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.tvUser_id.setText("Company id: "+listItem.getUser_id());
        holder.tvRate.setText("Rate per kg: "+listItem.getRate());

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
                                Uri soundUri = RingtoneManager
                                        .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                notification = new NotificationCompat.Builder(context);
                                notification.setAutoCancel(true);

                                if(success){

                                    Toast.makeText(context, "Trip booked successfully for contact number of driver please see notification!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context , TabbedUser.class);
                                    notification.setSmallIcon(R.mipmap.ic_launcher);
                                    notification.setTicker("This is ticker");
                                    notification.setWhen(System.currentTimeMillis());
                                    notification.setContentTitle("Trip Booked");
                                    notification.setContentText("Driver's contact number:-"+listItem.getPhone());
                                    PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
                                    notification.setContentIntent(pendingIntent);
                                    notification.setSound(soundUri);

                                    //Build notification and issuing it
                                    NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                                    nm.notify(uniqueID,notification.build());
                                    context.startActivity(intent);
                                    ((Activity)context).finish();

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

                    TripConfirm tripConfirm = new TripConfirm(user_id, trip_id,weight, responseListener);

                    RequestQueue queue = Volley.newRequestQueue(context);
                    queue.add(tripConfirm);

                }
            });


        }

        @Override
        public void onClick(View view) {

        }
    }
}
