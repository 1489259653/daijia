package com.inool.daijia.coupon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.inool.daijia.model.entity.coupon.CouponInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.inool.daijia.model.vo.coupon.NoReceiveCouponVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CouponInfoMapper extends BaseMapper<CouponInfo> {

    IPage<NoReceiveCouponVo> findNoReceivePage(Page<CouponInfo> pageParam, Long customerId);
}
