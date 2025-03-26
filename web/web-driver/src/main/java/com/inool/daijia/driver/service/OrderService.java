package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.form.order.OrderFeeForm;
import com.inool.daijia.model.form.order.StartDriveForm;
import com.inool.daijia.model.form.order.UpdateOrderCartForm;
import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.order.CurrentOrderInfoVo;
import com.inool.daijia.model.vo.order.NewOrderDataVo;
import com.inool.daijia.model.vo.order.OrderInfoVo;

import java.util.List;

public interface OrderService {


    Integer getOrderStatus(Long orderId);

    List<NewOrderDataVo> findNewOrderQueueData(Long driverId);

    Boolean robNewOrder(Long driverId, Long orderId);

    CurrentOrderInfoVo searchCustomerCurrentOrder(Long customerId);

    CurrentOrderInfoVo searchDriverCurrentOrder(Long driverId);


    OrderInfoVo getOrderInfo(Long orderId, Long driverId);

    Boolean driverArriveStartLocation(Long orderId, Long driverId);

    Boolean updateOrderCart(UpdateOrderCartForm updateOrderCartForm);

    Boolean startDrive(StartDriveForm startDriveForm);

    Boolean endDrive(OrderFeeForm orderFeeForm);

    PageVo findDriverOrderPage(Long driverId, Long page, Long limit);
}
