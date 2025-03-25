package com.inool.daijia.order.service;

import com.inool.daijia.model.entity.order.OrderMonitor;
import com.baomidou.mybatisplus.extension.service.IService;
import com.inool.daijia.model.entity.order.OrderMonitorRecord;

public interface OrderMonitorService extends IService<OrderMonitor> {

    Boolean saveOrderMonitorRecord(OrderMonitorRecord orderMonitorRecord);
}
