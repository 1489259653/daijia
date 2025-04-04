package com.inool.daijia.driver.service.impl;

import com.inool.daijia.driver.service.LocationService;
import com.inool.daijia.map.client.LocationFeignClient;
import com.inool.daijia.model.form.map.OrderServiceLocationForm;
import com.inool.daijia.model.form.map.UpdateDriverLocationForm;
import com.inool.daijia.model.form.map.UpdateOrderLocationForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationFeignClient locationFeignClient;

    @Override
    public Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm) {
        return locationFeignClient.updateDriverLocation(updateDriverLocationForm).getData();
    }

    @Override
    public Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm) {
        return locationFeignClient.updateOrderLocationToCache(updateOrderLocationForm).getData();
    }

    @Override
    public Boolean saveOrderServiceLocation(List<OrderServiceLocationForm> orderLocationServiceFormList) {
        return locationFeignClient.saveOrderServiceLocation(orderLocationServiceFormList).getData();
    }
}
