package com.mall.service;

import com.github.pagehelper.PageInfo;
import com.mall.common.ServerResponse;
import com.mall.vo.OrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * 订单service接口
 * @author dhf
 */
public interface IOrderService {

    ServerResponse createOrder(Integer userId,Integer shippingId);

    ServerResponse<String> cancel(Integer userId,Long orderNo);

    ServerResponse getOrderCartProduct(Integer userId);

    ServerResponse<OrderVo> getOrderDetail(Integer userInd, Long orderNo);

    ServerResponse<PageInfo> getOrderList(Integer userId,int pageNum,int pageSize);

    ServerResponse pay(Long orderNo,Integer userId,String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Long orderNo,Integer uerId);


    //backend
    ServerResponse<PageInfo> manageList(int pageNum,int pageSize);

    ServerResponse<OrderVo> manageDetail(Long orderNo);

    ServerResponse<PageInfo> manageSearch(Long orderNo,int pageNum,int pageSize);

    ServerResponse<String> manageSendGoods(Long orderNo);


    //hour个小时内未付款的订单，进行关闭
    void closeOrder(int hour);

}
