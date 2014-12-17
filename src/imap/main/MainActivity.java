package imap.main;

import imap.main.MyOrientationListener.OnOrientationListener;
import imap.nettools.NetThread;
import imap.nettools.Variable;
import imap.nettools.ViewSpotData;
import imap.util.MyRecorder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.spec.IvParameterSpec;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.animation.StateListAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.LabeledIntent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/*定位用import*/

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
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
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;

//import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.ui.routeguide.fsm.RouteGuideFSM.IFSMDestStateListener;
//假如用到位置提醒功能，需要import该类
//如果使用地理围栏功能，需要import如下类
import com.baidu.location.BDGeofence;
import com.baidu.location.BDLocationStatusCodes;
import com.baidu.location.GeofenceClient;
import com.baidu.location.GeofenceClient.OnAddBDGeofencesResultListener;
import com.baidu.location.GeofenceClient.OnGeofenceTriggerListener;
import com.baidu.location.GeofenceClient.OnRemoveBDGeofencesResultListener;
import com.example.imap.R;

import android.app.AlarmManager;

public class MainActivity extends Activity {

	AlarmManager aManager;

	/** 语音音量显示 */
	private Dialog dialog;

	/** MediaPlayer */
	private MediaPlayer media;

	/** MyRecorder */
	private MyRecorder recorder;

	/** 更新时间的线程 */
	private Thread timeThread;

	/** 更新进度条的线程 */
	private Thread barThread;

	/** 更新的语音图标 */
	private ImageButton dialog_image;

	/** 显示播放条 */
	private ProgressBar bar;

	private static int MAX_TIME = 0; // 最长录制时间，单位秒，0为无时间限制
	private static int MIX_TIME = 1; // 最短录制时间，单位秒，0为无时间限制，建议设为1

	private static int RECORD_NO = 0; // 不在录音
	private static int RECORD_ING = 1; // 正在录音
	private static int RECODE_ED = 2; // 完成录音

	private static int RECODE_STATE = 0; // 录音的状态

	private static float recodeTime = 0.0f; // 录音的时间
	private static double voiceValue = 0.0; // 麦克风获取的音量值

	private static boolean playState = false; // 播放状态
	/***********************************************************/
	public int select_button = -1;
	long firstTime = 0;
	private MapView mMapView;
	private InfoWindow mInfoWindow;
	BaiduMap mBaiduMap;
	List<ViewSpotData> viewspotlist;
	ArrayList<Marker> markers = new ArrayList<Marker>();
	ArrayList<Intent> intents = new ArrayList<Intent>();
	ArrayList<PendingIntent> pendingIntents = new ArrayList<>();
	Marker marker1;
	int sure_choose_marker = 0;
	EditText show;
	public int radius = 30;
	public static int alarmCount = 0;
	/**
	 * 当前的经纬度
	 */
	private double mCurrentLantitude;
	private double mCurrentLongitude;
	/**
	 * 最近记录的经纬度
	 */
	private double pre_mCurrentLantitude = 0;
	private double pre_mCurrentLongitude = 0;

	/**
	 * 当前的精度
	 */
	private float mCurrentAccracy;
	/**
	 * 方向传感器的监听器
	 */
	private MyOrientationListener myOrientationListener;
	/**
	 * 方向传感器X方向的值
	 */
	private int mXDirection;

	/**
	 * 判断是否边走边听
	 */
	// private MyAppData myAppData_listen;
	private static int walk_listen = 0;
	/**
	 * 判断是哪个景点确定了上传语音
	 */
	private int view_point_sure_to_update = -1;
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

