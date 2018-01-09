package com.example.meow.FindCat;

/**
 * Created by win on 2017/12/25.
 */

public class findCatFoundMessage {

    String losePlace;
    String catType;
    String catSex;
    String content;

    findCatFoundMessage(String losePlace,String catType,String catSex,String content){
        this.losePlace = losePlace;
        this.catType = catType;
        this.catSex = catSex;
        this.content = content;
    }

    String getLosePlace(){
        return losePlace;
    }

    String getCatType(){
        return catType;
    }

    String getCatSex(){
        return catSex;
    }

    String getContent(){
        return content;
    }

}
