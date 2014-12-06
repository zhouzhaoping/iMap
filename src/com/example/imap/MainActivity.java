package com.example.imap;

import java.util.ArrayList;
import java.util.List;

import android.animation.StateListAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/*定位用import*/

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.GeofenceClient;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
//import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;

public class MainActivity extends Activity {

	public int select_button = -1;
	long firstTime = 0;
	private MapView mMapView;
	private InfoWindow mInfoWindow;
	BaiduMap mBaiduMap;
	Marker markera;
	EditText show;
	/******** 14-12-03 xj *************************************************/
	// UI相关
	OnCheckedChangeListener radioButtonListener;
	Button requestLocButton;
	boolean isFirstLoc = true;// 是否首次定位

	LocationClient mLocClient;
	public BDLocationListener myListener = new MyLocationListenner();
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	public GeofenceClient mGeofenceClient = null;

	/******** 14-12-05 xj ********************************************/
	/*
	 * public class AddGeofenceListener implements
	 * OnAddBDGeofencesResultListener {
	 * 
	 * @Override public void onAddBDGeofencesResult(int statusCode, String
	 * geofenceRequestId) { if (statusCode == BDLocationStatusCodes.SUCCESS) {
	 * mGeofenceClient.start(); // 围栏创建成功 } } }
	 * 
	 * public class RemoveFenceListener implements
	 * OnRemoveBDGeofencesResultListener {
	 * 
	 * @Override public void onRemoveBDGeofencesByRequestIdsResult(int
	 * statusCode, String[] geofenceRequestIds) { if (statusCode ==
	 * BDLocationStatusCodes.SUCCESS) { // 围栏删除成功 } } }
	 * 
	 * /**************************************************************
	 */

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
			if (isFirstLoc) {
				isFirstLoc = false;
				LatLng ll = new LatLng(location.getLatitude(),
						location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);
			}
			// 经纬度
			// System.out.println(location.getLatitude());
			// System.out.println(location.getLongitude());
			location.getLatitude();
			location.getLongitude();

			// ///
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Location location_temp=new Location("GPS");
			location_temp.setLatitude(location.getLatitude());
			location_temp.setLongitude(location.getLongitude());
			//updateView(location_temp);测试用的
			// logMsg(sb.toString());
			// ///
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
//测试用的函数updateView
	public void updateView(Location newLocation)
	{
		if (newLocation != null)
		{
			StringBuilder sb = new StringBuilder();
			sb.append("实时位置信息：\n");
			sb.append("经度： ");
			sb.append(newLocation.getLongitude());
			sb.append("纬度： ");
			sb.append(newLocation.getLatitude());		
			show.setText(sb.toString());
		}
		else
		{ 
			show.setText("");
		}
	}
	
	
	
	/********************************************************************/
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);

		
		
		
		
		
		
/*测试实时经纬度用***********************************************************************************/
		
		show = (EditText) findViewById(R.id.show);
		
		
		
