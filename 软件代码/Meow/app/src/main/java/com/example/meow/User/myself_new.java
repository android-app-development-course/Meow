package com.example.meow.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.meow.R;

public class myself_new extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself_new);

        title = (TextView)findViewById(R.id.toobar_title);
        title.setText("我的消息");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(" ");//设置主标题如果标题还在就用这个
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
