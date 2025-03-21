package com.inool.daijia.mgr.service;

import com.inool.daijia.model.entity.system.SysPost;
import com.inool.daijia.model.query.system.SysPostQuery;
import com.inool.daijia.model.vo.base.PageVo;

import java.util.List;

public interface SysPostService {

    SysPost getById(Long id);

    void save(SysPost sysPost);

    void update(SysPost sysPost);

    void remove(Long id);

    PageVo<SysPost> findPage(Long page, Long limit, SysPostQuery sysPostQuery);

    void updateStatus(Long id, Integer status);

    List<SysPost> findAll();
}
