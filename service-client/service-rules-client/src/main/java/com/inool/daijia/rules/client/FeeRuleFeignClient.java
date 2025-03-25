package com.inool.daijia.rules.client;

import com.inool.daijia.common.result.Result;
import com.inool.daijia.model.form.rules.FeeRuleRequestForm;
import com.inool.daijia.model.form.rules.RewardRuleRequestForm;
import com.inool.daijia.model.vo.rules.FeeRuleResponseVo;
import com.inool.daijia.model.vo.rules.RewardRuleResponseVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "service-rules")
public interface FeeRuleFeignClient {

    /**
     * 计算订单费用
     * @param calculateOrderFeeForm
     * @return
     */
    @PostMapping("/rules/fee/calculateOrderFee")
    Result<FeeRuleResponseVo> calculateOrderFee(@RequestBody FeeRuleRequestForm calculateOrderFeeForm);
    /**
     * 计算订单奖励费用
     * @param rewardRuleRequestForm
     * @return
     */
    @PostMapping("/rules/reward/calculateOrderRewardFee")
    Result<RewardRuleResponseVo> calculateOrderRewardFee(@RequestBody RewardRuleRequestForm rewardRuleRequestForm);
}