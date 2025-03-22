package com.inool.daijia.driver.service;

import com.inool.daijia.model.entity.driver.DriverInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.vo.driver.DriverAuthInfoVo;
import com.inool.daijia.model.vo.driver.DriverLoginVo;

public interface DriverInfoService extends IService<DriverInfo> {

    Long login(String code);

    DriverLoginVo getDriverLoginInfo(Long driverId);

    DriverAuthInfoVo getDriverAuthInfo(Long driverId);
}
