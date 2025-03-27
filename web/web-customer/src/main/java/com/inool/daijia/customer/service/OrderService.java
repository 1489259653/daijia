package com.inool.daijia.customer.service;

import com.inool.daijia.model.form.customer.ExpectOrderForm;
import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.form.map.CalculateDrivingLineForm;
import com.inool.daijia.model.form.payment.CreateWxPaymentForm;
import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.customer.ExpectOrderVo;
import com.inool.daijia.model.vo.driver.DriverInfoVo;
import com.inool.daijia.model.vo.map.DrivingLineVo;
import com.inool.daijia.model.vo.map.OrderLocationVo;
import com.inool.daijia.model.vo.map.OrderServiceLastLocationVo;
import com.inool.daijia.model.vo.order.OrderInfoVo;
import com.inool.daijia.model.vo.payment.WxPrepayVo;

public interface OrderService {

    ExpectOrderVo expectOrder(ExpectOrderForm expectOrderForm);
    Integer getOrderStatus(Long orderId);
    Long submitOrder(SubmitOrderForm submitOrderForm);


    OrderInfoVo getOrderInfo(Long orderId, Long customerId);

    DrivingLineVo calculateDrivingLine(CalculateDrivingLineForm calculateDrivingLineForm);

    DriverInfoVo getDriverInfo(Long orderId, Long customerId);

    OrderLocationVo getCacheOrderLocation(Long orderId);

    OrderServiceLastLocationVo getOrderServiceLastLocation(Long orderId);

    PageVo findCustomerOrderPage(Long customerId, Long page, Long limit);

    WxPrepayVo createWxPayment(CreateWxPaymentForm createWxPaymentForm);

    Boolean queryPayStatus(String orderNo);
}
