package com.example.meow.Cat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meow.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Cat_DailyFeed extends AppCompatActivity implements View.OnClickListener{

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    private Button wantToFeed;
    private TextView FeedDate;

    List<String> feedTime;

    CatFeedAdapter mTopAdapter;

    //需要适配的数据
    private String [] catFeedMessage = {"10:00   李花花喂了TA",
                                            "10:00   苍太喂了TA",
                                            "8:00   庞倩倩喂了TA",
                                            "7:00   阿淋喂了TA"};

    //置顶评论列表和评论列表
    private ListView mListViewCatFeedMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__daily_feed);

        //设置顶部导航栏
        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("喂养日志");
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

        //获取系统时间
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = sDateFormat.format(new java.util.Date());
        FeedDate = (TextView)findViewById(R.id.tv_currentDate);
        FeedDate.setText(date);


        feedTime = new ArrayList<String>();

        wantToFeed = (Button) findViewById(R.id.btn_wantToFeed) ;
        wantToFeed.setOnClickListener(this);

        //初始化ListView控件
        mListViewCatFeedMessage = (ListView) findViewById(R.id.lv_catFeedMessage);
        //创建一个Adapter的实例
        mTopAdapter = new CatFeedAdapter();
        //设置Adapter
        mListViewCatFeedMessage.setAdapter(mTopAdapter);

    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //保存到数据库，并返回
            case R.id.btn_wantToFeed:
                SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh:mm");
                String time = sDateFormat.format(new java.util.Date());
                feedTime.add(time.substring(11));
                mTopAdapter.notifyDataSetChanged();
                break;
        }//end switch
    }//end onClick

    //适配器
    class CatFeedAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return catFeedMessage.length + feedTime.size();
        }

        //得到item代表的对象
        @Override
        public Object getItem(int position) {
            return catFeedMessage[position];
        }

        //得到item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //将list_item.xml文件找出来并转换成vi对象
            View view = View.inflate(Cat_DailyFeed.this, R.layout.activity_cat_daily_feed_list_item, null);
            //加上喂养记录
            TextView tv_name = (TextView) view.findViewById(R.id.tv_feedRecord);
            if(position < feedTime.size()){
                tv_name.setText(feedTime.get(position) + "   瓜皮的id酱喂了TA");
            }
            else{
                tv_name.setText(catFeedMessage[position-feedTime.size()]);
            }

            return view;
        }
    }
}
