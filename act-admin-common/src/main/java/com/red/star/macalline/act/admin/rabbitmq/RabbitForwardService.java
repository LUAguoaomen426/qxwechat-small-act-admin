package com.red.star.macalline.act.admin.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author nofish.yan@gmail.com
 * @date 2018/4/10.
 * MQ消息发送服务
 */
@Service
public class RabbitForwardService {

    private static final Logger logger = LoggerFactory.getLogger(RabbitForwardService.class);

    @Resource
    private AmqpTemplate amqpTemplate;

    /**
     * 发送消息到指定队列
     *
     * @param queue 队列
     * @param data  消息对象
     * @param <T>   消息对象类型
     */
    public <T> void sendMsgToQueue(String queue, T data) {
        String message = JSON.toJSONString(data);
        logger.info("Send message to RMQ, queue = {}, msg = {}", queue, message);
        amqpTemplate.convertAndSend(queue, message);
    }

    public <T> void sendMsgToExchange(String exchange, String routingKey, T data) {
        String message = JSON.toJSONString(data);
        logger.info("Send message to RMQ, exchange = {},routingKey = {}, msg = {}", exchange, routingKey, message);
        amqpTemplate.convertAndSend(exchange, routingKey, message);
    }

    public <T> void sendMsgToFanoutExchange(String fanoutExchange, T data) {
        String message = JSON.toJSONString(data);
        logger.info("Send message to RMQ, FanoutExchange = {}, msg = {}", fanoutExchange, message);
        amqpTemplate.convertAndSend(fanoutExchange, "", message);
    }
}
