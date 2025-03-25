package com.inool.daijia.driver.service;

import com.inool.daijia.model.vo.order.TextAuditingVo;

public interface CiService {

    Boolean imageAuditing(String path);

    TextAuditingVo textAuditing(String content);
}
