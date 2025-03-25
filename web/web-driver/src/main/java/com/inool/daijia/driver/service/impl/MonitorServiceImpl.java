package com.inool.daijia.driver.service.impl;

import com.inool.daijia.driver.client.CiFeignClient;
import com.inool.daijia.driver.service.FileService;
import com.inool.daijia.driver.service.MonitorService;
import com.inool.daijia.model.entity.order.OrderMonitorRecord;
import com.inool.daijia.model.form.order.OrderMonitorForm;
import com.inool.daijia.model.vo.order.TextAuditingVo;
import com.inool.daijia.order.client.OrderMonitorFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class MonitorServiceImpl implements MonitorService {

    @Autowired
    private FileService fileService;

    @Autowired
    private OrderMonitorFeignClient orderMonitorFeignClient;

    @Autowired
    private CiFeignClient ciFeignClient;

    @Override
    public Boolean upload(MultipartFile file, OrderMonitorForm orderMonitorForm) {
        //上传对话文件
        String url = fileService.upload(file);
        log.info("upload: {}", url);

        //保存订单监控记录数
        OrderMonitorRecord orderMonitorRecord = new OrderMonitorRecord();
        orderMonitorRecord.setOrderId(orderMonitorForm.getOrderId());
        orderMonitorRecord.setFileUrl(url);
        orderMonitorRecord.setContent(orderMonitorForm.getContent());
        //记录审核结果

        TextAuditingVo textAuditingVo = ciFeignClient.textAuditing(orderMonitorForm.getContent()).getData();
        orderMonitorRecord.setResult(textAuditingVo.getResult());
        orderMonitorRecord.setKeywords(textAuditingVo.getKeywords());
        orderMonitorFeignClient.saveMonitorRecord(orderMonitorRecord);
        return true;
    }

}
