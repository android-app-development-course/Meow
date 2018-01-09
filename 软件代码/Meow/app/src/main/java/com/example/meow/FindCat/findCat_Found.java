package com.example.meow.FindCat;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meow.Cat_Circle.cat_circle_new;
import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;
import com.example.meow.User.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pipi on 2017/11/21.
 */

public class findCat_Found extends Activity {

    private ListView mListView;

    //暂存从数据库读出来的寻猫启事信息
    private List<findCatFoundMessage> findCatFoundList;

    MyBaseAdapter mAdapter;
    MeowDataBase meowDataBase;
    List<Drawable> findcat_image1;
    List<Drawable> findcat_image2;

    User user;

    //需要适配的数据
    private String[] names = {"日食记","华师撸猫协会"};

    //需要适配的头像图片
    private int[] portrait = {R.drawable.user, R.drawable.scnu};

    //需要适配的地点
    private String[] place = {"华师西区","华师西区"};

    //需要适配的品种
    private String[] kind = {"橘猫","白猫"};

    //需要适配的性别
    private String[] sex = {"雌性","雄性"};

    //需要适配的猫圈语句
    private String[] describe = {"家里猫主子不见了很着急QAQ","谁帮忙找找我家白猫猫QAQ"};

    //需要适配的照片1
    private int[] photo_1 = {R.drawable.orangecat1,R.drawable.whitecat1};

    //需要适配的照片2
    private int[] photo_2 = {R.drawable.orangecat2,R.drawable.whitecat2};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cat_list);

        findCatFoundList = new ArrayList<findCatFoundMessage>();
        user = new User();
        findcat_image1 = new ArrayList<Drawable>();
        findcat_image2 = new ArrayList<Drawable>();

        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.find_cat_lv);
        //实例化数据库对象并读取数据库的信息
        meowDataBase = new MeowDataBase(findCat_Found.this);
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
        Cursor cursor = db.query("findcat_found",null,
                null,null,null,
                null,null);

        if(cursor.getCount() == 0){
            //Toast.makeText(findCat_Found.this,"暂无数据",Toast.LENGTH_SHORT).show();
        }
        else{
            try{
                //先清除数据
                findCatFoundList.clear();
                //将数据库的信息添加到列表中
                cursor.moveToLast();
                int position = cursor.getPosition();
                byte[] bmpout;
                Bitmap bitmap;
                BitmapDrawable bitmapDrawable;
                Drawable drawable;
                while(position >= 0){
                    position --;
                    findCatFoundList.add(new findCatFoundMessage(
                            cursor.getString(cursor.getColumnIndex("place")),
                            cursor.getString(cursor.getColumnIndex("type")),
                            cursor.getString(cursor.getColumnIndex("sex")),
                            cursor.getString(cursor.getColumnIndex("content"))));
                    //获取数据
                    bmpout = cursor.getBlob(cursor.getColumnIndex("findcatimage1"));
                    //将获取的数据转换成drawable
                    bitmap = BitmapFactory.decodeByteArray(bmpout, 0, bmpout.length, null);
                    bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable = bitmapDrawable;
                    findcat_image1.add(drawable);
                    //获取数据
                    bmpout = cursor.getBlob(cursor.getColumnIndex("findcatimage2"));
                    //将获取的数据转换成drawable
                    bitmap = BitmapFactory.decodeByteArray(bmpout, 0, bmpout.length, null);
                    bitmapDrawable = new BitmapDrawable(bitmap);
                    drawable = bitmapDrawable;
                    findcat_image2.add(drawable);
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
            return names.length + findCatFoundList.size();
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
            View view = View.inflate(findCat_Found.this, R.layout.activity_find_cat_list_item, null);

            if(position < findCatFoundList.size()) {
                //发表人名称
                TextView tv_name = (TextView) view.findViewById(R.id.find_cat_name);
                tv_name.setText(user.getUserName());
                //发表人头像
                ImageView imageView = (ImageView) view.findViewById(R.id.find_cat_portrait);
                imageView.setBackgroundResource(R.drawable.photo);
                //猫咪地点
                TextView tv_place = (TextView) view.findViewById(R.id.find_cat_place);
                tv_place.setText(findCatFoundList.get(position).getLosePlace());
                //猫咪种类
                TextView tv_kind = (TextView) view.findViewById(R.id.find_cat_kind);
                tv_kind.setText(findCatFoundList.get(position).getCatType());
                //猫咪性别
                TextView tv_sex = (TextView) view.findViewById(R.id.find_cat_sex);
                tv_sex.setText(findCatFoundList.get(position).getCatSex());
                //描述
                TextView tv_describe = (TextView) view.findViewById(R.id.find_cat_decribe);
                tv_describe.setText(findCatFoundList.get(position).getContent());
                //照片1
                ImageView imageView1 = (ImageView) view.findViewById(R.id.find_cat_photo1);
                imageView1.setImageDrawable(findcat_image1.get(position));
                //照片2
                ImageView imageView2 = (ImageView) view.findViewById(R.id.find_cat_photo2);
                imageView2.setImageDrawable(findcat_image2.get(position));
            }
            else{
                //发表人名称
                TextView tv_name = (TextView) view.findViewById(R.id.find_cat_name);
                tv_name.setText(names[position-findCatFoundList.size()]);
                //发表人头像
                ImageView imageView = (ImageView) view.findViewById(R.id.find_cat_portrait);
                imageView.setBackgroundResource(portrait[position-findCatFoundList.size()]);
                //猫咪地点
                TextView tv_place = (TextView) view.findViewById(R.id.find_cat_place);
                tv_place.setText(place[position-findCatFoundList.size()]);
                //猫咪种类
                TextView tv_kind = (TextView) view.findViewById(R.id.find_cat_kind);
                tv_kind.setText(kind[position-findCatFoundList.size()]);
                //猫咪性别
                TextView tv_sex = (TextView) view.findViewById(R.id.find_cat_sex);
                tv_sex.setText(sex[position-findCatFoundList.size()]);
                //描述
                TextView tv_describe = (TextView) view.findViewById(R.id.find_cat_decribe);
                tv_describe.setText(describe[position-findCatFoundList.size()]);
                //照片1
                ImageView imageView1 = (ImageView) view.findViewById(R.id.find_cat_photo1);
                imageView1.setBackgroundResource(photo_1[position-findCatFoundList.size()]);
                //照片2
                ImageView imageView2 = (ImageView) view.findViewById(R.id.find_cat_photo2);
                imageView2.setBackgroundResource(photo_2[position-findCatFoundList.size()]);
            }

            //联系方框
            Button tv_contact = (Button) view.findViewById(R.id.find_cat_contact);
            tv_contact.setText("联系主人");
            //从发现地点变为丢失地点
            TextView tv = (TextView) view.findViewById(R.id.tv_loseOrFindPlace);
            tv.setText("丢失地点：");

            return view;
        }
    }

}
