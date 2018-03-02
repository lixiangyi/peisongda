package com.theaty.peisongda.system;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theaty.peisongda.model.peisongda.MemberModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

import foundation.toast.ToastUtil;
import foundation.util.StringUtil;

/**
 * 数据存取
 *
 * @author
 */
public class DatasStore {
	public static final String APP_KEY = "24789690";
//	public static final String APP_KEY = "23015524";
	private static AppContext myApp = AppContext.getInstance();
	public static SharedPreferences infoSP = myApp.getSharedPreferences("tongyequan_info", Context.MODE_PRIVATE);
	private static final String KEY_FIRST_LAUNCH = "fistLaunch";
	private static final String KEY_USER_PHONE = "key_user_phone";
	private static final String KEY_USER_PASSWORD = "key_user_password";
	//用户城市
	private static final String KEY_USER_CITY = "key_user_city";

	public static void setFirstLaunch(Boolean mm) {
		infoSP.edit().putBoolean(KEY_FIRST_LAUNCH, mm)
				.commit();
	}

	public static Boolean isFirstLaunch() {
		return infoSP.getBoolean(KEY_FIRST_LAUNCH, true);
	}

	private static String KEY_SAVE_PASSWORD = "isSavePassword";

	/**
	 * 读取登陆页面"记住密码"复选框状态
	 *
	 * @return
	 */
	public static boolean isSavePassword() {
		return infoSP.getBoolean(KEY_SAVE_PASSWORD, false);
	}

	/**
	 * 保存登陆页面"记住密码"复选框状态
	 *
	 * @return
	 */
	public static void setSavePassword(boolean isSave) {
		infoSP.edit().putBoolean(KEY_SAVE_PASSWORD, isSave).commit();
	}

	/**
	 * 保存用户电话登录使用的电话号码
	 *
	 * @param phone
	 */
	public static void saveUserPhone(String phone) {
		infoSP.edit().putString(KEY_USER_PHONE, phone).commit();
	}

	/**
	 * 读取用户电话号码
	 */
	public static String getUserPhone() {
		return infoSP.getString(KEY_USER_PHONE, "");
	}




	private static final String CUR_MEMBER_KEY = "curMember";

	// 存档会员信息
	public static void setCurMember(MemberModel mm) {
		if (mm == null) {
			ToastUtil.showToast("模型消息：setCurMember 模型为 null");
			return;
		}
		String verString = mm.isLegal();
		if (verString.equals("access")) {
			putObj2Sp(infoSP, CUR_MEMBER_KEY, mm);
		} else {
			ToastUtil.showToast("模型消息：setCurMember" + verString);
		}
	}

	// 获取会员信息
	public static MemberModel getCurMember() {
		MemberModel mmMemberModel = (MemberModel) getObjectFromSp(
				infoSP, CUR_MEMBER_KEY, MemberModel.class);

		if (mmMemberModel == null) {
			return new MemberModel();
		}
		String verString = mmMemberModel.isLegal();
		if (!verString.equals("access")) {
			ToastUtil.showToast("模型消息：getCurMember" + verString);
			return null;
		}
//		MemberModel mmMemberModel = new MemberModel();
//		mmMemberModel.key = "3adf83cc46a451e81643bd8f3ec82bec";
//		mmMemberModel.key = "154bc3eb50a9e55c27c9e91be52cdf85";
		return mmMemberModel;
	}

	// 清除当前用户
	public static void removeCurMember() {
		infoSP.edit().remove(CUR_MEMBER_KEY).commit();// 移除
	}

	// 判断是否登录
	public static boolean isLogin() {
		if (getCurMember() != null && !StringUtil.isEmpty(getCurMember().key)) {
			return true;
		} else {
			return false;
		}
	}
	public static void setCurBlueTooth(BluetoothDevice mm) {
		if (mm == null) {
			return;
		}
		putObj2Sp(infoSP, "setCurBlueTooth", mm);
	}

	public static BluetoothDevice getCurBlueTooth() {
		BluetoothDevice mmMemberModel = (BluetoothDevice) getObjectFromSp(
				infoSP, "setCurBlueTooth", BluetoothDevice.class);
		return mmMemberModel;
	}




	//
	// gson一个对象并存储 如果为null 就删除原有对象
	public static void putObj2Sp(SharedPreferences sp, String key, Object o) {
		if (o != null) {
			Gson gson = new Gson();
			try {
				String ssString = gson.toJson(o, o.getClass());
				sp.edit().putString(key, ssString).commit();
			} catch (Exception e) {
				Log.e("putObj2Sp json转换出错: ", e.getMessage());
			}
		} else {
			sp.edit().remove(key).commit();
		}
	}

	// 获取一个对象 getObjectFromSp(String key,*.class) 失败返回null
	public static Object getObjectFromSp(SharedPreferences sp, String key, Type type) {
		Gson gson = new Gson();
		String ssString = sp.getString(key, "");
		return gson.fromJson(ssString, type);
	}

	private static final String VERSION_NUM = "version_num";

	public static String getVersionNum() {
		return infoSP.getString(VERSION_NUM, "1");
	}

	public static void saveVersionNum(String versionNum) {
		infoSP.edit().putString(VERSION_NUM, versionNum).apply();
	}

	private static final String KEY_GETUI_CID = "getui_cid";

	public static void setCid(String cid) {
		infoSP.edit().putString(KEY_GETUI_CID, cid).apply();
	}

	public static String getCid() {
		return infoSP.getString(KEY_GETUI_CID, null);
	}



	// ********** 搜索商品历史 **********
	private static final String SP_SEARCH_GOODS_HISTORY = "sp_search_goods_history";

	// 保存单个
	public static void saveGoodsSearchHistory(String word) {
		ArrayList<String> list = getGoodsSearchHistory();
		list.add(word);

		infoSP.edit().putString(SP_SEARCH_GOODS_HISTORY, new Gson().toJson(list)).apply();
	}

	// 获取全部
	public static ArrayList<String> getGoodsSearchHistory() {
		ArrayList<String> list = new ArrayList<>();

		String json = infoSP.getString(SP_SEARCH_GOODS_HISTORY, null);
		if (!TextUtils.isEmpty(json)) {
			list = new Gson().fromJson(json, new TypeToken<ArrayList<String>>() {
			}.getType());
		}

		return list;
	}

	// 移除单个
	public static void removeGoodsSearcHistory(String word) {
		ArrayList<String> list = getGoodsSearchHistory();
		list.remove(word);

		infoSP.edit().putString(SP_SEARCH_GOODS_HISTORY, new Gson().toJson(list)).apply();
	}


}
