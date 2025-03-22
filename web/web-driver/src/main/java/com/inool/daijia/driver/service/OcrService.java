package com.inool.daijia.driver.service;

import com.inool.daijia.model.vo.driver.DriverLicenseOcrVo;
import com.inool.daijia.model.vo.driver.IdCardOcrVo;
import org.springframework.web.multipart.MultipartFile;

public interface OcrService {

    IdCardOcrVo idCardOcr(MultipartFile file);

    DriverLicenseOcrVo driverLicenseOcr(MultipartFile file);
}
