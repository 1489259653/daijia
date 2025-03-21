package com.inool.daijia.customer.service;

import com.inool.daijia.model.vo.customer.CustomerLoginVo;

public interface CustomerService {


    String login(String code);

    CustomerLoginVo getCustomerLoginInfo(Long l);


}
