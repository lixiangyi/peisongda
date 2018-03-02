package com.theaty.peisongda.bean;

import java.io.Serializable;

/**
 * Created by wujian on 2017/3/7.
 */

public class SearchBean  implements Serializable {
//    @param keyword       搜索关键词
//	 * @param search_type   搜索类型(1选宠搜索2优品搜索)
//	 * @param gc_id         分类ID
//	 * @param market_id     宠市id(没有传0)
//	 * @param store_id      宠舍id(没有传0)
//	 * @param sort_type     排序依据(1:综合;2:价格递减;3:价格递增;)
//	 * @param a_id          属性ID(多个属性之间用下划线隔开,单个示例:3050;多个示例:3050_3068;)
//	 * @param brand_id      品牌ID
//	 * @param pet_sex       宠物性别(0:全部;1:公;2母)
//	 * @param minimum_price 最低价(没有传0)
//	 * @param maximum_price 最高价(没有传0)
    public  int   minimum_price;
    public  int   maximum_price;
    public  int   pet_sex;
    public  String   a_id;
    public  String  cate_name;
    public  String  market_name;
    public  String   keyword;
    public  int   search_type;
    public  String   gc_name;
    public  int   gc_id;
    public  int   market_id;
    public  int   store_id;
    public  int   sort_type;
    public  int   brand_id;
    public  int   is_supplier;

    public String title; // 宠市宠社传过去的标题栏
}
