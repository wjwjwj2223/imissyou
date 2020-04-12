/**
 * @作者 7七月
 * @微信公号 林间有风
 * @开源项目 $ http://talelin.com
 * @免费专栏 $ http://course.talelin.com
 * @我的课程 $ http://imooc.com/t/4294850
 * @创建时间 2020-04-05 17:40
 */
package com.lin.imissyou.manager.redis;

import com.lin.imissyou.bo.OrderMessageBO;

import com.lin.imissyou.exception.http.ServerErrorException;
import com.lin.imissyou.services.CouponBackService;
import com.lin.imissyou.services.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
public class TopicMessageListener implements MessageListener {

    @Autowired
    private  ApplicationEventPublisher topicMessageListener;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();

        String expiredKey = new String(body);
        String topic = new String(channel);

        OrderMessageBO messageBO = new OrderMessageBO(expiredKey);
        this.topicMessageListener.publishEvent(messageBO);
    }
}
