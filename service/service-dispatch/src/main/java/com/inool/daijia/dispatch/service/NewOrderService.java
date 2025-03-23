package com.inool.daijia.dispatch.service;

import com.inool.daijia.model.vo.dispatch.NewOrderTaskVo;

public interface NewOrderService {

    Long addAndStartTask(NewOrderTaskVo newOrderTaskVo);

    Boolean executeTask(Long jobId);
}
