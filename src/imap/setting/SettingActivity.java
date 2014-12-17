package imap.setting;

import com.example.imap.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class SettingActivity extends Activity {
    
	LinearLayout mapdownload;
	LinearLayout help;
	LinearLayout about;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

		findViews();
		showResults();
		setListensers();
    }
    
    private void findViews()
    {
    	mapdownload = (LinearLayout)findViewById(R.id.mapdownload);
    	help = (LinearLayout)findViewById(R.id.help);
    	about = (LinearLayout)findViewById(R.id.about);
    }

    private void showResults()
    {
    	
    }
    
    private void setListensers()
    {
    	mapdownload.setOnClickListener(downloadListener);
    	help.setOnClickListener(helpListener);
    	about.setOnClickListener(aboutListener);
    }
    
    private View.OnClickListener aboutListener = new View.OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent();
			intent.setClass(SettingActivity.this, AboutActivity.class);
			startActivity(intent);
        }
    };
    
    private View.OnClickListener helpListener = new View.OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent();
			intent.setClass(SettingActivity.this, HelpActivity.class);
			startActivity(intent);
        }
    };
    
   private View.OnClickListener downloadListener = new View.OnClickListener() {
        public void onClick(View v) {
        	Intent intent = new Intent();
			intent.setClass(SettingActivity.this, imap.main.OfflineDemo.class);
			startActivity(intent);
        }
    };
}