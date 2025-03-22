package com.inool.daijia.map.service;

import com.inool.daijia.model.form.map.CalculateDrivingLineForm;
import com.inool.daijia.model.vo.map.DrivingLineVo;

public interface MapService {

    DrivingLineVo calculateDrivingLine(CalculateDrivingLineForm calculateDrivingLineForm);
}
