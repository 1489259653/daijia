package com.inool.daijia.driver.service.impl;

import com.alibaba.fastjson.JSON;
import com.inool.daijia.common.execption.InoolException;
import com.inool.daijia.common.result.ResultCodeEnum;
import com.inool.daijia.dispatch.client.NewOrderFeignClient;
import com.inool.daijia.driver.service.OrderService;
import com.inool.daijia.map.client.LocationFeignClient;
import com.inool.daijia.map.client.MapFeignClient;
import com.inool.daijia.model.entity.order.OrderInfo;
import com.inool.daijia.model.form.customer.SubmitOrderForm;
import com.inool.daijia.model.form.map.CalculateDrivingLineForm;
import com.inool.daijia.model.form.order.*;
import com.inool.daijia.model.form.rules.FeeRuleRequestForm;
import com.inool.daijia.model.form.rules.ProfitsharingRuleRequestForm;
import com.inool.daijia.model.form.rules.RewardRuleRequestForm;
import com.inool.daijia.model.vo.map.DrivingLineVo;
import com.inool.daijia.model.vo.order.CurrentOrderInfoVo;
import com.inool.daijia.model.vo.order.NewOrderDataVo;
import com.inool.daijia.model.vo.order.OrderInfoVo;
import com.inool.daijia.model.vo.rules.FeeRuleResponseVo;
import com.inool.daijia.model.vo.rules.ProfitsharingRuleResponseVo;
import com.inool.daijia.model.vo.rules.RewardRuleResponseVo;
import com.inool.daijia.order.client.OrderInfoFeignClient;
import com.inool.daijia.rules.client.FeeRuleFeignClient;
import com.inool.daijia.rules.client.ProfitsharingRuleFeignClient;
import com.inool.daijia.rules.client.RewardRuleFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoFeignClient orderInfoFeignClient;

    @Autowired
    private LocationFeignClient locationFeignClient;

    @Autowired
    private FeeRuleFeignClient feeRuleFeignClient;

    @Autowired
    private RewardRuleFeignClient rewardRuleFeignClient;

    @Autowired
    private ProfitsharingRuleFeignClient profitsharingRuleFeignClient;

    @Autowired
    private NewOrderFeignClient newOrderFeignClient;


    @Override
    public Boolean endDrive(OrderFeeForm orderFeeForm) {
        //1.获取订单信息
        OrderInfo orderInfo = orderInfoFeignClient.getOrderInfo(orderFeeForm.getOrderId()).getData();
        if(orderInfo.getDriverId().longValue() != orderFeeForm.getDriverId().longValue()) {
            throw new InoolException(ResultCodeEnum.ARGUMENT_VALID_ERROR);
        }

        //2.计算订单实际里程
        BigDecimal realDistance = locationFeignClient.calculateOrderRealDistance(orderFeeForm.getOrderId()).getData();
        log.info("结束代驾，订单实际里程：{}", realDistance);

        //3.计算代驾实际费用
        FeeRuleRequestForm feeRuleRequestForm = new FeeRuleRequestForm();
        feeRuleRequestForm.setDistance(realDistance);
        feeRuleRequestForm.setStartTime(orderInfo.getStartServiceTime());
        //等候时间
        Integer waitMinute = Math.abs((int) ((orderInfo.getArriveTime().getTime() - orderInfo.getAcceptTime().getTime()) / (1000 * 60)));
        feeRuleRequestForm.setWaitMinute(waitMinute);
        log.info("结束代驾，费用参数：{}", JSON.toJSONString(feeRuleRequestForm));
        FeeRuleResponseVo feeRuleResponseVo = feeRuleFeignClient.calculateOrderFee(feeRuleRequestForm).getData();
        log.info("费用明细：{}", JSON.toJSONString(feeRuleResponseVo));
        //订单总金额 需加上 路桥费、停车费、其他费用、乘客好处费
        BigDecimal totalAmount = feeRuleResponseVo.getTotalAmount().add(orderFeeForm.getTollFee()).add(orderFeeForm.getParkingFee()).add(orderFeeForm.getOtherFee()).add(orderInfo.getFavourFee());
        feeRuleResponseVo.setTotalAmount(totalAmount);

        //4.计算系统奖励
        //4.1.获取订单数
        String startTime = new DateTime(orderInfo.getStartServiceTime()).toString("yyyy-MM-dd") + " 00:00:00";
        String endTime = new DateTime(orderInfo.getStartServiceTime()).toString("yyyy-MM-dd") + " 24:00:00";
        Long orderNum = orderInfoFeignClient.getOrderNumByTime(startTime, endTime).getData();
        //4.2.封装参数
        RewardRuleRequestForm rewardRuleRequestForm = new RewardRuleRequestForm();
        rewardRuleRequestForm.setStartTime(orderInfo.getStartServiceTime());
        rewardRuleRequestForm.setOrderNum(orderNum);
        //4.3.执行
        RewardRuleResponseVo rewardRuleResponseVo = rewardRuleFeignClient.calculateOrderRewardFee(rewardRuleRequestForm).getData();
        log.info("结束代驾，系统奖励：{}", JSON.toJSONString(rewardRuleResponseVo));

        //5.计算分账信息
        ProfitsharingRuleRequestForm profitsharingRuleRequestForm = new ProfitsharingRuleRequestForm();
        profitsharingRuleRequestForm.setOrderAmount(feeRuleResponseVo.getTotalAmount());
        profitsharingRuleRequestForm.setOrderNum(orderNum);
        ProfitsharingRuleResponseVo profitsharingRuleResponseVo = profitsharingRuleFeignClient.calculateOrderProfitsharingFee(profitsharingRuleRequestForm).getData();
        log.info("结束代驾，分账信息：{}", JSON.toJSONString(profitsharingRuleResponseVo));

        //6.封装更新订单账单相关实体对象
        UpdateOrderBillForm updateOrderBillForm = new UpdateOrderBillForm();
        updateOrderBillForm.setOrderId(orderFeeForm.getOrderId());
        updateOrderBillForm.setDriverId(orderFeeForm.getDriverId());
        //路桥费、停车费、其他费用
        updateOrderBillForm.setTollFee(orderFeeForm.getTollFee());
        updateOrderBillForm.setParkingFee(orderFeeForm.getParkingFee());
        updateOrderBillForm.setOtherFee(orderFeeForm.getOtherFee());
        //乘客好处费
        updateOrderBillForm.setFavourFee(orderInfo.getFavourFee());

        //实际里程
        updateOrderBillForm.setRealDistance(realDistance);
        //订单奖励信息
        BeanUtils.copyProperties(rewardRuleResponseVo, updateOrderBillForm);
        //代驾费用信息
        BeanUtils.copyProperties(feeRuleResponseVo, updateOrderBillForm);

        //分账相关信息
        BeanUtils.copyProperties(profitsharingRuleResponseVo, updateOrderBillForm);
        updateOrderBillForm.setProfitsharingRuleId(profitsharingRuleResponseVo.getProfitsharingRuleId());
        log.info("结束代驾，更新账单信息：{}", JSON.toJSONString(updateOrderBillForm));

        //7.结束代驾更新账单
        orderInfoFeignClient.endDrive(updateOrderBillForm);
        return true;
    }

    @Override
    public Integer getOrderStatus(Long orderId) {
        return orderInfoFeignClient.getOrderStatus(orderId).getData();
    }


    @Override
    public Boolean updateOrderCart(UpdateOrderCartForm updateOrderCartForm) {
        return orderInfoFeignClient.updateOrderCart(updateOrderCartForm).getData();
    }

    @Override
    public Boolean driverArriveStartLocation(Long orderId, Long driverId) {
        return orderInfoFeignClient.driverArriveStartLocation(orderId, driverId).getData();
    }

    @Override
    public List<NewOrderDataVo> findNewOrderQueueData(Long driverId) {
        return newOrderFeignClient.findNewOrderQueueData(driverId).getData();
    }


    @Override
    public Boolean robNewOrder(Long driverId, Long orderId) {
        return orderInfoFeignClient.robNewOrder(driverId, orderId).getData();
    }

    @Override
    public CurrentOrderInfoVo searchCustomerCurrentOrder(Long customerId) {
        return orderInfoFeignClient.searchCustomerCurrentOrder(customerId).getData();
    }

    @Override
    public CurrentOrderInfoVo searchDriverCurrentOrder(Long driverId) {
        return orderInfoFeignClient.searchDriverCurrentOrder(driverId).getData();
    }

    @Override
    public OrderInfoVo getOrderInfo(Long orderId, Long driverId) {
        //订单信息
        OrderInfo orderInfo = orderInfoFeignClient.getOrderInfo(orderId).getData();
        if(orderInfo.getDriverId().longValue() != driverId.longValue()) {
            throw new InoolException(ResultCodeEnum.ILLEGAL_REQUEST);
        }

        //封装订单信息
        OrderInfoVo orderInfoVo = new OrderInfoVo();
        orderInfoVo.setOrderId(orderId);
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        return orderInfoVo;
    }

    @Override
    public Boolean startDrive(StartDriveForm startDriveForm) {
        return orderInfoFeignClient.startDrive(startDriveForm).getData();
    }

}
