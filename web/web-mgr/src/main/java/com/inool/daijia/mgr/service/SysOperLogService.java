package com.inool.daijia.mgr.service;

import com.inool.daijia.model.entity.system.SysOperLog;
import com.inool.daijia.model.query.system.SysOperLogQuery;
import com.inool.daijia.model.vo.base.PageVo;

public interface SysOperLogService {

    PageVo<SysOperLog> findPage(Long page, Long limit, SysOperLogQuery sysOperLogQuery);

    /**
     * 保存系统日志记录
     */
    void saveSysLog(SysOperLog sysOperLog);

    SysOperLog getById(Long id);
}
