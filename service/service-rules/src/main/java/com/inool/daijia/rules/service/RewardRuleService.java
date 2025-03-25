package com.inool.daijia.rules.service;

import com.inool.daijia.model.vo.rules.RewardRuleResponseVo;
import com.inool.daijia.model.form.rules.RewardRuleRequestForm;

public interface RewardRuleService {

    RewardRuleResponseVo calculateOrderRewardFee(RewardRuleRequestForm rewardRuleRequestForm);
}
