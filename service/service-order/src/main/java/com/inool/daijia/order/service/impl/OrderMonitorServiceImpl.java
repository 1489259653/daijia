package com.inool.daijia.order.service.impl;

import com.inool.daijia.model.entity.order.OrderMonitor;
import com.inool.daijia.model.entity.order.OrderMonitorRecord;
import com.inool.daijia.order.mapper.OrderMonitorMapper;
import com.inool.daijia.order.repository.OrderMonitorRecordRepository;
import com.inool.daijia.order.service.OrderMonitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderMonitorServiceImpl extends ServiceImpl<OrderMonitorMapper, OrderMonitor> implements OrderMonitorService {

    @Autowired
    private OrderMonitorRecordRepository orderMonitorRecordRepository;

    @Override
    public Boolean saveOrderMonitorRecord(OrderMonitorRecord orderMonitorRecord) {
        orderMonitorRecordRepository.save(orderMonitorRecord);
        return true;
    }
}
