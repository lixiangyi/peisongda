package com.theaty.peisongda.system;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.multidex.MultiDex;

import com.blankj.utilcode.utils.Utils;
import com.theaty.peisongda.db.DBOpenHelper;
import com.theaty.peisongda.db.HistoryDataManager;
import com.theaty.peisongda.oss.OssManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import foundation.toast.ToastManager;
import foundation.util.DataCleanManager;
import foundation.util.MethodsCompat;
import foundation.util.ThreadUtils;

public class AppContext extends CrashReportingApplication {

    private static final String TAG = AppContext.class.getSimpleName();
    private static AppContext mContext;
    private DBOpenHelper dbOpenHelper;
    private HistoryDataManager historyDataManager;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        // 工具类初始化
        Utils.init(this);

        // 个推初始化
//        PushManager.getInstance().initialize(this);

        // 效果图比对功能
//		if (BuildConfig.DEBUG) {
//			startService(new Intent(this, ControlWindowService.class));
//

        // 友盟初始化，未申请到各平台key时请注释友盟初始化方法
        Config.DEBUG = true;
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sin2/callback";
        UMShareAPI.get(this);
        Config.isJumptoAppStore = true;

        //OSS初始化
        OssManager.initOSS();
    }


    public DBOpenHelper getDBOpenHelper() {
        if (dbOpenHelper == null) {
            dbOpenHelper = new DBOpenHelper(this, DatasStore.getUserPhone());
        }
        return dbOpenHelper;
    }

    public HistoryDataManager getHistoryDataManager() {
        if (historyDataManager == null) {
            historyDataManager = new HistoryDataManager(this, getDBOpenHelper().getReadableDatabase());
        }
        return historyDataManager;
    }

    @Override
    public String getReportUrl() {
        return null;
    }

    @Override
    public Bundle getCrashResources() {
        return null;
    }

    //dex65k限制
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static AppContext getInstance() {
        return mContext;
    }

    {
        PlatformConfig.setWeixin("wx917dcc143484d8f8", "81d2536eef25998a2fdab5988cc48ff2");
//        PlatformConfig.setSinaWeibo("1907290095", "7295ad1aaee90d7b41d77d91003527e4");
//        PlatformConfig.setQQZone("1106626240", "IyrEDIzts9j5BvrI");
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache(boolean showToast) {
        final Handler handler = showToast ? new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    ToastManager.manager.show("缓存清除成功");
                } else {
                    ToastManager.manager.show("缓存清除失败");
                }
            }
        } : null;
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    clearAppCache();
                    msg.what = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    msg.what = -1;
                }
                if (handler != null)
                    handler.sendMessage(msg);
            }
        });
    }

    /**
     * 清除app缓存
     */
    public void clearAppCache() {
        DataCleanManager.cleanDatabases(this);
        // 清除数据缓存
        DataCleanManager.cleanInternalCache(this);
        // 2.2版本才有将应用缓存转移到sd卡的功能
        if (isMethodsCompat(android.os.Build.VERSION_CODES.FROYO)) {
            DataCleanManager.cleanCustomCache(MethodsCompat
                    .getExternalCacheDir(this));
        }

    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

}
