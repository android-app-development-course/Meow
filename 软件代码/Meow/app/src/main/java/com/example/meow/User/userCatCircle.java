package com.example.meow.User;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.meow.Cat.Cat_UserComment;
import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;

import java.util.ArrayList;
import java.util.List;

public class userCatCircle extends AppCompatActivity {

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    private ListView mListView;
    private CatCircleAdapter mAdapter;
    MeowDataBase meowDataBase;

    //从数据库读出来的猫圈内容
    List<String> userCatCircleContentList;
    List<Drawable> catCircle_image1;
    List<Drawable> catCircle_image2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cat_circle);

        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("我的猫圈");
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

        userCatCircleContentList = new ArrayList<String>();
        catCircle_image1=new ArrayList<Drawable>();
        catCircle_image2=new ArrayList<Drawable>();

        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.lv_userCatCircle);
        //实例化数据库对象并读取数据库的信息
        meowDataBase = new MeowDataBase(userCatCircle.this);
        ReadData();

        //创建一个Adapter的实例
        mAdapter = new CatCircleAdapter();
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
                userCatCircleContentList.clear();
                //将数据库的信息添加到列表中
                cursor.moveToLast();
                int position = cursor.getPosition();
                byte[] bmpout;
                Bitmap bitmap;
                BitmapDrawable bitmapDrawable;
                Drawable drawable;
                while(position >= 0){
                    position --;
                    userCatCircleContentList.add(cursor.getString(cursor.getColumnIndex("content")));
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

    class CatCircleAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return userCatCircleContentList.size();
        }

        //得到item代表的对象
        @Override
        public Object getItem(int position) {
            return userCatCircleContentList.get(position);
        }

        //得到item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //将list_item.xml文件找出来并转换成vi对象
            View view = View.inflate(userCatCircle.this, R.layout.activity_user_cat_circle_list_item, null);

            //为猫圈加照片1
            ImageView imageView1 = (ImageView) view.findViewById(R.id.iv_Photo1);
            imageView1.setImageDrawable(catCircle_image1.get(position));
            //为猫圈加照片2
            ImageView imageView2 = (ImageView) view.findViewById(R.id.iv_Photo2);
            imageView2.setImageDrawable(catCircle_image2.get(position));
            //加内容
            TextView tv_content = (TextView) view.findViewById(R.id.tv_Concent);
            tv_content.setText(userCatCircleContentList.get(position));

            return view;
        }
    }

}
