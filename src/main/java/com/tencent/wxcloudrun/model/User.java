package com.tencent.wxcloudrun.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    private String userID;
    private String email;
    private String nickname;
    private List<Integer> likedComment;
    private String likedCommentJSON;
    private List<Integer> savedProduct;
    private String savedProductJSON;

    private List<Integer> savedRental;
    private String savedRentalJSON;
    private Boolean isStudent;
    private Integer avatar;
    private String wechatID;
    private Boolean subscribe;


    /**
     * constructor for new user
     *
     * @param nickname nickname of user;
     */
    public User(String nickname) {
        this.nickname = nickname;
        this.email = null;
        this.likedComment = new ArrayList<>();
        this.savedProduct = new ArrayList<>();
        this.isStudent = false;
        this.avatar = 1;
        this.subscribe = false;
    }
}
