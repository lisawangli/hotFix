package com.example.wangli.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class HelperOne {

    public void haha(Context context){
        int i = 10/0;
        Toast.makeText(context,"i======"+i,Toast.LENGTH_LONG).show();
    }
}
