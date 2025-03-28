package com.inool.daijia.mgr.service;


import com.inool.daijia.model.entity.system.SysUser;
import com.inool.daijia.model.query.system.SysUserQuery;
import com.inool.daijia.model.vo.base.PageVo;

public interface SysUserService {

    SysUser getById(Long id);

    void save(SysUser sysUser);

    void update(SysUser sysUser);

    void remove(Long id);

    PageVo<SysUser> findPage(Long page, Long limit, SysUserQuery sysUserQuery);

    void updateStatus(Long id, Integer status);


}
