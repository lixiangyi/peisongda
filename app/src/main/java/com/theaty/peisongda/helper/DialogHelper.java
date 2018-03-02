package com.theaty.peisongda.helper;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import org.droidparts.util.intent.IntentFactory;

import app.AppManager;
import foundation.util.StringUtil;

/**
 * Created by wujian on 2017/3/8.
 */

public class DialogHelper {


    //提示拨打电话；
    public static void showHitTelDialod(final String store_phone) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AppManager.getAppManager().currentActivity());
        alertDialog.setTitle("提示").setMessage("确定拨打电话" + store_phone + "么?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = IntentFactory.getDialIntent(!StringUtil.isEmpty(store_phone) ? store_phone : "");
                AppManager.getAppManager().currentActivity().startActivity(intent);

            }
        }).setNegativeButton("取消", null).create().show();

    }
}
