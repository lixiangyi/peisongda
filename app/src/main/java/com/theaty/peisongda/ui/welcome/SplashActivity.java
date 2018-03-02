package com.theaty.peisongda.ui.welcome;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.theaty.peisongda.R;
import com.theaty.peisongda.system.DatasStore;
import com.theaty.peisongda.ui.MainActivity;
import com.theaty.peisongda.ui.login.activity.LoginActivity;


import butterknife.BindView;
import foundation.base.activity.BaseActivity;


public class SplashActivity extends BaseActivity {

    @BindView(R.id.splashIv)
    ImageView splashIv;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_splash);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        startWelcome();
    }

    private void startWelcome() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (DatasStore.isLogin()) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    LoginSampleHelper.getInstance().initIMKit(DatasStore.getCurMember().member_name,DatasStore.APP_KEY);
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
//                if (DatasStore.isFirstLaunch()) {
//                    startActivity(new Intent(SplashActivity.this, WelcomeActivity.class));
//                    finish();
//                }else {


                finish();

//                }
            }

        }).start();
    }


}
