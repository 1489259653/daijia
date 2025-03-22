package com.inool.daijia.rules.service;

import com.inool.daijia.model.form.rules.FeeRuleRequestForm;
import com.inool.daijia.model.vo.rules.FeeRuleResponseVo;

public interface FeeRuleService {

    FeeRuleResponseVo calculateOrderFee(FeeRuleRequestForm calculateOrderFeeForm);
}
