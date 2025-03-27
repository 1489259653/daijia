package com.inool.daijia.order.receiver;

import com.inool.daijia.common.constant.MqConst;
import com.inool.daijia.order.service.OrderInfoService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderReceiver {

    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * 系统取消订单
     *
     * @param orderId
     * @throws IOException
     */
    @RabbitListener(queues = MqConst.QUEUE_CANCEL_ORDER)
    public void systemCancelOrder(String orderId, Message message, Channel channel) throws IOException {
        try {
            //1.处理业务
            if (orderId != null) {
                log.info("【订单微服务】关闭订单消息：{}", orderId);
                orderInfoService.systemCancelOrder(Long.parseLong(orderId));
            }
            //2.手动应答
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("【订单微服务】关闭订单业务异常：{}", e);
        }
    }

    /**
     * 订单分账成功，更新分账状态
     *
     * @param orderNo
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConst.QUEUE_PROFITSHARING_SUCCESS, durable = "true"),
            exchange = @Exchange(value = MqConst.EXCHANGE_ORDER),
            key = {MqConst.ROUTING_PROFITSHARING_SUCCESS}
    ))
    public void profitsharingSuccess(String orderNo, Message message, Channel channel) throws IOException {
        orderInfoService.updateProfitsharingStatus(orderNo);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }


}