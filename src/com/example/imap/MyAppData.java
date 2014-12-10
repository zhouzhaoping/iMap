package com.example.imap;

import android.app.Application; 
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//参照http://oyeal.iteye.com/blog/941183
public class MyAppData extends Application{ 

    private int mylabel ;   
    private int mylabel2 ;   
    public int getLabel(){ 
        return mylabel; 
    }    
    public void setLabel(int s){ 
        this.mylabel = s; 
    } 
    public int getLabel2(){ 
        return mylabel2; 
    }    
    public void setLabel2(int s){ 
        this.mylabel2 = s; 
    } 
    @Override 
    public void onCreate() { 
        // TODO Auto-generated method stub 
        super.onCreate(); 
        setLabel(-1); //初始化全局变量 
        setLabel2(-1);
    }    
    
    
} 
