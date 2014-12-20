package imap.setting;

import imap.main.LoginActivity;
import imap.nettools.Variable;

import org.json.JSONException;

import com.example.imap.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends Activity {
    
	LinearLayout range;
	TextView rangetext;
	LinearLayout mapdownload;
	LinearLayout help;
	LinearLayout about;
	
	private RadioOnClick OnClick;
	//private ListView areaListView;
	
	LinearLayout defaultvoice;
	LinearLayout onlywifi;
	LinearLayout skin;
	LinearLayout language;
	
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
    	range = (LinearLayout)findViewById(R.id.range);
    	rangetext = (TextView)findViewById(R.id.rangetext);
    	mapdownload = (LinearLayout)findViewById(R.id.mapdownload);
    	help = (LinearLayout)findViewById(R.id.help);
    	about = (LinearLayout)findViewById(R.id.about);
    	
    	defaultvoice = (LinearLayout)findViewById(R.id.defaultvoice);
    	onlywifi = (LinearLayout)findViewById(R.id.onlywifi);
    	skin = (LinearLayout)findViewById(R.id.skin);
    	language = (LinearLayout)findViewById(R.id.language);
    }

    private void showResults()
    {
    	SharedPreferences sp =SettingActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
    	int range = sp.getInt("range", 1);
    	rangetext.setText("边走边听范围：" + Variable.areas[range]);
    	OnClick = new RadioOnClick(range);
    }
    
    private void setListensers()
    {
    	range.setOnClickListener(new RadioClickListener());//setOnClickListener(rangeListener);
    	mapdownload.setOnClickListener(downloadListener);
    	help.setOnClickListener(helpListener);
    	about.setOnClickListener(aboutListener);
    	
    	defaultvoice.setOnClickListener(heheListener);
    	onlywifi.setOnClickListener(heheListener);
    	skin.setOnClickListener(heheListener);
    	language.setOnClickListener(heheListener);
    }
    

    private View.OnClickListener heheListener = new View.OnClickListener() {
        public void onClick(View v) {
        	 Toast.makeText(SettingActivity.this, "该功能暂未开放~", Toast.LENGTH_LONG).show();
        }
    };
    
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
    
    
    
    // 边走边听范围
    class RadioClickListener implements OnClickListener {
    	   @Override
    	   public void onClick(View v) {
    	    AlertDialog ad =new AlertDialog.Builder(SettingActivity.this).setTitle("选择距离")
    	    .setSingleChoiceItems(Variable.areas, OnClick.getIndex(), OnClick).create();
    	    //areaListView=ad.getListView();
    	    ad.show();
    	   }
    	     }
    class RadioOnClick implements DialogInterface.OnClickListener{
    	   private int index;
    	 
    	   public RadioOnClick(int index){
    	    this.index = index;
    	   }
    	   public void setIndex(int index){
    	    this.index=index;
    	   }
    	   public int getIndex(){
    	    return index;
    	   }
    	 
    	   public void onClick(DialogInterface dialog, int whichButton){
    	     setIndex(whichButton);
    	     Toast.makeText(SettingActivity.this, "您已经选择了 " +  ":" + Variable.areas[index], Toast.LENGTH_LONG).show();
    	     dialog.dismiss();
    	     
    	     SharedPreferences sp = SettingActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
			  Editor editor = sp.edit();
			  editor.putInt("range", index);
			  editor.commit();
			  
			  rangetext.setText("边走边听范围：" + Variable.areas[index]);
    	   }
    	 }
}