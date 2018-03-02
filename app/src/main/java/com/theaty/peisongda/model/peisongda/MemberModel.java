package com.theaty.peisongda.model.peisongda;

import android.text.TextUtils;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.sina.weibo.sdk.utils.LogUtil;
import com.theaty.peisongda.model.BaseModel;
import com.theaty.peisongda.model.ResultsModel;
import com.theaty.peisongda.model.SystemModel;
import com.theaty.peisongda.model.adapter.ThtGosn;
import com.theaty.peisongda.oss.Ossutil;
import com.theaty.peisongda.system.DatasStore;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

import foundation.log.LogUtils;

/**
 * @author Theaty
 * @desc
 */
public class MemberModel extends BaseModel {
    /**
     * 会员id' ,
     */
    public int member_id;
    /**
     * 会员名称' ,
     */
    public String member_name;
    /**
     * 真实姓名' ,
     */
    public String member_truename;
    /**
     * 会员头像' ,
     */
    public String member_avatar;
    /**
     * 会员性别' ,
     */
    public int member_sex;
    /**
     * 生日' ,
     */
    public String member_birthday;
    /**
     * 会员密码' ,
     */
    public String member_passwd;
    /**
     * 支付密码' ,
     */
    public String member_paypwd;
    /**
     * 会员邮箱' ,
     */
    public String member_email;
    /**
     * 0未绑定1已绑定' ,
     */
    public int member_email_bind;
    /**
     * 手机号' ,
     */
    public String member_mobile;
    /**
     * 0未绑定1已绑定' ,
     */
    public int member_mobile_bind;
    /**
     * qq' ,
     */
    public String member_qq;
    /**
     * 阿里旺旺' ,
     */
    public String member_ww;
    /**
     * 登录次数' ,
     */
    public int member_login_num;
    /**
     * 会员注册时间' ,
     */
    public String member_time;
    /**
     * 当前登录时间' ,
     */
    public String member_login_time;
    /**
     * 上次登录时间' ,
     */
    public String member_old_login_time;
    /**
     * 当前登录ip' ,
     */
    public String member_login_ip;
    /**
     * 上次登录ip' ,
     */
    public String member_old_login_ip;
    /**
     * qq互联id' ,
     */
    public String member_qqopenid;
    /**
     * qq账号相关信息' ,
     */
    public String member_qqinfo;
    /**
     * 新浪微博登录id' ,
     */
    public String member_sinaopenid;
    /**
     * 新浪账号相关信息序列化值' ,
     */
    public String member_sinainfo;
    /**
     * 微信帐号id' ,
     */
    public String member_wx_openid;
    /**
     * 会员积分' ,
     */
    public int member_points;
    /**
     * 预存款可用金额' ,
     */
    public Double available_predeposit;
    /**
     * 预存款冻结金额' ,
     */
    public Double freeze_predeposit;
    /**
     * 可用充值卡余额' ,
     */
    public Double available_rc_balance;
    /**
     * 冻结充值卡余额' ,
     */
    public Double freeze_rc_balance;
    /**
     * 是否允许举报(1可以/2不可以)' ,
     */
    public int inform_allow;
    /**
     * 会员是否有购买权限 1为开启 0为关闭' ,
     */
    public int is_buy;
    /**
     * 会员是否有咨询和发送站内信的权限 1为开启 0为关闭' ,
     */
    public int is_allowtalk;
    /**
     * 会员的开启状态 1为开启 0为关闭' ,
     */
    public int member_state;
    /**
     * sns空间访问次数' ,
     */
    public int member_snsvisitnum;
    /**
     * 地区id' ,
     */
    public int member_areaid;
    /**
     * 城市id' ,
     */
    public int member_cityid;
    /**
     * 省份id' ,
     */
    public int member_provinceid;
    /**
     * 地区内容' ,
     */
    public String member_areainfo;
    /**
     * 隐私设定' ,
     */
    public String member_privacy;
    /**
     * 会员常用操作' ,
     */
    public String member_quicklink;
    /**
     * 会员经验值' ,
     */
    public int member_exppoints;
    /**
     * 环信用户名' ,
     */
    public String hx_username;
    /**
     * 环信密码' ,
     */
    public String hx_userpwd;
    /**
     * 会员昵称' ,
     */
    public String member_nick;
    /**
     * 会员个推cid' ,
     */
    public String member_cid_1;
    /**
     * 会员个推cid' ,
     */
    public String member_cid_2;
    /**
     * 会员个推cid' ,
     */
    public String member_cid_3;
    /**
     * 会员个推cid' ,
     */
    public String member_cid_4;
    /**
     * 会员个推cid' ,
     */
    public String member_cid_5;
    /**
     * 客户端类型 android ios（和member_cid_1对应）' ,
     */
    public String client_type_1;
    /**
     * 客户端类型 android ios（和member_cid_1对应）' ,
     */
    public String client_type_2;
    /**
     * 客户端类型 android ios（和member_cid_1对应）' ,
     */
    public String client_type_3;
    /**
     * 客户端类型 android ios（和member_cid_1对应）' ,
     */
    public String client_type_4;
    /**
     * 客户端类型 android ios（和member_cid_1对应）' ,
     */
    public String client_type_5;
    public String key;


