package com.inool.daijia.customer.service;

import com.inool.daijia.model.form.customer.ExpectOrderForm;
import com.inool.daijia.model.vo.customer.ExpectOrderVo;

public interface OrderService {

    ExpectOrderVo expectOrder(ExpectOrderForm expectOrderForm);
}
