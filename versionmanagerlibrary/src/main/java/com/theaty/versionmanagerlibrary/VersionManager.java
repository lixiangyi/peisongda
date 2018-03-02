package com.theaty.versionmanagerlibrary;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;

import com.theaty.versionmanagerlibrary.service.ControlWindowService;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;


///**
// * Created by lixiangyi on 2017/12/4.
// */
//
public class VersionManager {
    private static VersionManager mVersionManager;
    private static MusicServiceConn musicServiceConn;
    private Activity activity;
    private  Intent intent;
    public VersionManager(Context context, String url) {
        initOKHttpUtils();
        activity = (Activity)context;
//        intent =  new Intent(context, ControlWindowService.class);
//        intent.putExtra("url",url);
//        musicServiceConn = new MusicServiceConn();
//        context.bindService(intent,musicServiceConn,BIND_AUTO_CREATE);
    }

    public static VersionManager getInstance(Context context, String url) {

        if (mVersionManager == null) {
            mVersionManager = new VersionManager(context,url);
        }
        Intent intent =  new Intent(context, ControlWindowService.class);
        intent.putExtra("url",url);
        musicServiceConn = new MusicServiceConn();
        context.startService(intent);
        return mVersionManager;
    }
    public static VersionManager getInstance() {

        return mVersionManager;
    }
    public void unregister(){
//        activity.unbindService(musicServiceConn);
//        mVersionManager=null;
    }

    static class MusicServiceConn implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // TODO Auto-generated method stub

        }

    }
    /**
     * 初始化okhttpUtils，来自张鸿洋大神
     */
    private void initOKHttpUtils() {
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)//信任所有HTTPS网站
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppID(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称信息]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * [获取应用程序版本名称code]
     *
     * @param context
     * @return 当前应用的版本名称
     */
    public static int getVersionCode(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;

        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return -1;
    }
}
