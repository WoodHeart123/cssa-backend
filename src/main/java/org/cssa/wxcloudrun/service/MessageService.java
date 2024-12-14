package org.cssa.wxcloudrun.service;


import org.cssa.wxcloudrun.model.Message;
import org.cssa.wxcloudrun.model.Response;
import org.springframework.stereotype.Service;

@Service
public interface MessageService {

    public Response<Object> createMessage(Message message);

    public Response<Object> getMessageListUnderPost(Integer postID, Message.PostType postType);


    public Response<Object> getNewMessageList(String openid);
}
