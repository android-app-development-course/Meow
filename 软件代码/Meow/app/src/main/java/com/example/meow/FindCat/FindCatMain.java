package com.example.meow.FindCat;

import android.app.TabActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.meow.R;

public class FindCatMain extends TabActivity {

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;
    private Button releaseFindCat;

    TabHost mTabHost;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    int currentView = 0;
    private static int maxTabIndex = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cat_main);

        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("寻猫");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        //setSupportActionBar(toolbar);
        //设置toolbar按钮发布猫圈
        releaseFindCat = (Button) findViewById(R.id.FunctionButton);
        releaseFindCat.setBackgroundResource(R.drawable.ic_camera);
        releaseFindCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FindCatMain.this,findCatFoundRelease.class);
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
        mTabHost.addTab(mTabHost.newTabSpec("寻猫启示").setIndicator("寻猫启示")
                .setContent(new Intent(this, findCat_Found.class)));
        mTabHost.addTab(mTabHost.newTabSpec("失猫认领")
                .setIndicator("失猫认领").setContent(new Intent(this, findCat_Lose.class)));
        mTabHost.setCurrentTab(0);

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
    class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
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
