package com.example.meow.Cat;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;

public class CatUserCommentRelease extends AppCompatActivity implements View.OnClickListener{

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    private EditText CatUserComment;
    private Button Release;

    //SQLiteOpenHelper子类
    MeowDataBase meowDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_user_comment_release);

        //设置toolbar
        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("");
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

        meowDataBase = new MeowDataBase(CatUserCommentRelease.this);

        Release = (Button) findViewById(R.id.btn_release);
        Release.setOnClickListener(this);

        CatUserComment = (EditText)findViewById(R.id.et_comment);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //保存到数据库，并返回
            case R.id.btn_release:
                //保存数据
                SaveData();
                finish();
                break;
        }//end switch
    }//end onClick

    //保存数据到数据库中
    void SaveData()
    {
        //获取可写入的SQLiteDatabase对象
        SQLiteDatabase db = meowDataBase.getWritableDatabase();
        //创建ContentValues对象
        ContentValues values = new ContentValues();

        //将信息插入到数据库中
        values.put("comment",CatUserComment.getText().toString().trim());
        //插入到数据库中
        db.insert("cat_user_comment",null,values);
        //关闭数据库
        db.close();
    }

}
