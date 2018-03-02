package com.theaty.peisongda.model.peisongda;

import com.theaty.peisongda.model.BaseModel;

/**
 * 
 * @author Theaty 
 * @desc 
 */
public class DishesLibraryModel extends BaseModel {
/** 菜品id' , */
public  int  dishes_id;
/** 菜品名称' , */
public  String  dishes_name;
/** 菜品价格' , */
public  Double  dishes_price;
/** 菜品默认图片' , */
public  String  dishes_image;
/** 添加时间' , */
public  int  dishes_addtime;
/** 排序' , */
public  int  dishes_sort;
/** 分类id' , */
public  int  class_id;
/** 分类名称' , */
public  String  class_name;

 public int dishes_ischoose ;//1是被选中，0是未被选中

 public int choose ;//1是被选中，0是未被选中



//初始化默认值 
 public DishesLibraryModel() {
dishes_id  = 0;//菜品id' ,
dishes_name  = "";//菜品名称' ,
dishes_price  = 0d;//菜品价格' ,
dishes_image  = "";//菜品默认图片' ,
dishes_addtime  = 0;//添加时间' ,
dishes_sort  = 0;//排序' ,
class_id  = 0;//分类id' ,
class_name  = "";//分类名称' ,
}


}