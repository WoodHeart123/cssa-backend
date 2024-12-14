package org.cssa.wxcloudrun.service.impl;


import org.cssa.wxcloudrun.model.Message;
import org.cssa.wxcloudrun.model.Response;
import org.cssa.wxcloudrun.service.MessageService;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public Response<Object> createMessage(Message message) {
        return null;
    }

    @Override
    public Response<Object> getMessageListUnderPost(Integer postID, Message.PostType postType) {
        return null;
    }

    @Override
    public Response<Object> getNewMessageList(String openid) {
        return null;
    }
}
