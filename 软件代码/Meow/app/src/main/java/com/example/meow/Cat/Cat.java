package com.example.meow.Cat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.meow.R;

public class Cat extends AppCompatActivity {

    //猫咪基本信息
    private Button catBasicMessage;
    //猫咪喂养日志
    private Button catDailyFeed;
    //猫咪用户评论
    private Button catUserComment;

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat);

        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("流浪猫");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //实例化按钮
        catBasicMessage = (Button) findViewById(R.id.btn_catBasicMessage);
        catDailyFeed = (Button) findViewById(R.id.btn_catDailyFeed);
        catUserComment = (Button) findViewById(R.id.btn_catUserComment);

        //设置按钮猫咪基本信息的点击事件
        catBasicMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置页面跳转
                Intent intent = new Intent(Cat.this, Cat_BasicMessage.class);
                startActivity(intent);
            }
        });

        //设置按钮猫咪喂养日志的点击事件
        catDailyFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置页面跳转
                Intent intent = new Intent(Cat.this, Cat_DailyFeed.class);
                startActivity(intent);
            }
        });

        //设置按钮猫咪用户评论的点击事件
        catUserComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //设置页面跳转
                Intent intent = new Intent(Cat.this, Cat_UserComment.class);
                startActivity(intent);
            }
        });
    }
}
