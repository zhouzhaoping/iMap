package com.example.imap;

import com.example.imap.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_signup);
	 findViews();
	 showResults();
	 setListensers(); 
	}
	 
	 long firstTime=0;
	 private Button button_comfirm;
	 private Button button_cancle;
	 private EditText view_username;
	 private EditText view_password;
	 private EditText view_password2;
	 	 
	 private void findViews()
	 {
		 button_comfirm = (Button) findViewById(R.id.signup_button);
		 button_cancle = (Button) findViewById(R.id.cancle_button);
		 view_username = (EditText) findViewById(R.id.username_edit);
		 view_password = (EditText) findViewById(R.id.password_edit);
		 view_password2 = (EditText) findViewById(R.id.password_edit2);
	}
	 private void showResults() {
	 }
	 
	 private void setListensers() {
		 button_comfirm.setOnClickListener(comfirmListener);
		 button_cancle.setOnClickListener(cancleListener);
	 }
	 
	 private Button.OnClickListener comfirmListener = new Button.OnClickListener()
	 {
		  public void onClick(View v)
		 {
			  String username = view_username.getText().toString();
			  String password = view_password.getText().toString();
			  String password2 = view_password2.getText().toString();
			  
			  if(username.isEmpty() || password.isEmpty() || password2.isEmpty())
				  Toast.makeText(SignupActivity.this, "用户名、密码不能为空！", 
			                 Toast.LENGTH_SHORT).show(); 
			  else if(!password.equals(password2))
			  {
				  Toast.makeText(SignupActivity.this, "两次输入密码不同！", 
			                 Toast.LENGTH_SHORT).show(); 
			  }
			  else
			  {
				  //TODO:发送注册信息，判断用户名是否已经存在
				  //NetThread.url = "http://" + username + "/cgi-bin/handler.py";
				  //NetThread netThread = new NetThread(password, -1, -1, -1);

				  SharedPreferences sp = SignupActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
				  Editor editor = sp.edit();
				  editor.putString("username", username);
				  editor.putString("password", password);
				  editor.commit();
				  
				  Intent intent = new Intent();
				  intent.setClass(SignupActivity.this, LoginActivity.class);
				  /*
				  Bundle bundle = new Bundle();
				  bundle.putString("KEY_USERNAME", username);
				  bundle.putString("KEY_PASSWORD", password);
				  intent.putExtras(bundle);
				  */
				  startActivity(intent);
				  Toast.makeText(SignupActivity.this, username+"注册成功！", 
			                 Toast.LENGTH_SHORT).show(); 
				  SignupActivity.this.finish();
			  }
		 }
	 };
	 private Button.OnClickListener cancleListener = new Button.OnClickListener()
	 {
		  public void onClick(View v)
		 {
			  Intent intent = new Intent();
			  intent.setClass(SignupActivity.this, LoginActivity.class);
			  
			  startActivity(intent);
			  SignupActivity.this.finish();
		 }
	 };
	 
	 @Override 
	    public boolean onKeyUp(int keyCode, KeyEvent event) { 
	        if (keyCode == KeyEvent.KEYCODE_BACK) { 
	            long secondTime = System.currentTimeMillis(); 
	            if (secondTime - firstTime > 1600) {//如果两次按键时间间隔大于1600毫秒，则不退出 
	                Toast.makeText(SignupActivity.this, "再按一次退出程序...", 
	                        Toast.LENGTH_SHORT).show(); 
	                firstTime = secondTime;//更新firstTime 
	                return true; 
	            } else { 
	                System.exit(0);//否则退出程序 
	            } 
	        } 
	        return super.onKeyUp(keyCode, event); 
	    } 
}