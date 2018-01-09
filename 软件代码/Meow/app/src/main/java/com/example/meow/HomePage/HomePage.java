package com.example.meow.HomePage;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;
import android.widget.VideoView;

import com.example.meow.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    //页面滚动条
    private ScrollView scrollview;

    //顶部导航栏
    private Toolbar toolbar;
    private Button message;
    private TextView title;

    //VideoView
    private VideoView video;
    RelativeLayout rl_video;

    //banner
    //顶部滚动海报
    private Banner banner;
    public static List<?> images=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //设定滚动条
        scrollview = (ScrollView) findViewById(R.id.scrollview);
        scrollview.smoothScrollTo(0,20);

        //初始化
        initToolbar();
        initVideoView();
        initBanner();

    }

    //初始化顶部导航栏
    private void initToolbar(){
        //设置顶部导航栏
        message= (Button) findViewById(R.id.FunctionButton);
        title = (TextView) findViewById(R.id.toobar_title);

        message.setBackgroundResource(R.drawable.xiaoxi);
        title.setText("喵呜");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(HomePage.this, home_news.class);
                startActivity(intent);
            }
        });
    }

    //初始化视频播放器
    private void initVideoView(){
        video = (VideoView) findViewById(R.id.vv_video);
        rl_video = (RelativeLayout) findViewById(R.id.rl_video);
        rl_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //本地的视频在手机SD卡根目录添加一个视频
                String videoPath = "Video/rabbit.mp4" ;
                //获取手机内置SD卡的路径
                File SDPath = Environment.getExternalStorageDirectory();
                File file = new File(SDPath,videoPath);
                //获取文件的绝对路径
                String path = file.getAbsolutePath();

                //设置视频控制器
                video.setMediaController(new MediaController(HomePage.this));
                //播放完成回调
                video.setOnCompletionListener( new MyPlayerOnCompletionListener());
                //设置视频路径
                video.setVideoPath(path);
                video.requestFocus();
                //开始播放视频
                video.start();
            }
        });
    }

    //初始化滚动海报
    private void initBanner(){
        /*
        加载本地资源
        第三方加载器一般都支持（资源文件、文件、Uri、assets、raw、ContentProvider、sd卡资源）
        这里只是以glide举例加载本地（资源文件）图片，
        加载本地图片的具体方法请参考你使用的图片加载器，
        图片加载的链接格式是你在setImages时设置的格式，
        原样返回的，所以格式问题请仔细检查！*/
        Banner banner = (Banner) findViewById(R.id.banner);
        //本地图片数据（资源文件）
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.b1);
        list.add(R.mipmap.b2);
        list.add(R.mipmap.b3);
        banner.setImages(list)
                .setImageLoader(new GlideImageLoader())
                .start();
    }


    @Override
    protected void onRestart(){
        super.onRestart();
        //设置页面初始位置
        scrollview.smoothScrollTo(0,20);
    }

    /**
     * 详细使用
     *滚动海报
     * @param view
     */
    public void BannerDetailed(View view) {
        Banner banner = (Banner) findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(1500);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }


    //顶部栏菜单项搜索
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.search_view);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setIconified(false);
        SearchView.SearchAutoComplete et = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        et.setHint("请输入您需要的内容");
        //设置提示文本的颜色et.setHintTextColor(Color.WHITE);
        //设置输入文本的颜色et.setTextColor(Color.WHITE);
        //设置提交按钮是否可见searchView.setSubmitButtonEnabled(true);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(HomePage.this, "您输入的文本为" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    //监听视频播放结束
    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText( HomePage.this, "播放结束", Toast.LENGTH_SHORT).show();
        }
    }



}
