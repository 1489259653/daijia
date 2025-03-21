package com.inool.daijia.customer.controller;

import com.inool.daijia.common.constant.RedisConstant;
import com.inool.daijia.common.login.InoolLogin;
import com.inool.daijia.common.result.Result;
import com.inool.daijia.common.util.AuthContextHolder;
import com.inool.daijia.customer.service.CustomerService;
import com.inool.daijia.model.vo.customer.CustomerLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Tag(name = "客户API接口管理")
@RestController
@RequestMapping("/customer")
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerController {
    @Autowired
    private CustomerService customerInfoService;

    @Operation(summary = "小程序授权登录")
    @GetMapping("/login/{code}")
    public Result<String> wxLogin(@PathVariable String code) {
        return Result.ok(customerInfoService.login(code));
    }
    @Autowired
    private RedisTemplate redisTemplate;

    @Operation(summary = "获取客户登录信息")
    @InoolLogin
    @GetMapping("/getCustomerLoginInfo")
    public Result<CustomerLoginVo> getCustomerLoginInfo() {
        Long customerId = AuthContextHolder.getUserId();
        return Result.ok(customerInfoService.getCustomerLoginInfo(customerId));
    }
}

