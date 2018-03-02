package com.theaty.peisongda.model;

import com.google.gson.JsonSyntaxException;
import com.theaty.peisongda.model.adapter.ThtGosn;

/**
 * 
 */

/**
 * 
 * @author blueam
 * @desc 返回的数据结构
 */
public class ResultsModel {

	public ResultsModel() {
		state = 0;
	}

	public ResultsModel(int sState, String errorMsg) {
		this.state = sState;
		this.errorMsg = errorMsg;
	}

	private Integer state;// 状态指示符
	private String jsonDatas;// 数据Json格式主体
	private String stringDatas;// 数据string格式主体
	private String errorMsg;// 错误描述

	public String getStringDatas() {
		return stringDatas;
	}

	public void setStringDatas(String stringDatas) {
		this.stringDatas = stringDatas;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getJsonDatas() {
		return jsonDatas;
	}

	public void setJsonDatas(String jsonDatas) {
		this.jsonDatas = jsonDatas;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * @desc 获取实例
	 * @param requestStr
	 * 返回值：
	 */
	public static ResultsModel getInstanseFromStr(String requestStr) {
		ResultsModel rm = new ResultsModel();

		ResultsModelO resultsModelo = null;
		try {
			resultsModelo = (ThtGosn.genGson()).fromJson(requestStr, ResultsModelO.class);
		} catch (JsonSyntaxException e) {
			rm.errorMsg = "网络错误";
			rm.state = 0;
			return rm;
		}
		int arg1 = 0;
		if (resultsModelo != null) {
			arg1 = resultsModelo.state;

			if (arg1 == 1)
				rm.setJsonDatas((ThtGosn.genGson()).toJson(resultsModelo.datas));
			else
				rm.setErrorMsg((String)resultsModelo.datas);
			rm.setState(arg1);// 设置状态指示
			if (resultsModelo.datas != null)
				rm.setStringDatas(resultsModelo.datas.toString());// 设置string主体
		}

		return rm;
	}

	/**
	 * 
	 * @author Administrator 返回 实体类
	 * 
	 */
	public class ResultsModelO {

		public ResultsModelO() {
			// TODO Auto-generated constructor stub
		}

		public int state;// 状态指示符
		public Object datas;// 数据主体

	}

	// string 转 ascii
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

}
