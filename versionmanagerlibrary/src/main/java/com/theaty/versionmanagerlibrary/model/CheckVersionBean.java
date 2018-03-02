package com.theaty.versionmanagerlibrary.model;

import android.util.Log;

import com.theaty.versionmanagerlibrary.model.GsonAdapter.ThtGosn;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by lixiangyi on 2017/12/4.
 */

public class CheckVersionBean extends BaseModel {
    public int update_state;//更新提示(0不提示1必须更新2提示更新)
    public version_info version_info;

    /**
     * 获取版本信息
     *
     * @param url         url
     * @param version_num          验证码
     * @param bib
     */
    public void getVersion(String url, String version_num,   final BaseModelIB bib) {
//        String url = buildGetUrl("member", "reset_password");//构建API地址
        if (bib == null)
            Log.e("TTError", "reset_password 参数bib为null");// 回调不能为空
        BIBStart(bib);// 开始bib

        OkHttpUtils.post().url(url)
                .addParams("version_num", version_num)
                .addParams("version_code", "Android")
                .build().execute(new StringCallback() {
            @Override
            public void onResponse(String response, int id) {
                ResultsModel rsm = ResultsModel.getInstanseFromStr(response);
                if (rsm.getState() == 1) {//成功
                    CheckVersionBean checkVersionBean = ThtGosn.genGson().fromJson(rsm.getJsonDatas(), CheckVersionBean.class);
                    BIBSucessful(bib,checkVersionBean);// 接口执行
                } else {// 失败
                    BIBFailed(bib, rsm);// 失败标志位
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                BIBFailed(bib, new ResultsModel(-999, "网络超时"));// 访问接口失败
            }
        });
    }

}
