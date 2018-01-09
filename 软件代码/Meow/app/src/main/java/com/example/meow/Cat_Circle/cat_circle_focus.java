package com.example.meow.Cat_Circle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meow.R;

/**
 * Created by win on 2017/11/20.
 */

public class cat_circle_focus extends Activity {
    private ListView mListView;

    //需要适配的数据
    private String[] names = {"李洁莹", "陈玉淋", "庞倩婷", "詹萍"};

    //需要适配的头像图片
    private int[] head_photo = {R.drawable.lhh, R.drawable.cyl, R.drawable.pqt, R.drawable.zp};

    //需要适配的猫圈语句
    private String[] content = {"喵喵喵？情侣头像", "赵淦森老师找我要猫咪地图耶！", "开始吸猫后发现猫咪很可爱", "今日份撸猫+1"};

    //需要适配的照片1
    private int[] photo_1 = {R.drawable.lhh_1, R.drawable.cyl_1, R.drawable.pqt_1, R.drawable.zp_1};

    //需要适配的照片2
    private int[] photo_2 = {R.drawable.lhh_2, R.drawable.cyl_2, R.drawable.pqt_2, R.drawable.zp_2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_circle_list);
        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.cat_circle_lv);
        //创建一个Adapter的实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        //设置Adapter
        mListView.setAdapter(mAdapter);
    }

    class MyBaseAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return names.length;
        }

        //得到item代表的对象
        @Override
        public Object getItem(int position) {
            return names[position];
        }

        //得到item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //将list_item.xml文件找出来并转换成vi对象
            View view = View.inflate(cat_circle_focus.this, R.layout.cat_circle_list_item, null);
            //为猫圈加名字
            TextView tv_name = (TextView) view.findViewById(R.id.cat_circle_tv_name);
            tv_name.setText(names[position]);
            //为猫圈加头像
            ImageView imageView = (ImageView) view.findViewById(R.id.cat_circle_item_image);
            imageView.setBackgroundResource(head_photo[position]);
            //为猫圈加内容
            TextView tv_content = (TextView) view.findViewById(R.id.cat_circle_tv_content);
            tv_content.setText(content[position]);
            //为猫圈加照片1
            ImageView imageView1 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo1);
            imageView1.setBackgroundResource(photo_1[position]);
            //为猫圈加照片2
            ImageView imageView2 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo2);
            imageView2.setBackgroundResource(photo_2[position]);
            return view;
        }
    }

}
