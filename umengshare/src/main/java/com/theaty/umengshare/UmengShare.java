package com.theaty.umengshare;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;

/**
 * 使用本页面方法之前请在主项目的AppContext中设置分享分享平台的appKey
 */
public class UmengShare {

	// 分享的页面有qq和微博的话需要添加此方法，只能添加在Activity中
	//	@Override
	//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	//		super.onActivityResult(requestCode, resultCode, data);
	//		UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
	//
	//	}

	/**
	 * 分享无图片链接
	 */
	public static void shareUrl(Activity activity, String title, String url) {
		new ShareAction(activity).withText(title).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.TENCENT, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS).withTargetUrl(url).open();
	}

	/**
	 * 分享有图片链接
	 */
	public static void shareUrlWithImage(Activity activity, String title, String image, String url) {
		new ShareAction(activity).withText(title).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.TENCENT, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS).withMedia(new UMImage(activity, image)).withTargetUrl(url).open();
	}

	public static void shareImageOnly(Activity activity, File file, UMShareListener listener) {
		new ShareAction(activity).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.QQ,
				SHARE_MEDIA.QZONE, SHARE_MEDIA.TENCENT, SHARE_MEDIA.EMAIL, SHARE_MEDIA.SMS).withMedia(new UMImage(activity, file)).setCallback(listener).open();
	}

	public static void shareByOnePlatform(Activity activity, SHARE_MEDIA platform, File image, UMShareListener listener) {
		new ShareAction(activity).setPlatform(platform).withMedia(new UMImage(activity, image)).setCallback(listener).share();
	}
}
