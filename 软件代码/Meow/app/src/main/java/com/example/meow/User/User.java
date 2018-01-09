package com.example.meow.User;

/**
 * Created by win on 2017/12/24.
 */

public class User {

    //用户昵称
    private String userName;
    //猫圈数目
    private int catCircleNum;
    //寻猫启事数目
    private int findCatFoundNum;

    public User(){
        this.userName = "瓜皮的id酱";
        this.catCircleNum = 0;
        this.findCatFoundNum = 0;
    }

    public String getUserName(){
        return userName;
    }

    public void setCatCircleNum(int num){
        this.catCircleNum = num;
    }

}
