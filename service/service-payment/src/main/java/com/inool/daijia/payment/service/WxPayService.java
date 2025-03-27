package com.inool.daijia.payment.service;

import com.inool.daijia.model.form.payment.PaymentInfoForm;
import com.inool.daijia.model.vo.payment.WxPrepayVo;
import jakarta.servlet.http.HttpServletRequest;

public interface WxPayService {


    WxPrepayVo createWxPayment(PaymentInfoForm paymentInfoForm);

    void wxnotify(HttpServletRequest request);

    Object queryPayStatus(String orderNo);

    void handleOrder(String orderNo);
}
