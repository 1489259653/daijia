package com.inool.daijia.system.service;

import com.inool.daijia.model.entity.system.SysLoginLog;
import com.inool.daijia.model.query.system.SysLoginLogQuery;
import com.inool.daijia.model.vo.base.PageVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface SysLoginLogService extends IService<SysLoginLog> {

    PageVo<SysLoginLog> findPage(Page<SysLoginLog> pageParam, SysLoginLogQuery sysLoginLogQuery);

    /**
     * 记录登录信息
     *
     * @param sysLoginLog
     * @return
     */
    void recordLoginLog(SysLoginLog sysLoginLog);

}