	private String timeString;

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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 在使用SDK各组件之前初始化context信息，传入ApplicationContext
		// 注意该方法要再setContentView方法之前实现
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main);
		aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
		
		final Intent alerIntent = new Intent(MainActivity.this,
				WalkListen.class);
	
		/*
		 * 测试实时经纬度用**************************************************************
		 * ********************
		 */

		show = (EditText) findViewById(R.id.show);
		 
		 
		/************************************************************************************/

		// 获取地图控件引用
		mMapView = (MapView) findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();

		// 普通地图
		mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
		// 将数据库中景点坐标放入列表markers中
		init();
		mMapView.showZoomControls(false);

		
		//将景点放入公共变量中
		final MyAppData mylatlong = (MyAppData)getApplication();
		mylatlong.setviewlist(viewspotlist);
		//List<ViewSpotData> temp;
		
		// 获取全局变量
		// myAppData_listen = (MyAppData) getApplication();

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
		requestLocButton.setOnClickListener(btnClickListener);// 设置监听函数
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
		// 初始化传感器
		initOritationListener();
		myOrientationListener.start();
		/****** 14-12-03 xj**点击景点，显示语音 **********************************************/

		//
		/*
		 * BitmapDescriptor ooa = BitmapDescriptorFactory
		 * .fromResource(R.drawable.icon); LatLng llA = new LatLng(39.996987,
		 * 116.313082); OverlayOptions option = new
		 * MarkerOptions().position(llA).icon(ooa) .zIndex(9).draggable(true);
		 * marker1 = (Marker) (mBaiduMap.addOverlay(option));
		 */
		// //////////////////////////////////////////////////////////////////////////////////////////
		final Builder builder = new AlertDialog.Builder(this);

		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			int num = 0;
			int pre_marker = -1;

			public boolean onMarkerClick(final Marker marker) {

				if (sure_choose_marker == 0) {
					num = 0;
					pre_marker = -1;
				}

				if (select_button == -1) {
					for (int i = 0; i < markers.size(); i++) {
						if (marker == markers.get(i)) {
							builder.setIcon(R.drawable.viewpoint);

							builder.setTitle(viewspotlist.get(i).getName());
							builder.setMessage(viewspotlist.get(i)
									.getDescription());
							final int j = i;

							builder.setPositiveButton("更多",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {

											Intent intent = new Intent();
											intent.putExtra("id", viewspotlist
													.get(j).getId());
											intent.setClass(
													MainActivity.this,
													imap.musiclist.MusicListActivity.class);

											startActivity(intent);
										}
									});
							builder.setNegativeButton("默认",
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog,
												int which) {
											// 这里添加默认语音
										}
									});
							builder.create().show();
						}

					}

				}
				if (select_button == 2)// 短按话筒键，选择录音地点
				{

					for (int i = 0; i < markers.size(); i++) {

						if (marker == markers.get(i)) {
							if (num == 0)// 保证地图上只能选择一个点
							{
								System.out.println("num: " + num + " pre: "
										+ pre_marker);
								markers.get(i)
										.setIcon(
												BitmapDescriptorFactory
														.fromResource(R.drawable.icon_choosed));
								view_point_sure_to_update = i;
								sure_choose_marker = 1;
								num = 1;
								pre_marker = i;
							} else {
								System.out.println("num: " + num + " pre: "
										+ pre_marker);
								markers.get(pre_marker)
										.setIcon(
												BitmapDescriptorFactory
														.fromResource(R.drawable.icon_choosing));
								markers.get(i)
										.setIcon(
												BitmapDescriptorFactory
														.fromResource(R.drawable.icon_choosed));
								view_point_sure_to_update = i;
								pre_marker = i;
								sure_choose_marker = 1;
							}

						}
					}
				}

				return true;
			}
		});

		/****************************************************************/

		/*
		 * 为每个marker添加一个临近警告地理围栏*************************************************
		 * *********************************
		 * 参见http://blog.csdn.net/huang_hws/article/details/7327670
		 */

		// 定位服务常量
		/*
		 * String locService = Context.LOCATION_SERVICE; // 定位服务管理器实例
		 * LocationManager locationManager; locationManager = (LocationManager)
		 * getSystemService(locService);
		 * 
		 * for (int i = 0; i < markers.size(); i++) { intents.add(new
		 * Intent(this, AlertReceiver.class));
		 * intents.get(i).putExtra(String.valueOf(i), i);
		 * intents.get(i).putExtra("walk_listen", walk_listen); //
		 * 由于不是马上触发，所以需要PendingIntent
		 * pendingIntents.add(PendingIntent.getBroadcast(this, alarmCount++,
		 * intents.get(i), PendingIntent.FLAG_UPDATE_CURRENT)); // 添加临近警告 //
		 * 参照http://blog.csdn.net/flowingflying/article/details/38871219
		 * locationManager.addProximityAlert(
		 * markers.get(i).getPosition().latitude, markers.get(i)
		 * .getPosition().longitude, radius, -1, pendingIntents.get(i)); }
		 * 
		 * // Intent intent1 = new Intent(this, AlertReceiver.class); //
		 * intent1.putExtra("intent1", "intent1"); // 由于不是马上触发，所以需要PendingIntent
		 * // PendingIntent pi1 = PendingIntent.getBroadcast(this, alarmCount++,
		 * // intent1, // PendingIntent.FLAG_UPDATE_CURRENT); // 添加临近警告
		 * 参照http://blog.csdn.net/flowingflying/article/details/38871219 //
		 * locationManager.addProximityAlert(marker1.getPosition().latitude, //
		 * marker1.getPosition().longitude, radius, -1, pi1); /*** end
		 * **********
		 * ************************************************************
		 * **********
		 */

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

		/******* 14-12-07 xj 为边走边听添加判断 *************************************************************/

		final ImageButton button_listen = (ImageButton) findViewById(R.id.button_listen);
		
		final PendingIntent alermpi = PendingIntent.getService(
				MainActivity.this, 0, alerIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		button_listen.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				if (walk_listen == 1) {
					walk_listen = 0;
					System.out.println("walk_listen: " + walk_listen);
					button_listen.setImageResource(R.drawable.listen_off);
					// 关闭边走边听
					
				//	mylatlong.setmylabellat(mCurrentLantitude);
				//	mylatlong.setmylabellong(mCurrentLongitude);
					mylatlong.setwalk_listen(walk_listen);

					aManager.set(AlarmManager.RTC, System.currentTimeMillis(), alermpi);
					aManager.cancel(alermpi);
					
					//stopService(alerIntent);	
				} else {
					walk_listen = 1;
					System.out.println("walk_listen: " + walk_listen);
					button_listen.setImageResource(R.drawable.listen);
					// 开启边走边听
					// if(当前经纬度和最近一次记录的经纬度相差一定距离)
					if (true) 
					{
						
						// System.out.println(mCurrentLantitude+"+"+mCurrentLongitude);
						
						//mylatlong.setmylabellat(mCurrentLantitude);
						//mylatlong.setmylabellong(mCurrentLongitude);
						mylatlong.setwalk_listen(walk_listen);
						
						aManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(), 5000,
								alermpi);
					}

				}
				// 跳转到设置activity
			}
		});
		/********************************************************************/

		/********************************************************************/

		/********** 14-12-03 xj* 添加下面5个按钮的选项提示 ***********************/

		final ImageButton button_viewpoint = (ImageButton) findViewById(R.id.button_viewpoint);
		final ImageButton button_person = (ImageButton) findViewById(R.id.button_person);
		final ImageButton button_huatong = (ImageButton) findViewById(R.id.button_huatong);
		final ImageButton button_paihang = (ImageButton) findViewById(R.id.button_paihang);
		final ImageButton button_settings = (ImageButton) findViewById(R.id.button_settings);
		final TextView Tip = (TextView) findViewById(R.id.textview_tip);
		final Builder alert_viewpoint = new AlertDialog.Builder(this);
		final AlertDialog.Builder alert_recorder = new AlertDialog.Builder(this);

		// 添加景点
		// 地图长按时间监听注册
		OnMapLongClickListener listener_view = new OnMapLongClickListener() {
			/**
			 * 地图长按事件监听回调函数
			 * 
			 * @param point
			 *            长按的地理坐标
			 */
			public void onMapLongClick(final LatLng point) {
				//System.out.println("select:" + select_button);
				if (select_button != 0)
					return;
				select_button = -1;
				button_viewpoint.setImageResource(R.drawable.viewpoint);
				// 恢复按钮
				Tip.setVisibility(View.GONE);
				button_person.setEnabled(true);
				button_huatong.setEnabled(true);
				button_paihang.setEnabled(true);
				button_settings.setEnabled(true);
				// 显示弹窗
				alert_viewpoint.setIcon(R.drawable.viewpoint);
				alert_viewpoint.setTitle("添加景点");

				// 装载对话框界面布局
				final TableLayout loginForm = (TableLayout) getLayoutInflater()
						.inflate(R.layout.addview, null);
				alert_viewpoint.setView(loginForm);

				alert_viewpoint.setPositiveButton("确定"

				, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						// 设置点击后对话框不删除
						try {
							Field field = dialog.getClass().getSuperclass()
									.getDeclaredField("mShowing");
							field.setAccessible(true);
							field.set(dialog, false);
						} catch (Exception e) {
							e.printStackTrace();
						}

						SharedPreferences sp = MainActivity.this
								.getSharedPreferences("imap", MODE_PRIVATE);
						String username = sp.getString("username", "");
						String password = sp.getString("password", "");

						EditText add_viewpoint_name = (EditText) loginForm
								.findViewById(R.id.add_viewpoint_name);
						EditText add_viewpoint_reason = (EditText) loginForm
								.findViewById(R.id.add_viewpoint_reason);
						String add_name = add_viewpoint_name.getText()
								.toString();
						String add_reason = add_viewpoint_reason.getText()
								.toString();
						String add_latitude = point.latitude + "";
						String add_longtitude = point.longitude + "";

						if (add_name.isEmpty() || add_reason.isEmpty())
							Toast.makeText(MainActivity.this, "景点名、理由不能为空！",
									Toast.LENGTH_SHORT).show();
						else if (add_name.length() > 20)
							Toast.makeText(MainActivity.this, "经景点名在20个字以内！",
									Toast.LENGTH_SHORT).show();
						else if (add_reason.length() > 200)
							Toast.makeText(MainActivity.this, "理由在6~14位！",
									Toast.LENGTH_SHORT).show();
						else if (add_latitude.isEmpty()
								|| add_longtitude.isEmpty())
							Toast.makeText(MainActivity.this, "获取经纬度失败！",
									Toast.LENGTH_SHORT).show();
						else {
							NetThread netthread = new NetThread(username,
									password);
							netthread.makeParam(Variable.applySpot, add_name,
									add_reason, add_longtitude, add_latitude);
							int returnCode = netthread.beginDeal();
							if (returnCode == 0) {
								Toast.makeText(MainActivity.this,
										add_name + " 申请已发出，我们会在一个工作日内处理！",
										Toast.LENGTH_SHORT).show();

								try {
									Field field = dialog.getClass()
											.getSuperclass()
											.getDeclaredField("mShowing");
									field.setAccessible(true);
									field.set(dialog, true);
								} catch (Exception e) {
									e.printStackTrace();
								}
							} else if (returnCode == -1) {
								Toast.makeText(MainActivity.this, "网络错误！",
										Toast.LENGTH_SHORT).show();
							} else {
								Toast.makeText(MainActivity.this,
										Variable.errorCode[returnCode] + "！",
										Toast.LENGTH_SHORT).show();
							}
						}
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
				// 将按钮设置成不可按
				Tip.setVisibility(View.VISIBLE);
				button_person.setEnabled(false);
				button_huatong.setEnabled(false);
				button_paihang.setEnabled(false);
				button_settings.setEnabled(false);

			}
		});

		button_person.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 1;
				// 跳转到个人activity
			}
		});
		// 话筒短按，地图上所有图标发亮，然后选择一个开始录音
		button_huatong.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				button_huatong.setImageResource(R.drawable.huatonging);

				// button_huatong.setBackgroundColor(0x00000000);
				select_button = 2;
				// 对地图上所有marker 标亮
				sure_choose_marker = 0;
				lightallmarkers();
				button_person.setEnabled(false);
				button_viewpoint.setEnabled(false);
				button_paihang.setEnabled(false);
				button_settings.setEnabled(false);

			}
		});

		// 录音按钮监听

		bar = (ProgressBar) findViewById(R.id.progressBar1);

		button_huatong.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (sure_choose_marker != 1)
					return false;

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下
					// 如果当前不是正在录音状态，开始录音
					button_huatong.setImageResource(R.drawable.huatong2);
					if (RECODE_STATE != RECORD_ING) {
						SimpleDateFormat sDateFormat = new SimpleDateFormat(
								"yyyyMMddhhmmss");
						timeString = sDateFormat.format(new java.util.Date());
						recorder = new MyRecorder("voice_"
								+ view_point_sure_to_update + "_" + timeString);
						RECODE_STATE = RECORD_ING;
						// 显示录音情况
						showVoiceDialog();
						// 开始录音
						recorder.start();
						// timeText.setVisibility(View.VISIBLE);
						bar.setVisibility(View.GONE);
						// 计时线程
						myThread();
					}
					break;

				case MotionEvent.ACTION_UP:// 离开
					// 如果是正在录音
					if (RECODE_STATE == RECORD_ING) {
						RECODE_STATE = RECODE_ED;
						// 如果录音图标正在显示,关闭
						if (dialog.isShowing()) {
							dialog.dismiss();
						}

						// 停止录音
						recorder.stop();
						voiceValue = 0.0;
						sure_choose_marker = 0;
						// if (recodeTime < MIX_TIME) {
						if (recodeTime < MAX_TIME) {
							// showWarnToast();
							// recordBt.setText("按住录音");
							RECODE_STATE = RECORD_NO;
						} else {

							// //////////////////////////////////////////////////////////

							// 参数还原
							select_button = -1;
							sure_choose_marker = 0;
							for (int i = 0; i < markers.size(); i++) {
								markers.get(i).setIcon(
										BitmapDescriptorFactory
												.fromResource(R.drawable.icon));
							}
							button_huatong.setImageResource(R.drawable.huatong);
							button_person.setEnabled(true);
							button_viewpoint.setEnabled(true);
							button_paihang.setEnabled(true);
							button_settings.setEnabled(true);
							// ////////////////////////////////////////////////
							Intent intent = new Intent();
							intent.putExtra("view_point_sure_to_update",
									view_point_sure_to_update);
							intent.putExtra("timeString", timeString);
							intent.putExtra("recodeTime", recodeTime);
							intent.setClass(MainActivity.this,
									Huatong_dialog.class);

							startActivity(intent);
							// timeText.setText("录音时间：" + ((int)recodeTime));
						}
					}
					break;
				}
				return false;
			}
		});

		button_paihang.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 3;
				// 跳转到排行activity
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, imap.rank.RankListActivity.class);
				startActivity(intent);
			}
		});

		button_settings.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				select_button = 4;
				// 跳转到设置activity
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, imap.setting.SettingActivity.class);
				startActivity(intent);
			}
		});
	}

	/*************************************************************************/
	/**
	 * 初始化方向传感器
	 */
	private void initOritationListener() {
		myOrientationListener = new MyOrientationListener(
				getApplicationContext());
		myOrientationListener
				.setOnOrientationListener(new OnOrientationListener() {
					public void onOrientationChanged(float x) {
						mXDirection = (int) x;

						// 构造定位数据
						MyLocationData locData = new MyLocationData.Builder()
								.accuracy(mCurrentAccracy)
								// 此处设置开发者获取到的方向信息，顺时针0-360
								.direction(mXDirection)
								.latitude(mCurrentLantitude)
								.longitude(mCurrentLongitude).build();
						// 设置定位数据
						mBaiduMap.setMyLocationData(locData);
						MyLocationConfiguration config = new MyLocationConfiguration(
								mCurrentMode, true, null);
						mBaiduMap.setMyLocationConfigeration(config);

					}
				});

	}

	public void lightallmarkers() {
		for (int i = 0; i < markers.size(); i++) {
			markers.get(i).setIcon(
					BitmapDescriptorFactory
							.fromResource(R.drawable.icon_choosing));
		}
	}

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
	//	aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
	//	Intent i = new Intent(MainActivity.this, WalkListen.class);
	//	PendingIntent pi_d = PendingIntent.getActivity(MainActivity.this, 0, i,
	//			PendingIntent.FLAG_UPDATE_CURRENT);
	//	aManager.cancel(pi_d);
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

			if (walk_listen == 1) {
				Toast.makeText(MainActivity.this, "请先关闭边走边听功能，然后退出",
						Toast.LENGTH_SHORT).show();
				return true;
			} else {
				if (secondTime - firstTime > 1600) {// 如果两次按键时间间隔大于1600毫秒，则不退出
					Toast.makeText(MainActivity.this, "再按一次退出程序...",
							Toast.LENGTH_SHORT).show();
					firstTime = secondTime;// 更新firstTime
					return true;
				} else {
					System.exit(0);// 否则退出程序
				}
			}
		}
		return super.onKeyUp(keyCode, event);
	}

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
					.direction(mXDirection).latitude(location.getLatitude())
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
			mCurrentLantitude = location.getLatitude();
			mCurrentLongitude = location.getLongitude();
			mCurrentAccracy = location.getRadius();
			final MyAppData mylatlong = (MyAppData)getApplication();
			mylatlong.setmylabellat(mCurrentLantitude);
			mylatlong.setmylabellong(mCurrentLongitude);

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
			mCurrentLantitude = location.getLatitude();
			mCurrentLongitude = location.getLongitude();
			Location location_temp = new Location("GPS");// 测试用的
			location_temp.setLatitude(location.getLatitude());
			location_temp.setLongitude(location.getLongitude());
			updateView(location_temp);
			// logMsg(sb.toString());
			// ///
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	// 测试用的函数updateView
	public void updateView(Location newLocation) {
		if (newLocation != null) {
			final MyAppData mylatlong = (MyAppData)getApplication();
			StringBuilder sb = new StringBuilder();
			sb.append("经度 \n");
			sb.append(newLocation.getLongitude()+"\n");
			sb.append(mylatlong.getmylabellong()+"\n");
			sb.append("\n纬度 \n");
			sb.append(newLocation.getLatitude()+"\n");
			sb.append(mylatlong.getmylabellat()+"\n");
			sb.append("\n select:\n");
			sb.append(select_button);
			show.setText(sb.toString());
		} else {
			show.setText("");
		}
	}

	/********************************************************************/

	public void init() {
		// TODO 自动生成的方法存根
		// 添加景点坐标于markers
		BitmapDescriptor ooa = BitmapDescriptorFactory
				.fromResource(R.drawable.icon);

		// LatLng llA = new LatLng(39.996987, 116.313082);
		// OverlayOptions option = new MarkerOptions().position(llA).icon(ooa)
		// .zIndex(9).draggable(true);
		// marker1 = (Marker) (mBaiduMap.addOverlay(option));

		Toast.makeText(MainActivity.this, "正在载入景点。。。。。。", Toast.LENGTH_SHORT)
				.show();

		SharedPreferences sp = MainActivity.this.getSharedPreferences("imap",
				MODE_PRIVATE);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");

		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.updateSpot, 0 + "");
		int returnCode = netthread.beginDeal();

		if (returnCode == 0) {
			viewspotlist = netthread.getSpotsList();
			
			for (int i = 0; i < viewspotlist.size(); ++i)
			{
				Marker mark = (Marker) mBaiduMap.addOverlay(
						new MarkerOptions()
						.position(new LatLng(viewspotlist.get(i).getLatitude(), viewspotlist.get(i).getLongitude()))
						.icon(ooa)
						.zIndex(9).draggable(false)
						);
				markers.add(mark);
			
				if (viewspotlist.get(i).getVisible() == 0)
				{
					//System.out.println(viewspotlist.get(i).getName() + " " + viewspotlist.get(i).getVisible());
					mark.setVisible(false);
				}
			}
			Toast.makeText(MainActivity.this, "景点载入成功！", Toast.LENGTH_SHORT)
					.show();
		} else if (returnCode == -1) {
			Toast.makeText(MainActivity.this, "网络错误！", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(MainActivity.this,
					Variable.errorCode[returnCode] + "！", Toast.LENGTH_SHORT)
					.show();
		}
	}

	


	
	
	/** 显示正在录音的图标 */
	private void showVoiceDialog() {
		dialog = new Dialog(MainActivity.this, R.style.MyDialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.talk_layout);
		dialog_image = (ImageButton) dialog.findViewById(R.id.talk_log);
		dialog.show();
	}

	/** 录音时间太短时Toast显示 */
	void showWarnToast() {
		Toast toast = new Toast(MainActivity.this);
		LinearLayout linearLayout = new LinearLayout(MainActivity.this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 20);

		// 定义一个ImageView
		ImageView imageView = new ImageView(MainActivity.this);
		imageView.setImageResource(R.drawable.icon_chat_talk_no); // 图标

		TextView mTv = new TextView(MainActivity.this);
		mTv.setText("时间太短   录音失败");
		mTv.setTextSize(14);
		mTv.setTextColor(Color.WHITE);// 字体颜色
		// mTv.setPadding(0, 10, 0, 0);

		// 将ImageView和ToastView合并到Layout中
		linearLayout.addView(imageView);
		linearLayout.addView(mTv);
		linearLayout.setGravity(Gravity.CENTER);// 内容居中
		linearLayout.setBackgroundResource(R.drawable.record_bg);// 设置自定义toast的背景

		toast.setView(linearLayout);
		toast.setGravity(Gravity.CENTER, 0, 0);// 起点位置为中间 100为向下移100dp
		toast.show();
	}

	// 录音Dialog图片随声音大小切换
	void setDialogImage() {

		if (voiceValue < 200.0) {
			dialog_image.setImageResource(R.drawable.record_animate_01);
		} else if (voiceValue > 200.0 && voiceValue < 400) {
			dialog_image.setImageResource(R.drawable.record_animate_02);
		} else if (voiceValue > 400.0 && voiceValue < 800) {
			dialog_image.setImageResource(R.drawable.record_animate_03);
		} else if (voiceValue > 800.0 && voiceValue < 1600) {
			dialog_image.setImageResource(R.drawable.record_animate_04);
		} else if (voiceValue > 1600.0 && voiceValue < 3200) {
			dialog_image.setImageResource(R.drawable.record_animate_05);
		} else if (voiceValue > 3200.0 && voiceValue < 5000) {
			dialog_image.setImageResource(R.drawable.record_animate_06);
		} else if (voiceValue > 5000.0 && voiceValue < 7000) {
			dialog_image.setImageResource(R.drawable.record_animate_07);
		} else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_08);
		} else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_09);
		} else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_10);
		} else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_11);
		} else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_12);
		} else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_13);
		} else if (voiceValue > 28000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_14);
		}
	}

	/** 录音计时线程 */
	private void myThread() {
		timeThread = new Thread(ImageThread);
		timeThread.start();
	}

	/** 录音线程 */
	private Runnable ImageThread = new Runnable() {

		@Override
		public void run() {
			recodeTime = 0.0f;
			// 如果是正在录音状态
			while (RECODE_STATE == RECORD_ING) {
				if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
					handler.sendEmptyMessage(0x10);
				} else {
					try {
						Thread.sleep(200);

						recodeTime += 0.2;
						if (RECODE_STATE == RECORD_ING) {
							voiceValue = recorder.getAmplitude();
							handler.sendEmptyMessage(0x11);
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}
			}

		}

		Handler handler = new Handler() {
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case 0x10:
					// 录音超过15秒自动停止,录音状态设为语音完成
					if (RECODE_STATE == RECORD_ING) {
						RECODE_STATE = RECODE_ED;
						// 如果录音图标正在显示的话,关闭显示图标
						if (dialog.isShowing()) {
							dialog.dismiss();
						}

						// 停止录音
						recorder.stop();
						voiceValue = 0.0;

						// 如果录音时长小于1秒，显示录音失败的图标
						if (recodeTime < 1.0) {
							showWarnToast();
							// timeText.setText("");
							// recordBt.setText("按住录音");
							RECODE_STATE = RECORD_NO;
						} else {
							// recordBt.setText("按住录音");
							// timeText.setText("录音时间:" + ((int) recodeTime));
						}
					}
					break;

				case 0x11:
					// timeText.setText("");
					// recordBt.setText("正在录音");
					setDialogImage();
					break;
				}
			};
		};
	};
}
