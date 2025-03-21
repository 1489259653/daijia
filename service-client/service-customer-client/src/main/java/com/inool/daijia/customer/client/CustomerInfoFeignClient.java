package com.inool.daijia.customer.client;

import com.inool.daijia.common.result.Result;
import com.inool.daijia.model.vo.customer.CustomerLoginVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-customer")
public interface CustomerInfoFeignClient {
    /**
     * 小程序授权登录
     * @param code
     * @return
     */
    @GetMapping("/customer/info/login/{code}")
    Result<Long> login(@PathVariable String code);

    Result<CustomerLoginVo> getCustomerLoginInfo(Long customerId);
}