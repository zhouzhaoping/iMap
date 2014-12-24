package imap.main;

import imap.nettools.NetThread;
import imap.nettools.Variable;
import imap.nettools.ViewSpotData;
import imap.setting.SettingActivity;
import imap.storage.VoiceCache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy;

import org.json.JSONException;

import com.baidu.platform.comapi.map.m;
import com.baidu.platform.comapi.map.w;

import android.R.integer;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

public class WalkListen extends Service {

	double mCurrentLantitude;
	double mCurrentLongitude;
	double preLantitude;
	double preLongitude;
	int walk_listen;
	//List<ViewSpotData> viewspotlist;
	/** MediaPlayer */
	private static MediaPlayer media;

	private static boolean playState = false; // 播放状态

	public double radius = 25;//定位播放距离

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		
			MyAppData mylatlong = (MyAppData) getApplication();
			//viewspotlist = mylatlong.getviewlist();
			
			mCurrentLantitude = mylatlong.getmylabellat();
			mCurrentLongitude = mylatlong.getmylabellong();
			preLantitude = mylatlong.getpre_mylabellat();
			preLongitude = mylatlong.getmylabellong();
			walk_listen = mylatlong.getwalk_listen();
			
			SharedPreferences sp = getSharedPreferences("imap", MODE_PRIVATE);
			radius = Variable.ranges[sp.getInt("range", 1)];
			
			//Toast.makeText(WalkListen.this, "" + radius, Toast.LENGTH_SHORT).show();
			//Toast.makeText(
				//	WalkListen.this,
					//mCurrentLantitude + " " + mCurrentLongitude + " "
						//	+ walk_listen, Toast.LENGTH_SHORT).show();

			//System.out.println("您好：：" + flags + "  " + startId);

