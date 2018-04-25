package com.mall.common;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * 公共常量
 * @author dhf
 */
public class Const {

    //用于当前用户存取于session中时使用
    public static final String CURRENT_USER = "currentUser";

    //用于验证用户名和email是否有效
    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
    public static final String TOKEN_PREFIX = "token_";


    public interface RedisCacheExtime{
        int REDIS_SESSION_TIME = 60 * 30;//30min
    }

    /**
     * 用户角色代码
     */
    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    /**
     * 购物车相关属性
     */
    public interface Cart{
        int CHECKED = 1;    //购物车选中
        int UN_CHECKED = 0; //未选中

        String LIMIT_NUM_FAIL = "LIMIT_NUM_FAIL";//产品数量超出库存
        String LIMIT_NUM_SUCCESS = "LIMIT_NUM_SUCCESS";
    }


    /**
     * 产品排序规则
     */
    public interface ProductListOrderBy{
        Set<String> PRICE_ASC_DESC = Sets.newHashSet("price_desc","price_asc");
    }


    /**
     * 产品在售状态
     */
    public enum ProductStatusEnum{
        ON_SALE(1,"在线");

        private int code;
        private String value;

        ProductStatusEnum(int code,String value) {
            this.code = code;
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    /**
     * 订单状态
     */
    public enum OrderStatusEnum{
        CANCELED(0,"已取消"),
        NO_PAY(10,"未支付"),
        PAID(20,"已付款"),
        SHIPPED(40,"已发货"),
        ORDER_SUCCESS(50,"订单完成"),
        ORDER_CLOSE(60,"订单关闭");

        OrderStatusEnum(int code,String value) {
            this.code = code;
            this.value = value;
        }
        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static OrderStatusEnum codeOf(int code){
            for (OrderStatusEnum orderStatusEnum : values()) {
                if (orderStatusEnum.getCode() == code) {
                    return orderStatusEnum;
                }
            }
            throw new RuntimeException("没有找到对应枚举");
        }
    }

    /**
     * 支付宝回调状态
     */
    public interface AlipayCallback{
        String TRADE_STATUS_WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
        String TRADE_STATUS_TRADE_SUCCESS = "TRADE_SUCCESS";

        String RESPONSE_SUCCESS = "success";
        String RESPONSE_FAILED = "failed";
    }

    /**
     * 支付平台
     */
    public enum PayPlatformEnum{
        ALIPAY(1,"支付宝");

        PayPlatformEnum(int code,String value) {
            this.code = code;
            this.value = value;
        }
        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * 支付方式
     */
    public enum PaymentTypeEnum{
        ONLINE_PAY(1,"在线支付");

        PaymentTypeEnum(int code,String value) {
            this.code = code;
            this.value = value;
        }
        private int code;
        private String value;

        public int getCode() {
            return code;
        }

        public String getValue() {
            return value;
        }

        public static PaymentTypeEnum codeOf(int code){
            for (PaymentTypeEnum paymentTypeEnum : values()) {
                if (paymentTypeEnum.getCode() == code) {
                    return paymentTypeEnum;
                }
            }
            throw new RuntimeException("没有找到对应枚举");
        }

    }


    public interface REDIS_LOCK{
        String CLOSE_ORDER_TASK_LOCK = "CLOSE_ORDER_TASK_LOCK";
    }


}
