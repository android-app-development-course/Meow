package com.example.meow.Cat_Circle;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;
import com.example.meow.User.User;
import com.example.meow.share.shareUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by win on 2017/11/20.
 */

public class cat_circle_new extends Activity {

    private ListView mListView;

    MyBaseAdapter mAdapter;
    MeowDataBase meowDataBase;

    //从数据库读出来的猫圈内容
    List<String> catCircleContentList;

    List<Drawable> catCircle_image1;
    List<Drawable> catCircle_image2;

    //当前用户
    User user;

    //需要适配的数据
    private String[] names = {"庞倩婷", "詹萍", "李洁莹", "陈玉淋"};

    //需要适配的头像图片
    private int[] head_photo = {R.drawable.pqt, R.drawable.zp, R.drawable.lhh, R.drawable.cyl};

    //需要适配的猫圈语句
    private String[] content = {"开始吸猫后发现猫咪很可爱", "今日份撸猫+1", "喵喵喵？情侣头像", "赵淦森老师找我要猫咪地图耶！"};

    //需要适配的照片1
    private int[] photo_1 = {R.drawable.pqt_1, R.drawable.zp_1, R.drawable.lhh_1, R.drawable.cyl_1};

    //需要适配的照片2
    private int[] photo_2 = {R.drawable.pqt_2, R.drawable.zp_2, R.drawable.lhh_2, R.drawable.cyl_2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cat_circle_list);

        catCircleContentList = new ArrayList<String>();
        user = new User();

        catCircle_image1=new ArrayList<Drawable>();
        catCircle_image2=new ArrayList<Drawable>();

        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.cat_circle_lv);
        //实例化数据库对象并读取数据库的信息
        meowDataBase = new MeowDataBase(cat_circle_new.this);
        ReadData();

        //创建一个Adapter的实例
        mAdapter = new MyBaseAdapter();
        //设置Adapter
        mListView.setAdapter(mAdapter);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Refresh();
    }

    //刷新列表
    void Refresh(){
        ReadData();
        mAdapter.notifyDataSetChanged();
    }

    //从数据库读取内容
    void ReadData(){
        //获得可以读取数据的SQLiteDataBase对象
        SQLiteDatabase db = meowDataBase.getReadableDatabase();
        //游标
        Cursor cursor = db.query("cat_circle",null,
                null,null,null,
                null,null);

        if(cursor.getCount() == 0){
            //Toast.makeText(cat_circle_new.this,"暂无数据",Toast.LENGTH_SHORT).show();
        }
        else{
            try{
                //先清除数据
                catCircleContentList.clear();
                catCircle_image1.clear();
                catCircle_image2.clear();
                //将数据库的信息添加到列表中
                cursor.moveToLast();
                int position = cursor.getPosition();
                byte[] bmpout;
                Bitmap bitmap;
                BitmapDrawable bitmapDrawable;
                Drawable drawable;
                while(position >= 0){
                    position --;
                    catCircleContentList.add(cursor.getString(cursor.getColumnIndex("content")));
                    //获取数据
                    bmpout = cursor.getBlob(cursor.getColumnIndex("catcircleimage1"));
                    //将获取的数据转换成drawable
                    bitmap = BitmapFactory.decodeByteArray(bmpout, 0, bmpout.length, null);
                    bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable = bitmapDrawable;
                    catCircle_image1.add(drawable);
                    //获取数据
                    bmpout = cursor.getBlob(cursor.getColumnIndex("catcircleimage2"));
                    //将获取的数据转换成drawable
                    bitmap = BitmapFactory.decodeByteArray(bmpout, 0, bmpout.length, null);
                    bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable = bitmapDrawable;
                    catCircle_image2.add(drawable);
                    cursor.moveToPosition(position);
                }
            }
            //空指针异常
            catch (NullPointerException e){
            }
        }//end else
        //关闭游标和数据库
        cursor.close();
        db.close();
    }


    class MyBaseAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return names.length + catCircleContentList.size();
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
            View view = View.inflate(cat_circle_new.this, R.layout.cat_circle_list_item, null);
            if(position < catCircleContentList.size())
            {
                //为猫圈加名字
                TextView tv_name = (TextView) view.findViewById(R.id.cat_circle_tv_name);
                tv_name.setText(user.getUserName());
                //为猫圈加头像
                ImageView imageView = (ImageView) view.findViewById(R.id.cat_circle_item_image);
                imageView.setBackgroundResource(R.drawable.photo);
                //为猫圈加内容
                TextView tv_content = (TextView) view.findViewById(R.id.cat_circle_tv_content);
                tv_content.setText(catCircleContentList.get(position));
                //为猫圈加照片1
                ImageView imageView1 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo1);
                imageView1.setImageDrawable(catCircle_image1.get(position));
                //为猫圈加照片2
                ImageView imageView2 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo2);
                imageView2.setImageDrawable(catCircle_image2.get(position));
            }
            else{
                //为猫圈加名字
                TextView tv_name = (TextView) view.findViewById(R.id.cat_circle_tv_name);
                tv_name.setText(names[position-catCircleContentList.size()]);
                //为猫圈加头像
                ImageView imageView = (ImageView) view.findViewById(R.id.cat_circle_item_image);
                imageView.setBackgroundResource(head_photo[position-catCircleContentList.size()]);
                //为猫圈加内容
                TextView tv_content = (TextView) view.findViewById(R.id.cat_circle_tv_content);
                tv_content.setText(content[position-catCircleContentList.size()]);
                //为猫圈加照片1
                ImageView imageView1 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo1);
                imageView1.setBackgroundResource(photo_1[position-catCircleContentList.size()]);
                //为猫圈加照片2
                ImageView imageView2 = (ImageView) view.findViewById(R.id.cat_circle_iv_photo2);
                imageView2.setBackgroundResource(photo_2[position-catCircleContentList.size()]);
            }

            //小鱼干点赞
            final Button fish = (Button) view.findViewById(R.id.btn_fish);
            fish.setOnClickListener(new View.OnClickListener() {
                boolean flag = false;
                @Override
                public void onClick(View view) {
                    flag = !flag;
                    if(flag)
                        fish.setBackgroundResource(R.mipmap.fish_gold);
                    else fish.setBackgroundResource(R.mipmap.fish);
                }
            });

            //点击分享到其他平台
            Button share = (Button)view.findViewById(R.id.btn_share);
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //分享
                    shareUtil.shareLink(getString(R.string.github_url),
                            getString(R.string.share_title), cat_circle_new.this);

                }
            });


            return view;
        }
    }
}
