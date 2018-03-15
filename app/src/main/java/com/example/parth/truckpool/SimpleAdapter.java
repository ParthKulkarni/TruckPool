package com.example.parth.truckpool;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by lenovo on 9/30/2017.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.MyViewHolder> {

    private List<String> list_item ;
    private Context context;


    public SimpleAdapter(List<String> list, Context context) {

        this.list_item = list;
        this.context = context;
    }
    public SimpleAdapter(Context context)
    {
        this.context = context;
    }
    // Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
    @Override
    public SimpleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a layout
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item4, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    public void clear() {
        list_item.clear(); //clear list
    }
    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, final int position ) {


        viewHolder.country_name.setText(list_item.get(position));
        System.out.println(list_item.get(position).toString());

        viewHolder.country_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Toast.makeText(context, list_item.get(position),
                        Toast.LENGTH_LONG).show();*/
                EditText searchone = (EditText) ((Activity)context).findViewById(R.id.search);
                searchone.setText(list_item.get(position).toString());
                String truck_no = list_item.get(position).toString();
                Intent intent =new Intent(context,StatusUpdateActivity.class);
                intent.putExtra("truck_no", truck_no);
                context.startActivity(intent);
            }
        });

    }

    // initializes textview in this class
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView country_name;

        public MyViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            country_name = (TextView) itemLayoutView.findViewById(R.id.country_name);

        }
    }

    //Returns the total number of items in the data set hold by the adapter.
    @Override
    public int getItemCount() {
        return list_item.size();
    }

}
