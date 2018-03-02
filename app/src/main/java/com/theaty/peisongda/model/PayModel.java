package com.theaty.peisongda.model;

import com.lidroid.xutils.http.RequestParams;

/**
 * 支付模型
 *
 * @author blueam
 */
public class PayModel extends BaseModel {

    public int log_id;
    public int log_itemid;
    public String log_itemname;//项目名称
    public int log_memberid;
    public String log_membername;
    public long log_time;
    public long payment_time;
    public int payment_state;
    public int member_id;
    public String member_true_name;

    public int item_id;
    public String item_name;
    public String item_uname;
    public double item_price;
    public String entry_number;
    public long entry_date;

    public int contest_id;
    public String contest_name;
    public int contest_code;
    public int division_id;
    public String division_title;
    public int division_code;

    /**
     * 支付方式代号 (alipay、wxpay、ylpay、predeposit)
     */
    public String payment_code;
    /**
     * 支付方式名称（支付宝、微信支付、银联支付、余额支付）
     */
    public String payment_name;
    /**
     * 支付编号
     */
    public String pay_sn;
    /**
     * 实付金额
     */
    public double api_pay_amount;
    /**
     * 订单信息
     */
    public String order_info;

    /**
     * 订单加密信息，支付宝和微信都有
     */
    public String sign;

    /*******
     * 微信支付参数
     *****/
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public long timestamp;

    /**
     * 中宠缴费, 支付金额
     */
    public double log_itemamount;

    /**********
     * 提交订单所需参数
     **********/
    public int ifcart; // 是否从购物车购买(1是0否
    public String cart_id; // 商品编号|购买数量(如果是从购物车购买格式为:购物车编号1|购买数量1,购物车编号2|购买数量2)
    public int address_id;// 地址ID
    public int city_id; // 城市ID
    public String pay_name; // 支付方式
    public int pd_pay; // 是否使用预存款支付
    public double pd_amount;// 预存款支付金额
    public int points_pay = 0; // 是否使用积分支付
    public int points_amount = 0;// 积分支付数量
    public String member_paypwd;// 支付密码
    public String voucher;// 代金券（代金券模板ID|代金券店铺ID（用代金券列表里面的voucher_store_id的值）|代金券金额）;is_video:是否视频(1是0否)
    public int is_video;// 是否是视频
    private RequestParams params;
}
