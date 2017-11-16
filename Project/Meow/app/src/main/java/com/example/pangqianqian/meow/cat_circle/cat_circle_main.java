package com.example.pangqianqian.meow.cat_circle;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import com.example.pangqianqian.meow.R;

public class cat_circle_main extends ActivityGroup {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_circle_main);
        //设置tabhost
        initTabs();
        //tabHost.setup();
    }

    private void initTabs(){
        TabHost mTabHost = (TabHost)findViewById(R.id.tabhost);
        mTabHost.setup(this.getLocalActivityManager());
        // 添加日志列表的tab,注意下面的setContent中的代码.是这个需求实现的关键
        mTabHost.addTab(mTabHost.newTabSpec("推荐").setIndicator("推荐").setContent(new Intent(this,cat_circle_commend.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("最新").setIndicator("最新").setContent(new Intent(this,cat_circle_new.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("附近").setIndicator("附近").setContent(new Intent(this,cat_circle_nearby.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
        mTabHost.addTab(mTabHost.newTabSpec("关注").setIndicator("关注").setContent(new Intent(this,cat_circle_focus.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));
    }
}
