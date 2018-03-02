package com.theaty.peisongda.ui.mine.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.theaty.peisongda.R;

import butterknife.BindView;
import foundation.base.activity.BaseActivity;

public class AboutUsActivity extends BaseActivity {

    @BindView(R.id.textView3)
    TextView textView3;

    @Override
    protected View onCreateContentView() {
        return inflateContentView(R.layout.activity_about_us);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("关于软件");
        registerBack();
        textView3.setText(getVerName(this));
    }

    public static void into(Context context) {
        Intent starter = new Intent(context, AboutUsActivity.class);
        context.startActivity(starter);
    }

    public static String getVerName(Context context) {
        String verName = "";
        try {
            verName = context.getPackageManager().
                    getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

}
