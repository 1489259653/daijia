package com.inool.daijia.map.service.impl;

import com.alibaba.nacos.client.naming.utils.CollectionUtils;
import com.inool.daijia.common.constant.RedisConstant;
import com.inool.daijia.common.constant.SystemConstant;
import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.inool.daijia.common.util.LocationUtil;
import com.inool.daijia.driver.client.DriverInfoFeignClient;
import com.inool.daijia.map.client.LocationFeignClient;
import com.inool.daijia.map.repository.OrderServiceLocationRepository;
import com.inool.daijia.map.service.LocationService;
import com.inool.daijia.model.entity.driver.DriverSet;
import com.inool.daijia.model.entity.map.OrderServiceLocation;
import com.inool.daijia.model.form.map.OrderServiceLocationForm;
import com.inool.daijia.model.form.map.SearchNearByDriverForm;
import com.inool.daijia.model.form.map.UpdateDriverLocationForm;
import com.inool.daijia.model.form.map.UpdateOrderLocationForm;
import com.inool.daijia.model.vo.map.NearByDriverVo;
import com.inool.daijia.model.vo.map.OrderLocationVo;
import com.inool.daijia.model.vo.map.OrderServiceLastLocationVo;
import com.inool.daijia.order.client.OrderInfoFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.*;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LocationServiceImpl implements LocationService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private DriverInfoFeignClient driverInfoFeignClient;
    @Autowired
    private LocationFeignClient locationFeignClient;


    @Autowired
    private OrderServiceLocationRepository orderServiceLocationRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private OrderInfoFeignClient orderInfoFeignClient;

    @Override
    public BigDecimal calculateOrderRealDistance(Long orderId) {
        List<OrderServiceLocation> orderServiceLocationList = orderServiceLocationRepository.findByOrderIdOrderByCreateTimeAsc(orderId);
        double realDistance = 0;
        if(!CollectionUtils.isEmpty(orderServiceLocationList)) {
            for (int i = 0, size=orderServiceLocationList.size()-1; i < size; i++) {
                OrderServiceLocation location1 = orderServiceLocationList.get(i);
                OrderServiceLocation location2 = orderServiceLocationList.get(i+1);

                double distance = LocationUtil.getDistance(location1.getLatitude().doubleValue(), location1.getLongitude().doubleValue(), location2.getLatitude().doubleValue(), location2.getLongitude().doubleValue());
                realDistance += distance;
            }
        }
        //测试过程中，没有真正代驾，实际代驾GPS位置没有变化，模拟：实际代驾里程 = 预期里程 + 5
        if(realDistance == 0) {
            return orderInfoFeignClient.getOrderInfo(orderId).getData().getExpectDistance().add(new BigDecimal("5"));
        }
        return new BigDecimal(realDistance);
    }

    @Override
    public OrderServiceLastLocationVo getOrderServiceLastLocation(Long orderId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("orderId").is(orderId));
        query.with(Sort.by(Sort.Order.desc("createTime")));
        query.limit(1);
        OrderServiceLocation orderServiceLocation = mongoTemplate.findOne(query, OrderServiceLocation.class);

        //封装返回对象
        OrderServiceLastLocationVo orderServiceLastLocationVo = new OrderServiceLastLocationVo();
        BeanUtils.copyProperties(orderServiceLocation, orderServiceLastLocationVo);
        return orderServiceLastLocationVo;
    }

    @Override
    public Boolean saveOrderServiceLocation(List<OrderServiceLocationForm> orderLocationServiceFormList) {
        List<OrderServiceLocation> list = new ArrayList<>();
        orderLocationServiceFormList.forEach(item -> {
            OrderServiceLocation orderServiceLocation = new OrderServiceLocation();
            BeanUtils.copyProperties(item, orderServiceLocation);
            orderServiceLocation.setId(ObjectId.get().toString());
            orderServiceLocation.setCreateTime(new Date());
            list.add(orderServiceLocation);
        });
        orderServiceLocationRepository.saveAll(list);
        return true;
    }

    @Override
    public OrderLocationVo getCacheOrderLocation(Long orderId) {
        OrderLocationVo orderLocationVo = (OrderLocationVo)redisTemplate.opsForValue().get(RedisConstant.UPDATE_ORDER_LOCATION + orderId);
        return orderLocationVo;
    }

    @Override
    public Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm) {
        OrderLocationVo orderLocationVo = new OrderLocationVo();
        orderLocationVo.setLongitude(updateOrderLocationForm.getLongitude());
        orderLocationVo.setLatitude(updateOrderLocationForm.getLatitude());
        redisTemplate.opsForValue().set(RedisConstant.UPDATE_ORDER_LOCATION + updateOrderLocationForm.getOrderId(), orderLocationVo);
        return true;
    }

    @Override
    public List<NearByDriverVo> searchNearByDriver(SearchNearByDriverForm searchNearByDriverForm) {
        // 搜索经纬度位置5公里以内的司机
        //定义经纬度点
        Point point = new Point(searchNearByDriverForm.getLongitude().doubleValue(), searchNearByDriverForm.getLatitude().doubleValue());
        //定义距离：5公里(系统配置)
        Distance distance = new Distance(SystemConstant.NEARBY_DRIVER_RADIUS, RedisGeoCommands.DistanceUnit.KILOMETERS);
        //定义以point点为中心，distance为距离这么一个范围
        Circle circle = new Circle(point, distance);

        //定义GEO参数
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                .includeDistance() //包含距离
                .includeCoordinates() //包含坐标
                .sortAscending(); //排序：升序

        // 1.GEORADIUS获取附近范围内的信息
        GeoResults<RedisGeoCommands.GeoLocation<String>> result = this.redisTemplate.opsForGeo().radius(RedisConstant.DRIVER_GEO_LOCATION, circle, args);

        //2.收集信息，存入list
        List<GeoResult<RedisGeoCommands.GeoLocation<String>>> content = result.getContent();

        //3.返回计算后的信息
        List<NearByDriverVo> list = new ArrayList();
        if(!CollectionUtils.isEmpty(content)) {
            Iterator<GeoResult<RedisGeoCommands.GeoLocation<String>>> iterator = content.iterator();
            while (iterator.hasNext()) {
                GeoResult<RedisGeoCommands.GeoLocation<String>> item = iterator.next();

                //司机id
                Long driverId = Long.parseLong(item.getContent().getName());
                //当前距离
                BigDecimal currentDistance = new BigDecimal(item.getDistance().getValue()).setScale(2, RoundingMode.HALF_UP);
                log.info("司机：{}，距离：{}",driverId, item.getDistance().getValue());

                //获取司机接单设置参数
                DriverSet driverSet = driverInfoFeignClient.getDriverSet(driverId).getData();
                //接单里程判断，acceptDistance==0：不限制，
                if(driverSet.getAcceptDistance().doubleValue() != 0 && driverSet.getAcceptDistance().subtract(currentDistance).doubleValue() < 0) {
                    continue;
                }
                //订单里程判断，orderDistance==0：不限制
                if(driverSet.getOrderDistance().doubleValue() != 0 && driverSet.getOrderDistance().subtract(searchNearByDriverForm.getMileageDistance()).doubleValue() < 0) {
                    continue;
                }

                //满足条件的附近司机信息
                NearByDriverVo nearByDriverVo = new NearByDriverVo();
                nearByDriverVo.setDriverId(driverId);
                nearByDriverVo.setDistance(currentDistance);
                list.add(nearByDriverVo);
            }
        }
        return list;
    }


    @Override
    public Boolean removeDriverLocation(Long driverId) {
        redisTemplate.opsForGeo().remove(RedisConstant.DRIVER_GEO_LOCATION, driverId.toString());
        return true;
    }

    @Override
    public Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm) {
        //开启接单了才能更新司机接单位置
        DriverSet driverSet = driverInfoFeignClient.getDriverSet(updateDriverLocationForm.getDriverId()).getData();
        if(driverSet.getServiceStatus().intValue() == 1) {
            return locationFeignClient.updateDriverLocation(updateDriverLocationForm).getData();
        } else {
            throw new InoolException(ResultCodeEnum.NO_START_SERVICE);
        }
    }
}
