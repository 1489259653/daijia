package com.inool.daijia.driver.controller;

import com.inool.daijia.common.login.InoolLogin;
import com.inool.daijia.common.result.Result;
import com.inool.daijia.common.util.AuthContextHolder;
import com.inool.daijia.driver.service.DriverService;
import com.inool.daijia.model.vo.driver.DriverLoginVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Tag(name = "司机API接口管理")
@RestController
@RequestMapping(value="/driver")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Operation(summary = "小程序授权登录")
    @GetMapping("/login/{code}")
    public Result<String> login(@PathVariable String code) {
        return Result.ok(driverService.login(code));
    }
    @Operation(summary = "获取司机登录信息")
    @InoolLogin
    @GetMapping("/getDriverLoginInfo")
    public Result<DriverLoginVo> getDriverLoginInfo() {
        Long driverId = AuthContextHolder.getUserId();
        return Result.ok(driverService.getDriverLoginInfo(driverId));
    }
}

