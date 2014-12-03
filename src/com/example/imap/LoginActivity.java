package com.example.imap;

import com.example.imap.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
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

public class LoginActivity extends Activity {
	 public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	 setContentView(R.layout.activity_login);
	 findViews();
	 showResults();
	 setListensers(); 
	}
	 
	 long firstTime=0;
	 private Button button_login;
	 private Button button_tosignup;
	 private EditText view_username;
	 private EditText view_password;
	 	 
	 private void findViews()
	 {
		 button_login = (Button) findViewById(R.id.login_button);
		 button_tosignup = (Button) findViewById(R.id.tosignup_button);
		 view_username = (EditText) findViewById(R.id.username_edit);
		 view_password = (EditText) findViewById(R.id.password_edit);
	}
	 private void showResults() {
		 SharedPreferences sp = LoginActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
		 String name = sp.getString("username", "");
		 String pw = sp.getString("password", "");
		 if (!name.isEmpty() && !pw.isEmpty())
		 {
			 view_username.setText(name);
			 view_password.setText(pw);
		 }
			 
	 }
	 
	 private void setListensers() {
		 button_login.setOnClickListener(loginListener);
		 button_tosignup.setOnClickListener(SignupListener);
	 }
	 
	 private Button.OnClickListener loginListener = new Button.OnClickListener()
	 {
		  public void onClick(View v)
		 {
			  String username = view_username.getText().toString();
			  String password = view_password.getText().toString();
			  
			  SharedPreferences sp = LoginActivity.this.getSharedPreferences("imap", MODE_PRIVATE);
			  Editor editor = sp.edit();
			  editor.putString("username", username);
			  editor.putString("password", password);
			  editor.commit();
			  
			  //TODO:登陆验证
			  //NetThread.url = "http://" + username + "/cgi-bin/handler.py";
			  //NetThread netThread = new NetThread(password, -1, -1, -1);
			  Intent intent = new Intent();
			  intent.setClass(LoginActivity.this, MainActivity.class);
			  /*
			  Bundle bundle = new Bundle();
			  bundle.putString("KEY_USERNAME", username);
			  bundle.putString("KEY_PASSWORD", password);
			  intent.putExtras(bundle);
			  */
			  startActivity(intent);
			  Toast.makeText(LoginActivity.this, username+"登录成功！", 
		                 Toast.LENGTH_SHORT).show(); 
			  LoginActivity.this.finish();
			  /*
			  if (netThread.sendPassword())
			  {
			   		Intent intent = new Intent();
					  intent.setClass(LoginActivity.this, MainActivity.class);
					  Bundle bundle = new Bundle();
					  bundle.putString("KEY_USERNAME", username);
					  bundle.putString("KEY_PASSWORD", password);
					  intent.putExtras(bundle);
					  startActivity(intent);
					  Toast.makeText(LoginActivity.this, "登录成功！", 
				                 Toast.LENGTH_SHORT).show(); 
					  LoginActivity.this.finish();
			  } else {
				  NetThread.url = "http://162.105.171.246/cgi-bin/handler.py";
				  Intent intent = new Intent();
				  intent.setClass(LoginActivity.this, MainActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putString("KEY_USERNAME", username);
				  bundle.putString("KEY_PASSWORD", password);
				  intent.putExtras(bundle);
				  startActivity(intent);
				  
				  Toast.makeText(LoginActivity.this, "IP地址或密码错误！", 
			                 Toast.LENGTH_SHORT).show(); 
				  LoginActivity.this.finish();
			  }*/
			  /*
			  if (username.equals("") || password.equals(""))
			  {
				  Toast.makeText(LoginActivity.this, "IP地址或密码不能为空！", 
			                 Toast.LENGTH_SHORT).show(); 
			  }
			  else if (username.equals("162.105.171.246") && password.equals("123456"))
			{
				// Close this Activity
				  Intent intent = new Intent();
				  intent.setClass(LoginActivity.this, MainActivity.class);
				  Bundle bundle = new Bundle();
				  bundle.putString("KEY_USERNAME", username);
				  bundle.putString("KEY_PASSWORD", password);
				  intent.putExtras(bundle);
				  startActivity(intent);
				  LoginActivity.this.finish();
			  } else {
				  Toast.makeText(LoginActivity.this, "IP地址或密码错误！", 
			                 Toast.LENGTH_SHORT).show(); 
			  }
			  */
		 }
	 };
	 private Button.OnClickListener SignupListener = new Button.OnClickListener()
	 {
		  public void onClick(View v)
		 {
			  Intent intent = new Intent();
			  intent.setClass(LoginActivity.this, SignupActivity.class);
			  /*
			  Bundle bundle = new Bundle();
			  bundle.putString("KEY_USERNAME", username);
			  bundle.putString("KEY_PASSWORD", password);
			  intent.putExtras(bundle);
			  */
			  startActivity(intent);
			  LoginActivity.this.finish();
		 }
	 };
	 
	 @Override 
	    public boolean onKeyUp(int keyCode, KeyEvent event) { 
	        if (keyCode == KeyEvent.KEYCODE_BACK) { 
	            long secondTime = System.currentTimeMillis(); 
	            if (secondTime - firstTime > 1600) {//如果两次按键时间间隔大于1600毫秒，则不退出 
	                Toast.makeText(LoginActivity.this, "再按一次退出程序...", 
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