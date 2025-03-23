package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.vo.order.NewOrderDataVo;

import java.util.List;

public interface OrderService {


    Integer getOrderStatus(Long orderId);

    List<NewOrderDataVo> findNewOrderQueueData(Long driverId);
}
