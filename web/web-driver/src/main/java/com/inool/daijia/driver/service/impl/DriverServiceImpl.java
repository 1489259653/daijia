package com.inool.daijia.driver.service.impl;

import com.inool.daijia.common.constant.RedisConstant;
import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.inool.daijia.dispatch.client.NewOrderFeignClient;
import com.inool.daijia.driver.client.DriverInfoFeignClient;
import com.inool.daijia.driver.service.DriverService;
import com.inool.daijia.map.client.LocationFeignClient;
import com.inool.daijia.model.form.driver.DriverFaceModelForm;
import com.inool.daijia.model.form.driver.UpdateDriverAuthInfoForm;
import com.inool.daijia.model.vo.driver.DriverAuthInfoVo;
import com.inool.daijia.model.vo.driver.DriverLoginVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverInfoFeignClient driverInfoFeignClient;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private LocationFeignClient locationFeignClient;

    @Autowired
    private NewOrderFeignClient newOrderDispatchFeignClient;

    @SneakyThrows
    @Override
    public String login(String code) {
        //获取openId
        Long driverId = driverInfoFeignClient.login(code).getData();

        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set(RedisConstant.USER_LOGIN_KEY_PREFIX + token, driverId.toString(), RedisConstant.USER_LOGIN_KEY_TIMEOUT, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public DriverLoginVo getDriverLoginInfo(Long driverId) {
        return driverInfoFeignClient.getDriverLoginInfo(driverId).getData();
    }
    @Override
    public DriverAuthInfoVo getDriverAuthInfo(Long driverId) {
        return driverInfoFeignClient.getDriverAuthInfo(driverId).getData();
    }

    @Override
    public Boolean updateDriverAuthInfo(UpdateDriverAuthInfoForm updateDriverAuthInfoForm) {
        return driverInfoFeignClient.UpdateDriverAuthInfo(updateDriverAuthInfoForm).getData();
    }

    @Override
    public Boolean creatDriverFaceModel(DriverFaceModelForm driverFaceModelForm) {
        return driverInfoFeignClient.creatDriverFaceModel(driverFaceModelForm).getData();
    }
    @Override
    public Boolean isFaceRecognition(Long driverId) {
        return driverInfoFeignClient.isFaceRecognition(driverId).getData();
    }

    @Override
    public Boolean verifyDriverFace(DriverFaceModelForm driverFaceModelForm) {
        return driverInfoFeignClient.verifyDriverFace(driverFaceModelForm).getData();
    }


    @Override
    public Boolean startService(Long driverId) {
        //判断认证状态
        DriverLoginVo driverLoginVo = driverInfoFeignClient.getDriverLoginInfo(driverId).getData();
        if(driverLoginVo.getAuthStatus().intValue() != 2) {
            throw new InoolException(ResultCodeEnum.AUTH_ERROR);
        }

        //判断当日是否人脸识别
        Boolean isFaceRecognition = driverInfoFeignClient.isFaceRecognition(driverId).getData();
        if(!isFaceRecognition) {
            throw new InoolException(ResultCodeEnum.FACE_ERROR);
        }

        //更新司机接单状态
        driverInfoFeignClient.updateServiceStatus(driverId, 1);

        //删除司机位置信息
        locationFeignClient.removeDriverLocation(driverId);

        //清空司机新订单队列
        newOrderDispatchFeignClient.clearNewOrderQueueData(driverId);
        return true;
    }
}
