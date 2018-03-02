package com.theaty.peisongda.bean;

import org.droidparts.annotation.sql.Column;
import org.droidparts.annotation.sql.Table;
import org.droidparts.model.Entity;

/**
 * Created by wujian on 2016/12/29.
 *
 * 查询历史  历史记录表
 */

@Table(name = "history")
public class History extends Entity {
    //内容
    @Column(name = "content",nullable = true)
    public  String  content;
    //时间
    @Column(name = "date",nullable = false)
    public  String  date;
}
