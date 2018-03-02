package com.theaty.peisongda.model.peisongda;

import com.theaty.peisongda.model.BaseModel;

import java.util.ArrayList;

/**
 * 
 * @author Theaty 
 * @desc 
 */
public class OrderModel extends BaseModel {
/** 订单索引id' , */
public  int  order_id;
/** 订单编号' , */
public  String  order_sn;
/** 支付单号' , */
public  String  pay_sn;
/** 卖家店铺id' , */
public  int  store_id;
/** 卖家店铺名称' , */
public  String  store_name;
/** 买家id' , */
public  int  buyer_id;
/** 买家姓名' , */
public  String  buyer_name;
/** 买家电子邮箱' , */
public  String  buyer_email;
/** 订单生成时间' , */
public  String  add_time;
/** 支付方式名称代码' , */
public  String  payment_code;
/** 支付(付款)时间' , */
public  int  payment_time;
/** 订单完成时间' , */
public  int  finnshed_time;
/** 商品总价格' , */
public  Double  goods_amount;
/** 订单总价格' , */
public  Double  order_amount;
/** 充值卡支付金额' , */
public  Double  rcb_amount;
/** 预存款支付金额' , */
public  Double  pd_amount;
/** 运费' , */
public  Double  shipping_fee;
/** 评价状态 0未评价，1已评价，2已过期未评价' , */
public  int  evaluation_state;
/** 订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;21:已接单;' , */
public  int  order_state;
/** 退款状态:0是无退款,1是部分退款,2是全部退款' , */
public  int  refund_state;
/** 锁定状态:0是正常,大于0是锁定,默认是0' , */
public  int  lock_state;
/** 删除状态0未删除1放入回收站2彻底删除' , */
public  int  delete_state;
/** 退款金额' , */
public  Double  refund_amount;
/** 延迟时间,默认为0' , */
public  int  delay_time;
/** 1web2mobile' , */
public  int  order_from;
/** 物流单号' , */
public  String  shipping_code;
/** 配送方式（0配送1自提）' , */
public  int  delivery_mode;

/** 配送开始时间' , */
public  int  delivery_begin_time;
/** 配送结束时间' , */
public  int  delivery_end_time;
/** 配送员id' , */
public  int  deliver_id;
/** 配送员名称' , */
public  String  deliver_name;
/** 配送员电话' , */
public  String  deliver_phone;
/** 接单时间' , */
public  int  delivery_take_time;
/** 配送时间' , */
public  int  delivery_send_time;
/** 拣货员id' , */
public  int  picking_id;
/** 拣货员名称' , */
public  String  picking_name;
/** 拣货员电话' , */
public  String  picking_phone;
/** 拣货完成' , */
public  int  picking_time;
/** 拣货状态(1拣货中2拣货完成)' , */
public  int  picking_state;
/** 是否30分钟送达(1是0否)' , */
public  int  is_thirty;
/** 结算状态(0:未申请;1:待结算;2:已结算)' , */
public  int  bill_state;
/** 是否已发送短信(0否1是)' , */
public  int  send_message;

 public ArrayList<GoodsModel> extend_order_goods;
 public AddressModel reciver_info;
    public double order_amount_all;


    //初始化默认值 
 public OrderModel() {
order_id  = 0;//订单索引id' ,
order_sn  = "";//订单编号' ,
pay_sn  = "";//支付单号' ,
store_id  = 0;//卖家店铺id' ,
store_name  = "";//卖家店铺名称' ,
buyer_id  = 0;//买家id' ,
buyer_name  = "";//买家姓名' ,
buyer_email  = "";//买家电子邮箱' ,
add_time  = "";//订单生成时间' ,
payment_code  = "";//支付方式名称代码' ,
payment_time  = 0;//支付(付款)时间' ,
finnshed_time  = 0;//订单完成时间' ,
goods_amount  = 0d;//商品总价格' ,
order_amount  = 0d;//订单总价格' ,
rcb_amount  = 0d;//充值卡支付金额' ,
pd_amount  = 0d;//预存款支付金额' ,
shipping_fee  = 0d;//运费' ,
evaluation_state  = 0;//评价状态 0未评价，1已评价，2已过期未评价' ,
order_state  = 0;//订单状态：0(已取消)10(默认):未付款;20:已付款;30:已发货;40:已收货;21:已接单;' ,
refund_state  = 0;//退款状态:0是无退款,1是部分退款,2是全部退款' ,
lock_state  = 0;//锁定状态:0是正常,大于0是锁定,默认是0' ,
delete_state  = 0;//删除状态0未删除1放入回收站2彻底删除' ,
refund_amount  = 0d;//退款金额' ,
delay_time  = 0;//延迟时间,默认为0' ,
order_from  = 0;//1web2mobile' ,
shipping_code  = "";//物流单号' ,
delivery_mode  = 0;//配送方式（0配送1自提）' ,
delivery_begin_time  = 0;//配送开始时间' ,
delivery_end_time  = 0;//配送结束时间' ,
deliver_id  = 0;//配送员id' ,
deliver_name  = "";//配送员名称' ,
deliver_phone  = "";//配送员电话' ,
delivery_take_time  = 0;//接单时间' ,
delivery_send_time  = 0;//配送时间' ,
picking_id  = 0;//拣货员id' ,
picking_name  = "";//拣货员名称' ,
picking_phone  = "";//拣货员电话' ,
picking_time  = 0;//拣货完成' ,
picking_state  = 0;//拣货状态(1拣货中2拣货完成)' ,
is_thirty  = 0;//是否30分钟送达(1是0否)' ,
bill_state  = 0;//结算状态(0:未申请;1:待结算;2:已结算)' ,
send_message  = 0;//是否已发送短信(0否1是)' ,
}


}