package com.inool.daijia.order.service;

import com.inool.daijia.model.entity.order.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.form.order.OrderInfoForm;
import com.inool.daijia.model.vo.order.CurrentOrderInfoVo;

public interface OrderInfoService extends IService<OrderInfo> {

    Long saveOrderInfo(OrderInfoForm orderInfoForm);

    Integer getOrderStatus(Long orderId);

    Boolean robNewOrder(Long driverId, Long orderId);

    CurrentOrderInfoVo searchCustomerCurrentOrder(Long customerId);

    CurrentOrderInfoVo searchDriverCurrentOrder(Long driverId);
}
