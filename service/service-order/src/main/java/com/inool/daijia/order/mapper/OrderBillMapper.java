package com.inool.daijia.order.mapper;

import com.inool.daijia.model.entity.order.OrderBill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface OrderBillMapper extends BaseMapper<OrderBill> {

    int updateCouponAmount(Long orderId, BigDecimal couponAmount);
}
