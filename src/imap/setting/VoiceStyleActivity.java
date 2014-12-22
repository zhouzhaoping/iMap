package imap.setting;

import imap.nettools.Variable;

import com.example.imap.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class VoiceStyleActivity extends Activity {

	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_voicestyle);

		findView();
		setListener();
	}
	private RadioGroup sexRadioGroup;
	private RadioGroup languageRadioGroup;
	private RadioGroup styleRadioGroup;
	private Button sureButton;

	private void findView() {
		sexRadioGroup = (RadioGroup) findViewById(R.id.sex);
		languageRadioGroup = (RadioGroup) findViewById(R.id.language);
		styleRadioGroup = (RadioGroup) findViewById(R.id.style);
		
		SharedPreferences sp = VoiceStyleActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
    	int style = sp.getInt("style", 0);
    	int language = sp.getInt("language", 0);
    	int gender = sp.getInt("gender", 0);
    	int []int2id1 = {R.id.r10, R.id.r11, R.id.r12, R.id.r13, R.id.r14};
    	int []int2id2 = {R.id.r20, R.id.r21, R.id.r22, R.id.r23, R.id.r24};
    	int []int2id3 = {R.id.r30, R.id.r31, R.id.r32, R.id.r33, R.id.r34};
    	sexRadioGroup.check(int2id1[gender]);
		languageRadioGroup.check(int2id2[language]);
		styleRadioGroup.check(int2id3[style]);
		
		sureButton = (Button) findViewById(R.id.sure);
	}

	private void setListener() {
		sureButton.setOnClickListener(sureListener);
	}

	Button.OnClickListener sureListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			RadioButton sexRadioButton = (RadioButton) findViewById(sexRadioGroup
					.getCheckedRadioButtonId());
			RadioButton languageRadioButton = (RadioButton) findViewById(languageRadioGroup
					.getCheckedRadioButtonId());
			RadioButton styleRadioButton = (RadioButton) findViewById(styleRadioGroup
					.getCheckedRadioButtonId());

			
			int g = 0, l = 0, s = 0;
			for (int i = 0; i < 5; i++) {
				if (sexRadioButton.getText().toString()
						.equals(Variable.gender[i])) {
					g =i;
				}
			}

			for (int i = 0; i < 5; i++) {
				if (languageRadioButton.getText().toString()
						.equals(Variable.language[i])) {
					l = i;
				}
			}
			for (int i = 0; i < 5; i++) {
				if (styleRadioButton.getText().toString()
						.equals(Variable.style[i])) {
					s = i;
				}
			}
			
			SharedPreferences sp = VoiceStyleActivity.this
					.getSharedPreferences("imap", MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putInt("gender", g);
			editor.putInt("language", l);
			editor.putInt("style", s);
			editor.commit();
			
			SettingActivity.voicestyletext.setText(SettingActivity.style2String(g, l, s));
			Toast.makeText(VoiceStyleActivity.this, "修改成功！", Toast.LENGTH_LONG).show();
			finish();
		}
	};
}