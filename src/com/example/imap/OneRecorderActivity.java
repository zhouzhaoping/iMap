package com.example.imap;

import java.io.File;
import java.io.IOException;



import com.example.util.MyRecorder;
import com.example.util.ParseTimeUtil;

import android.app.Activity;
import android.app.Dialog;
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

		// 录音按钮监听
		recordBt.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:// 按下
					// 如果当前不是正在录音状态，开始录音
					if (RECODE_STATE != RECORD_ING) {
						recorder = new MyRecorder("voice");
						RECODE_STATE = RECORD_ING;
						// 显示录音情况
						showVoiceDialog();
						// 开始录音
						recorder.start();
						timeText.setVisibility(View.VISIBLE);
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

						if (recodeTime < MIX_TIME) {
							showWarnToast();
							recordBt.setText("按住录音");
							RECODE_STATE = RECORD_NO;
						} else {
							recordBt.setText("按住录音");
							MediaPlayer player = new MediaPlayer();
							File file = new File(Environment
									.getExternalStorageDirectory(), "myvoice/voice.amr");
							try {
								player.setDataSource(file.getAbsolutePath());
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (SecurityException e) {
								e.printStackTrace();
							} catch (IllegalStateException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
							timeText.setText("录音时间：" + ((int)recodeTime));
						}
					}
					break;
				}
				return false;
			}
		});

		// 播放语音
		playBt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 如果不是正在播放
				if (!playState) {
					// 实例化MediaPlayer对象
					media = new MediaPlayer();
					File file = new File(Environment
							.getExternalStorageDirectory(), "myvoice/voice.amr");
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
		});

	}

	

	/** 显示正在录音的图标 */
	private void showVoiceDialog() {
		dialog = new Dialog(OneRecorderActivity.this, R.style.AppTheme);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.talk_layout);
		dialog_image = (ImageButton) dialog.findViewById(R.id.talk_log);
		dialog.show();
	}

	/** 录音时间太短时Toast显示 */
	void showWarnToast() {
		Toast toast = new Toast(OneRecorderActivity.this);
		LinearLayout linearLayout = new LinearLayout(OneRecorderActivity.this);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setPadding(20, 20, 20, 20);

		// 定义一个ImageView
		ImageView imageView = new ImageView(OneRecorderActivity.this);
		imageView.setImageResource(R.drawable.icon_chat_talk_no); // 图标

		TextView mTv = new TextView(OneRecorderActivity.this);
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
		}else if (voiceValue > 200.0 && voiceValue < 400) {
			dialog_image.setImageResource(R.drawable.record_animate_02);
		}else if (voiceValue > 400.0 && voiceValue < 800) {
			dialog_image.setImageResource(R.drawable.record_animate_03);
		}else if (voiceValue > 800.0 && voiceValue < 1600) {
			dialog_image.setImageResource(R.drawable.record_animate_04);
		}else if (voiceValue > 1600.0 && voiceValue < 3200) {
			dialog_image.setImageResource(R.drawable.record_animate_05);
		}else if (voiceValue > 3200.0 && voiceValue < 5000) {
			dialog_image.setImageResource(R.drawable.record_animate_06);
		}else if (voiceValue > 5000.0 && voiceValue < 7000) {
			dialog_image.setImageResource(R.drawable.record_animate_07);
		}else if (voiceValue > 7000.0 && voiceValue < 10000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_08);
		}else if (voiceValue > 10000.0 && voiceValue < 14000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_09);
		}else if (voiceValue > 14000.0 && voiceValue < 17000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_10);
		}else if (voiceValue > 17000.0 && voiceValue < 20000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_11);
		}else if (voiceValue > 20000.0 && voiceValue < 24000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_12);
		}else if (voiceValue > 24000.0 && voiceValue < 28000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_13);
		}else if (voiceValue > 28000.0) {
			dialog_image.setImageResource(R.drawable.record_animate_14);
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
					System.out.println(bar.getProgress());
				}
			};
		};
	};
	
	
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
							timeText.setText("");
							recordBt.setText("按住录音");
							RECODE_STATE = RECORD_NO;
						} else {
							recordBt.setText("按住录音");
							timeText.setText("录音时间:" + ((int) recodeTime));
						}
					}
					break;

				case 0x11:
					timeText.setText("");
					recordBt.setText("正在录音");
					setDialogImage();
					break;
				}
			};
		};
	};
}
