package com.example.meow.ScanCode;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.meow.Cat.Cat;
import com.example.meow.R;

public class scanCode extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGHT = 1000;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);

        handler = new Handler();
        // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(scanCode.this,
                        Cat.class);
                startActivity(intent);
                scanCode.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
