package com.inool.daijia.customer.service;

import com.inool.daijia.model.entity.customer.CustomerInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.vo.customer.CustomerLoginVo;

public interface CustomerInfoService extends IService<CustomerInfo> {

    Long login(String code);

    CustomerLoginVo getCustomerLoginInfo(Long customerId);
}
