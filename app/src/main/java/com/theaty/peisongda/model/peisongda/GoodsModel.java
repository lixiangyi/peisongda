package com.theaty.peisongda.model.peisongda;

import com.theaty.peisongda.model.BaseModel;

/**
 * 
 * @author Theaty 
 * @desc 
 */
public class GoodsModel extends BaseModel {
/** 商品id(sku)' , */
public  int  goods_id;
 /** 商品id(sku)' , */
 public  int  rec_id;
/** 商品公共表id' , */
public  int  goods_commonid;
/** 商品名称（+规格名称）' , */
public  String  goods_name;
 public  double goods_num;
/** 商品广告词' , */
public  String  goods_jingle;
/** 店铺id' , */
public  int  store_id;
/** 店铺名称' , */
public  String  store_name;
/** 商品分类id' , */
public  int  gc_id;
/** 一级分类id' , */
public  int  gc_id_1;
/** 二级分类id' , */
public  int  gc_id_2;
/** 三级分类id' , */
public  int  gc_id_3;
/** 品牌id' , */
public  int  brand_id;
/** 商品价格' , */
public  Double  goods_price;
/** 商品促销价格' , */
public  Double  goods_promotion_price;
/** 促销类型 0无促销，1团购，2限时折扣' , */
public  int  goods_promotion_type;
/** 市场价' , */
public  Double  goods_marketprice;
/** 商家编号' , */
public  String  goods_serial;
/** 库存报警值' , */
public  int  goods_storage_alarm;
/** 商品点击数量' , */
public  int  goods_click;
/** 销售数量' , */
public  int  goods_salenum;
/** 收藏数量' , */
public  int  goods_collect;
/** 商品规格序列化' , */
public  String  goods_spec;
/** 商品库存' , */
public  int  goods_storage;
/** 商品主图' , */
public  String  goods_image;
/** 商品状态 0下架，1正常，10违规（禁售）' , */
public  int  goods_state;
/** 商品审核 1通过，0未通过，10审核中' , */
public  int  goods_verify;
/** 商品添加时间' , */
public  int  goods_addtime;
/** 商品编辑时间' , */
public  int  goods_edittime;
/** 一级地区id' , */
public  int  areaid_1;
/** 二级地区id' , */
public  int  areaid_2;
/** 颜色规格id' , */
public  int  color_id;
/** 运费模板id' , */
public  int  transport_id;
/** 运费 0为免运费' , */
public  Double  goods_freight;
/** 是否开具增值税发票 1是，0否' , */
public  int  goods_vat;
/** 商品推荐 1是，0否 默认0' , */
public  int  goods_commend;
/** 店铺分类id 首尾用,隔开' , */
public  String  goods_stcids;
/** 好评星级' , */
public  int  evaluation_good_star;
/** 评价数' , */
public  int  evaluation_count;
/** 是否为虚拟商品 1是，0否' , */
public  int  is_virtual;
/** 虚拟商品有效期' , */
public  int  virtual_indate;
/** 虚拟商品购买上限' , */
public  int  virtual_limit;
/** 是否允许过期退款， 1是，0否' , */
public  int  virtual_invalid_refund;
/** 是否为f码商品 1是，0否' , */
public  int  is_fcode;
/** 是否是预约商品 1是，0否' , */
public  int  is_appoint;
/** 是否是预售商品 1是，0否' , */
public  int  is_presell;
/** 是否拥有赠品' , */
public  int  have_gift;
/** 是否为平台自营' , */
public  int  is_own_shop;
/** 菜品id' , */
public  int  dishes_id;
 public  String  dishes_unit;//单位




//初始化默认值 
 public GoodsModel() {
goods_id  = 0;//商品id(sku)' ,
goods_commonid  = 0;//商品公共表id' ,
goods_name  = "";//商品名称（+规格名称）' ,
goods_jingle  = "";//商品广告词' ,
store_id  = 0;//店铺id' ,
store_name  = "";//店铺名称' ,
gc_id  = 0;//商品分类id' ,
gc_id_1  = 0;//一级分类id' ,
gc_id_2  = 0;//二级分类id' ,
gc_id_3  = 0;//三级分类id' ,
brand_id  = 0;//品牌id' ,
goods_price  = 0d;//商品价格' ,
goods_promotion_price  = 0d;//商品促销价格' ,
goods_promotion_type  = 0;//促销类型 0无促销，1团购，2限时折扣' ,
goods_marketprice  = 0d;//市场价' ,
goods_serial  = "";//商家编号' ,
goods_storage_alarm  = 0;//库存报警值' ,
goods_click  = 0;//商品点击数量' ,
goods_salenum  = 0;//销售数量' ,
goods_collect  = 0;//收藏数量' ,
goods_spec  = "";//商品规格序列化' ,
goods_storage  = 0;//商品库存' ,
goods_image  = "";//商品主图' ,
goods_state  = 0;//商品状态 0下架，1正常，10违规（禁售）' ,
goods_verify  = 0;//商品审核 1通过，0未通过，10审核中' ,
goods_addtime  = 0;//商品添加时间' ,
goods_edittime  = 0;//商品编辑时间' ,
areaid_1  = 0;//一级地区id' ,
areaid_2  = 0;//二级地区id' ,
color_id  = 0;//颜色规格id' ,
transport_id  = 0;//运费模板id' ,
goods_freight  = 0d;//运费 0为免运费' ,
goods_vat  = 0;//是否开具增值税发票 1是，0否' ,
goods_commend  = 0;//商品推荐 1是，0否 默认0' ,
goods_stcids  = "";//店铺分类id 首尾用,隔开' ,
evaluation_good_star  = 0;//好评星级' ,
evaluation_count  = 0;//评价数' ,
is_virtual  = 0;//是否为虚拟商品 1是，0否' ,
virtual_indate  = 0;//虚拟商品有效期' ,
virtual_limit  = 0;//虚拟商品购买上限' ,
virtual_invalid_refund  = 0;//是否允许过期退款， 1是，0否' ,
is_fcode  = 0;//是否为f码商品 1是，0否' ,
is_appoint  = 0;//是否是预约商品 1是，0否' ,
is_presell  = 0;//是否是预售商品 1是，0否' ,
have_gift  = 0;//是否拥有赠品' ,
is_own_shop  = 0;//是否为平台自营' ,
dishes_id  = 0;//菜品id' ,
}


}