			if (walk_listen == 0 && playState == true) {
				playState = false;
				media.stop();
				media.reset();
				media.release();
				mylatlong.setpre_mylabellat(-1);
				mylatlong.setpre_mylabellong(-1);
				mylatlong.setnear_listen_view_index(-1);
				return START_NOT_STICKY;
			}
			if (walk_listen == 0 && playState == false) {
				mylatlong.setpre_mylabellat(-1);
				mylatlong.setpre_mylabellong(-1);
				mylatlong.setnear_listen_view_index(-1);
				return START_NOT_STICKY;
			}

			
			if (distance(mCurrentLantitude, mCurrentLongitude, preLantitude, preLongitude) < 10) {
				
				return START_NOT_STICKY;
			}
			else 
			{
				mylatlong.setpre_mylabellat(mCurrentLantitude);//将最近地点更新，同时查看语音是否要更新
				mylatlong.setpre_mylabellong(mCurrentLongitude);
				double min = 99999;
				int index = -1;
				for(int i = 0 ;i < MainActivity.viewspotlist.size();i++)
				{
					double temp = distance(mCurrentLantitude, mCurrentLongitude, 
							MainActivity.viewspotlist.get(i).getLatitude(), MainActivity.viewspotlist.get(i).getLongitude());
					System.out.println(MainActivity.viewspotlist.get(i).getLatitude()+" "+ MainActivity.viewspotlist.get(i).getLongitude());
					System.out.println(i +" "+ temp);
					if(min >temp)// 当前坐标与景点i之间的距离 修改数值1
					{
						min = temp;//修改为当前坐标与景点i之间的距离；
						index = i;
					}
				}
			//	mylatlong.setnear_listen_view_index(0);
			//	System.out.println("index"+ index);
				
				if(min < radius)
				{
					if(mylatlong.getnear_listen_view_index() != index)
					{
						mylatlong.setnear_listen_view_index(index);//更换语音
					//	System.out.println("更换了语音"+index);
						if(playState == true)
						{
							//更换默认语音，景点编号为index
							media.stop();
							media.reset();
							media.release();
						}
						media = new MediaPlayer();
						//更改为第i位景点的默认语音
						
						//签到
						String spotid = MainActivity.viewspotlist.get(index).getId() + "";
						 String name = sp.getString("username", "");
						 String pw = sp.getString("password", "");
						 
						 NetThread netthread = new NetThread(name, pw);
						  netthread.makeParam(Variable.markSpot, spotid);
						  int returnCode = netthread.beginDeal();
						  
						  if (returnCode == 0)
						  {
							  Toast.makeText(this, "已经在" + MainActivity.viewspotlist.get(index).getName() + "自动签到成功！", 
						                 Toast.LENGTH_SHORT).show(); 
						  }
						  else if (returnCode == -1)
						  {
							  Toast.makeText(this, "网络错误！", 
						                 Toast.LENGTH_SHORT).show();
						  }
						  else
						  {
							  Toast.makeText(this, Variable.errorCode[returnCode] + "！", 
						                 Toast.LENGTH_SHORT).show();
						  }
						 
						 // 获取语音
						String filepath = VoiceCache.getDefaultVoiceBySpotId(this, spotid);
						if (filepath == null)
							return START_NOT_STICKY;
						
						File file = new File(filepath);//默认语音i的地点，或者需要下载的

						// 设置播放资源
						try {
							media.setDataSource(file.getAbsolutePath());
							// 准备播放
							media.prepare();
							// 开始播放
							media.start();
						} catch (IllegalArgumentException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (SecurityException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (IllegalStateException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (IOException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
						// 改变播放的标志位
						playState = true;

						// 设置播放结束时监听
						media.setOnCompletionListener(new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								if (playState) {
									playState = false;
									media.reset();
									media.release();

								}
							}
						});

					}
				}
				
			}
					
		return START_NOT_STICKY;

	}

	@Override
	public void onCreate() {
		
		/*
		 * viewspotlist = mylatlong.getviewlist(); for(int i = 0 ;i <
		 * viewspotlist.size();i++) {
		 * System.out.println(viewspotlist.get(i).getLatitude()+" "+
		 * viewspotlist.get(i).getLongitude()); }
		 */
		// final MyAppData mylatlong = (MyAppData)getApplication();
		//MyAppData mylatlong = (MyAppData) getApplication();
		//viewspotlist = mylatlong.getviewlist();
		super.onCreate();
		// 获取所有景点的坐标

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public boolean stopService(Intent name) {
		// TODO 自动生成的方法存根
		stopSelf();
		return super.stopService(name);
	}

	public double distance(double n1, double e1, double n2, double e2)  
    {  
        double jl_jd = 102834.74258026089786013677476285;//每经度单位米;  
        double jl_wd = 111712.69150641055729984301412873;//每纬度单位米;  
        double b = Math.abs((e1 - e2) * jl_jd);  
        double a = Math.abs((n1 - n2) * jl_wd);  
        return Math.sqrt((a * a + b * b));  
 
    }  
}

/*
 * 
 * import android.app.Service; import android.app.WallpaperManager; import
 * android.content.Intent; import android.os.IBinder;
 * 
 * 
 * public class WalkListen extends Service {
 * 
 * int[] wallpapers = new int[]{ R.drawable.face2, R.drawable.face3,
 * R.drawable.face0, R.drawable.face1 };
 * 
 * WallpaperManager wManager;
 * 
 * int current = 0;
 * 
 * @Override public int onStartCommand(Intent intent, int flags, int startId) {
 * 
 * if(current >= 4) current = 0; try { System.out.println("您好：："+flags
 * +"  "+startId); wManager.setResource(wallpapers[current++]); } catch
 * (Exception e) { e.printStackTrace(); } return START_STICKY; }
 * 
 * @Override public void onCreate() { super.onCreate();
 * 
 * wManager = WallpaperManager.getInstance(this); }
 * 
 * @Override public IBinder onBind(Intent intent) { return null; } }
 */