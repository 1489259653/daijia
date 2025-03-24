package com.inool.daijia.driver.service;

import com.inool.daijia.model.form.map.UpdateDriverLocationForm;
import com.inool.daijia.model.form.map.UpdateOrderLocationForm;

public interface LocationService {


    Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm);

    Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm);
}
