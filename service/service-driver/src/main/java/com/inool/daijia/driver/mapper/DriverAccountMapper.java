package com.inool.daijia.driver.mapper;

import com.inool.daijia.model.entity.driver.DriverAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface DriverAccountMapper extends BaseMapper<DriverAccount> {


    Integer add(Long driverId, BigDecimal amount);
}
