package com.mall.service;

import com.mall.common.ServerResponse;

import java.util.Map;

/**
 * @author dhf
 */
public interface IOrderService {

    ServerResponse pay(Long orderNo,Integer userId,String path);

    ServerResponse aliCallback(Map<String,String> params);

    ServerResponse queryOrderPayStatus(Long orderNo,Integer uerId);

}