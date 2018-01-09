package com.example.meow.FindCat;


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
 * Created by pipi on 2017/11/21.
 */

public class findCat_Lose extends Activity {

    private ListView mListView;

    //需要适配的数据
    private String[] names = {"喵呜小组","华师撸猫协会"};

    //需要适配的头像图片
    private int[] portrait = {R.drawable.user,R.drawable.scnu};

    //需要适配的地点
    private String[] place = {"华师西区","华师东区"};

    //需要适配的品种
    private String[] kind = {"橘猫","白猫"};

    //需要适配的性别
    private String[] sex = {"雌性","雄性"};

    //需要适配的猫圈语句
    private String[] describe = {"谁家的胖橘呀","谁家的白猫猫啊好可怜"};

    //需要适配的照片1
    private int[] photo_1 = {R.drawable.orangecat1,R.drawable.whitecat1};

    //需要适配的照片2
    private int[] photo_2 = {R.drawable.orangecat2,R.drawable.whitecat2};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cat_list);
        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.find_cat_lv);
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
            View view = View.inflate(findCat_Lose.this, R.layout.activity_find_cat_list_item, null);
            //发表人名称
            TextView tv_name = (TextView) view.findViewById(R.id.find_cat_name);
            tv_name.setText(names[position]);
            //发表人头像
            ImageView imageView = (ImageView) view.findViewById(R.id.find_cat_portrait);
            imageView.setBackgroundResource(portrait[position]);
            //猫咪地点
            TextView tv_place = (TextView) view.findViewById(R.id.find_cat_place);
            tv_place.setText(place[position]);
            //猫咪种类
            TextView tv_kind = (TextView) view.findViewById(R.id.find_cat_kind);
            tv_kind.setText(kind[position]);
            //猫咪性别
            TextView tv_sex = (TextView) view.findViewById(R.id.find_cat_sex);
            tv_sex.setText(sex[position]);
            //描述
            TextView tv_describe = (TextView) view.findViewById(R.id.find_cat_decribe);
            tv_describe.setText(describe[position]);
            //照片1
            ImageView imageView1 = (ImageView) view.findViewById(R.id.find_cat_photo1);
            imageView1.setBackgroundResource(photo_1[position]);
            //照片2
            ImageView imageView2 = (ImageView) view.findViewById(R.id.find_cat_photo2);
            imageView2.setBackgroundResource(photo_2[position]);
            //联系方框
            TextView tv_contact = (TextView) view.findViewById(R.id.find_cat_contact);
            tv_contact.setText("联系组织");
            return view;
        }
    }

}
