package com.example.imap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// 
		boolean isEnter = intent.getBooleanExtra(
			LocationManager.KEY_PROXIMITY_ENTERING, false);
		if(isEnter)
		{
			//显示提示信息，進入語音
			Toast.makeText(context
				, "提示語音"
				, Toast.LENGTH_LONG)
				.show();
		}
		else
		{
			// 显示提示信息
			Toast.makeText(context
				, "無"
				, Toast.LENGTH_LONG)
				.show();		
		}
	}
}
