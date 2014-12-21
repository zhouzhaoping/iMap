package imap.me;

import imap.main.LoginActivity;
import imap.main.SignupActivity;
import imap.musiclist.PagerSlidingTabStrip;
import imap.nettools.NetThread;
import imap.nettools.Variable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.imap.R;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MeActivity extends FragmentActivity {

	private ViewPager pager;
	private PagerSlidingTabStrip tabs;

	private ImageView facepic;
	private TextView name;
	private TextView voicesum;
	private TextView likesum;
	private TextView signinsum;
	private ImageButton edit;

	private MeListFragment uploadFragment;
	private MeListFragment unuploadFragment;
	private MeListFragment viewspotsFragment;

	private DisplayMetrics dm;// 获取当前屏幕的密度

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_me);

		findViews();
		showResults();
		setListensers();
	}

	private void findViews() {
		dm = getResources().getDisplayMetrics();
		pager = (ViewPager) findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

		facepic = (ImageView) findViewById(R.id.facepic);
		name = (TextView) findViewById(R.id.name);
		voicesum = (TextView) findViewById(R.id.voicesum);
		likesum = (TextView) findViewById(R.id.likesum);
		signinsum = (TextView) findViewById(R.id.signinsum);
		edit = (ImageButton) findViewById(R.id.edit);
	}

	private void showResults() {
		// TODO 从数据库查询数据
		SharedPreferences sp = MeActivity.this.getSharedPreferences("imap",
				MODE_PRIVATE);
		String username = sp.getString("username", "");
		String password = sp.getString("password", "");
		int facenum = sp.getInt("face", 0);

		facepic.setImageResource(Variable.int2pic(facenum));
		name.setText("姓名：" + sp.getString("username", ""));

		NetThread netthread = new NetThread(username, password);
		netthread.makeParam(Variable.myInfo);
		int returnCode = netthread.beginDeal();

		if (returnCode == 0) {
			JSONObject obj = netthread.getReturn();
			try {
				voicesum.setText("上传语音："
						+ Integer.parseInt(obj.getString("totalUploads")));
				likesum.setText("获得点赞："
						+ Integer.parseInt(obj.getString("totalLikes")));
				signinsum.setText("签到景点："
						+ Integer.parseInt(obj.getString("totalMarks")));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (returnCode == -1) {
			Toast.makeText(MeActivity.this, "网络错误！获取用户信息失败！",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(MeActivity.this,
					Variable.errorCode[returnCode] + "！获取用户信息失败！",
					Toast.LENGTH_SHORT).show();
		}
	}

	private void setListensers() {
		pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		tabs.setViewPager(pager);
		setTabsValue();

		edit.setOnClickListener(editListener);
	}

	/**
	 * 对PagerSlidingTabStrip的各项属性进行赋值。
	 */
	private void setTabsValue() {
		// 设置Tab是自动填充满屏幕的
		tabs.setShouldExpand(true);
		// 设置Tab的分割线是透明的
		tabs.setDividerColor(Color.TRANSPARENT);
		// 设置Tab底部线的高度
		tabs.setUnderlineHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 1, dm));
		// 设置Tab Indicator的高度
		tabs.setIndicatorHeight((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 4, dm));
		// 设置Tab标题文字的大小
		tabs.setTextSize((int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_SP, 16, dm));
		// 设置Tab Indicator的颜色
		tabs.setIndicatorColor(Color.parseColor("#45c01a"));
		// 设置选中Tab文字的颜色 (这是我自定义的一个方法)
		tabs.setSelectedTextColor(Color.parseColor("#45c01a"));
		// 取消点击Tab时的背景色
		tabs.setTabBackground(0);
	}

	public class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		private final String[] titles = { "未上传", "已上传", "已签到" };

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public int getCount() {
			return titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			System.out.println(position);
			switch (position) {
			case 0:
				if (uploadFragment == null) {
					uploadFragment = new MeListFragment(0);
				}
				return uploadFragment;
			case 1:
				if (unuploadFragment == null) {
					unuploadFragment = new MeListFragment(1);
				}
				return unuploadFragment;
			case 2:
				if (viewspotsFragment == null) {
					viewspotsFragment = new MeListFragment(2);
				}
				return viewspotsFragment;
			default:
				return null;
			}
		}

	}

	private Button.OnClickListener editListener = new Button.OnClickListener() {
		public void onClick(View v) {
			Toast.makeText(MeActivity.this, "进入修改个人信息界面！", Toast.LENGTH_SHORT)
					.show();
		}
	};
}