package com.inool.daijia.customer.service;

import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import com.inool.daijia.model.vo.coupon.NoUseCouponVo;

public interface CouponService  {


    PageVo<NoReceiveCouponVo> findNoReceivePage(Long customerId, Long page, Long limit);

    PageVo<NoUseCouponVo> findNoUsePage(Long customerId, Long page, Long limit);
}
