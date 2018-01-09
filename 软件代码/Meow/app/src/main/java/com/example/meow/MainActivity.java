package com.example.meow;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.meow.FindCat.FindCatMain;
import com.example.meow.HomePage.HomePage;
import com.example.meow.User.myself;
import com.example.meow.Cat_Circle.cat_circle_main;
import com.example.meow.ScanCode.scanCode;

public class MainActivity extends TabActivity {

    private TabHost mTabHost;

    /*private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    private GestureDetector gestureDetector;
    View.OnTouchListener gestureListener;
    int currentView = 0;
    private static int maxTabIndex = 4;
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置tabhost
        initTab();
    }


    private void initTab() {

        this.mTabHost = getTabHost();
        mTabHost.setup(this.getLocalActivityManager());


        mTabHost.addTab(buildTabSpec("homepage", "首页",
                R.drawable.tab_menu_homepage, new Intent(this, HomePage.class)));

        mTabHost.addTab(buildTabSpec("findcat", "寻猫",
                R.drawable.tab_menu_findcat, new Intent(this, FindCatMain.class)));

        mTabHost.addTab(buildTabSpec("scancode", "扫一扫",
                R.drawable.tab_menu_scancode, new Intent(this, scanCode.class)));

        mTabHost.addTab(buildTabSpec("catcircle", "猫圈",
                R.drawable.tab_menu_catcircle, new Intent(this, cat_circle_main.class)));

        mTabHost.addTab(buildTabSpec("myself", "我",
                R.drawable.tab_menu_myself, new Intent(this, myself.class)));

        //为扫一扫设置点击事件，页面跳转，不保留底部导航栏
        mTabHost.getTabWidget().getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,scanCode.class);
                startActivity(intent);
            }
        });

      /*  gestureDetector = new GestureDetector(new MyGestureDetector());
        gestureListener = new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gestureDetector.onTouchEvent(event)) {
                    return true;
                }
                return false;
            }
        };*/

    }

    private TabHost.TabSpec buildTabSpec(String tag, String resLabel,
                                         int resIcon, final Intent content) {
        //新建一个View来获得图标和名称
        View view = View.inflate(MainActivity.this, R.layout.bottom_tab, null);
        TextView tabText = (TextView) view.findViewById(R.id.tab_text);
        ImageView tabImage = (ImageView) view.findViewById(R.id.tab_image);
        tabText.setText(resLabel);
        tabImage.setBackgroundResource(resIcon);
        return MainActivity.this.mTabHost.newTabSpec(tag).setIndicator(view).setContent(content);
        //return this.mTabHost.newTabSpec(tag).setIndicator(resLabel,
        //      getResources().getDrawable(resIcon)).setContent(content);
    }

   /* // 左右滑动刚好页面也有滑动效果
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
                        if (currentView == 1) currentView++;
                        currentView++;
                    }
                    tabHost.setCurrentTab(currentView);
                } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                        && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                    if (currentView == 0) {
                        currentView = maxTabIndex;
                    } else {
                        if (currentView == 3) currentView--;
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
*/



}
