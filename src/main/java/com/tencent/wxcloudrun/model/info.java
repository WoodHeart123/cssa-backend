package com.tencent.wxcloudrun.model;

import lombok.Data;

import java.util.ArrayList;


@Data
public class info {
    public String type;
    public String name;
    public ArrayList<String> options;
    public String optionsJSON;
    public String value;
}
