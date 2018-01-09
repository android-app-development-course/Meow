package com.example.meow.Cat_Circle;

/**
 * Created by win on 2017/11/20.
 */

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;
import com.example.meow.R;


public class cat_circle_main extends TabActivity {


    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;
    private Button releaseCatCircle;

    TabHost mTabHost;
    //滑动
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    int currentView = 0;
    private static int maxTabIndex = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_circle_main);

        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("猫圈");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        //设置toolbar按钮发布猫圈
        releaseCatCircle = (Button) findViewById(R.id.FunctionButton);
        releaseCatCircle.setBackgroundResource(R.drawable.ic_camera);
        releaseCatCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cat_circle_main.this,cat_circle_release.class);
                startActivity(intent);
            }
        });

        //设置tabhost
        initTabs();
    }

    private void initTabs() {

        mTabHost = getTabHost();
        mTabHost.setup(this.getLocalActivityManager());
        // 添加日志列表的tab,注意下面的setContent中的代码.是这个需求实现的关键
        mTabHost.addTab(mTabHost.newTabSpec("推荐").setIndicator("推荐").setContent(new Intent(this, cat_circle_commend.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("最新").setIndicator("最新").setContent(new Intent(this, cat_circle_new.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("附近").setIndicator("附近").setContent(new Intent(this, cat_circle_nearby.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("关注").setIndicator("关注").setContent(new Intent(this, cat_circle_focus.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.setCurrentTab(1);

        gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };
    }


    // 左右滑动刚好页面也有滑动效果
    class MyGestureDetector extends SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            TabHost tabHost = getTabHost();
            try {
                if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                    return false;
                // right to left swipe
                if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (currentView == maxTabIndex) {
                        currentView = 0;
                    } else {
                        currentView++;
                    }
                    tabHost.setCurrentTab(currentView);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (currentView == 0) {
                        currentView = maxTabIndex;
                    } else {
                        currentView--;
                    }
                    tabHost.setCurrentTab(currentView);
                }
            } catch (Exception e) {
            }
            return false;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }


}
