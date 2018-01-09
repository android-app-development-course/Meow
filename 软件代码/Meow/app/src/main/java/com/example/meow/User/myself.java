package com.example.meow.User;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;

public class myself extends AppCompatActivity {

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    private TextView myCatCircle;
    private TextView myCatCircleNum;

    //数据库
    MeowDataBase meowDataBase;

    User user;

    private Button mycat;
    private Button mynew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);

        title = (TextView)findViewById(R.id.toobar_title);
        title.setText("我");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        meowDataBase = new MeowDataBase(myself.this);
        user = new User();

        myCatCircleNum = (TextView) findViewById(R.id.tv_catCircleNum);
        myCatCircleNum.setText(""+getCatCircleNum());

        //我的猫圈跳转
        myCatCircle = (TextView) findViewById(R.id.tv_catCircle);
        myCatCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(myself.this,userCatCircle.class);
                startActivity(intent);
            }
        });

        //我的猫咪跳转
        mycat =(Button)findViewById(R.id.mycat);
        mycat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(myself.this, myself_one.class);
                startActivity(intent);
            }
        });

        //我的消息跳转
        mynew =(Button)findViewById(R.id.mynew);
        mynew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(myself.this, myself_new.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        myCatCircleNum.setText(""+getCatCircleNum());
    }

    //获取猫圈数目
    Long getCatCircleNum(){
        //得到操作数据库的实例
        SQLiteDatabase db = meowDataBase.getReadableDatabase();
        // 调用查找书库代码并返回数据源
        Cursor cursor = db.rawQuery("select count(*)from cat_circle",null);
        //游标移到第一条记录准备获取数据
        cursor.moveToFirst();
        // 获取数据中的LONG类型数据
        Long count = cursor.getLong(0);
        return count;
    }
}
