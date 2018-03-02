package com.theaty.versionmanagerlibrary.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.theaty.versionmanagerlibrary.VersionManager;

import java.io.File;


/**
 * If there is no bug, then it is created by ChenFengYao on 2017/4/19,
 * otherwise, I do not know who create it either.
 */
public class InstallUtil {
    //普通安装
    public static void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT > 23) {
            File file = (new File(apkPath));
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
           String ss =  VersionManager.getAppID(context);
            Uri apkUri = FileProvider.getUriForFile(context, VersionManager.getAppID(context), file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
