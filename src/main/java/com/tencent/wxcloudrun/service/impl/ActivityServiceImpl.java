package com.tencent.wxcloudrun.service.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;
import com.tencent.wxcloudrun.dao.ActivityMapper;
import com.tencent.wxcloudrun.model.Activity;
import com.tencent.wxcloudrun.model.Response;
import com.tencent.wxcloudrun.model.info;
import com.tencent.wxcloudrun.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Response createActivity(Activity activity) {
        try {
            if (activity.getAdditionalInfo() == null) {
                //activity.setAdditionalInfo(new ArrayList<>());
            }
            //activity.setUserJoined(new ArrayList<>());
            //activity.setAdditionalInfoJSON(JSON.toJSONString(activity.getAdditionalInfo()));
            //activity.setUserJoinedListJSON(JSON.toJSONString(activity.getUserJoined()));
            activityMapper.createActivity(activity);
            return Response.builder().status(100).message("成功").build();
        }catch(Exception exception){
            return Response.builder().status(101).message(exception.getMessage()).build();
        }
    }

    @Override
    public Response findByID(int id) {
        Activity activity = activityMapper.findByID(id);
        String userinfoJSON = activity.getAdditionalInfoJSON();
        activity.setAdditionalInfo(getinfo(userinfoJSON));
        if (activity != null) {
            return Response.builder().status(100).message("成功").data(activity).build();
        } else {
            return Response.builder().status(505).message("没有这个活动").build();
        }
    }

    private ArrayList<info> getinfo(String infoJSON) {
        List<info> list = new ArrayList<>();
        String s = infoJSON;
        Pattern p = Pattern.compile("\\{[^{]+}");
        Matcher m = p.matcher(s);
        ArrayList<info> addtionalinfo = new ArrayList<>();
        while(m.find()){
            info la = new info();
            String temp = m.group();
            System.out.println("组合： " + temp);
            List<String> a = List.of(temp.split(","));
            String l = "";
            int judge = 0;
            for (int i = 0; i < a.size(); i++) {
                if(a.get(i).contains("\"options\"")) {
                    l += a.get(i) + ", ";
                    i++;
                    while (!a.get(i).contains("]")) {
                        l += a.get(i) + ", ";
                        i++;
                    }
                    l += a.get(i);
                    judge = 1;
                }
                if (judge == 0){
                    List<String> b = List.of(a.get(i).split("[\\{}]"));
                    for (String f: b) {
                        if(!f.equals("")){
                            List<String> d = List.of(f.split(":"));
                            if (d.get(0).contains("\"name\"")) {
                                la.name = List.of(d.get(1).split("\"")).get(1);
                            } else if (d.get(0).contains("\"type\"")) {
                                la.type = List.of(d.get(1).split("\"")).get(1);
                            }
                        }
                    }
                } else {
                    List<String> b = List.of(l.split("[\\{}]"));
                    for (String f: b) {
                        if(f != ""){
                            la.optionsJSON = "{" + f.trim() + "}";
                            List<String> d = List.of(f.split(":"));
                            List<String> g = List.of(d.get(1).split("[\\[\\]]"));
                            List<String> t = List.of(g.get(1).split(","));
                            ArrayList<String> v = new ArrayList<>();
                            v.addAll(t);
                            for(int q = 0; q < v.size(); q++) {
                                v.set(q, v.get(q).trim());
                                v.set(q, List.of(v.get(q).split("\"")).get(1));
                            }
                            la.options = v;
                        }
                    }
                }
                judge = 0;
            }
            addtionalinfo.add(la);
        }
        return addtionalinfo;
    }
}
