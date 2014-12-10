package com.example.imap;

import com.baidu.navisdk.ui.routeguide.subview.i;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver
{
	 private MyAppData myAppData_listen; 
	@Override
	public void onReceive(Context context, Intent intent)
	{
		
		// 
		boolean isEnter = intent.getBooleanExtra(
			LocationManager.KEY_PROXIMITY_ENTERING, false);
		Bundle bundle= intent.getExtras(); 
		int walk_listen_onoff = bundle.getInt("walk_listen");
		if(walk_listen_onoff == 0) return ;
		//获取markers的个数
		for(int i = 0;i<10 ;i++)
		{
			String k = (String) bundle.getCharSequence(String.valueOf(i));
			if(isEnter)
			{
				//显示提示信息，进入语音
				Toast.makeText(context
					, "提示語音: "+k
					, Toast.LENGTH_LONG)
					.show();
				final Builder temp = new AlertDialog.Builder(null);
				temp.setTitle("biabia"); temp.setMessage("haaha");
				temp.create().show();
				//进入语音播放的activity或者service
			}
			else
			{
				// 显示提示信息
				//Toast.makeText(context
				////	, "无"
				//	, Toast.LENGTH_LONG)
				//	.show();		
			}
		}
		
	}
}
