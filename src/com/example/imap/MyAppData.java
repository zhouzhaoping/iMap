package com.example.imap;

import android.app.Application; 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//参照http://oyeal.iteye.com/blog/941183
public class MyAppData extends Application{ 

    private int mylabel ;     
    public int getLabel(){ 
        return mylabel; 
    }    
    public void setLabel(int s){ 
        this.mylabel = s; 
    } 

    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        setLabel(1); //初始化全局变量        
    }    
    
    
} 
