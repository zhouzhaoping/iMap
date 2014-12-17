package imap.setting;

import java.util.ArrayList;
import java.util.List;

import com.example.imap.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class HelpActivity extends Activity {

    private ViewPager myViewPager; //页卡内容

    private List<View> list; // 存放页卡

    private TextView dot1,dot2,dot3;    //这些点都是文字


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_help);
        initDot();
        initViewPager();
    }

    private void initDot(){
        dot1=(TextView) this.findViewById(R.id.textView1);      //这些点都是文字
        dot2=(TextView) this.findViewById(R.id.textView2);
        dot3=(TextView) this.findViewById(R.id.textView3);
    }

    private void initViewPager(){
        myViewPager=(ViewPager) this.findViewById(R.id.viewPager);
        list=new ArrayList<View>();

        LayoutInflater inflater=getLayoutInflater();

        View view =inflater.inflate(R.layout.help3, null);       //只是为了等下findviewbuid而独立拿出来赋给view

        list.add(inflater.inflate(R.layout.help1, null));
        list.add(inflater.inflate(R.layout.help2, null));
        list.add(view);

        myViewPager.setAdapter(new MyPagerAdapter(list));

        myViewPager.setOnPageChangeListener(new MyPagerChangeListener());
    }

    class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView(mListViews.get(arg1));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
            return mListViews.get(arg1);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == (arg1);
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }
    }

    class MyPagerChangeListener implements OnPageChangeListener{


        @Override
        public void onPageSelected(int arg0) {
            // TODO Auto-generated method stub
            switch (arg0) {                           //设置点的颜色
            case 0:
                dot1.setTextColor(Color.WHITE);
                dot2.setTextColor(Color.BLACK);
                dot3.setTextColor(Color.BLACK);
                break;

            case 1:
                dot1.setTextColor(Color.BLACK);
                dot2.setTextColor(Color.WHITE);
                dot3.setTextColor(Color.BLACK);
                break;

            case 2:
                dot1.setTextColor(Color.BLACK);
                dot2.setTextColor(Color.BLACK);
                dot3.setTextColor(Color.WHITE);
                break;

            }
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }



    }
}