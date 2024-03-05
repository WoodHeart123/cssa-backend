package org.cssa.wxcloudrun.service.impl;

import com.alibaba.fastjson2.JSONObject;
import org.cssa.wxcloudrun.model.WechatResponse;
import org.cssa.wxcloudrun.service.WeChatAPI;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeChatAPIImpl implements WeChatAPI {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();
    @Override
    public WechatResponse MsgCheck(String content, String openId, Integer scene) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject requestObject = new JSONObject();
        requestObject.put("content", content);
        requestObject.put("version", 2);
        requestObject.put("scene", scene);
        requestObject.put("openid", openId);


        HttpEntity<String> request =
                new HttpEntity<>(requestObject.toString(), headers);
        return REST_TEMPLATE.postForObject("https://api.weixin.qq.com/wxa/msg_sec_check", request, WechatResponse.class);
    }
}
