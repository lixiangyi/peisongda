package com.theaty.peisongda.model;

import com.blankj.utilcode.utils.NetworkUtils;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.RequestParams;
import com.theaty.peisongda.system.AppContext;

import java.io.Serializable;

import foundation.toast.ToastUtil;

/**
 * @author 苗雨沛
 * @desc 基础模型
 */
public class BaseModel implements Serializable {
	private static final long serialVersionUID = 4298232973186841996L;
	private static final String LOGISTICS = "497dc13a7b7cbf2f";

	public static String HOSTIP = "47.95.38.243";
	public static String API_HOST_PRE = "http://" + HOSTIP + "/mobile";// 主机地址
	public static String API_IMAGE_HOST_PRE = "http://" + HOSTIP + "/ttshop/data/uploadshop/store/goods/";// 图片主机地址


	public BaseModel() {
		// 网络检验
		if (AppContext.getInstance() != null
				&& !NetworkUtils.isConnected()) {
			ToastUtil.showToast("请检查网络!");
		}
	}

	// 通用接口
	public abstract interface BaseModelIB {
		public abstract void StartOp();// 开始执行

		public abstract void successful(Object o);// 成功返回

		public abstract void failed(ResultsModel resultsModel);// 失败 及标识
	}

	// 通用接口2
	public abstract interface BaseModelIB2 extends BaseModelIB {

		public abstract void onLoading(long total, long current,
									   boolean isUploading);
	}

	// 对外接口
	public void BIBStart(BaseModelIB bib) {
		bib.StartOp();
	}

	public void BIBSucessful(BaseModelIB bib, Object o) {
		bib.successful(o);
	}

	public void BIBFailed(BaseModelIB bib, ResultsModel resultsModel) {
		bib.failed(resultsModel);
	}

	public void BIBOnLoading(BaseModelIB2 bib, long total, Long current,
							 Boolean isUploading) {
		bib.onLoading(total, current, isUploading);
	}

	// 根据act和op构建url
	public String buildGetUrl(String actName, String opName) {
		return API_HOST_PRE + "/" + actName + "/" + opName;
	}

	public String buildLogisticsUrl(String shipping_code) {
		return "http://www.kuaidi100.com/applyurl?key=" + LOGISTICS + "&com=" + "shunfeng" + "&nu=" + shipping_code;
	}

	/**
	 * 顺风：shunfeng， 申通：shentong
	 * 圆通：yuantong， 韵达：yunda
	 * 天天：tiantian， 中通：zhongtong
	 * ems：ems
	 */
	public String buildLogisticsUrl(String logisticsType, String shipping_code) {
		return "http://www.kuaidi100.com/applyurl?key=" + LOGISTICS + "&com=" + logisticsType + "&nu=" + shipping_code;
	}

	// 根据act和op构建url
	public String buildGetUrl(String actName, String opName,
							  RequestParams params) {
		String sb = new String();
		for (org.apache.http.NameValuePair nvp : params.getQueryStringParams()) {
			sb = sb + "&" + nvp.getName() + "=" + nvp.getValue();
		}
		return API_HOST_PRE + "?act=" + actName + "&op=" + opName + sb;
	}

	// 通过json反序列化为实例
	public BaseModel fromJson(String jsonStr) {
		return (new Gson()).fromJson(jsonStr, this.getClass());
	}

	// 类 序列化为 json字符串
	public String toJson() {
		return (new Gson()).toJson(this);
	}

	// 生成一个网络访问
	public HttpUtils genHttpUtils() {
		HttpUtils utils = new HttpUtils(30000);// 30秒超时
		utils.configCurrentHttpCacheExpiry(0);// 0秒缓存
		return utils;
	}

	public HttpUtils genMaxHttpUtils() {
		HttpUtils utils = new HttpUtils(Integer.MAX_VALUE);// 8秒超时
		utils.configCurrentHttpCacheExpiry(0);// 0秒缓存
		return utils;
	}
}
