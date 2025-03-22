package com.inool.daijia.driver.service.impl;

import com.alibaba.fastjson.JSON;

import com.inool.daijia.driver.client.CosFeignClient;
import com.inool.daijia.driver.service.CosService;
import com.inool.daijia.model.vo.driver.CosUploadVo;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.tencentcloudapi.common.profile.Region;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class CosServiceImpl implements CosService {
    @Autowired
    private CosFeignClient cosFeignClient;

    @Override
    public CosUploadVo upload(MultipartFile file, String path) {
        return cosFeignClient.upload(file, path).getData();
    }

}
