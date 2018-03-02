package com.theaty.peisongda.model.peisongda;

import com.theaty.peisongda.model.BaseModel;

import java.util.ArrayList;

/**
 * 
 * @author Theaty 
 * @desc 
 */
public class DishesClassModel extends BaseModel {
/** 菜品分类id' , */
public  int  class_id;
/** 分类名称' , */
public  String  class_name;
/** 排序' , */
public  int  class_sort;


 public ArrayList<DishesLibraryModel> child_dishes;
 public boolean isSelected;


 //初始化默认值 
 public DishesClassModel() {
class_id  = 0;//菜品分类id' ,
class_name  = "";//分类名称' ,
class_sort  = 0;//排序' ,
}


}