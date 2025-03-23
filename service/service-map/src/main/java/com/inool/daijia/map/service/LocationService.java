package com.inool.daijia.map.service;

import com.inool.daijia.model.form.map.UpdateDriverLocationForm;

public interface LocationService {

    Boolean updateDriverLocation(UpdateDriverLocationForm updateDriverLocationForm);

    Boolean removeDriverLocation(Long driverId);
}
