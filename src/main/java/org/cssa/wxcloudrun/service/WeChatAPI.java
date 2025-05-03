package org.cssa.wxcloudrun.service;

import org.cssa.wxcloudrun.model.WechatResponse;
import org.springframework.stereotype.Service;

@Service
public interface WeChatAPI {

    /**
     * 参考https://developers.weixin.qq.com/miniprogram/dev/OpenApiDoc/sec-center/sec-check/msgSecCheck.html
     * @param content 用户输入内容
     * @param openId 用户openid
     * @param Scene 场景枚举值（1 资料；2 评论；3 论坛；4 社交日志）
     */
    WechatResponse MsgCheck(String content, String openId, Integer Scene);
}
