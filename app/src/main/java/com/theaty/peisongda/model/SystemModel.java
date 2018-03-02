package com.theaty.peisongda.model;

/**
 * Created by Administrator on 2016/11/25.
 */
public class SystemModel extends BaseModel {
	/**
	 * 版本id
	 */
	public int version_id;
	/**
	 * 版本编号(安卓返回：Android，IOS返回：IOS)
	 */
	public String version_code;
	/**
	 * 版本号, 后台用的是此变量，对应versionCode
	 */
	public long version_num;
	/**
	 * 更新地址
	 */
	public String update_url;
}