/************************************************************************************/
		
		
		
		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

		/******** 14-12-03 xj* 定位功能 ************************************************/
		// 设置地图缩放级别
		MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(18.0f);
		mBaiduMap.animateMapStatus(u);
		requestLocButton = (Button) findViewById(R.id.button1);
		mCurrentMode = LocationMode.NORMAL;
		requestLocButton.setText("普通");
		OnClickListener btnClickListener = new OnClickListener() {// 按钮监听函数
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
		option_loc.setScanSpan(1000);// 设置扫描间隔，单位是毫秒
		option_loc.setNeedDeviceDirect(true);// 在网络定位中，获取手机机头所指的方向
		// Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
		option_loc
				.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Hight_Accuracy);
		mLocClient.setLocOption(option_loc);

		mLocClient.start();

		/****** 14-12-03 xj**点击景点，显示语音 **********************************************/
		// 在地图上添加Marker，并显示

		BitmapDescriptor ooa = BitmapDescriptorFactory
				.fromResource(R.drawable.icon);
		LatLng llA = new LatLng(39.996987, 116.313082);
		OverlayOptions option = new MarkerOptions().position(llA).icon(ooa)
				.zIndex(9).draggable(true);
		markera = (Marker) (mBaiduMap.addOverlay(option));

		// /
		// //////////////////////////////////////////////////////////////////////////////////////////
		final Builder builder = new AlertDialog.Builder(this);
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				if (marker == markera) {
					builder.setIcon(R.drawable.tools);
					builder.setTitle("选择语音");
					builder.setMessage("none");
					builder.setPositiveButton("更多",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 这里添加默认语音
									Intent intent = new Intent();
									intent.setClass(MainActivity.this,
											MusicActivity.class);

									startActivity(intent);
								}
							});
					builder.setNegativeButton("默认",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
									// 这里跳转到语音列表
									Intent intent = new Intent();
									intent.setClass(MainActivity.this,
											OfflineDemo.class);
									startActivity(intent);
								}
							});
					builder.create().show();
				}

				return true;
			}
		});

		/****************************************************************/

		/************************** 14-12-04 xj下面要开始添加地理围栏 ********************************/
		// final Builder temp = new AlertDialog.Builder(this);
		/*
		 * class GeofenceEnterLister implements OnGeofenceTriggerListener {
		 * 
		 * @Override public void onGeofenceTrigger(String geofenceRequestId) {
		 * // 进入围栏，围栏Id = geofenceRequestId System.out .println(
		 * "进入围栏了**********************************************************");
		 * 
		 * temp.setTitle("biabia"); temp.setMessage("haaha");
		 * temp.create().show(); }
		 * 
		 * @Override public void onGeofenceExit(String geofenceRequestId) { //
		 * 退出围栏，围栏Id = geofenceRequestId }
		 * 
		 * } mGeofenceClient = new GeofenceClient(getApplicationContext()); //
		 * 注册并开启围栏扫描服务 mGeofenceClient .registerGeofenceTriggerListener(new
		 * GeofenceEnterLister()); // mGeofenceClient.start();
		 * 
		 * BDGeofence fence = new BDGeofence.Builder() .setGeofenceId("markera")
		 * .setCircularRegion(markera.getAnchorX(), markera.getAnchorY(),
		 * 40).setExpirationDruation(14400 * 20)
		 * .setCoordType("bd09ll").build();
		 * 
		 * // 添加一个围栏： mGeofenceClient.addBDGeofence(fence, new
		 * AddGeofenceListener()); // 删除，指定要删除围栏的名字列表 List<String> fences = new
		 * ArrayList<String>(); fences.add("markera"); // mGeofenceClient.
		 * removeBDGeofences(fences, new // RemoveFenceListener());
		 */

		/********************************************************************/

		/********** 14-12-03 xj* 添加下面5个按钮的选项提示 ***********************/
		// 添加景点
		final ImageButton button_viewpoint = (ImageButton) findViewById(R.id.button_viewpoint);
		final ImageButton button_person = (ImageButton) findViewById(R.id.button_person);
		final ImageButton button_huatong = (ImageButton) findViewById(R.id.button_huatong);
		final ImageButton button_paihang = (ImageButton) findViewById(R.id.button_paihang);
		final ImageButton button_settings = (ImageButton) findViewById(R.id.button_settings);
		final TextView Tip = (TextView) findViewById(R.id.textview_tip);
		final Builder alert_viewpoint = new AlertDialog.Builder(this);

		// 地图长按时间监听注册
		OnMapLongClickListener listener_view = new OnMapLongClickListener() {
			/**
			 * 地图长按事件监听回调函数
			 * 
			 * @param point
			 *            长按的地理坐标
			 */
			public void onMapLongClick(LatLng point) {

				if (select_button != 0)
					return;
				select_button = -1;
				button_viewpoint.setImageResource(R.drawable.viewpoint);
				Tip.setVisibility(View.GONE);

				// 显示弹窗
				alert_viewpoint.setIcon(R.drawable.viewpoint);
				alert_viewpoint.setTitle("添加景点");

				// 装载对话框界面布局
				TableLayout loginForm = (TableLayout) getLayoutInflater()
						.inflate(R.layout.addview, null);
				alert_viewpoint.setView(loginForm);

				alert_viewpoint.setPositiveButton("确定"

				, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 发送增加景点请求，使用point传地址
					}
				});

				alert_viewpoint.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// 取消，不做操作
							}
						});

				alert_viewpoint.create().show();
			}
		};
		mBaiduMap.setOnMapLongClickListener(listener_view);
		// 长按时间结束

		button_viewpoint.setOnClickListener(

		new View.OnClickListener() {

			public void onClick(View v) {
				select_button = 0;
				button_viewpoint.setImageResource(R.drawable.viewing);

				Tip.setVisibility(View.VISIBLE);

			}
		});

		button_person.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 1;
				// 跳转到个人activity
			}
		});
		button_huatong.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				button_huatong.setImageResource(R.drawable.huatonging);

				// button_huatong.setBackgroundColor(0x00000000);
				select_button = 2;
				// 对地图上所有marker 标亮
				//
				select_button = 5;
			}
		});

		button_huatong.setOnLongClickListener(new View.OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO 自动生成的方法存根
				if (select_button != 5)
					return false;
				button_huatong.setImageResource(R.drawable.huatong2);
				// button_huatong.setBackgroundColor(0x00000000);
				select_button = -1;
				// 开始录音
				// 录音结束
				// 添加button_huatong.setImageResource(R.drawable.huatong);
				return true;

			}
		}

		);

		button_paihang.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 3;
				// 跳转到排行activity
			}
		});

		button_settings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 4;
				// 跳转到设置activity
			}
		});
	}

	/*************************************************************************/

	/*
	 * button_settings.setOnClickListener(new ImageButton.OnClickListener() {
	 * public void onClick(View v) { Intent intent = new Intent();
	 * intent.setClass(MainActivity.this, MusicActivity.class);
	 * 
	 * startActivity(intent); //MainActivity.this.finish(); } });
	 */

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

		// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理

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
		// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
		mMapView.onPause();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long secondTime = System.currentTimeMillis();
			if (secondTime - firstTime > 1600) {// 如果两次按键时间间隔大于1600毫秒，则不退出
				Toast.makeText(MainActivity.this, "再按一次退出程序...",
						Toast.LENGTH_SHORT).show();
				firstTime = secondTime;// 更新firstTime
				return true;
			} else {
				System.exit(0);// 否则退出程序
			}
		}
		return super.onKeyUp(keyCode, event);
	}
}
