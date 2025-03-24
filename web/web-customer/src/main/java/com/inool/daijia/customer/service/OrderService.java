package com.inool.daijia.customer.service;

import com.inool.daijia.model.form.customer.ExpectOrderForm;
import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.form.map.CalculateDrivingLineForm;
import com.inool.daijia.model.vo.customer.ExpectOrderVo;
import com.inool.daijia.model.vo.map.DrivingLineVo;
import com.inool.daijia.model.vo.order.OrderInfoVo;

public interface OrderService {

    ExpectOrderVo expectOrder(ExpectOrderForm expectOrderForm);
    Integer getOrderStatus(Long orderId);
    Long submitOrder(SubmitOrderForm submitOrderForm);


    OrderInfoVo getOrderInfo(Long orderId, Long customerId);

    DrivingLineVo calculateDrivingLine(CalculateDrivingLineForm calculateDrivingLineForm);
}
