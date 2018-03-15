package com.example.parth.truckpool;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by parth on 3/10/17.
 */

public class IntroManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public IntroManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences("first", 0);
        editor = pref.edit();
    }

    public void setFirst(boolean isFirst){
        editor.putBoolean("Check", isFirst);
        editor.commit();
    }

    public boolean check(){
        return pref.getBoolean("Check", true);
    }

}
