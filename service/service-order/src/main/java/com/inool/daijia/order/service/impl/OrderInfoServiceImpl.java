package com.inool.daijia.order.service.impl;

import com.inool.daijia.model.entity.order.OrderInfo;
import com.inool.daijia.order.mapper.OrderInfoMapper;
import com.inool.daijia.order.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {


}
