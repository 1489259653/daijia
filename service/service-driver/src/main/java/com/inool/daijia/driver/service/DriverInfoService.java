package com.inool.daijia.driver.service;

import com.inool.daijia.model.entity.driver.DriverInfo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface DriverInfoService extends IService<DriverInfo> {

    Long login(String code);
}
