package com.inool.daijia.driver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inool.daijia.driver.mapper.DriverAccountDetailMapper;
import com.inool.daijia.driver.mapper.DriverAccountMapper;
import com.inool.daijia.driver.service.DriverAccountService;
import com.inool.daijia.model.entity.driver.DriverAccount;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inool.daijia.model.entity.driver.DriverAccountDetail;
import com.inool.daijia.model.form.driver.TransferForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DriverAccountServiceImpl extends ServiceImpl<DriverAccountMapper, DriverAccount> implements DriverAccountService {
    @Autowired
    private DriverAccountMapper driverAccountMapper;

    @Autowired
    private DriverAccountDetailMapper driverAccountDetailMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean transfer(TransferForm transferForm) {
        //去重
        long count = driverAccountDetailMapper.selectCount(new LambdaQueryWrapper<DriverAccountDetail>().eq(DriverAccountDetail::getTradeNo, transferForm.getTradeNo()));
        if(count > 0) return true;

        //添加账号金额
        driverAccountMapper.add(transferForm.getDriverId(), transferForm.getAmount());

        //添加账户明细
        DriverAccountDetail driverAccountDetail = new DriverAccountDetail();
        BeanUtils.copyProperties(transferForm, driverAccountDetail);
        driverAccountDetailMapper.insert(driverAccountDetail);
        return true;
    }

}
