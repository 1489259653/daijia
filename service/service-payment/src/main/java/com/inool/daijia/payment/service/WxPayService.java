package com.inool.daijia.payment.service;

import com.inool.daijia.model.form.payment.PaymentInfoForm;
import com.inool.daijia.model.vo.payment.WxPrepayVo;

public interface WxPayService {


    WxPrepayVo createWxPayment(PaymentInfoForm paymentInfoForm);
}
