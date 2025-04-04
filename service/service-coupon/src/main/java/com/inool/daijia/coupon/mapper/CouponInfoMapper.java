package com.inool.daijia.coupon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inool.daijia.model.entity.coupon.CouponInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import com.inool.daijia.model.vo.coupon.NoUseCouponVo;
import com.inool.daijia.model.vo.coupon.UsedCouponVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    IPage<NoReceiveCouponVo> findNoReceivePage(Page<CouponInfo> pageParam, Long customerId);

    IPage<NoUseCouponVo> findNoUsePage(Page<CouponInfo> pageParam, Long customerId);

    IPage<UsedCouponVo> findUsedPage(Page<CouponInfo> pageParam, Long customerId);

    int updateReceiveCount(Long couponId);

    int updateReceiveCountByLimit(@Param("id") Long id);

    List<NoUseCouponVo> findNoUseList(Long customerId);

    int updateUseCount(Long id);
}
