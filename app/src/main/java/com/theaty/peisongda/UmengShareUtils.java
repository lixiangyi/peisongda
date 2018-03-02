package com.theaty.peisongda;

import android.text.TextUtils;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;

import foundation.base.activity.BaseActivity;
import foundation.log.LogUtils;
import foundation.toast.ToastUtil;

/**
 * Created by Administrator on 2017/3/9.
 */

public class UmengShareUtils {

    // 请将下面代码复制到调用UmengShareUtils的Activity中
//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
//	}

    private BaseActivity mActivity;
    private ShareAction mShare;

    public UmengShareUtils(BaseActivity activity) {
        mActivity = activity;
        mShare = new ShareAction(activity);
    }

    /**
     * 分享商品
     */
//    public void shareByGoods(final CookbookModel model) {
//        if (null == model) {
//            ToastUtil.showToast("未获取到商品信息, 请稍后重试");
//            return;
//        }
//
//        new MemberModel().share_interface(0,model.cookbook_id+"" , new BaseModel.BaseModelIB() {
//            @Override
//            public void StartOp() {
//                mActivity.showLoading();
//            }
//
//            @Override
//            public void successful(Object o) {
//                mActivity.hideLoading();
//                shareUrl(model.cookbook_title, model.cookbook_desc, (String) o);
//            }
//
//            @Override
//            public void failed(ResultsModel resultsModel) {
//                mActivity.hideLoading();
//                ToastUtil.showToast(resultsModel.getErrorMsg());
//            }
//        });
//    }

    /**
     * 分享
     */
    public void share(SHARE_MEDIA p) {

                shareUrl("这是我的二维码", "快来看看吧", "http://jobs.ikepin.com/Mobile/Index/share_app",p);

    }



    /**
     * 分享app
     */
    public void shareByApp() {

        shareUrl("同业圈APP下载地址", "快来加入我们吧!", "http://jobs.ikepin.com/Mobile/Index/share_app");

    }
    /**
     * 单平台分享图片文件
     *
     * @param image
     * @param platform
     */
    public void share(String image, SHARE_MEDIA platform) {
        if (null == image) {
            ToastUtil.showToast("未获取到图片信息, 请稍后重试");
            return;
        }
        if (SHARE_MEDIA.WEIXIN == platform) {
            UMEmoji emoji = new UMEmoji(mActivity, image);
            emoji.setThumb(new UMImage(mActivity, image));
            mShare.setPlatform(platform).withMedia(emoji).setCallback(umShareListener).share();
        } else {
            mShare.setPlatform(platform).withMedia(new UMImage(mActivity, image)).setCallback(umShareListener).share();
        }
    }


    private void shareUrl(String title, String text, String url) {
        LogUtils.d("***", "url: " + url);
        if (TextUtils.isEmpty(url)) {
            ToastUtil.showToast("获取分享地址失败, 请稍后重试");
            return;
        }

        mShare.setDisplayList(

                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withTitle(title)
                .withText(text)
                .withMedia(new UMImage(mActivity,R.mipmap.logo))
                .withTargetUrl(url)
                .setCallback(umShareListener)
                .open();
    }

    private void shareUrl(String title, String text, final String url,SHARE_MEDIA platform) {
        LogUtils.d("***", "url: " + url);
        if (TextUtils.isEmpty(url)) {
            ToastUtil.showToast("获取分享地址失败, 请稍后重试");
            return;
        }

        mShare
                .setPlatform(platform)
                .withTitle(title)
                .withText(text)
                .withMedia(new UMImage(mActivity,R.mipmap.ic_launcher))
                .withTargetUrl(url)
                .setCallback(new UMShareListener() {

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        //ShareActivity.into(mActivity,url);
                        ToastUtil.showToast("已分享");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtil.showToast("分享错误: " + throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtil.showToast("取消分享");
                    }
                }).share();
    }

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA share_media) {
            ToastUtil.showToast("已分享");
        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ToastUtil.showToast("分享错误: " + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            ToastUtil.showToast("取消分享");
        }
    };

}
