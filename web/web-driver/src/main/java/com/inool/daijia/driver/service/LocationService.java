package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.map.OrderServiceLocationForm;
import com.inool.daijia.model.form.map.UpdateDriverLocationForm;
import com.inool.daijia.model.form.map.UpdateOrderLocationForm;

import java.util.List;

public interface LocationService {


    Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm);

    Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm);

    Boolean saveOrderServiceLocation(List<OrderServiceLocationForm> orderLocationServiceFormList);
}
