package com.juhuifu.quartz.base.constants;

public class AppConstants {
    /** 表示能被使用 */
    public final static String IS_ENABLED = "Y";
    
    /** ajax 请求code */
    public static final String CODE = "code";
    
    /** ajax 请求结果 success */
    public static final String SUCCESS = "success";

    /** ajax 请求结果 fail */
    public static final String FAIL = "fail";
    
    /** ajax 请求结果 isMultiSubmit */
    public static final String ISMULTISUBMIT= "isMultiSubmit";
    
    /** ajax 请求结果 unavailable */
    public static final String UNAVAILABLE= "unavailable";
    
    /**
     * 买入时，资金不足
     */
    public static final String NOT_ENOUGH_AMOUNT="not_enough_amount";
    
    /**
     * 卖出是，持仓不足
     */
    public static final String NOT_ENOUGH_POSITION="not_enough_position";
    
    /**
     * 用户未登陆
     */
    public static final String NOT_LOGINED="not login";
    
    /**
     * 买卖数量不为100整数倍
     */
    public static final String NOT_HUNDREDS="not_hundreds";
    
    /**
     * 账户已冻结
     */
    public static final String NOT_AVAILABLE="not_available";
    
    /**
     * 不在交易时间
     */
    public static final String NOT_TRADEING_TIME="not_trading_time";
    
    /**
     * 涨停不让买
     */
    public static final String  UP_HIGHER="up_higher";
    
    /**
     * 跌停不让卖
     */
    public static final String DOWN_LOWER="down_lower";
    
    /**
     * ST股票标志
     */
    public static final String ST="SSEST";
    
    /**
     * 普通股票涨停比率
     */
    public static final Double UP_RATE=1.10d;
    
    /**
     * 普通股票跌停比率
     */
    public static final Double DOWN_RATE=0.9d;
    
    /**
     * ST股票涨停比率
     */
    public static final Double ST_UP_RATE = 1.05d;
    
    /**
     * ST股票跌停比率
     */
    public static final Double ST_DOWN_RATE = 0.95d;
    /**
     * 股票类型不对，不能买入
     */
    public static final String CAN_NOT_BUY="can_not_buy"; 
    
    /**
     * 不能卖出
     */
    public static final String CAN_NOT_SELL="can_not_sell"; 
    
    /** 批处理多少条数据提交一次 */
    public static final int BATCH_SIZE = 1000;
    
    /** token失效 */
    public static final int TOKENTIMEOUT=-9999;
    public static final String TOKENTIMEOUTSTR="-9999";
    /** 开盘时间 */
    public static final String AM = "09:30:00";
    public static final String PM = "15:00:00";
}
