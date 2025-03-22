package com.inool.daijia.driver.service;

import com.inool.daijia.model.vo.driver.DriverLoginVo;

public interface DriverService {


    String login(String code);

    DriverLoginVo getDriverLoginInfo(Long driverId);
}