    //初始化默认值
    public MemberModel() {
        member_id = 0;//会员id' ,
        member_name = "";//会员名称' ,
        member_truename = "";//真实姓名' ,
        member_avatar = "";//会员头像' ,
        member_sex = 0;//会员性别' ,
        member_birthday = "";//生日' ,
        member_passwd = "";//会员密码' ,
        member_paypwd = "";//支付密码' ,
        member_email = "";//会员邮箱' ,
        member_email_bind = 0;//0未绑定1已绑定' ,
        member_mobile = "";//手机号' ,
        member_mobile_bind = 0;//0未绑定1已绑定' ,
        member_qq = "";//qq' ,
        member_ww = "";//阿里旺旺' ,
        member_login_num = 0;//登录次数' ,
        member_time = "";//会员注册时间' ,
        member_login_time = "";//当前登录时间' ,
        member_old_login_time = "";//上次登录时间' ,
        member_login_ip = "";//当前登录ip' ,
        member_old_login_ip = "";//上次登录ip' ,
        member_qqopenid = "";//qq互联id' ,
        member_qqinfo = "";//qq账号相关信息' ,
        member_sinaopenid = "";//新浪微博登录id' ,
        member_sinainfo = "";//新浪账号相关信息序列化值' ,
        member_wx_openid = "";//微信帐号id' ,
        member_points = 0;//会员积分' ,
        available_predeposit = 0d;//预存款可用金额' ,
        freeze_predeposit = 0d;//预存款冻结金额' ,
        available_rc_balance = 0d;//可用充值卡余额' ,
        freeze_rc_balance = 0d;//冻结充值卡余额' ,
        inform_allow = 0;//是否允许举报(1可以/2不可以)' ,
        is_buy = 0;//会员是否有购买权限 1为开启 0为关闭' ,
        is_allowtalk = 0;//会员是否有咨询和发送站内信的权限 1为开启 0为关闭' ,
        member_state = 0;//会员的开启状态 1为开启 0为关闭' ,
        member_snsvisitnum = 0;//sns空间访问次数' ,
        member_areaid = 0;//地区id' ,
        member_cityid = 0;//城市id' ,
        member_provinceid = 0;//省份id' ,
        member_areainfo = "";//地区内容' ,
        member_privacy = "";//隐私设定' ,
        member_quicklink = "";//会员常用操作' ,
        member_exppoints = 0;//会员经验值' ,
        hx_username = "";//环信用户名' ,
        hx_userpwd = "";//环信密码' ,
        member_nick = "";//会员昵称' ,
        member_cid_1 = "";//会员个推cid' ,
        member_cid_2 = "";//会员个推cid' ,
        member_cid_3 = "";//会员个推cid' ,
        member_cid_4 = "";//会员个推cid' ,
        member_cid_5 = "";//会员个推cid' ,
        client_type_1 = "";//客户端类型 android ios（和member_cid_1对应）' ,
        client_type_2 = "";//客户端类型 android ios（和member_cid_1对应）' ,
        client_type_3 = "";//客户端类型 android ios（和member_cid_1对应）' ,
        client_type_4 = "";//客户端类型 android ios（和member_cid_1对应）' ,
        client_type_5 = "";//客户端类型 android ios（和member_cid_1对应）' ,
    }

