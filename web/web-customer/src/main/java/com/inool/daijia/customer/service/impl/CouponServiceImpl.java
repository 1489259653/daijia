package com.inool.daijia.customer.service.impl;

import com.inool.daijia.coupon.client.CouponFeignClient;
import com.inool.daijia.customer.service.CouponService;
import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import com.inool.daijia.model.vo.coupon.NoUseCouponVo;
import com.inool.daijia.model.vo.coupon.UsedCouponVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponFeignClient couponFeignClient;

    @Override
    public PageVo<NoReceiveCouponVo> findNoReceivePage(Long customerId, Long page, Long limit) {
        return couponFeignClient.findNoReceivePage(customerId, page, limit).getData();
    }
    @Override
    public PageVo<NoUseCouponVo> findNoUsePage(Long customerId, Long page, Long limit) {
        return couponFeignClient.findNoUsePage(customerId, page, limit).getData();
    }

    @Override
    public PageVo<UsedCouponVo> findUsedPage(Long customerId, Long page, Long limit) {
        return couponFeignClient.findUsedPage(customerId, page, limit).getData();
    }
    @Override
    public Boolean receive(Long customerId, Long couponId) {
        return couponFeignClient.receive(customerId, couponId).getData();
    }

}
