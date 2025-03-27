package com.inool.daijia.coupon.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inool.daijia.model.entity.coupon.CouponInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import com.inool.daijia.model.vo.coupon.NoUseCouponVo;

public interface CouponInfoService extends IService<CouponInfo> {


    PageVo<NoReceiveCouponVo> findNoReceivePage(Page<CouponInfo> pageParam, Long customerId);

    PageVo<NoUseCouponVo> findNoUsePage(Page<CouponInfo> pageParam, Long customerId);
}
