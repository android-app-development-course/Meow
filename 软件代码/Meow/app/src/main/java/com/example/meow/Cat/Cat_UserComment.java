package com.example.meow.Cat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meow.Cat_Circle.cat_circle_commend;
import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.HomePage.HomePage;
import com.example.meow.R;
import com.example.meow.User.User;

import java.util.ArrayList;
import java.util.List;

public class Cat_UserComment extends AppCompatActivity {

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    //置顶评论列表和评论列表
    private ListView mListViewTopComment;
    private ListView mListViewUserComment;

    TopBaseAdapter mTopAdapter;
    UserBaseAdapter mUserAdapter;

    MeowDataBase meowDataBase;

    //从数据库读出来的用户评论
    List<String> catUserCommentList;

    //当前用户
    User user;

    //置顶的评论
    //需要适配的数据
    private String [] top_names = {"华师撸猫协会"};
    //需要适配的头像图片
    private int [] top_head_photos = {R.drawable.organization};
    //需要适配的评论
    private String [] top_comments = {"请大家不要频繁喂养~"};


    //用户评论
    //需要适配的数据
    private String[] user_names = {"李花花", "苍太"};
    //需要适配的头像图片
    private int[] user_head_photos = {R.drawable.lhh, R.drawable.zp};
    //需要适配的评论
    private String[] user_comments = {"瓜皮好乖好可爱~", "用力吸一口QAQ"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat__user_comment);

        //顶部导航栏
        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("用户评论");
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

        catUserCommentList = new ArrayList<String>();
        user = new User();

        //实例化数据库对象并读取数据库的信息
        meowDataBase = new MeowDataBase(Cat_UserComment.this);
        ReadData();

        //初始化ListView控件
        mListViewTopComment = (ListView) findViewById(R.id.lv_catTopComment);
        mListViewUserComment = (ListView) findViewById(R.id.lv_catUserComment);

        //创建一个Adapter的实例
        mTopAdapter = new TopBaseAdapter();
        mUserAdapter = new UserBaseAdapter();

        //设置Adapter
        mListViewTopComment.setAdapter(mTopAdapter);
        mListViewUserComment.setAdapter(mUserAdapter);

    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Refresh();
    }

    //刷新列表
    void Refresh(){
        ReadData();
        mUserAdapter.notifyDataSetChanged();
    }

    //从数据库读取内容
    void ReadData(){
        //获得可以读取数据的SQLiteDataBase对象
        SQLiteDatabase db = meowDataBase.getReadableDatabase();
        //游标
        Cursor cursor = db.query("cat_user_comment",null,
                null,null,null,
                null,null);

        if(cursor.getCount() == 0){}
        else{
            try{
                //先清除数据
                catUserCommentList.clear();
                //将数据库的信息添加到列表中
                cursor.moveToLast();
                int position = cursor.getPosition();
                while(position >= 0){
                    position --;
                    catUserCommentList.add(cursor.getString(cursor.getColumnIndex("comment")));
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


    //顶部栏菜单项评论
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cat_user_comment_release_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.comment) {
            Intent intent = new Intent(Cat_UserComment.this,CatUserCommentRelease.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class TopBaseAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return top_names.length;
        }

        //得到item代表的对象
        @Override
        public Object getItem(int position) {
            return top_names[position];
        }

        //得到item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //将list_item.xml文件找出来并转换成vi对象
            View view = View.inflate(Cat_UserComment.this, R.layout.activity_cat_user_comment_list_item, null);
            //为用户加名字
            TextView tv_name = (TextView) view.findViewById(R.id.tv_userName);
            tv_name.setText(top_names[position]);
            //为用户加头像
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_userHeadPhoto);
            imageView.setBackgroundResource(top_head_photos[position ]);
            //为评论加内容
            TextView tv_content = (TextView) view.findViewById(R.id.tv_userComment);
            tv_content.setText(top_comments[position]);
            return view;
        }
    }

    class UserBaseAdapter extends BaseAdapter {

        //得到item的总数
        @Override
        public int getCount() {
            //返回ListView Item条目的总数
            return user_names.length + catUserCommentList.size();
        }

        //得到item代表的对象
        @Override
        public Object getItem(int position) {
            return user_names[position];
        }

        //得到item的id
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            //将list_item.xml文件找出来并转换成vi对象
            View view = View.inflate(Cat_UserComment.this, R.layout.activity_cat_user_comment_list_item, null);
            if(position < catUserCommentList.size()) {
                //为用户加名字
                TextView tv_name = (TextView) view.findViewById(R.id.tv_userName);
                tv_name.setText(user.getUserName());
                //为用户加头像
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_userHeadPhoto);
                imageView.setBackgroundResource(R.drawable.photo);
                //为评论加内容
                TextView tv_content = (TextView) view.findViewById(R.id.tv_userComment);
                tv_content.setText(catUserCommentList.get(position));
            }
            else {
                //为用户加名字
                TextView tv_name = (TextView) view.findViewById(R.id.tv_userName);
                tv_name.setText(user_names[position - catUserCommentList.size()]);
                //为用户加头像
                ImageView imageView = (ImageView) view.findViewById(R.id.iv_userHeadPhoto);
                imageView.setBackgroundResource(user_head_photos[position - catUserCommentList.size()]);
                //为评论加内容
                TextView tv_content = (TextView) view.findViewById(R.id.tv_userComment);
                tv_content.setText(user_comments[position - catUserCommentList.size()]);
            }
            return view;
        }
    }

}
