package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.customer.SubmitOrderForm;

public interface OrderService {


    Long submitOrder(SubmitOrderForm submitOrderForm);
}
