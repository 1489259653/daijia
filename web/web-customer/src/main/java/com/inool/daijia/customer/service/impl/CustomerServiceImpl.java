package com.inool.daijia.customer.service.impl;

import com.inool.daijia.common.constant.RedisConstant;
import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.common.result.Result;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.inool.daijia.customer.client.CustomerInfoFeignClient;
import com.inool.daijia.customer.service.CustomerService;
import com.inool.daijia.model.vo.customer.CustomerLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerServiceImpl implements CustomerService {


    @Autowired
    private CustomerInfoFeignClient customerInfoFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public String login(String code) {
        Long customerId = customerInfoFeignClient.login(code).getData();

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY_PREFIX+token, customerId.toString(), RedisConstant.USER_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
        return token;
    }


    @Override
    public CustomerLoginVo getCustomerLoginInfo(Long customerId) {
        return customerInfoFeignClient.getCustomerLoginInfo(customerId).getData();
    }
}
