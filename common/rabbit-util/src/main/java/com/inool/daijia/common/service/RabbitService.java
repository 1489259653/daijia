package com.inool.daijia.common.service;


import com.alibaba.fastjson.JSON;
import com.inool.daijia.common.entity.InoolCorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  发送消息
     * @param exchange 交换机
     * @param routingKey 路由键
     * @param message 消息
     */
    public boolean sendMessage(String exchange, String routingKey, Object message) {
        //1.创建自定义相关消息对象-包含业务数据本身，交换器名称，路由键，队列类型，延迟时间,重试次数
        InoolCorrelationData correlationData = new InoolCorrelationData();
        String uuid = "mq:" + UUID.randomUUID().toString().replaceAll("-", "");
        correlationData.setId(uuid);
        correlationData.setMessage(message);
        correlationData.setExchange(exchange);
        correlationData.setRoutingKey(routingKey);
        //2.将相关消息封装到发送消息方法中

        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);

        //3.将相关消息存入Redis  Key：UUID  相关消息对象  10 分钟
        redisTemplate.opsForValue().set(uuid, JSON.toJSONString(correlationData), 10, TimeUnit.MINUTES);

        //log.info("生产者发送消息成功：{}，{}，{}", exchange, routingKey, message);
        return true;
    }
}