package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Info {
    public String type;
    public String name;
    public ArrayList<String> options;
    public String value;
}
