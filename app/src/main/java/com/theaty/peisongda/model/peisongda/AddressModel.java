package com.theaty.peisongda.model.peisongda;

import com.theaty.peisongda.model.BaseModel;

/**
 * 
 * @author Theaty 
 * @desc 
 */
public class AddressModel extends BaseModel {
/** id' , */
public  int  catering_id;
/** 发布类型(1:求职,2:招聘,3:供应,4:求购)' , */
public  int  class_id;
/** 头像' , */
public  String  member_avatar;
/** 详细地址' , */
public  String  address_info;
/** 手机号' , */
public  String  member_mobile;
/** 描述' , */
public  String  catering_desc;

 public  String  area_info;//地区信息
 public  String member_nick;  //姓名
 public  long add_time;  //发布时间
 public  String class_name;  //发布类型
 public  int class_sort;  //发布分类
 public boolean isChoose;
 public String share_url; //分享地址
 public int member_id;
 public  String    reciver_name;
 public  String address;
    public String mob_phone;

    //初始化默认值
 public AddressModel() {
catering_id  = 0;//id' ,
class_id  = 0;//发布类型(1:求职,2:招聘,3:供应,4:求购)' ,
member_avatar  = "";//头像' ,
address_info  = "";//详细地址' ,
member_mobile  = "";//手机号' ,
catering_desc  = "";//描述' ,
}


}