package com.example.meow.FindCat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meow.DataBase.MeowDataBase;
import com.example.meow.R;

import java.io.ByteArrayOutputStream;

public class findCatFoundRelease extends AppCompatActivity implements View.OnClickListener{

    //顶部导航栏
    private Toolbar toolbar;
    private TextView title;

    private EditText losePlace;
    private EditText catType;
    private EditText catSex;
    private EditText content;
    private Button Release;

    private ImageView iv_addPhoto1;
    private ImageView iv_addPhoto2;
    //用来控制当前是选择哪个位置的照片
    static int i=1;
    //调用相册
    private static final int GALLERY_CODE = 1;
    //图片1
    Bitmap image1;
    //图片2
    Bitmap image2;

    //SQLiteOpenHelper子类
    MeowDataBase meowDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_cat_found_release);

        //设置toolbar
        title = (TextView) findViewById(R.id.toobar_title);
        title.setText("");
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

        init();

    }

    void init(){

        meowDataBase = new MeowDataBase(findCatFoundRelease.this);

        losePlace = (EditText)findViewById(R.id.et_losePlace);
        catType = (EditText)findViewById(R.id.et_catType);
        catSex = (EditText)findViewById(R.id.et_catSex);
        content = (EditText)findViewById(R.id.et_content);

        Release = (Button)findViewById(R.id.btn_release);
        Release.setOnClickListener(this);

        //第一张照片按钮
        iv_addPhoto1=(ImageView)findViewById(R.id.iv_addPhoto1);
        iv_addPhoto1.setOnClickListener(this);
        //第二张照片按钮
        iv_addPhoto2=(ImageView)findViewById(R.id.iv_addPhoto2);
        iv_addPhoto2.setOnClickListener(this);


    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            //保存到数据库，并返回
            case R.id.btn_release:
                //保存数据
                SaveData();
                finish();
                break;
            case R.id.iv_addPhoto1:
                //拍照选择
                i=1;
                chooseFromGallery();
                break;
            case R.id.iv_addPhoto2:
                //从相册选取
                i=2;
                chooseFromGallery();
                break;
            default:
                break;
        }//end switch
    }//end onClick

    /**
     * 从相册选择图片
     */
    private void chooseFromGallery() {
        //构建一个内容选择的Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置选择类型为图片类型
        intent.setType("image/*");
        //打开图片选择
        startActivityForResult(intent, GALLERY_CODE);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获取图片
        if (requestCode == GALLERY_CODE  && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            try {
                if (i==1){
                    image1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    iv_addPhoto1.setImageBitmap(image1);
                    iv_addPhoto2.setVisibility(View.VISIBLE);
                }
                else{
                    image2 = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage));
                    iv_addPhoto2.setImageBitmap(image2);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("tag", e.getMessage());
                //Toast.makeText(this,"程序崩溃",Toast.LENGTH_SHORT).show();
            }
        }
    }

    //保存数据到数据库中
    void SaveData()
    {
        //获取可写入的SQLiteDatabase对象
        SQLiteDatabase db = meowDataBase.getWritableDatabase();
        //创建ContentValues对象
        ContentValues values = new ContentValues();
        //将图片保存为字节流
        ByteArrayOutputStream os1=new ByteArrayOutputStream();
        ByteArrayOutputStream os2=new ByteArrayOutputStream();
        image1.compress(Bitmap.CompressFormat.PNG,100,os1);
        image2.compress(Bitmap.CompressFormat.PNG,100,os2);

        //将信息插入到数据库中
        values.put("findcatimage1",os1.toByteArray());
        values.put("findcatimage2",os2.toByteArray());
        values.put("place",losePlace.getText().toString().trim());
        values.put("type",catType.getText().toString().trim());
        values.put("sex",catSex.getText().toString().trim());
        values.put("content",content.getText().toString().trim());
        //插入到数据库中
        db.insert("findcat_found",null,values);
        //关闭数据库
        db.close();
    }
}
