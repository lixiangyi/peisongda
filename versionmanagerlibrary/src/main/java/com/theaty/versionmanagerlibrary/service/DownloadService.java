package com.theaty.versionmanagerlibrary.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.theaty.versionmanagerlibrary.VersionManager;
import com.theaty.versionmanagerlibrary.Utils.FileUtils;
import com.theaty.versionmanagerlibrary.Utils.InstallUtil;

import java.io.File;

/**
 * apk后台下载与更新
 */

public class DownloadService extends Service {
    public static final String BROADCAST_ACTION = "com.example.android.threadsample.BROADCAST";
    public static final String EXTENDED_DATA_STATUS = "com.example.android.threadsample.STATUS";
    public  String url = "http://www.aiyakeji.com/download/yonghuduan.apk";
    private long requestId;
    private IntentFilter filter;
    private BroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();

    }


    protected void downloadApk() {
        //下载前删除下载目录下的旧文件
        File oldApk = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), VersionManager.getAppID(this)+ VersionManager.getVersionCode(this)+".apk");
        if (oldApk.exists())
            oldApk.delete();

        //获取DownloadManager对象
        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));

        File apkfile = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), VersionManager.getAppID(this)+ VersionManager.getVersionCode(this)+".apk");
        request.setDestinationUri(Uri.fromFile(apkfile));

//        //设置网络下载环境，默认在WiFi和移动网络都允许下载
//        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置显示通知栏，下载完成后通知栏自动消失
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        //设置通知栏标题

        request.setTitle( VersionManager.getAppName(DownloadService.this));
        request.setDescription("应用正在下载");
        request.setAllowedOverRoaming(false);
        //获得唯一下载id
        requestId= downloadManager.enqueue(request);
        //将id放进Intent
        Intent localIntent = new Intent(BROADCAST_ACTION);
        localIntent.putExtra(EXTENDED_DATA_STATUS, requestId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        url = intent.getStringExtra("url");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long reference = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (requestId == reference) {

                    File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), VersionManager.getAppID(DownloadService.this)+
                            VersionManager.getVersionCode(DownloadService.this)+".apk");
                    if (file.exists()) {
                        FileUtils.setPermission(file.getPath());//提升读写权限,否则可能出现解析异常
                        InstallUtil.installNormal(DownloadService.this, file.getPath());
                    } else {
                        Toast.makeText(context,"下载出错！无法安装",Toast.LENGTH_SHORT).show();
                    }
                    stopSelf();
                    context.unregisterReceiver(this);
                }
            }
        };
        getApplicationContext().registerReceiver(receiver, filter);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), VersionManager.getAppID(this)+
                VersionManager.getVersionCode(this)+".apk");

        if (file.exists()) {
            FileUtils.setPermission(file.getPath());//提升读写权限,否则可能出现解析异常
            InstallUtil.installNormal(DownloadService.this, file.getPath());
            stopSelf();
        } else {
            downloadApk();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
