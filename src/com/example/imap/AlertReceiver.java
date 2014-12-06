package com.example.imap;

import com.baidu.navisdk.ui.routeguide.subview.i;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class AlertReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// 
		boolean isEnter = intent.getBooleanExtra(
			LocationManager.KEY_PROXIMITY_ENTERING, false);
		Bundle bundle= intent.getExtras(); 
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
			}
			else
			{
				// 显示提示信息
				Toast.makeText(context
					, "无"
					, Toast.LENGTH_LONG)
					.show();		
			}
		}
		
	}
}