    /**
     * 检验自身是否一个合法的类型
     *
     * @return
     */
    public String isLegal() {

//		if (member_id <= 0) {
//			return " member_id非法";
//		}
        if (key.length() < 1) {
            return " key非法";
        }
        if (TextUtils.isEmpty(member_name)) {
            return " member_name非法";
        }
        return "access";
    }

    /**
     * 分享接口
     *
     * @param type         分享的类型（0--商品详情，1--收藏菜谱列表的分享）
     * @param cookbook_ids 对应要分享的菜谱id(注：分享收藏列表时，该参数传列表的所有cookbook_id,且以","分割)
     * @param bib
     */
    public void share_interface(int type, String cookbook_ids, final BaseModelIB bib) {
        String url = buildGetUrl("Index", "share_interface"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "share_interface"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("type", String.valueOf(type));
        switch (type) {
            case 0:
                params.addBodyParameter("cookbook_ids", cookbook_ids);
                break;
            case 1:
                params.addBodyParameter("cookbook_ids", cookbook_ids);
                break;
            case 2:
                break;
        }
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功

                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 关于我们
     *
     * @param bib
     */
    public void Index(final BaseModelIB bib) {
        String url = buildGetUrl("Index", "about_us"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "about_us"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功

                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 注册生成验证码
     *
     * @param username 用户名（电话号码）
     * @param bib
     */
    public void login_identifycode(String username, final BaseModelIB bib) {
        String url = buildGetUrl("Login", "identifycode"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "identifycode 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();

        params.addBodyParameter("phone_num", username);
        params.addBodyParameter("tycode", getCode(username));
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 生成32位md5码
     *
     * @param password
     * @return
     */
    public static String md5Password(String password) {

        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }

            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public String getCode(String phoneNum) {
        BigInteger b = new BigInteger(phoneNum);
        BigInteger c = new BigInteger(phoneNum.trim().substring(1, 9));
        BigInteger ss = b.mod(c);
        Calendar cc = Calendar.getInstance();
        int day = cc.get(Calendar.DAY_OF_MONTH);
        int ss2 = ss.intValue() + day;
        String ss1 = md5Password(ss2 + "");
        return ss1;
    }

    /**
     * 会员登陆
     *
     * @param username 用户名（电话号码）
     * @param password 用户密码
     *                 //@param client           类型 客户端类型（'android'，'wechat', 'ios'）
     * @param bib
     */
    public void Login(String username, String password, final BaseModelIB bib) {
        String url = buildGetUrl("Login", "third_party_loginadd"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "third_party_loginadd 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("username", username);
        params.addBodyParameter("identify_code", password);
        params.addBodyParameter("openid_type", 1+"");
        params.addBodyParameter("client", "android");
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    MemberModel ims = (ThtGosn.genGson()).fromJson(rm.getJsonDatas(), MemberModel.class);
                    DatasStore.setCurMember(ims);
                    uploadCid(DatasStore.getCid());
                    BIBSucessful(bib, ims);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 修改会员个推cid
     *
     * @param key        key
     * @param member_cid 个推CID
     *                   //     * @param client_type_1  客户端类型
     * @param bib
     */
    public void update_cid(String key, String member_cid, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "update_cid"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "update_cid 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();

        params.addBodyParameter("key", key);
        params.addBodyParameter("member_cid", member_cid);
        params.addBodyParameter("client", "android");


        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    public void uploadCid(String cid) {
        if (DatasStore.isLogin()) {
            update_cid(DatasStore.getCurMember().key, cid, new BaseModelIB() {
                @Override
                public void StartOp() {
                }

                @Override
                public void successful(Object o) {
                    LogUtils.e("保存个推id： " + (String) o);
                }

                @Override
                public void failed(ResultsModel resultsModel) {
                    LogUtils.e("保存个推id： " + resultsModel.getStringDatas());
                }
            });
        }
    }

    /**
     * 客户端首页
     *
     * @param keywords key值
     * @param curpage  页码
     * @param bib
     */
    public void index(String keywords, int curpage, final BaseModelIB bib) {
        String url = buildGetUrl("Index", "index"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "order_list"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("keywords", keywords);
        params.addBodyParameter("curpage", String.valueOf(curpage));
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    ArrayList<CateringModel> orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), new TypeToken<ArrayList<CateringModel>>() {
                    }.getType());
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 会员详细信息
     *
     * @param key key值
     * @param bib
     */
    public void member_info(String key, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "member_info"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "member_info"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", key);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    MemberModel orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), MemberModel.class);
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 修改会员详细信息
     *
     * @param key key值
     * @param bib
     */
    public void member_update(String key, String img, String member_nick, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "member_update"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "member_update"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", key);
        if (img.trim().length() > 0) {
            params.addBodyParameter("img", new Ossutil().getOssUrlAvatar(img, Ossutil.avatar));
        }
        if (member_nick.length() > 0) {
            params.addBodyParameter("member_nick", member_nick);
        }
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    MemberModel orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), MemberModel.class);
                    DatasStore.setCurMember(orderModels);
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 发布信息
     *
     * @param key           key值
     * @param member_mobile 手机号码
     * @param class_id      分类id
     *                      //     * @param province_id   省份ID
     * @param province_name 省份名称
     *                      //     * @param city_id   城市ID
     * @param city_name     城市名称
     *                      //     * @param area_id   县级ID
     * @param area_name     县级名称
     * @param address_info  详细地址
     * @param catering_desc 描述
     * @param bib
     */
    public void info_add(String key, String member_nick, String member_mobile, int class_id, String province_name, String city_name, String area_name,
                         String address_info, String catering_desc, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "info_add"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "info_add"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", key);
        params.addBodyParameter("member_nick", member_nick);
//        params.addBodyParameter("province_id", String.valueOf(province_id));
        params.addBodyParameter("class_id", String.valueOf(class_id));
        params.addBodyParameter("member_mobile", member_mobile);
        params.addBodyParameter("province_name", province_name);
//        params.addBodyParameter("city_id", String.valueOf(city_id));
//        params.addBodyParameter("area_id", String.valueOf(area_id));
        params.addBodyParameter("city_name", city_name);
        params.addBodyParameter("area_name", area_name);
        params.addBodyParameter("address_info", address_info);
        params.addBodyParameter("catering_desc", catering_desc);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    CateringModel cateringModel = ThtGosn.genGson().fromJson(rm.getJsonDatas(), CateringModel.class);
                    BIBSucessful(bib, cateringModel);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 发布类型
     *
     * @param key key值
     * @param bib
     */
    public void info_class(String key, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "info_class"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "info_class"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", key);

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    ArrayList<CateringModel> orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), new TypeToken<ArrayList<CateringModel>>() {
                    }.getType());
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 添加意见反馈
     *
     * @param bib
     */
    public void feedback_add(
            String feedback,

            final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "feedback_add"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "feedback_add 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);

        params.addBodyParameter("feedback", feedback + "");

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 会员详细信息
     *
     * @param key key值
     * @param bib
     */
    public void hx_member_info(String key, int member_id, final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "hx_member_info"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "member_info"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", key);
        params.addBodyParameter("member_id", String.valueOf(member_id));
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    MemberModel orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), MemberModel.class);
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 版本信息
     *
     * @param bib
     */
    public void mb_version(final BaseModelIB bib) {
        String url = buildGetUrl("UserHelp", "mb_version"); // 构建API地址
        if (bib == null)
            LogUtil.e("TTError", "mb_version 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("version_code", "android");

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    SystemModel sm = (ThtGosn.genGson()).fromJson(rm.getJsonDatas(), SystemModel.class);
                    BIBSucessful(bib, sm);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 修改商品价格
     *
     * @param bib
     */
    public void edit_price(
            String goods_price,
            int goods_id,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "edit_price"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "edit_price 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);

        params.addBodyParameter("goods_id", goods_id + "");
        params.addBodyParameter("goods_price", goods_price + "");
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 删除商品
     *
     * @param bib
     */
    public void delete_goods(

            int goods_id,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "delete_goods"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "delete_goods 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        params.addBodyParameter("goods_id", goods_id + "");

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }


    /**
     * 我的商品管理
     *
     * @param curpage         key值
     * @param bib
     */
    public void dishes_manage(int  curpage, final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "dishes_manage"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "dishes_manage"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("curpage", curpage+"");
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    ArrayList<GoodsModel> orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), new TypeToken<ArrayList<GoodsModel>>() {
                    }.getType());
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 添加菜品--
     *
     * @param bib
     */
    public void save_dishes(

            String dishes_ids,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "save_dishes"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "save_dishes 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        params.addBodyParameter("dishes_ids", dishes_ids + "");

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }
    /**
     * 菜品库
     *
     * @param curpage         key值
     * @param bib
     */
    public void dishes_library(int  curpage, final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "dishes_library"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "dishes_library"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("curpage", curpage+"");
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    ArrayList<DishesClassModel> orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), new TypeToken<ArrayList<DishesClassModel>>() {
                    }.getType());
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 打印订单--
     *
     * @param bib
     */
    public void print_order(

            String order_ids,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreOrder", "print_order"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "print_order 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        params.addBodyParameter("order_ids", order_ids + "");

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }
    /**
     * 打印订单--
     *
     * @param bib
     */
    public void empty_order(


            final BaseModelIB bib) {
        String url = buildGetUrl("StoreOrder", "empty_order"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "empty_order 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);

        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }

    /**
     * 订单列表
     *
     * @param curpage         key值
     * @param order_state     状态（10：未处理；20：已处理）
     * @param bib
     */
    public void order_list(int order_state,int  curpage, final BaseModelIB bib) {
        String url = buildGetUrl("StoreOrder", "order_list"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "order_list"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("curpage", curpage+"");
        params.addBodyParameter("order_state", order_state+"");
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    ArrayList<OrderModel> orderModels = ThtGosn.genGson().fromJson(rm.getJsonDatas(), new TypeToken<ArrayList<OrderModel>>() {
                    }.getType());
                    BIBSucessful(bib, orderModels);
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }
    /**
     * 订单商品数量更改
     *
     * @param bib
     */
    public void order_gn_change(
            String rec_id,
            int order_id,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreOrder", "order_gn_change"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "order_gn_change 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        params.addBodyParameter("rec_id", rec_id + "");
        params.addBodyParameter("order_id", order_id + "");
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }
    /**
     * 商品价格批量更改
     *
     * @param bib
     */
    public void goods_price_change(
            String goods_id,
            final BaseModelIB bib) {
        String url = buildGetUrl("StoreDishes", "goods_price_change"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "goods_price_change 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        params.addBodyParameter("goods_id", goods_id + "");
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }
    /**
     * 客服电话等
     *
     * @param bib
     */
    public void setting(

            final BaseModelIB bib) {
        String url = buildGetUrl("UserHelp", "setting"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "order_gn_change 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }


    /**
     * 获取店铺二维码
     *
     * @param bib
     */
    public void get_member_code(
            final BaseModelIB bib) {
        String url = buildGetUrl("MemberIndex", "get_member_code"); // 构建API地址
        if (bib == null)
            LogUtils.e("TTError", "goods_price_change 参数bib为null"); // 回调不能为空

        BIBStart(bib); // 开始bib
        RequestParams params = new RequestParams();
        params.addBodyParameter("key", DatasStore.getCurMember().key);
        (genHttpUtils()).send(HttpRequest.HttpMethod.POST, url, params, new RequestCallBack<String>() {// 开始请求
            @Override
            public void onSuccess(ResponseInfo<String> resultInfo) {
                ResultsModel rm = ResultsModel.getInstanseFromStr(resultInfo.result);
                if (rm.getState() == 1) { // 成功
                    BIBSucessful(bib, rm.getStringDatas());
                } else {// 失败
                    BIBFailed(bib, rm); // 失败标志位
                }
            }

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时")); // 访问接口失败, 可能网络原因, 或者服务器宕机等造成
            }
        });
    }


}