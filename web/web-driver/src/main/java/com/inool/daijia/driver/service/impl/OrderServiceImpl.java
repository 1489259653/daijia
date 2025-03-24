package com.inool.daijia.driver.service.impl;

import com.inool.daijia.dispatch.client.NewOrderFeignClient;
import com.inool.daijia.driver.service.OrderService;
import com.inool.daijia.map.client.MapFeignClient;
import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.form.map.CalculateDrivingLineForm;
import com.inool.daijia.model.form.order.OrderInfoForm;
import com.inool.daijia.model.form.rules.FeeRuleRequestForm;
import com.inool.daijia.model.vo.map.DrivingLineVo;
import com.inool.daijia.model.vo.order.CurrentOrderInfoVo;
import com.inool.daijia.model.vo.order.NewOrderDataVo;
import com.inool.daijia.model.vo.rules.FeeRuleResponseVo;
import com.inool.daijia.order.client.OrderInfoFeignClient;
import com.inool.daijia.rules.client.FeeRuleFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoFeignClient orderInfoFeignClient;

    @Override
    public Integer getOrderStatus(Long orderId) {
        return orderInfoFeignClient.getOrderStatus(orderId).getData();
    }
    @Autowired
    private NewOrderFeignClient newOrderFeignClient;

    @Override
    public List<NewOrderDataVo> findNewOrderQueueData(Long driverId) {
        return newOrderFeignClient.findNewOrderQueueData(driverId).getData();
    }


    @Override
    public Boolean robNewOrder(Long driverId, Long orderId) {
        return orderInfoFeignClient.robNewOrder(driverId, orderId).getData();
    }

    @Override
    public CurrentOrderInfoVo searchCustomerCurrentOrder(Long customerId) {
        return orderInfoFeignClient.searchCustomerCurrentOrder(customerId).getData();
    }

    @Override
    public CurrentOrderInfoVo searchDriverCurrentOrder(Long driverId) {
        return orderInfoFeignClient.searchDriverCurrentOrder(driverId).getData();
    }
}
