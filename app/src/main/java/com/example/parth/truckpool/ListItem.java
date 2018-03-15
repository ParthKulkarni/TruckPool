package com.example.parth.truckpool;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by parth on 20/9/17.
 */

public class ListItem implements Serializable{

    private String user_id;
    private String rate;
    private String name;
    private String From;
    private String To;
    private String truck_no;
    private String phone;
    private String status;

    public ListItem(String user_id, String rate,String From,String To,String truck_no,String phone,String status) {
        this.user_id = user_id;
        this.rate = rate;
        this.From = From;
        this.To = To;
        this.truck_no = truck_no;
        this.phone = phone;
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getTruck_no() { return truck_no; }
    public String getPhone() { return phone; }
    public String getRate() {
        return rate;
    }
    public String getStatus() {
        return status;
    }
    public String getFrom() {
        return From;
    }
    public String getTo() {
        return To;
    }
}
