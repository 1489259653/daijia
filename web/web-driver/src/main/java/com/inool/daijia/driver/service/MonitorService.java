package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.order.OrderMonitorForm;
import org.springframework.web.multipart.MultipartFile;

public interface MonitorService {

    Boolean upload(MultipartFile file, OrderMonitorForm orderMonitorForm);
}
