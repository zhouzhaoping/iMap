package imap.main;

import imap.nettools.Variable;

import java.io.BufferedWriter;
import java.io.File;
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
		
		final String []check_sex = {"未知","男生","女生","混合"};
		final String []check_language = {"未知","中文","英文","方言"};
		final String []check_sytle = {"其他","严肃","欢快","搞笑"};
		
		uploadButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				editText_name.getText().toString();
				editText_brief.getText().toString();
				// TODO 自动生成的方法存根
				File file_amr = new File(Variable.voicepath,
						"voice_" + view_point_sure_to_update
								+ "_" + timeString + ".amr");
				if (file_amr.exists())
					file_amr.delete();
				finish();
			}
		});

		sureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//标签文件地址
				File file_txt = new File(Variable.voicepath,
						"voice_" + view_point_sure_to_update
								+ "_" + timeString + ".txt");
				try {
					BufferedWriter writer = new BufferedWriter(
							new FileWriter(file_txt));
					//添加选择框的值
					RadioButton sexRadioButton = (RadioButton)findViewById(sexRadioGroup.getCheckedRadioButtonId());
					RadioButton languageRadioButton = (RadioButton)findViewById(languageRadioGroup.getCheckedRadioButtonId());
					RadioButton styleRadioButton = (RadioButton)findViewById(styleRadioGroup.getCheckedRadioButtonId());
					writer.write(editText_name.getText().toString() + "\n");
					writer.write(editText_brief.getText().toString()+ "\n");
					for(int i = 0 ;i<4;i++)
					{
						if(sexRadioButton.getText().toString().equals(check_sex[i]))
						{
							//System.out.println(sexRadioButton.getText().toString() +" "+ check_sex[i]);
							writer.write(i+ "\n");
						}
					}
					
					for(int i = 0 ;i<4;i++)
					{
						if(languageRadioButton.getText().toString().equals(check_language[i]))
						{
							//System.out.println(sexRadioButton.getText().toString() +" "+ check_sex[i]);
							writer.write(i+ "\n");
						}
					}
					for(int i = 0 ;i<4;i++)
					{
						if(styleRadioButton.getText().toString().equals(check_sytle[i]))
						{
							//System.out.println(sexRadioButton.getText().toString() +" "+ check_sex[i]);
							writer.write(i+ "\n");
						}
					}
					//writer.write(sexRadioButton.getText().toString()+ "\n");
					
				/*	if(checkBoxlanguage_c.isChecked())
						writer.write(checkBoxlanguage_c.getText().toString()+ "\n");
					if(checkBoxlanguage_e.isChecked())
						writer.write(checkBoxlanguage_e.getText().toString()+ "\n");
				*/	
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
}
		
