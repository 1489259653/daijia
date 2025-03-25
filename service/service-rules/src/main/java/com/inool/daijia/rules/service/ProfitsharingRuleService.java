package com.inool.daijia.rules.service;

import com.inool.daijia.model.form.rules.ProfitsharingRuleRequestForm;
import com.inool.daijia.model.vo.rules.ProfitsharingRuleResponseVo;

public interface ProfitsharingRuleService {

    ProfitsharingRuleResponseVo calculateOrderProfitsharingFee(ProfitsharingRuleRequestForm profitsharingRuleRequestForm);
}
