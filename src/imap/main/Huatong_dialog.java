package imap.main;

import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.example.imap.R;








import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.Toast;

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
		final RadioGroup languageRadioGroup = (RadioGroup) findViewById(R.id.language);
		final RadioGroup styleRadioGroup = (RadioGroup) findViewById(R.id.style);
		//final CheckBox checkBoxlanguage_c = (CheckBox) findViewById(R.id.chinese);
		//final CheckBox checkBoxlanguage_e = (CheckBox) findViewById(R.id.english);
		final EditText editText_name = (EditText) findViewById(R.id.speak_input);
		final EditText editText_brief = (EditText) findViewById(R.id.speak_brief_input);
		Button uploadButton  =(Button)findViewById(R.id.upload);	
		Button sureButton  =(Button)findViewById(R.id.sure);	
		Button cancelButton  =(Button)findViewById(R.id.cancel);
		Button playButton  =(Button)findViewById(R.id.play);
		
		
		
		final String []check_sex = {"未知","男生","女生","混合","其他"};
		final String []check_language = {"未知","中文","英文","方言","其他"};
		final String []check_sytle = {"未知","正式","狂野","欢乐","其他"};
		
		uploadButton.setOnClickListener(new View.OnClickListener() {
			//
			@Override
			public void onClick(View v) {
				
				String spotid = MainActivity.viewspotlist.get(view_point_sure_to_update).getId() + "";
				String title = editText_name.getText().toString();
				String description = editText_brief.getText().toString();
				
				RadioButton sexRadioButton = (RadioButton)findViewById(sexRadioGroup.getCheckedRadioButtonId());
				RadioButton languageRadioButton = (RadioButton)findViewById(languageRadioGroup.getCheckedRadioButtonId());
				RadioButton styleRadioButton = (RadioButton)findViewById(styleRadioGroup.getCheckedRadioButtonId());
				
				String style = null;
		    	String language = null;
		    	String gender = null;
				for(int i = 0 ;i<5;i++)
				{
					if(sexRadioButton.getText().toString().equals(check_sex[i]))
					{
						style = i + "";
					}
				}
				
				for(int i = 0 ;i<5;i++)
				{
					if(languageRadioButton.getText().toString().equals(check_language[i]))
					{
						language = i + "";
					}
				}
				for(int i = 0 ;i<5;i++)
				{
					if(styleRadioButton.getText().toString().equals(check_sytle[i]))
					{
						gender = i + "";
					}
				}
				
				File file_amr = new File(Variable.voicepath,
						"voice_" + view_point_sure_to_update
								+ "_" + timeString + ".amr");
				
				String fileaString= null;
				try {
					// 读取文件
					FileInputStream fileInputStream = new FileInputStream(file_amr);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					int i;
					while ((i = fileInputStream.read()) != -1)
						byteArrayOutputStream.write(i);
					fileInputStream.close();
					
					// 把文件存在一个字节数组中
					byte[] filea = byteArrayOutputStream.toByteArray();
					byteArrayOutputStream.close();
					fileaString = new String(filea, "ISO-8859-1");
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				SharedPreferences sp = Huatong_dialog.this.getSharedPreferences("imap", 0);
				String username = sp.getString("username", "");
				String password = sp.getString("password", "");
				
				NetThread netthread = new NetThread(username, password);
				netthread.makeParam(Variable.sendVoice, spotid, fileaString, title, description, gender, language, style);
				int returnCode = netthread.beginDeal();
				
				if (returnCode == 0)
				{
					if (file_amr.exists())
						file_amr.delete();
					finish();
					
					Toast.makeText(Huatong_dialog.this, "语音上传成功！请静待处理~", 
				                 Toast.LENGTH_SHORT).show(); 
					finish();
				  }
				  else if (returnCode == -1)
				  {
					  Toast.makeText(Huatong_dialog.this, "网络错误！", 
				                 Toast.LENGTH_SHORT).show();
				  }
				  else
				  {
					  Toast.makeText(Huatong_dialog.this, Variable.errorCode[returnCode] + "！", 
				                 Toast.LENGTH_SHORT).show();
				  }
				
				
				
			}
		});

		sureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//标签文件地址
				
				try {
					
					if(editText_name.getText().toString().isEmpty())
					{
						 Toast.makeText(Huatong_dialog.this, "语音名称不能为空！", 
				                 Toast.LENGTH_SHORT).show(); 
						 return;
					}
					if(editText_name.getText().toString().length()> 10)
					{
						Toast.makeText(Huatong_dialog.this, "语音名称不能过长！", 
				                 Toast.LENGTH_SHORT).show(); 
						return;
						
					}
					if(editText_brief.getText().toString().length()> 80)
					{
						Toast.makeText(Huatong_dialog.this, "语音介绍不能过长！", 
				                 Toast.LENGTH_SHORT).show(); 
						return;
					}
					
					File file_txt = new File(Variable.voicepath,
							"voice_" + view_point_sure_to_update
									+ "_" + timeString + ".txt");
					
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(file_txt));
					//添加选择框的值
					RadioButton sexRadioButton = (RadioButton)findViewById(sexRadioGroup.getCheckedRadioButtonId());
					RadioButton languageRadioButton = (RadioButton)findViewById(languageRadioGroup.getCheckedRadioButtonId());
					RadioButton styleRadioButton = (RadioButton)findViewById(styleRadioGroup.getCheckedRadioButtonId());
					
					writer.write(editText_name.getText().toString() + "\n");
					writer.write(editText_brief.getText().toString()+ "\n");
					
					for(int i = 0 ;i<5;i++)
					{
						if(sexRadioButton.getText().toString().equals(check_sex[i]))
						{
							writer.write(i+ "\n");
						}
					}
					
					for(int i = 0 ;i<5;i++)
					{
						if(languageRadioButton.getText().toString().equals(check_language[i]))
						{
							
							writer.write(i+ "\n");
						}
					}
					for(int i = 0 ;i<5;i++)
					{
						if(styleRadioButton.getText().toString().equals(check_sytle[i]))
						{
							writer.write(i+ "\n");
						}
					}
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
				File file_amr = new File(Variable.voicepath,
						"voice_" + view_point_sure_to_update
								+ "_" + timeString + ".amr");
				if (file_amr.exists())
					file_amr.delete();
				finish();
			}
		});
		playButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
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
	
	@Override 
	 public boolean onKeyUp(int keyCode, KeyEvent event) 
	 {
		return true; 
	} 
}
		
