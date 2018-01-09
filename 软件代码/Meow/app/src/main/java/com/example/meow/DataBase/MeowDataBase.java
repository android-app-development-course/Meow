package com.example.meow.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pipi on 2017/12/24.
 */

public class MeowDataBase extends SQLiteOpenHelper {

    //上下文对象、数据库名称、游标工厂（通常是null）、数据库版本
    public MeowDataBase(Context context) {
        super(context, "Meow", null, 1);
    }

    @Override
    //数据库第一次被创建时调用该方法
    public void onCreate(SQLiteDatabase db) {

        //创建findcat_found表
        db.execSQL("CREATE TABLE findcat_found(_findCatFoundId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "place VARCHAR(100)," +
                "type VARCHAR(50)," +
                "sex VARCHAR(10)," +
                "content TEXT,"+
                "findcatimage1 BLOB,"+
                "findcatimage2 BLOB)"
        );

        //创建cat_circle表
        db.execSQL("CREATE TABLE cat_circle(_catCircleId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content TEXT," +
                "catcircleimage1 BLOB," +
                "catcircleimage2 BLOB)"
        );

        //创建cat_user_comment表
        db.execSQL("CREATE TABLE cat_user_comment(_catUserCommentId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "comment TEXT)");


    }


    @Override
    //在数据库版本号增加时调用
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
