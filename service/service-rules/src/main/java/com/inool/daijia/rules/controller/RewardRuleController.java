package com.inool.daijia.rules.controller;

import com.inool.daijia.common.result.Result;
import com.inool.daijia.model.form.rules.RewardRuleRequestForm;
import com.inool.daijia.model.vo.rules.RewardRuleResponseVo;
import com.inool.daijia.rules.service.RewardRuleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/rules/reward")
@SuppressWarnings({"unchecked", "rawtypes"})
public class RewardRuleController {

    @Autowired
    private RewardRuleService rewardRuleService;

    @Operation(summary = "计算订单奖励费用")
    @PostMapping("/calculateOrderRewardFee")
    public Result<RewardRuleResponseVo> calculateOrderRewardFee(@RequestBody RewardRuleRequestForm rewardRuleRequestForm) {
        return Result.ok(rewardRuleService.calculateOrderRewardFee(rewardRuleRequestForm));
    }
}

