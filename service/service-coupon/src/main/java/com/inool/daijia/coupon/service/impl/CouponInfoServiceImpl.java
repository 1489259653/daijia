package com.inool.daijia.coupon.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inool.daijia.coupon.mapper.CouponInfoMapper;
import com.inool.daijia.coupon.service.CouponInfoService;
import com.inool.daijia.model.entity.coupon.CouponInfo;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.inool.daijia.model.vo.base.PageVo;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import com.inool.daijia.model.vo.coupon.NoUseCouponVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CouponInfoServiceImpl extends ServiceImpl<CouponInfoMapper, CouponInfo> implements CouponInfoService {

    @Autowired
    private CouponInfoMapper couponInfoMapper;

    @Override
    public PageVo<NoReceiveCouponVo> findNoReceivePage(Page<CouponInfo> pageParam, Long customerId) {
        IPage<NoReceiveCouponVo> pageInfo = couponInfoMapper.findNoReceivePage(pageParam, customerId);
        return new PageVo(pageInfo.getRecords(), pageInfo.getPages(), pageInfo.getTotal());
    }

    @Override
    public PageVo<NoUseCouponVo> findNoUsePage(Page<CouponInfo> pageParam, Long customerId) {
        IPage<NoUseCouponVo> pageInfo = couponInfoMapper.findNoUsePage(pageParam, customerId);
        return new PageVo(pageInfo.getRecords(), pageInfo.getPages(), pageInfo.getTotal());
    }
}
