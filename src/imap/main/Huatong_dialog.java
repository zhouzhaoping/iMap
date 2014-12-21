package imap.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import com.example.imap.R;






import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

public class Huatong_dialog extends Activity {
	
	int view_point_sure_to_update;
	String timeString;
	int recodeTime;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.huatong_dialog);
		Intent intent = this.getIntent();
		Bundle bundle = intent.getExtras(); 
		view_point_sure_to_update = bundle.getInt("view_point_sure_to_update");
		timeString = bundle.getString("timeString");
		recodeTime = bundle.getInt("recodeTime");
		
		final RadioGroup sexRadioGroup = (RadioGroup) findViewById(R.id.sex);
		
		final CheckBox checkBoxlanguage_c = (CheckBox) findViewById(R.id.chinese);
		final CheckBox checkBoxlanguage_e = (CheckBox) findViewById(R.id.english);
		final EditText editText_name = (EditText) findViewById(R.id.speak_input);
		final EditText editText_brief = (EditText) findViewById(R.id.speak_brief_input);
		Button sureButton  =(Button)findViewById(R.id.sure);	
		Button cancelButton  =(Button)findViewById(R.id.cancel);
		Button playButton  =(Button)findViewById(R.id.play);
		
		
		
		sureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				//标签文件地址
				File file_txt = new File(Environment
						.getExternalStorageDirectory(),
						"myvoice/voice_" + view_point_sure_to_update
								+ "_" + timeString + ".txt");
				try {
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(file_txt));
					//添加选择框的值
					RadioButton sexRadioButton = (RadioButton)findViewById(sexRadioGroup.getCheckedRadioButtonId());
					writer.write(editText_name.getText().toString() + "\n");
					writer.write(editText_brief.getText().toString()+ "\n");
					writer.write(sexRadioButton.getText().toString()+ "\n");
					if(checkBoxlanguage_c.isChecked())
						writer.write(checkBoxlanguage_c.getText().toString()+ "\n");
					if(checkBoxlanguage_e.isChecked())
						writer.write(checkBoxlanguage_e.getText().toString()+ "\n");
					
					writer.close();

				} catch (Exception e) {

				}
				finish();
			}
		});
		cancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				File file_amr = new File(Environment
						.getExternalStorageDirectory(),
						"myvoice/voice_" + view_point_sure_to_update
								+ "_" + timeString + ".amr");
				if (file_amr.exists())
					file_amr.delete();
				finish();
			}
		});
		playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				
				// 播放语音
				Intent intent = new Intent();
				intent.putExtra("view_point_sure_to_update",
						view_point_sure_to_update);
				intent.putExtra("timeString", timeString);
				intent.setClass(Huatong_dialog.this,
						OneRecorderActivity.class);

				startActivity(intent);
			}
		});
		
		
	}
}
		/*final AlertDialog.Builder alert_recorder = new AlertDialog.Builder(this);
	
		// 开始弹出对话框,对话框里有播放，确定，取消按钮，
		// 然后将select=-1，sure_choose_marker = 0;话筒图片还原
		// 显示弹窗
		final TableLayout labeLayout = (TableLayout) getLayoutInflater()
				.inflate(R.layout.label, null);
		alert_recorder.setView(labeLayout);
		alert_recorder.setMessage("录音时间：" + ((int) recodeTime));

		// //////////////////////////////////////////////////////////

		alert_recorder.setCancelable(false);
		alert_recorder.setIcon(R.drawable.huatong);
		alert_recorder.setTitle("录音结束");

		

		alert_recorder.setPositiveButton("播放",
				new DialogInterface.OnClickListener() {
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
						// 播放语音
						Intent intent = new Intent();
						intent.putExtra("view_point_sure_to_update",
								view_point_sure_to_update);
						intent.putExtra("timeString", timeString);
						intent.setClass(Huatong_dialog.this,
								OneRecorderActivity.class);

						startActivity(intent);

					}
				});
		alert_recorder.setNeutralButton("确定",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						MediaPlayer player = new MediaPlayer();
						// SimpleDateFormat sDateFormat = new
						// SimpleDateFormat("yyyyMMddhhmmss");
						File file = new File(Environment
								.getExternalStorageDirectory(),
								"myvoice/voice_" + view_point_sure_to_update
										+ "_" + timeString + ".amr");
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

						try {
							Field field = dialog.getClass().getSuperclass()
									.getDeclaredField("mShowing");
							field.setAccessible(true);
							field.set(dialog, true);
						} catch (Exception e) {
							e.printStackTrace();
						}

						// 添加标签，生成和voice同名的文档记录
						File file_txt = new File(Environment
								.getExternalStorageDirectory(),
								"myvoice/voice_" + view_point_sure_to_update
										+ "_" + timeString + ".txt");
						// write_file();

						EditText log_name = (EditText) labeLayout
								.findViewById(R.id.log_name);
						EditText log_content = (EditText) labeLayout
								.findViewById(R.id.log_content);

						try {
							BufferedWriter writer = new BufferedWriter(
									new FileWriter(file_txt));

							writer.write(log_name.getText().toString() + "\n");
							writer.write(log_content.getText().toString()
									+ "\n");
							writer.close();

						} catch (Exception e) {

						}
					finish();
					}

				});
		alert_recorder.setNegativeButton("取消",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 取消，不做操作
						try {
							Field field = dialog.getClass().getSuperclass()
									.getDeclaredField("mShowing");
							field.setAccessible(true);
							field.set(dialog, true);
						} catch (Exception e) {
							e.printStackTrace();
						}
						finish();
					}
				});
		alert_recorder.create().show();

	}*/

