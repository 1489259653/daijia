package com.inool.daijia.customer.service.impl;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.alibaba.nacos.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.customer.mapper.CustomerInfoMapper;
import com.inool.daijia.customer.mapper.CustomerLoginLogMapper;
import com.inool.daijia.customer.service.CustomerInfoService;
import com.inool.daijia.model.entity.customer.CustomerInfo;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inool.daijia.model.entity.customer.CustomerLoginLog;
import com.inool.daijia.model.vo.customer.CustomerLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CustomerInfoServiceImpl extends ServiceImpl<CustomerInfoMapper, CustomerInfo> implements CustomerInfoService {

    @Autowired
    private WxMaService wxMaService;

    @Autowired
    private CustomerLoginLogMapper customerLoginLogMapper;
    /**
     * 条件：
     *      1、前端开发者appid与服务器端appid一致
     *      2、前端开发者必须加入开发者
     * @param code
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public Long login(String code) {
        String openId = null;
        try {
            //获取openId
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            openId = sessionInfo.getOpenid();
            log.info("【小程序授权】openId={}", openId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InoolException(ResultCodeEnum.WX_CODE_ERROR);
        }

        CustomerInfo customerInfo = this.getOne(new LambdaQueryWrapper<CustomerInfo>().eq(CustomerInfo::getWxOpenId, openId));
        if(null == customerInfo) {
            customerInfo = new CustomerInfo();
            customerInfo.setNickname(String.valueOf(System.currentTimeMillis()));
            customerInfo.setAvatarUrl("https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
            customerInfo.setWxOpenId(openId);
            this.save(customerInfo);
        }

        //登录日志
        CustomerLoginLog customerLoginLog = new CustomerLoginLog();
        customerLoginLog.setCustomerId(customerInfo.getId());
        customerLoginLog.setMsg("小程序登录");
        customerLoginLogMapper.insert(customerLoginLog);
        return customerInfo.getId();
    }

    @Override
    public CustomerLoginVo getCustomerLoginInfo(Long customerId) {
        CustomerInfo customerInfo = this.getById(customerId);
        CustomerLoginVo customerInfoVo = new CustomerLoginVo();
        BeanUtils.copyProperties(customerInfo, customerInfoVo);
        //判断是否绑定手机号码，如果未绑定，小程序端发起绑定事件
        Boolean isBindPhone = StringUtils.hasText(customerInfo.getPhone());
        customerInfoVo.setIsBindPhone(isBindPhone);
        return customerInfoVo;
    }
}
