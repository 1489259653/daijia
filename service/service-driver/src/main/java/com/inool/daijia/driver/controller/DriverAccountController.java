package com.inool.daijia.driver.controller;

import com.inool.daijia.model.form.driver.TransferForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.inool.daijia.common.result.Result;
import com.inool.daijia.driver.service.DriverInfoService;
import com.inool.daijia.model.form.driver.DriverFaceModelForm;
import com.inool.daijia.model.form.driver.UpdateDriverAuthInfoForm;
import com.inool.daijia.model.vo.driver.DriverAuthInfoVo;
import com.inool.daijia.model.vo.driver.DriverInfoVo;
import com.inool.daijia.model.vo.driver.DriverLoginVo;
import com.inool.daijia.model.entity.driver.DriverSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.inool.daijia.driver.service.DriverAccountService;


@Slf4j
@Tag(name = "司机账户API接口管理")
@RestController
@RequestMapping(value="/driver/account")
@SuppressWarnings({"unchecked", "rawtypes"})
public class DriverAccountController {
    @Autowired
    private DriverAccountService driverAccountService;

    @Operation(summary = "转账")
    @PostMapping("/transfer")
    public Result<Boolean> transfer(@RequestBody TransferForm transferForm) {
        return Result.ok(driverAccountService.transfer(transferForm));
    }

}

