package imap.main;

import imap.util.MyRecorder;
import imap.util.ParseTimeUtil;

import java.io.File;
import java.io.IOException;






import com.example.imap.R;

import android.R.integer;
import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class OneRecorderActivity extends Activity {

	/** 录音按钮 */
	private Button recordBt;

	/** 播放按钮 */
	private ImageButton playBt;

	/** 录音时间 */
	private TextView timeText;

	/** 语音音量显示 */
	private Dialog dialog;

	/** MediaPlayer */
	private MediaPlayer media;

	/** MyRecorder */
	private MyRecorder recorder;

	/** 更新时间的线程 */
	private Thread timeThread;
	
	/**更新进度条的线程*/
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice_one);
		this.initViews();
	}

	/** 初始化 */
	private void initViews() {
		recordBt = (Button) findViewById(R.id.bt_recorder);
		playBt = (ImageButton) findViewById(R.id.bt_play);
		bar = (ProgressBar) findViewById(R.id.progressBar1);
		timeText = (TextView) findViewById(R.id.time);

			Intent intent = this.getIntent();
			Bundle bundle = intent.getExtras(); 
			int view = bundle.getInt("view_point_sure_to_update");
			String time = bundle.getString("timeString");
			
		
				// 如果不是正在播放
				if (!playState) {
					// 实例化MediaPlayer对象
					media = new MediaPlayer();
					File file = new File(Environment
							.getExternalStorageDirectory(), "myvoice/voice_"+view+"_"+time+".amr");
					try {
						// 设置播放资源
						media.setDataSource(file.getAbsolutePath());
						// 准备播放
						media.prepare();
						// 开始播放
						media.start();
						timeText.setVisibility(View.GONE);
						bar.setVisibility(View.VISIBLE);
						
						bar.setMax(((int)media.getDuration()));
						System.out.println("count:"+bar.getMax());
						barUpdate();
						// 改变播放的标志位
						playState = true;

						// 设置播放结束时监听
						media.setOnCompletionListener(new OnCompletionListener() {

							@Override
							public void onCompletion(MediaPlayer mp) {
								if (playState) {
									playState = false;
									finish();
								}
							}
						});
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					// 如果正在播放
					if (media.isPlaying()) {
						media.stop();
						playState = false;
					} else {
						playState = false;
					}

				}
	}

	

	
	
	
	/**进度条线程*/
	private void barUpdate(){
		barThread = new Thread(BarUpdateThread);
		barThread.start();
	}

	/**进度条更新线程*/
	private Runnable BarUpdateThread = new Runnable() {
		
		@Override
		public void run() {

			
			for(bar.getProgress();bar.getProgress()<=bar.getMax();) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				handler.sendEmptyMessage(0x12);
			}
		}
		
		Handler handler = new Handler(){
			public void handleMessage(android.os.Message msg) {
				if(msg.what==0x12){
				
					bar.setProgress(((int)media.getCurrentPosition()));
					//System.out.println(bar.getProgress());
				}
			};
		};
	};
	
	
	
}
