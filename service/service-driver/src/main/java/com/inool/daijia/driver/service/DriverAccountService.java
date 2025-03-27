package com.inool.daijia.driver.service;

import com.inool.daijia.model.entity.driver.DriverAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.form.driver.TransferForm;

public interface DriverAccountService extends IService<DriverAccount> {


    Boolean transfer(TransferForm transferForm);
}
