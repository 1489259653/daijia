package com.inool.daijia.map.service;

import com.inool.daijia.model.form.map.SearchNearByDriverForm;
import com.inool.daijia.model.form.map.UpdateDriverLocationForm;
import com.inool.daijia.model.form.map.UpdateOrderLocationForm;
import com.inool.daijia.model.vo.map.NearByDriverVo;

import java.util.List;

public interface LocationService {

    Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm);

    Boolean removeDriverLocation(Long driverId);

    List<NearByDriverVo> searchNearByDriver(SearchNearByDriverForm searchNearByDriverForm);

    Boolean updateOrderLocationToCache(UpdateOrderLocationForm updateOrderLocationForm);
}
