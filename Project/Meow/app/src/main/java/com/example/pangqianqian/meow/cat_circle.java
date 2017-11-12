package com.example.pangqianqian.meow;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TabHost;
import android.widget.TabWidget;

public class cat_circle extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_circle);

        TabHost tabHost = (TabHost)findViewById(R.id.tabhost);
        tabHost.setup();

        //动态载入XML，而不需要Activity
        LayoutInflater i=LayoutInflater.from(this);
        i.inflate(R.layout.commend, tabHost.getTabContentView());
        i.inflate(R.layout.up_to_date, tabHost.getTabContentView());
        i.inflate(R.layout.nearby, tabHost.getTabContentView());
        i.inflate(R.layout.focus, tabHost.getTabContentView());

        tabHost.addTab(tabHost.newTabSpec("推荐").setIndicator("推荐").setContent(R.id.layout_commend));
        tabHost.addTab(tabHost.newTabSpec("最新").setIndicator("最新").setContent(R.id.layout_up_to_date));
        tabHost.addTab(tabHost.newTabSpec("附近").setIndicator("附近").setContent(R.id.layout_nearby));
        tabHost.addTab(tabHost.newTabSpec("关注").setIndicator("关注").setContent(R.id.layout_focus));


    }
}
