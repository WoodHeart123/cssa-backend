package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    
    private String userID;
    private String email;
    private String nickname;
    private List<Integer> likedComment;
    private String likedCommentJSON;
    private List<Integer> savedProduct;
    private String savedProductJSON;
    private Boolean isStudent;
    private Integer avatar;
    private String savedRentalJSON;
    private String wechatID;
    private Boolean subscribe;


    /**
     * a no argument constructor for mybatis injection
     */
    public User(){}

    /**
     * constructor for new user
     * @param nickname nickname of user;
     */
    public User(String nickname){
        this.nickname = nickname;
        this.email = null;
        this.likedComment = new ArrayList<>();
        this.savedProduct = new ArrayList<>();
        this.isStudent = false;
        this.avatar = 1;
        this.subscribe = false;
    }
}
