package com.theaty.versionmanagerlibrary.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theaty.versionmanagerlibrary.R;
import com.theaty.versionmanagerlibrary.VersionManager;
import com.theaty.versionmanagerlibrary.model.BaseModel;
import com.theaty.versionmanagerlibrary.model.CheckVersionBean;
import com.theaty.versionmanagerlibrary.model.ResultsModel;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ControlWindowService extends Service {

    private WindowManager windowManager;
    private ViewGroup mControlWindow;
    private ViewGroup mUpdataWindow;
    private Timer mTimer;
    private android.os.Handler mHandler;
    private String url_version;
    private CheckVersionBean checkVersionBean;

    public ControlWindowService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        mHandler = new android.os.Handler(Looper.getMainLooper());
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new RefreshControlWindow(), 0, 1000);
        }
        url_version = intent.getStringExtra("url");

        getVersion();
        return null;
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    private void getVersion()  {


        new CheckVersionBean().getVersion(url_version, VersionManager.getVersionCode(this) + "", new BaseModel.BaseModelIB() {
            @Override
            public void StartOp() {

            }

            @Override
            public void successful(Object o) {
                showDialog((CheckVersionBean) o);
                checkVersionBean = (CheckVersionBean) o;
            }

            @Override
            public void failed(ResultsModel resultsModel) {
                Toast.makeText(getApplicationContext(),resultsModel.getErrorMsg(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showDialog(CheckVersionBean data) {
        switch (data.update_state) {
            //更新提示(0不提示1必须更新2提示更新)
            case 0:
                //Do nothing
                break;
            case 1:
                showAutoUpdataDialog(data);
                //安装
//                Toast.makeText(getApplicationContext(),"旧版本停用",Toast.LENGTH_SHORT);
//                Intent serviceIntent = new Intent(getApplicationContext(), DownloadService.class);
//                serviceIntent.putExtra("url",data.version_info.update_url);
//                startService(serviceIntent);
                break;
            case 2:
                showUpdataDialog(data);
                break;

        }
    }
    private void showAutoUpdataDialog(CheckVersionBean data) {
        addWindow(data);
    }
    private void showUpdataDialog(CheckVersionBean data) {
        addWindow(data);
    }



    private void addWindow(final CheckVersionBean data) {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        if (mUpdataWindow == null) {

            mUpdataWindow = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.updata_window, new FrameLayout(this));
            TextView content = (TextView) mUpdataWindow.findViewById(R.id.content_version);
            content.setText(data.version_info.update_reason);
            mUpdataWindow.findViewById(R.id.positive_version).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //安装
                    Intent serviceIntent = new Intent(getApplicationContext(), DownloadService.class);
                    serviceIntent.putExtra("url",data.version_info.update_url);
                    startService(serviceIntent);
                    removeUpdataWindow();
                }
            });
            mUpdataWindow.findViewById(R.id.go_version).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(data.version_info.update_url));
                    getApplicationContext().startActivity(intent);
                    removeUpdataWindow();
                }
            });
            if (data.update_state==1) {
                mUpdataWindow.findViewById(R.id.no_version).setVisibility(View.INVISIBLE);
            }else {
                mUpdataWindow.findViewById(R.id.no_version).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeUpdataWindow();
                    }
                });
            }
            final WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.gravity = Gravity.CENTER;
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;


            mUpdataWindow.setLayoutParams(layoutParams);
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    windowManager.addView(mUpdataWindow, layoutParams);
                }
            });
        }
    }

    private void removeUpdataWindow() {
        if (mUpdataWindow != null) {
            windowManager.removeView(mUpdataWindow);
            mUpdataWindow = null;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        removeUpdataWindow();
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private class RefreshControlWindow extends TimerTask {
        @Override
        public void run() {
            if (!isApplicationInBackground(getApplicationContext())) {
              if(checkVersionBean!=null&&checkVersionBean.update_state==1) {
                  addWindow(checkVersionBean);
                }
            } else {
                removeUpdataWindow();
            }
        }
    }


    /**
     * 检查当前程序是否处于后台，需要权限android.permission.GET_TASKS
     */
    public static boolean isApplicationInBackground(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskList = am.getRunningTasks(1);
        if (taskList != null && !taskList.isEmpty())
        {
            ComponentName topActivity = taskList.get(0).topActivity;
            if (topActivity != null && !topActivity.getPackageName().equals(context.getPackageName()))
            {
                return true;
            }
        }
        return false;
    }

}
