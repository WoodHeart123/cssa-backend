package com.tencent.wxcloudrun.model;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class User {
    
    private String userID;
    private String email;
    private String nickname;
    private List<Integer> likedComment;
    private String likedCommentJSON;
    private Boolean isStudent;
}
