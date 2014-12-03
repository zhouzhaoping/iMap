package com.example.imap;

import android.app.Activity;
import android.content.DialogInterface;
//import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;


import android.widget.Toast;


/*定位用import*/

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类

public class MainActivity extends Activity {

	long firstTime=0;
	private MapView mMapView;
	private InfoWindow mInfoWindow;
	BaiduMap mBaiduMap;
	Marker markera;
	/********14-12-03 xj*************************************************/
	// UI相关
		OnCheckedChangeListener radioButtonListener;
		Button requestLocButton;
	
		
	LocationClient mLocClient;
	public BDLocationListener myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// map view 销毁后不在处理新接收的位置
			if (location == null || mMapView == null)
				return;
			MyLocationData locData = new MyLocationData.Builder()
					.accuracy(location.getRadius())
					// 此处设置开发者获取到的方向信息，顺时针0-360
					.direction(100).latitude(location.getLatitude())
					.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			//if (isFirstLoc) {
			//	isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
		//	}
				//经纬度
				location.getLatitude();
				location.getLongitude();
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	/********************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.activity_main);

		//获取地图控件引用  
        mMapView = (MapView) findViewById(R.id.bmapView);  
       mBaiduMap = mMapView.getMap();  
       
      //普通地图  
      mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);  
      //卫星地图  
     // mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
      
     
	/********14-12-03 xj* 定位功能************************************************/ 
      //设置地图缩放级别
    MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18.0f);
	mBaiduMap.animateMapStatus(u);
    requestLocButton = (Button) findViewById(R.id.button1);
	mCurrentMode = LocationMode.NORMAL;
	requestLocButton.setText("普通");  
	OnClickListener btnClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (mCurrentMode) {
			case NORMAL:
				requestLocButton.setText("跟随");
				mCurrentMode = LocationMode.FOLLOWING;
				mBaiduMap
						.setMyLocationConfigeration(new MyLocationConfiguration(
								mCurrentMode, true, mCurrentMarker));
				break;
			case COMPASS:
				requestLocButton.setText("普通");
				mCurrentMode = LocationMode.NORMAL;
				mBaiduMap
						.setMyLocationConfigeration(new MyLocationConfiguration(
								mCurrentMode, true, mCurrentMarker));
				break;
			case FOLLOWING:
				requestLocButton.setText("罗盘");
				mCurrentMode = LocationMode.COMPASS;
				mBaiduMap
						.setMyLocationConfigeration(new MyLocationConfiguration(
								mCurrentMode, true, mCurrentMarker));
				break;
			}
		}

		
	};
	requestLocButton.setOnClickListener(btnClickListener);
	mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
					mCurrentMode, true, null));
	
    // 开启定位图层
	mBaiduMap.setMyLocationEnabled(true);
	// 定位初始化
	mLocClient = new LocationClient(this);
	mLocClient.registerLocationListener(myListener);
	LocationClientOption option_loc = new LocationClientOption();
	option_loc.setOpenGps(true);// 打开gps
	option_loc.setCoorType("bd09ll"); // 设置坐标类型
	option_loc.setScanSpan(1000);//设置扫描间隔，单位是毫秒
	mLocClient.setLocOption(option_loc);
	mLocClient.start();
	
	
	
	
	
	
	/********************************************************************/
  /******14-12-02 xj**********************************************************/ 
    //在地图上添加Marker，并显示
  
   /* markera= (Marker) (mBaiduMap.addOverlay(option));
    OnMarkerClickListener listener = new OnMarkerClickListener() {  
        * 
        * 地图 Marker 覆盖物点击事件监听函数 
        * @param marker 被点击的 marker 
        *  
        public boolean onMarkerClick(final Marker marker){  
        	 
        	if(marker == markera)
        	{
        	OnInfoWindowClickListener lis = null;
        	
			lis = new OnInfoWindowClickListener() {
				public void onInfoWindowClick() {
					Button button_default = (Button) findViewById(R.id.button_huatong);
					
					final Builder builder_default = new AlertDialog.Builder(null);
					
					button_default.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO 自动生成的方法存根
							builder_default.setIcon(R.drawable.icon);
							builder_default.setTitle("自定义对话框");
							builder_default.setMessage("haha ");
							builder_default.setPositiveButton("默认",
								new OnClickListener()
							{
								
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO 自动生成的方法存根
									EditText show = (EditText) findViewById(R.id.bmapView);
									show.setText("确定");
									
								}
								});
							
							builder_default.setNegativeButton("更多",
									new OnClickListener()
								{
									
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										// TODO 自动生成的方法存根
										EditText show = (EditText) findViewById(R.id.bmapView);
										show.setText("更多更多");
										
									}
									});
							builder_default.create().show();
							
						}
					});
					
					
				}
			};
			
			mBaiduMap.showInfoWindow(mInfoWindow);
        	}
        	return true;
        }  
    };
   // listener.onMarkerClick(marker);
     mBaiduMap.setOnMarkerClickListener(listener);*/
   
    
  /****************************************************************/     
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	 @Override  
	    protected void onDestroy() {  
	          
	        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
	        
	     // 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mMapView.onDestroy();
			mMapView = null;
			super.onDestroy();
	    }  
	    @Override  
	    protected void onResume() {  
	        super.onResume();  
	        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
	        mMapView.onResume();  
	        }  
	    @Override  
	    protected void onPause() {  
	        super.onPause();  
	        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
	        mMapView.onPause();  
	        }  
	    
	    @Override 
	    public boolean onKeyUp(int keyCode, KeyEvent event) { 
	        if (keyCode == KeyEvent.KEYCODE_BACK) { 
	            long secondTime = System.currentTimeMillis(); 
	            if (secondTime - firstTime > 1600) {//如果两次按键时间间隔大于1600毫秒，则不退出 
	                Toast.makeText(MainActivity.this, "再按一次退出程序...", 
	                        Toast.LENGTH_SHORT).show(); 
	                firstTime = secondTime;//更新firstTime 
	                return true; 
	            } else { 
	                System.exit(0);//否则退出程序 
	            } 
	        } 
	        return super.onKeyUp(keyCode, event); 
	    } 
}
